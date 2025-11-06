package project3.com.example.rest_service.Controllers;

import java.net.URI;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import project3.com.example.rest_service.ActivityLogRequest;
import project3.com.example.rest_service.Services.TypeLogsService;
import project3.com.example.rest_service.entities.TypeLogs;

@RestController
@RequestMapping("/activity-logs")
public class TypeLogsController {

    private final TypeLogsService service;

    public TypeLogsController(TypeLogsService service) {
        this.service = service;
    }

    @GetMapping
    public List<TypeLogs> all() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> one(@PathVariable Integer id) {
        TypeLogs t = service.getById(id);
        if (t == null) return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("message", "Activity log not found"));
        return ResponseEntity.ok(t);
    }

    @GetMapping("/user/{userId}")
    public List<TypeLogs> byUser(@PathVariable Integer userId) {
        return service.getByUserId(userId);
    }

    @GetMapping("/activity/{activityTypeId}")
    public List<TypeLogs> byActivity(@PathVariable Integer activityTypeId) {
        return service.getByActivityTypeId(activityTypeId);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody ActivityLogRequest req) {
        if (req.getUserId() == null || req.getActivityTypeId() == null) {
            return ResponseEntity.badRequest().body(Map.of("message", "userId and activityTypeId required"));
        }
        try {
            TypeLogs created = service.create(req.getUserId(), req.getActivityTypeId(), req.getOccurredAt());
            return ResponseEntity.created(URI.create("/activity-logs/" + created.getActivityId())).body(created);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", ex.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        TypeLogs existing = service.getById(id);
        if (existing == null) return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("message", "Activity log not found"));
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}