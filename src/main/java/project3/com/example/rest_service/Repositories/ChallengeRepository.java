package project3.com.example.rest_service.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import project3.com.example.rest_service.entities.Challenge;

public interface ChallengeRepository extends JpaRepository<Challenge, Integer> {
    List<Challenge> findByUserId(Integer userId);
}