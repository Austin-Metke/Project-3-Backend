package project3.com.example.rest_service;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class LeaderboardService {

    public enum Range {
        WEEK, MONTH, SIX_MONTHS, YEAR, ALL_TIME
    }

    private final LeaderboardRepository repo;

    public LeaderboardService(LeaderboardRepository repo) {
        this.repo = repo;
    }

    public List<LeaderboardEntry> getLeaderboard(Range range, int limit) {
        var pageable = PageRequest.of(0, limit);

        return switch (range) {
            case WEEK       -> repo.weekly(pageable);
            case MONTH      -> repo.monthly(pageable);
            case SIX_MONTHS -> repo.lastSixMonths(pageable);
            case YEAR       -> repo.yearly(pageable);
            case ALL_TIME   -> repo.allTime(pageable);
        };
    }
}
