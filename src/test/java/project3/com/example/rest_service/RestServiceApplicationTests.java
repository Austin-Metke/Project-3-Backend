package project3.com.example.rest_service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class RestServiceApplicationTests {

    @MockBean
    private project3.com.example.rest_service.TypeLogsService typeLogsService;

    @Test
    void contextLoads() {
    }

}