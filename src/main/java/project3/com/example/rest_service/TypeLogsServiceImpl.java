package project3.com.example.rest_service;

import org.springframework.stereotype.Service;
import project3.com.example.rest_service.entities.TypeLogs;
import java.util.List;
import java.time.OffsetDateTime;

@Service
public class TypeLogsServiceImpl implements TypeLogsService {

    @Override
    public List<TypeLogs> getAll() { return List.of(); }

    @Override
    public TypeLogs getById(Integer id) { return null; }

    @Override
    public List<TypeLogs> getByUserId(Integer userId) { return List.of(); }

    @Override
    public List<TypeLogs> getByActivityTypeId(Integer activityTypeId) { return List.of(); }

    @Override
    public TypeLogs create(Integer userId, Integer activityTypeId, OffsetDateTime occurredAt) { return null; }

    @Override
    public void delete(Integer id) { /* implement deletion logic */ }
}
