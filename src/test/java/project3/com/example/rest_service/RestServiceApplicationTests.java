package project3.com.example.rest_service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@SpringBootTest
@ActiveProfiles("test")
class RestServiceApplicationTests {

    @MockitoBean
    private project3.com.example.rest_service.Services.TypeLogsService typeLogsService;

    @Test
    void contextLoads() {
    }

}