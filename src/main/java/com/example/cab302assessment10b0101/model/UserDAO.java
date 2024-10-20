package com.example.cab302assessment10b0101.model;

import com.example.cab302assessment10b0101.Utility.AlertManager;
import javafx.scene.control.Alert;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The UserDAO class provides data access methods for managing User records
 * in the database. It supports operations such as creating the user table,
 * inserting new users, validating user credentials, and retrieving all users.
 * This class follows the Singleton design pattern to ensure only one instance
 * exists throughout the application.
 */
public class UserDAO {

    private static UserDAO instance;
    private static Connection connection;

    /**
     * Private constructor to prevent direct instantiation.
     * Initializes the database connection.
     */
    private UserDAO() {
        connection = DatabaseConnector.getInstance();
    }

    /**
     * Singleton pattern to get the single instance of UserDAO.
     * Ensures that only one instance of this class is created throughout the application.
     *
     * @return The single instance of UserDAO.
     */
    public static synchronized UserDAO getInstance(){
        if(instance == null){
            instance = new UserDAO();
        }
        return instance;
    }

    /**
     * Creates the User table in the database if it doesn't already exist.
     * The table includes fields for the username, password, and the auto-incremented ID.
     */
    public void createTable() {
        try {
            Statement createTable = connection.createStatement();
            createTable.execute(
                    "CREATE TABLE IF NOT EXISTS Users ("
                            + "userId INTEGER PRIMARY KEY AUTOINCREMENT, "
                            + "username VARCHAR NOT NULL, "
                            + "password VARCHAR NOT NULL "
                            + ")"
            );
        } catch (SQLException ex) {
            AlertManager.getInstance().showAlert("Error: ", "Failed to create User Table.", Alert.AlertType.ERROR);
        }
    }

    /**
     * Inserts a new user record into the Users table.
     *
     * @param user The user object containing all details to be inserted.
     */
    public void insert(User user) {
        try {
            PreparedStatement insertUser = connection.prepareStatement(
                    "INSERT INTO Users (username, password) VALUES (?, ?)"
            );
            insertUser.setString(1, user.getUsername());
            insertUser.setString(2, user.getPassword());
            insertUser.execute();
        } catch (SQLException ex) {
            AlertManager.getInstance().showAlert("Error: ", "Failed to insert User into the Database.", Alert.AlertType.ERROR);
        }
    }

    /**
     * Updates the username record in the Users table.
     *
     * @param newUsername The new username of the user to be updated
     * @param userID The ID of the user
     */
    public void updateUsername(String newUsername, int userID) {
        try {
            PreparedStatement updateUsername = connection.prepareStatement(
                    "UPDATE Users SET username=? WHERE userId=?"
            );
            updateUsername.setString(1, newUsername);
            updateUsername.setInt(2, userID);
            updateUsername.execute();
        } catch (SQLException ex) {
            AlertManager.getInstance().showAlert("Error: ", "Failed to update username in the Database.", Alert.AlertType.ERROR);
        }
    }

    /**
     * Updates the password record in the Users table.
     *
     * @param newPassword The new password of the user to be updated
     * @param userID The ID of the user
     */
    public void updatePassword(String newPassword, int userID) {
        try {
            PreparedStatement updatePassword = connection.prepareStatement(
                    "UPDATE Users SET password=? WHERE userId=?"
            );
            updatePassword.setString(1, newPassword);
            updatePassword.setInt(2, userID);
            updatePassword.execute();
        } catch (SQLException ex) {
            AlertManager.getInstance().showAlert("Error: ", "Failed to update password in the Database.", Alert.AlertType.ERROR);
        }
    }

    /**
     * Validates the user's credentials against records in the Users table.
     *
     * @param username The username entered by the user.
     * @param password The password entered by the user.
     * @return A User object if credentials are valid; otherwise, null.
     */    public User validateCredentials(String username, String password) {
        try {
            String query = "SELECT * FROM Users WHERE username = ? AND password = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, password);

            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return new User(
                        rs.getInt("userId"),    // Assuming your table has a column 'userId'
                        rs.getString("username"),
                        rs.getString("password") // You might want to handle passwords more securely
                );
            } else {
                return null; // No user found
            }
        } catch (SQLException e) {
            AlertManager.getInstance().showAlert("Error: ", "Failed to validate User credentials.", Alert.AlertType.ERROR);
            return null;
        }
    }

    /**
     * Retrieves all users from the Users table.
     *
     * @return A list of User objects representing all users in the database.
     */
    public List<User> getAll() {
        List<User> user = new ArrayList<>();
        try {
            Statement getAll = connection.createStatement();
            ResultSet rs = getAll.executeQuery("SELECT * FROM Users");
            while (rs.next()) {
                user.add(
                        new User(
                                rs.getInt("userId"),
                                rs.getString("username"),
                                rs.getString("password")
                        )
                );
            }
        } catch (SQLException ex) {
            AlertManager.getInstance().showAlert("Error: ", "Failed to retrieve Users from the Database.", Alert.AlertType.ERROR);
        }
        return user;
    }
}