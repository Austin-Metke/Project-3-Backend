package project3.com.example.rest_service.Services;

import java.util.List;

import project3.com.example.rest_service.entities.TypeActivity;

public interface TypeActivityService {
    List<TypeActivity> getAllActivityTypes();
    TypeActivity getById(Integer id);
}