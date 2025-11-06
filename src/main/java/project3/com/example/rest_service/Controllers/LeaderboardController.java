package project3.com.example.rest_service.Controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import project3.com.example.rest_service.LeaderboardEntry;
import project3.com.example.rest_service.Services.LeaderboardService;

@RestController
@RequestMapping("/leaderboard")
public class LeaderboardController {

    private final LeaderboardService service;

    public LeaderboardController(LeaderboardService service) {
        this.service = service;
    }

    @GetMapping
    public List<LeaderboardEntry> getLeaderboard(
            @RequestParam(defaultValue = "WEEK") LeaderboardService.Range range,
            @RequestParam(defaultValue = "10") int limit) {

        return service.getLeaderboard(range, limit);
    }
}
