package project3.com.example.rest_service;

import java.util.List;

import org.springframework.web.bind.annotation.*;

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
