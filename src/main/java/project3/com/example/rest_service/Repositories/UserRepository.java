package project3.com.example.rest_service.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import project3.com.example.rest_service.entities.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByName(String name);

    Optional<User> findByEmail(String email);
}