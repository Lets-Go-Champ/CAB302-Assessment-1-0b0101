package com.example.cab302assessment10b0101.model;

import java.util.ArrayList;
import java.util.List;

/**
 * The MockUserDAO class is a mock implementation of the UserDAOInterface.
 * It is used to imitate the behavior of the actual UserDAO in unit tests,
 * specifically in UserDAOTest. It stores User objects in-memory using a List
 * and provides methods to simulate database operations such as insert, update,
 * delete, and retrieve users.
 */
public class MockUserDAO implements UserDAOInterface {

    private List<User> users = new ArrayList<>();
    private int currentId = 1;  // Simulate auto-incrementing ID

    /**
     * Inserts a new user into the mock data store.
     * Simulates the behavior of a real database by assigning an auto-incremented ID.
     *
     * @param user The User object to be inserted.
     */
    @Override
    public void insert(User user) {
        user.setId(currentId++);  // Set an auto-incremented ID
        users.add(user);
    }

    /**
     * Updates an existing user's details in the mock data store.
     *
     * @param user The User object with updated details.
     */
    @Override
    public void update(User user) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId() == user.getId()) {
                users.set(i, user);  // Replace the user in the list
                break;
            }
        }
    }

    /**
     * Deletes a user from the mock data store by ID.
     *
     * @param id The ID of the user to be deleted.
     */
    @Override
    public void delete(int id) {
        users.removeIf(user -> user.getId() == id);  // Remove user by ID
    }

    /**
     * Retrieves all users from the mock data store.
     *
     * @return A list of User objects representing all users.
     */
    @Override
    public List<User> getAll() {
        return new ArrayList<>(users);  // Return a copy of the list
    }

    /**
     * Retrieves a user by their ID from the mock data store.
     *
     * @param id The ID of the user to be retrieved.
     * @return The User object if found, otherwise null.
     */
    @Override
    public User getById(int id) {
        return users.stream()
                .filter(user -> user.getId() == id)
                .findFirst()
                .orElse(null);  // Find user by ID
    }


    /**
     * Validates user credentials by searching for a user with the matching
     * username and password in the mock data store.
     *
     * @param username The username to validate.
     * @param password The password to validate.
     * @return The User object if credentials are valid, otherwise null.
     */
    @Override
    public User validateCredentials(String username, String password) {
        return users.stream()
                .filter(user -> user.getUsername().equals(username) && user.getPassword().equals(password))
                .findFirst()
                .orElse(null);
    }
}

