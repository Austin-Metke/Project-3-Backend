package project3.com.example.rest_service.Controllers;

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

import project3.com.example.rest_service.UserNotFoundException;
import project3.com.example.rest_service.Repositories.UserRepository;
import project3.com.example.rest_service.dto.LoginRequestDto;
import project3.com.example.rest_service.dto.RegisterRequestDto;
import project3.com.example.rest_service.dto.UpdateUserDto;
import project3.com.example.rest_service.dto.UserDto;
import project3.com.example.rest_service.entities.User;

@RestController
@RequestMapping("/auth")
public class UserController {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }


    private UserDto toDto(User user) {
        if (user == null) {
            return null;
        }
        return new UserDto(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getGoogleID()
        );
    }

    // Fetch all users
    @GetMapping
    public CollectionModel<EntityModel<UserDto>> all() {
        List<EntityModel<UserDto>> users = repository.findAll().stream()
                .map(user -> {
                    UserDto dto = toDto(user);
                    return EntityModel.of(dto,
                            linkTo(methodOn(UserController.class).one(user.getId())).withSelfRel(),
                            linkTo(methodOn(UserController.class).all()).withRel("users"));
                })
                .collect(Collectors.toList());

        return CollectionModel.of(users,
                linkTo(methodOn(UserController.class).all()).withSelfRel());
    }

    // Fetch a single user by ID
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<UserDto>> one(@PathVariable Integer id) {
        User user = repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id.longValue()));

        UserDto dto = toDto(user);

        EntityModel<UserDto> model = EntityModel.of(dto,
                linkTo(methodOn(UserController.class).one(id)).withSelfRel(),
                linkTo(methodOn(UserController.class).all()).withRel("users"));

        return ResponseEntity.ok(model);
    }

    @PostMapping("/register")
    public ResponseEntity<?> newUser(@RequestBody RegisterRequestDto request) {

        if (request.getEmail() == null || request.getEmail().isBlank()) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", "Email is required"));
        }

        if (request.getPassword() == null || request.getPassword().isBlank()) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", "Password is required"));
        }

        if (request.getName() == null || request.getName().isBlank()) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", "Username is required"));
        }

        if (repository.findByEmail(request.getEmail()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("message", "Email already registered"));
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));

        User savedUser = repository.save(user);

        UserDto responseDto = toDto(savedUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> replaceUser(@RequestBody UpdateUserDto updateRequest,
                                         @PathVariable Integer id) {

        Optional<User> existingOpt = repository.findById(id);
        if (existingOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "User not found with id " + id));
        }

        User user = existingOpt.get();

        if (updateRequest.getName() != null && !updateRequest.getName().isBlank()) {
            user.setName(updateRequest.getName());
        }

        if (updateRequest.getEmail() != null && !updateRequest.getEmail().isBlank()) {
            user.setEmail(updateRequest.getEmail());
        }

        if (updateRequest.getPassword() != null && !updateRequest.getPassword().isBlank()) {
            user.setPasswordHash(passwordEncoder.encode(updateRequest.getPassword()));
        }

        User saved = repository.save(user);
        UserDto dto = toDto(saved);

        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Integer id) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto loginRequest) {
        if (loginRequest.getName() == null || loginRequest.getName().isBlank()
                || loginRequest.getPassword() == null || loginRequest.getPassword().isBlank()) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", "Username and password are required"));
        }

        Optional<User> userOpt = repository.findByName(loginRequest.getName());

        if (userOpt.isPresent()) {
            User user = userOpt.get();

            if (passwordEncoder.matches(loginRequest.getPassword(), user.getPasswordHash())) {
                UserDto dto = toDto(user);
                return ResponseEntity.ok(dto);  //TODO wrap in AuthResponseDto 
            }
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("message", "Invalid username or password"));
    }
}
