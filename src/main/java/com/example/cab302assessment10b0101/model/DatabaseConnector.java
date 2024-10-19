package com.example.cab302assessment10b0101.model;

import com.example.cab302assessment10b0101.Utility.AlertManager;
import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * The DatabaseConnector class manages the connection to the SQLite database.
 * It follows the singleton pattern to ensure that only one connection instance
 * is created and reused throughout the application.
 */
public class DatabaseConnector {
    private static Connection instance = null;

    /**
     * Private constructor to prevent direct instantiation.
     * Initializes the database connection to the SQLite database and enables foreign keys.
     */
    private DatabaseConnector() {
        String url = "jdbc:sqlite:database.db";
        try {
            instance = DriverManager.getConnection(url);
            enableForeignKeys(instance); //enable use of foreign keys
        } catch (SQLException sqlEx) {
            AlertManager.getInstance().showAlert("Error: ", "Failed to connect the Database.", Alert.AlertType.ERROR);
        }
    }

    /**
     * Enables foreign key constraints in SQLite.
     *
     * @param connection The active database connection.
     * @throws SQLException If enabling foreign keys fails.
     */
    private void enableForeignKeys(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.execute("PRAGMA foreign_keys = ON;");
        }
    }

    /**
     * Retrieves the singleton instance of the database connection.
     * If the connection does not exist, it creates a new one.
     *
     * @return The Connection instance for the database.
     */
    public static Connection getInstance() {
        if (instance == null) {
            new DatabaseConnector();
        }
        return instance;
    }
}