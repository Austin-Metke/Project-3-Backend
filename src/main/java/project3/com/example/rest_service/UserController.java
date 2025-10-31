package project3.com.example.rest_service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import project3.com.example.rest_service.entities.User;

/**
 * REST Controller for managing User entities.
 * Provides endpoints for CRUD operations and user login.
 */
@RestController
@RequestMapping("/auth")

public class UserController {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    // Constructor injection for UserRepository
    public UserController(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    // Fetch all users with HATEOAS links
    @GetMapping
    public CollectionModel<EntityModel<User>> all() {
        List<EntityModel<User>> users = repository.findAll().stream()
                .map(user -> EntityModel.of(user,
                        linkTo(methodOn(UserController.class).one(user.getId())).withSelfRel(),
                        linkTo(methodOn(UserController.class).all()).withRel("users")))
                .collect(Collectors.toList());

        return CollectionModel.of(users,
                linkTo(methodOn(UserController.class).all()).withSelfRel());
    }

    // Fetch a single user by ID with HATEOAS links
    @GetMapping("/{id}")
    public EntityModel<User> one(@PathVariable Integer id) {
        User user = repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id.longValue()));

        return EntityModel.of(user,
                linkTo(methodOn(UserController.class).one(user.getId())).withSelfRel(),
                linkTo(methodOn(UserController.class).all()).withRel("users"));
    }

    // Create a new user
    @PostMapping("/register")
    public ResponseEntity<?> newUser(@RequestBody User newUser) {

        if (repository.findByEmail(newUser.getEmail()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("message", "Email already registered"));
        }

        newUser.setPasswordHash(passwordEncoder.encode(newUser.getPasswordHash()));
        User savedUser = repository.save(newUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    // Update an existing user or create a new one if not found
    @PutMapping("/update/{id}")
    public User replaceUser(@RequestBody User newUser, @PathVariable Integer id) {
        return repository.findById(id).map(user -> {
            user.setName(newUser.getName());
            user.setEmail(newUser.getEmail());
            user.setPasswordHash(newUser.getPasswordHash());
            return repository.save(user);
        }).orElseGet(() -> {
            newUser.setId(id);
            return repository.save(newUser);
        });
    }

    // Delete a user by ID
    @DeleteMapping("/delete/{id}")
    public void deleteUser(@PathVariable Integer id) {
        repository.deleteById(id);
    }

    // Authenticate a user by username and password
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User loginRequest) {
        Optional<User> userOpt = repository.findByName(loginRequest.getName());

        if (userOpt.isPresent()) {
            User user = userOpt.get();

            if (passwordEncoder.matches(loginRequest.getPasswordHash(), user.getPasswordHash())) {
                return ResponseEntity.ok(user);
            }
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("message", "Invalid username or password"));
    }

}