package project3.com.example.rest_service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles; // added

@SpringBootTest
@ActiveProfiles("test") // added
class RestServiceApplicationTests {

    @Test
    void contextLoads() {
    }

}