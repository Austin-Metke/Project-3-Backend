package project3.com.example.rest_service;

import java.math.BigDecimal;

public interface LeaderboardEntry {
    Integer getUserId();
    String getName();
    Long getTotalPoints();
    BigDecimal getTotalCo2gSaved();
}
