package com.example.cab302assessment10b0101.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The CollectionDAO class provides data access methods for managing
 * Collection records in the database. It includes operations to create the
 * Collections table, insert new collections, and retrieve collections.
 * This class follows the Singleton pattern to ensure only one instance is used.
 */
public class CollectionDAO {
    private static CollectionDAO instance;
    private static Connection connection;

    /**
     * Private constructor to prevent direct instantiation.
     * Initializes the database connection.
     */
    private CollectionDAO() {
        connection = DatabaseConnector.getInstance();
    }

    /**
     * Returns the singleton instance of CollectionDAO.
     * Ensures only one instance exists in the application.
     *
     * @return The single instance of CollectionDAO.
     */
    public static synchronized CollectionDAO getInstance(){
        if(instance == null){
            instance = new CollectionDAO();
        }
        return instance;
    }

    /**
     * Creates the Collections table in the database if it doesn't already exist.
     * The table includes fields for collectionId (auto-incremented), userId, collectionName, and collectionDescription.
     */
    public void createTable() {
        try {
            Statement createTable = connection.createStatement();
            createTable.execute(
                    "CREATE TABLE IF NOT EXISTS Collections (" +
                            "collectionId INTEGER PRIMARY KEY AUTOINCREMENT," + // Auto-increment the ID
                            "userId INTEGER," +
                            "collectionName TEXT NOT NULL," +
                            "collectionDescription TEXT," + // Description can now be optional
                            "FOREIGN KEY (userId) REFERENCES Users(userId)" +
                            ");"
            );
        } catch (SQLException ex) {
            System.out.println("Error creating collection table: " + ex.getMessage());
        }
    }

    /**
     * Inserts a new collection into the Collections table.
     *
     * @param collection The Collection object to be inserted.
     */
    public void insert(Collection collection) {
        try {
            PreparedStatement insertCollection = connection.prepareStatement(
                    "INSERT INTO Collections (userId, collectionName, collectionDescription) " + // No need to insert the id
                            "VALUES (?, ?, ?);"
            );
            insertCollection.setInt(1, UserManager.getInstance().getCurrentUser().getId());
            insertCollection.setString(2, collection.getCollectionName());
            insertCollection.setString(3, collection.getCollectionDescription());
            insertCollection.execute();
        } catch (SQLException ex) {
            System.out.println("Error inserting into collection table: " + ex.getMessage());
        }
    }

    /**
     * Retrieves all collections from the Collections table.
     *
     * @return A list of all Collection objects from the database.
     */
    public List<Collection> getAll() {
        List<Collection> collections = new ArrayList<>();
        try {
            Statement getAll = connection.createStatement();
            ResultSet rs = getAll.executeQuery("SELECT * FROM Collections");
            while (rs.next()) {
                collections.add(
                        new Collection(
                                rs.getInt("userId"),
                                rs.getString("collectionName"),
                                rs.getString("collectionDescription")
                        )
                );
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving all collections: " + e.getMessage());
        }
        return collections;
    }

    /**
     * Retrieves the ID of a collection by the user's ID and the collection name.
     *
     * @param user The user who owns the collection.
     * @param collectionName The name of the collection.
     * @return The collection ID if found, or -1 if not found.
     */
    public int getCollectionsIDByUserAndCollectionName(User user, String collectionName) {
        int collectionId = -1; // Default value in case no result is found

        try {
            // Use a PreparedStatement to prevent SQL injection and ensure safe parameter handling
            String query = "SELECT collectionId FROM Collections WHERE collectionName = ? AND userId = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            
            stmt.setString(1, collectionName);  // Set the collection name
            stmt.setInt(2, user.getId());  // Set the user ID

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                collectionId = rs.getInt("collectionId"); // Get the collectionId from the result set
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving collections by User and Collection Name: " + e.getMessage());
        }
        return collectionId;
    }

    /**
     * Retrieves all collections for a specific user from the Collections table.
     *
     * @param user The user whose collections to retrieve.
     * @return A list of collections owned by the specified user.
     */
    public List<Collection> getCollectionsByUser(User user) {
        List<Collection> collections = new ArrayList<>();
        try {
            Statement getAll = connection.createStatement();

            ResultSet rs = getAll.executeQuery("SELECT * FROM Collections WHERE userId = " + user.getId());
            while (rs.next()) {
                collections.add(
                        new Collection(
                                rs.getInt("collectionId"),
                                rs.getInt("userId"),
                                rs.getString("collectionName"),
                                rs.getString("collectionDescription")
                        )
                );
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving collections by User: " + e.getMessage());
        }
        return collections;
    }
}