package project3.com.example.rest_service;

/**
 * Custom exception to handle cases where a user is not found in the database.
 */
public class UserNotFoundException extends RuntimeException {

    // Constructor that accepts a user ID and generates a custom error message
    public UserNotFoundException(Long id) {
        super("Could not find user: " + id);
    }
}