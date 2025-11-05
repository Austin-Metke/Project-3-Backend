package project3.com.example.rest_service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import project3.com.example.rest_service.entities.TypeLogs;

public interface LeaderboardRepository extends JpaRepository<TypeLogs, Integer> {

    @Query(value = """
        SELECT u.id   AS userId,
               u.name AS name,
               COALESCE(SUM(a.points), 0)      AS totalPoints,
               COALESCE(SUM(a.co2gSaved), 0)   AS totalCo2gSaved
        FROM User u
        JOIN TypeLogs l   ON l.user.id = u.id
        JOIN TypeActivity a ON l.activityType.id = a.id
        WHERE l.occurredAt >= date_trunc('week', now())
        GROUP BY u.id, u.name
        ORDER BY totalPoints DESC
        """,
        nativeQuery = true)
    List<LeaderboardEntry> weekly(Pageable pageable);

    @Query(value = """
        SELECT u.id   AS userId,
               u.name AS name,
               COALESCE(SUM(a.points), 0)      AS totalPoints,
               COALESCE(SUM(a.co2gSaved), 0)   AS totalCo2gSaved
        FROM User u
        JOIN TypeLogs l   ON l.user.id = u.id
        JOIN TypeActivity a ON l.activityType.id = a.id
        WHERE l.occurredAt >= date_trunc('month', now())
        GROUP BY u.id, u.name
        ORDER BY totalPoints DESC
        """,
        nativeQuery = true)
    List<LeaderboardEntry> monthly(Pageable pageable);

    @Query(value = """
        SELECT u.id   AS userId,
               u.name AS name,
               COALESCE(SUM(a.points), 0)      AS totalPoints,
               COALESCE(SUM(a.co2gSaved), 0)   AS totalCo2gSaved
        FROM User u
        JOIN TypeLogs l   ON l.user.id = u.id
        JOIN TypeActivity a ON l.activityType.id = a.id
        WHERE l.occurredAt >= (now() - interval '6 months')
        GROUP BY u.id, u.name
        ORDER BY totalPoints DESC
        """,
        nativeQuery = true)
    List<LeaderboardEntry> lastSixMonths(Pageable pageable);

    @Query(value = """
        SELECT u.id   AS userId,
               u.name AS name,
               COALESCE(SUM(a.points), 0)      AS totalPoints,
               COALESCE(SUM(a.co2gSaved), 0)   AS totalCo2gSaved
        FROM User u
        JOIN TypeLogs l   ON l.user.id = u.id
        JOIN TypeActivity a ON l.activityType.id = a.id
        WHERE l.occurredAt >= date_trunc('year', now())
        GROUP BY u.id, u.name
        ORDER BY totalPoints DESC
        """,
        nativeQuery = true)
    List<LeaderboardEntry> yearly(Pageable pageable);

    @Query(value = """
        SELECT u.id   AS userId,
               u.name AS name,
               COALESCE(SUM(a.points), 0)      AS totalPoints,
               COALESCE(SUM(a.co2gSaved), 0)   AS totalCo2gSaved
        FROM User u
        JOIN TypeLogs l   ON l.user.id = u.id
        JOIN TypeActivity a ON l.activityType.id = a.id
        GROUP BY u.id, u.name
        ORDER BY totalPoints DESC
        """,
        nativeQuery = true)
    List<LeaderboardEntry> allTime(Pageable pageable);
}
