package project3.com.example.rest_service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import project3.com.example.rest_service.Controllers.ChallengeController;
import project3.com.example.rest_service.Repositories.ChallengeRepository;
import project3.com.example.rest_service.entities.Challenge;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ChallengeController.class)
@AutoConfigureMockMvc(addFilters = false)
class ChallengeControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    ChallengeRepository repo;

    @Test
    void listAll_returnsAllChallenges() throws Exception {
        Challenge c1 = new Challenge(1, "A", "desc", 10, false, 5);
        Challenge c2 = new Challenge(2, "B", "desc2", 20, true, 6);
        when(repo.findAll()).thenReturn(List.of(c1, c2));

        mockMvc.perform(get("/challenges"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(List.of(c1, c2))));
    }

    @Test
    void getById_found_returnsChallenge() throws Exception {
        Challenge c = new Challenge(1, "A", "desc", 10, false, 5);
        when(repo.findById(1)).thenReturn(Optional.of(c));

        mockMvc.perform(get("/challenges/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(c)));
    }

    @Test
    void getById_notFound_returns404() throws Exception {
        when(repo.findById(99)).thenReturn(Optional.empty());

        mockMvc.perform(get("/challenges/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getByUser_returnsUserChallenges() throws Exception {
        Challenge c1 = new Challenge(1, "A", "desc", 10, false, 5);
        when(repo.findByUserId(5)).thenReturn(List.of(c1));

        mockMvc.perform(get("/challenges/user/5"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(List.of(c1))));
    }

    @Test
    void create_returnsCreatedChallenge() throws Exception {
        Challenge incoming = new Challenge(null, "New", "d", 15, false, 7);
        Challenge saved = new Challenge(10, "New", "d", 15, false, 7);

        when(repo.save(any(Challenge.class))).thenReturn(saved);

        mockMvc.perform(post("/challenges")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(incoming)))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(saved)));
    }

    @Test
    void update_found_updatesAndReturns() throws Exception {
        Challenge existing = new Challenge(1, "Old", "old", 5, false, 2);
        Challenge incoming = new Challenge(null, "Updated", "new", 50, true, 2);

        when(repo.findById(1)).thenReturn(Optional.of(existing));
        when(repo.save(any(Challenge.class))).thenAnswer(inv -> inv.getArgument(0));

        mockMvc.perform(put("/challenges/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(incoming)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated"))
                .andExpect(jsonPath("$.description").value("new"))
                .andExpect(jsonPath("$.points").value(50))
                .andExpect(jsonPath("$.isCompleted").value(true))
                .andExpect(jsonPath("$.userId").value(2));

        ArgumentCaptor<Challenge> captor = ArgumentCaptor.forClass(Challenge.class);
        verify(repo).save(captor.capture());
        // saved entity should contain the updated values and original id
        assert captor.getValue().getId().equals(1);
    }

    @Test
    void update_notFound_returns404() throws Exception {
        when(repo.findById(999)).thenReturn(Optional.empty());
        Challenge incoming = new Challenge(null, "X", "Y", 1, false, 1);

        mockMvc.perform(put("/challenges/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(incoming)))
                .andExpect(status().isNotFound());
    }

    @Test
    void delete_found_returnsNoContent() throws Exception {
        Challenge existing = new Challenge(1, "A", "d", 10, false, 1);
        when(repo.findById(1)).thenReturn(Optional.of(existing));
        doNothing().when(repo).deleteById(1);

        mockMvc.perform(delete("/challenges/1"))
                .andExpect(status().isNoContent());

        verify(repo).deleteById(1);
    }

    @Test
    void delete_notFound_returns404() throws Exception {
        when(repo.findById(42)).thenReturn(Optional.empty());

        mockMvc.perform(delete("/challenges/42"))
                .andExpect(status().isNotFound());

        verify(repo, never()).deleteById(anyInt());
    }
}