package project3.com.example.rest_service;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(Long id) {
        super("Could not find user: " + id);
    }
}