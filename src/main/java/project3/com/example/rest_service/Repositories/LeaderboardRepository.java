package project3.com.example.rest_service.Repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import project3.com.example.rest_service.LeaderboardEntry;
import project3.com.example.rest_service.entities.TypeLogs;

public interface LeaderboardRepository extends JpaRepository<TypeLogs, Integer> {

    @Query(value = """
        SELECT u.user_id AS userId,
               u.name    AS name,
               COALESCE(SUM(at.points), 0)     AS totalPoints,
               COALESCE(SUM(at.co2g_saved), 0) AS totalCo2gSaved
        FROM users u
        JOIN activity_logs al   ON al.user_id = u.user_id
        JOIN activity_types at  ON at.activity_type_id = al.activity_type_id
        WHERE (:start IS NULL OR al.occurred_at >= :start)
        GROUP BY u.user_id, u.name
        ORDER BY totalPoints DESC
        LIMIT :limit
        """,
        nativeQuery = true)
    List<LeaderboardEntry> leaderboardSince(
            @Param("start") LocalDateTime start,
            @Param("limit") int limit
    );
}
