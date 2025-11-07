package project3.com.example.rest_service.Controllers;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import project3.com.example.rest_service.entities.Challenge;
import project3.com.example.rest_service.Repositories.ChallengeRepository;

@RestController
@RequestMapping("/challenges")
public class ChallengeController {

    private final ChallengeRepository repo;

    @Autowired
    public ChallengeController(ChallengeRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<Challenge> listAll() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Challenge> getById(@PathVariable Integer id) {
        return repo.findById(id)
                .map(ch -> ResponseEntity.ok(ch))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}")
    public List<Challenge> getByUser(@PathVariable Integer userId) {
        return repo.findByUserId(userId);
    }

    @PostMapping
    public ResponseEntity<Challenge> create(@RequestBody Challenge challenge) {
        Challenge saved = repo.save(challenge);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Challenge> update(@PathVariable Integer id, @RequestBody Challenge incoming) {
        return repo.findById(id)
                .map(existing -> {
                    existing.setName(incoming.getName());
                    existing.setDescription(incoming.getDescription());
                    existing.setPoints(incoming.getPoints());
                    existing.setIsCompleted(incoming.getIsCompleted());
                    existing.setUserId(incoming.getUserId());
                    repo.save(existing);
                    return ResponseEntity.ok(existing);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        return repo.findById(id)
                .map(ch -> {
                    repo.deleteById(id);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}