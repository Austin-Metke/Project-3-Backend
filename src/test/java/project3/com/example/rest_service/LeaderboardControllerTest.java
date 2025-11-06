package project3.com.example.rest_service;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.transaction.annotation.Transactional;

import project3.com.example.rest_service.Repositories.TypeActivityRepository;
import project3.com.example.rest_service.Repositories.TypeLogsRepository;
import project3.com.example.rest_service.Repositories.UserRepository;
import project3.com.example.rest_service.entities.TypeActivity;
import project3.com.example.rest_service.entities.TypeLogs;
import project3.com.example.rest_service.entities.User;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
class LeaderboardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TypeActivityRepository activityRepo;

    @Autowired
    private TypeLogsRepository logsRepo;

    @Autowired
    private UserRepository userRepo;

    @BeforeEach
    void setup() {
        // clear data
        logsRepo.deleteAll();
        activityRepo.deleteAll();
        userRepo.deleteAll();

        // create sample users
        var u1 = new User("Alice", "alice@example.com", "hash1");
        var u2 = new User("Bob", "bob@example.com", "hash2");
        userRepo.save(u1);
        userRepo.save(u2);

        // create sample activity types
        var a1 = new TypeActivity();
        a1.setName("Recycling");
        a1.setPoints(20);
        a1.setCo2gSaved(BigDecimal.valueOf(100.0));
        activityRepo.save(a1);

        var a2 = new TypeActivity();
        a2.setName("Walking");
        a2.setPoints(10);
        a2.setCo2gSaved(BigDecimal.valueOf(50.0));
        activityRepo.save(a2);

        // log some activities
        var l1 = new TypeLogs(u1, a1);
        var l2 = new TypeLogs(u1, a2);
        var l3 = new TypeLogs(u2, a2);
        logsRepo.save(l1);
        logsRepo.save(l2);
        logsRepo.save(l3);
    }

    @Test
    void allTimeLeaderboard_returnsTopUsers() throws Exception {
        mockMvc.perform(get("/leaderboard")
                        .param("range", "ALL_TIME")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThan(0))))
                .andExpect(jsonPath("$[0].name", is("Alice")))
                .andExpect(jsonPath("$[0].totalPoints", greaterThanOrEqualTo(30)))
                .andExpect(jsonPath("$[1].name", is("Bob")));
    }
}
