package project3.com.example.rest_service.Controllers;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import project3.com.example.rest_service.Repositories.TypeActivityRepository;
import project3.com.example.rest_service.entities.TypeActivity;

@RestController
@RequestMapping("/activities")
public class TypeActivityController {

    private final TypeActivityRepository repository;

    public TypeActivityController(TypeActivityRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<TypeActivity> all() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> one(@PathVariable Integer id) {
        Optional<TypeActivity> t = repository.findById(id);
        if (t.isPresent()) {
            return ResponseEntity.ok(t.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "Activity type not found"));
        }
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody TypeActivity newType) {
        if (newType.getName() == null || newType.getName().isBlank()) {
            return ResponseEntity.badRequest().body(Map.of("message", "Name is required"));
        }
        TypeActivity existing = repository.findByName(newType.getName());
        if (existing != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("message", "Activity type already exists"));
        }
        TypeActivity saved = repository.save(newType);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> replace(@RequestBody TypeActivity newType, @PathVariable Integer id) {
        TypeActivity saved = repository.findById(id)
                .map(t -> {
                    t.setName(newType.getName());
                    t.setPoints(newType.getPoints());
                    t.setCo2gSaved(newType.getCo2gSaved());
                    return repository.save(t);
                })
                .orElseGet(() -> {
                    newType.setId(id);
                    return repository.save(newType);
                });
        return ResponseEntity.ok(saved);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        if (!repository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "Activity type not found"));
        }
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
