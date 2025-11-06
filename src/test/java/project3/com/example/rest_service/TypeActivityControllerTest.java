package project3.com.example.rest_service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import project3.com.example.rest_service.Controllers.TypeActivityController;
import project3.com.example.rest_service.Repositories.TypeActivityRepository;
import project3.com.example.rest_service.entities.TypeActivity;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TypeActivityController.class)
@AutoConfigureMockMvc(addFilters = false)
class TypeActivityControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    TypeActivityRepository repository;

    @Autowired
    ObjectMapper mapper;

    @Test
    void getAllReturnsList() throws Exception {
        TypeActivity t = new TypeActivity();
        t.setId(1);
        t.setName("Cycling");
        t.setPoints(10);
        t.setCo2gSaved(BigDecimal.valueOf(50));

        Mockito.when(repository.findAll()).thenReturn(List.of(t));

        mockMvc.perform(get("/activities"))
                .andExpect(status().isOk());
    }

    @Test
    void getByIdNotFoundReturns404() throws Exception {
        Mockito.when(repository.findById(99)).thenReturn(Optional.empty());

        mockMvc.perform(get("/activities/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void createConflictWhenNameExists() throws Exception {
        TypeActivity existing = new TypeActivity();
        existing.setId(2);
        existing.setName("Walking");

        Mockito.when(repository.findByName("Walking")).thenReturn(existing);

        TypeActivity payload = new TypeActivity();
        payload.setName("Walking");
        payload.setPoints(1);
        payload.setCo2gSaved(BigDecimal.valueOf(1));

        mockMvc.perform(post("/activities")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(payload)))
                .andExpect(status().isConflict());
    }

    @Test
    void deleteNotFoundReturns404() throws Exception {
        Mockito.when(repository.existsById(123)).thenReturn(false);

        mockMvc.perform(delete("/activities/123"))
                .andExpect(status().isNotFound());
    }
}