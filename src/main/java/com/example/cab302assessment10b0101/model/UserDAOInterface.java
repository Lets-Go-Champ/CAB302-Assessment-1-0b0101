package com.example.cab302assessment10b0101.model;

import java.util.List;

/**
 * The UserDAOInterface defines the contract for the data access operations
 * related to User objects. It is used with MockUserDAO to imitate the
 * behavior of UserDAO during testing.
 */
public interface UserDAOInterface {

    /**
     * Inserts a new user into the mock data store.
     *
     * @param user The User object to be inserted.
     */
    void insert(User user);

    /**
     * Updates the username of an existing user.
     *
     * @param newUsername The new username to set.
     * @param userId The ID of the user to update.
     */
    void updateUsername(String newUsername, int userId);

    /**
     * Updates the password of an existing user.
     *
     * @param newPassword The new password to set.
     * @param userId The ID of the user to update.
     */
    void updatePassword(String newPassword, int userId);


    /**
     * Retrieves all users from the mock data store.
     *
     * @return A list of all users.
     */
    List<User> getAll();

    /**
     * Retrieves a user from the mock data store based on their ID.
     *
     * @param id The ID of the user to retrieve.
     * @return The User object matching the provided ID, or null if not found.
     */
    User getById(int id);

    /**
     * Validates the user's credentials by checking the username and password.
     *
     * @param username The username to validate.
     * @param password The password to validate.
     * @return The User object if credentials are valid, or null if invalid.
     */
    User validateCredentials(String username, String password);
}