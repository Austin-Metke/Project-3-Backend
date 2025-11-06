package project3.com.example.rest_service;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.containsString;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;

import project3.com.example.rest_service.Controllers.UserController;
import project3.com.example.rest_service.Repositories.UserRepository;
import project3.com.example.rest_service.entities.User;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
class UserControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper om;

    @MockitoBean
    UserRepository repository;

    @MockitoBean
    PasswordEncoder passwordEncoder;

    private User user(Integer id, String name, String email, String pwHash) {
        User u = new User(name, email, pwHash);
        u.setId(id);
        return u;
    }

    // ---------- GET /auth (all) ----------
    @Test
    @DisplayName("GET /auth -> 200 and returns collection (HATEOAS)")
    void getAllUsers() throws Exception {
        var u1 = user(1, "Alice", "alice@example.com", "x");
        var u2 = user(2, "Bob", "bob@example.com", "y");
        given(repository.findAll()).willReturn(List.of(u1, u2));

        mvc.perform(get("/auth").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists());
    }

    // ---------- GET /auth/{id} ----------
    @Test
    @DisplayName("GET /auth/{id} -> 200 with user DTO payload")
    void getOneUser() throws Exception {
        var u = user(10, "Carol", "carol@example.com", "hash");
        given(repository.findById(10)).willReturn(Optional.of(u));

        mvc.perform(get("/auth/10").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                // EntityModel<UserDto> => DTO fields at root + _links
                .andExpect(jsonPath("$.id").value(10))
                .andExpect(jsonPath("$.name").value("Carol"))
                .andExpect(jsonPath("$.email").value("carol@example.com"))
                .andExpect(jsonPath("$._links").exists());
    }

    // ---------- POST /auth/register ----------
    @Test
    @DisplayName("POST /auth/register -> 409 when email already exists")
    void register_conflictOnDuplicateEmail() throws Exception {
        var existing = user(1, "X", "dup@example.com", "h");
        given(repository.findByEmail("dup@example.com"))
                .willReturn(Optional.of(existing));

        // RegisterRequestDto: name, email, password
        String json = """
                {
                  "name": "Dan",
                  "email": "dup@example.com",
                  "password": "plainpw"
                }
                """;

        mvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message", containsString("Email")));
    }

    @Test
    @DisplayName("POST /auth/register -> 201; password encoded; user saved; DTO returned")
    void register_createsAndEncodesPassword() throws Exception {
        var saved = user(99, "Erin", "erin@example.com", "ENCODED");

        given(repository.findByEmail("erin@example.com")).willReturn(Optional.empty());
        given(passwordEncoder.encode("plainpw")).willReturn("ENCODED");
        given(repository.save(any(User.class))).willReturn(saved);

        String json = """
                {
                  "name": "Erin",
                  "email": "erin@example.com",
                  "password": "plainpw"
                }
                """;

        mvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(99))
                .andExpect(jsonPath("$.email").value("erin@example.com"))
                .andExpect(jsonPath("$.name").value("Erin"))
                // DTO must not expose password or passwordHash
                .andExpect(jsonPath("$.passwordHash").doesNotExist())
                .andExpect(jsonPath("$.password").doesNotExist());

        // ensure encoding happened
        verify(passwordEncoder).encode("plainpw");

        var captor = ArgumentCaptor.forClass(User.class);
        verify(repository).save(captor.capture());
        org.junit.jupiter.api.Assertions.assertEquals(
                "ENCODED", captor.getValue().getPasswordHash());
    }

    // ---------- PUT /auth/update/{id} ----------
    @Test
    @DisplayName("PUT /auth/update/{id} -> 200 updates existing user")
    void update_existingUser() throws Exception {
        var existing = user(5, "Frank", "frank@old.com", "OLD_HASH");
        var saved = user(5, "Frank New", "frank@new.com", "NEW_HASH");

        given(repository.findById(5)).willReturn(Optional.of(existing));
        given(passwordEncoder.encode("newpw")).willReturn("NEW_HASH");
        given(repository.save(any(User.class))).willReturn(saved);

        // UpdateUserDto: name, email, password (all optional)
        String json = """
                {
                  "name": "Frank New",
                  "email": "frank@new.com",
                  "password": "newpw"
                }
                """;

        mvc.perform(put("/auth/update/5")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(5))
                .andExpect(jsonPath("$.email").value("frank@new.com"))
                .andExpect(jsonPath("$.name").value("Frank New"));

        verify(passwordEncoder).encode("newpw");
    }

    @Test
    @DisplayName("PUT /auth/update/{id} -> 404 when user not found")
    void update_notFound() throws Exception {
        given(repository.findById(77)).willReturn(Optional.empty());

        String json = """
                {
                  "name": "Gina",
                  "email": "gina@example.com",
                  "password": "pw"
                }
                """;

        mvc.perform(put("/auth/update/77")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", containsString("User not found")));

        verify(repository, never()).save(any(User.class));
    }

    // ---------- DELETE /auth/delete/{id} ----------
    @Test
    @DisplayName("DELETE /auth/delete/{id} -> 204 and repository.deleteById called when exists")
    void delete_deletesById() throws Exception {
        given(repository.existsById(42)).willReturn(true);

        mvc.perform(delete("/auth/delete/42"))
                .andExpect(status().isNoContent());

        verify(repository).deleteById(42);
    }

    @Test
    @DisplayName("DELETE /auth/delete/{id} -> 404 when user not found")
    void delete_notFound() throws Exception {
        given(repository.existsById(99)).willReturn(false);

        mvc.perform(delete("/auth/delete/99"))
                .andExpect(status().isNotFound());

        verify(repository, never()).deleteById(99);
    }

    // ---------- POST /auth/login ----------
    @Test
    @DisplayName("POST /auth/login -> 200 (matches) returns user DTO")
    void login_success() throws Exception {
        // Stored user has ENCODED password in passwordHash
        var stored = user(12, "Henry", "henry@example.com", "ENC");

        given(repository.findByName("Henry")).willReturn(Optional.of(stored));
        given(passwordEncoder.matches("plain", "ENC")).willReturn(true);

        // LoginRequestDto: name + password
        String json = """
                {"name":"Henry","password":"plain"}
                """;

        mvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(12))
                .andExpect(jsonPath("$.name").value("Henry"))
                .andExpect(jsonPath("$.email").value("henry@example.com"))
                .andExpect(jsonPath("$.password").doesNotExist())
                .andExpect(jsonPath("$.passwordHash").doesNotExist());
    }

    @Test
    @DisplayName("POST /auth/login -> 401 when user not found")
    void login_unauthorized_userNotFound() throws Exception {
        given(repository.findByName("Ivy")).willReturn(Optional.empty());

        String json = """
                {"name":"Ivy","password":"plain"}
                """;

        mvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.message", containsString("Invalid")));
    }

    @Test
    @DisplayName("POST /auth/login -> 400 when missing username or password")
    void login_badRequest_missingFields() throws Exception {
        // Missing password
        String json = """
                {"name":"NoPassword"}
                """;

        mvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", containsString("Username and password are required")));
    }
}
