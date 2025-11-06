package project3.com.example.rest_service.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import project3.com.example.rest_service.entities.TypeLogs;

public interface TypeLogsRepository extends JpaRepository<TypeLogs, Integer> {
    List<TypeLogs> findByUser_Id(Integer userId);
    List<TypeLogs> findByActivityType_Id(Integer activityTypeId);
}