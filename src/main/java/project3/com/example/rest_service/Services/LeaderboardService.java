package project3.com.example.rest_service.Services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import project3.com.example.rest_service.LeaderboardEntry;
import project3.com.example.rest_service.Repositories.LeaderboardRepository;

@Service
public class LeaderboardService {

    public enum Range {
        WEEK,
        MONTH,
        SIX_MONTHS,
        YEAR,
        ALL_TIME
    }

    private final LeaderboardRepository repo;

    public LeaderboardService(LeaderboardRepository repo) {
        this.repo = repo;
    }

    public List<LeaderboardEntry> getLeaderboard(Range range, int limit) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime start = switch (range) {
            case WEEK       -> now.minusWeeks(1);
            case MONTH      -> now.minusMonths(1);
            case SIX_MONTHS -> now.minusMonths(6);
            case YEAR       -> now.minusYears(1);
            case ALL_TIME   -> null;  // no lower bound
        };

        return repo.leaderboardSince(start, limit);
    }
}
