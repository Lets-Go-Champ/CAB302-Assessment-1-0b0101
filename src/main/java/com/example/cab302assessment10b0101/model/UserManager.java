package com.example.cab302assessment10b0101.model;


/**
 * The UserManager class provides a singleton instance to manage user sessions
 * in the library management system. It handles operations related to the
 * currently logged-in user, including setting the current user, retrieving
 * the current user, and logging out the user.
 */
public class UserManager {
    private static UserManager instance;
    private User currentUser;

    /**
     * Private constructor to prevent direct instantiation.
     */
    private UserManager() {}

    /**
     * Singleton pattern to get the single instance of UserManager.
     * Ensures that only one instance of this class is created throughout the application.
     *
     * @return The single instance of UserManager.
     */
    public static synchronized UserManager getInstance() {
        if (instance == null) {
            instance = new UserManager();
        }
        return instance;
    }

    /**
     * Sets the current logged-in user.
     *
     * @param user The User object representing the currently logged-in user.
     */
    public void setCurrentUser(User user) { this.currentUser = user; }

    /**
     * Retrieves the current logged-in user.
     *
     * @return The User object representing the currently logged-in user, or null if no user is logged in.
     */
    public User getCurrentUser() { return currentUser; }

    /**
     * Logs out the current user by setting the current user to null.
     */
    // Log out the current user
    public void logOut() {
        this.currentUser = null;
    }
}