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

import project3.com.example.rest_service.Controllers.TypeLogsController;
import project3.com.example.rest_service.Services.TypeLogsService;
import project3.com.example.rest_service.entities.TypeLogs;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TypeLogsController.class)
@AutoConfigureMockMvc(addFilters = false)
class TypeLogsControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    TypeLogsService service;

    @Autowired
    ObjectMapper mapper;

    @Test
    void getAllReturnsEmptyList() throws Exception {
        Mockito.when(service.getAll()).thenReturn(List.of());

        mockMvc.perform(get("/activity-logs"))
                .andExpect(status().isOk());
    }

    @Test
    void getByIdNotFoundReturns404() throws Exception {
        Mockito.when(service.getById(1)).thenReturn(null);

        mockMvc.perform(get("/activity-logs/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void createBadRequestWhenMissingFields() throws Exception {
        // missing userId and activityTypeId
        String payload = "{}";

        mockMvc.perform(post("/activity-logs")
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createSuccessReturnsCreated() throws Exception {
        // avoid reflective access to fields that may not exist in the entity
        TypeLogs created = new TypeLogs();

        Mockito.when(service.create(eq(2), eq(2), any())).thenReturn(created);

        String payload = "{\"userId\":2,\"activityTypeId\":2,\"occurredAt\":\"2025-01-01T00:00:00Z\"}";

        mockMvc.perform(post("/activity-logs")
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }

    @Test
    void deleteNotFoundReturns404() throws Exception {
        Mockito.when(service.getById(99)).thenReturn(null);

        mockMvc.perform(delete("/activity-logs/99"))
                .andExpect(status().isNotFound());
    }
}