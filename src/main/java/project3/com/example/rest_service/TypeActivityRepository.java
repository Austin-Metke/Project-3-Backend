package project3.com.example.rest_service;

import org.springframework.data.jpa.repository.JpaRepository;
import project3.com.example.rest_service.entities.TypeActivity;

public interface TypeActivityRepository extends JpaRepository<TypeActivity, Integer> {
    // Additional query methods (if needed) can be defined here
    TypeActivity findByName(String name);
}
