package project3.com.example.rest_service;

import org.springframework.stereotype.Service;
import project3.com.example.rest_service.entities.TypeLogs;
import project3.com.example.rest_service.entities.User;
import project3.com.example.rest_service.entities.TypeActivity;

import java.util.List;
import java.time.OffsetDateTime;
import java.util.Optional;

@Service
public class TypeLogsServiceImpl implements TypeLogsService {

    private final TypeLogsRepository typeLogsRepository;
    private final UserRepository userRepository;
    private final TypeActivityRepository typeActivityRepository;

    public TypeLogsServiceImpl(TypeLogsRepository typeLogsRepository,
            UserRepository userRepository,
            TypeActivityRepository typeActivityRepository) {
        this.typeLogsRepository = typeLogsRepository;
        this.userRepository = userRepository;
        this.typeActivityRepository = typeActivityRepository;
    }

    @Override
    public List<TypeLogs> getAll() {
        return typeLogsRepository.findAll();
    }

    @Override
    public TypeLogs getById(Integer id) {
        return typeLogsRepository.findById(id).orElse(null);
    }

    @Override
    public List<TypeLogs> getByUserId(Integer userId) {
        return typeLogsRepository.findByUser_Id(userId);
    }

    @Override
    public List<TypeLogs> getByActivityTypeId(Integer activityTypeId) {
        return typeLogsRepository.findByActivityType_Id(activityTypeId);
    }

    @Override
    public TypeLogs create(Integer userId, Integer activityTypeId, OffsetDateTime occurredAt) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) {
            throw new IllegalArgumentException("User not found: " + userId);
        }
        Optional<TypeActivity> typeOpt = typeActivityRepository.findById(activityTypeId);
        if (typeOpt.isEmpty()) {
            throw new IllegalArgumentException("Activity type not found: " + activityTypeId);
        }

        TypeLogs tl = new TypeLogs(userOpt.get(), typeOpt.get());
        if (occurredAt != null)
            tl.setOccurredAt(occurredAt);
        return typeLogsRepository.save(tl);
    }

    @Override
    public void delete(Integer id) {
        typeLogsRepository.deleteById(id);
    }
}
