package project3.com.example.rest_service.Services;

import java.util.List;
import project3.com.example.rest_service.entities.TypeLogs;

public interface TypeLogsService {
    List<TypeLogs> getAll();
    TypeLogs getById(Integer id);
    List<TypeLogs> getByUserId(Integer userId);
    List<TypeLogs> getByActivityTypeId(Integer activityTypeId);
    TypeLogs create(Integer userId, Integer activityTypeId, java.time.OffsetDateTime occurredAt);

    default List<String> findAll() {
        return List.of();
    }

    void delete(Integer id);
}