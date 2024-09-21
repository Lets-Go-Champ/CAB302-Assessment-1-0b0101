package com.example.cab302assessment10b0101.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * The DatabaseConnector class manages the connection to the SQLite database.
 * It follows the singleton pattern to ensure that only one connection instance
 * is created and reused throughout the application.
 */
public class DatabaseConnector {
    private static Connection instance = null;

    /**
     * Private constructor to prevent direct instantiation.
     * Initialises the database connection to the SQLite database.
     */
    private DatabaseConnector() {
        String url = "jdbc:sqlite:database.db";
        try {
            instance = DriverManager.getConnection(url);
        } catch (SQLException sqlEx) {
            System.err.println(sqlEx);
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