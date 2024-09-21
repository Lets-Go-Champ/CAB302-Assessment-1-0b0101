package com.example.cab302assessment10b0101.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The CollectionDAO class provides data access methods for interacting with the
 * Collections table in the database. It follows the singleton pattern to ensure
 * that only one instance of this class is created throughout the application.
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
     * Singleton pattern to get the single instance of CollectionDAO.
     * Ensures that only one instance of this class is created throughout the application.
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
     * The table includes fields for the collection name and description, as well as a foreign key reference to the user ID.
     */
    public void createTable() {
        try {
            Statement createTable = connection.createStatement();
            createTable.execute(
                    "CREATE TABLE IF NOT EXISTS Collections (" +
                            "collectionId INTEGER PRIMARY KEY AUTOINCREMENT," +
                            "userId INTEGER," +
                            "collectionName TEXT NOT NULL," +
                            "collectionDescription TEXT," +
                            "FOREIGN KEY (userId) REFERENCES Users(userId)" +
                            ");"
            );
        } catch (SQLException ex) {
            System.err.println("Error creating Collections table: " + ex.getMessage());
        }
    }

    /**
     * Inserts a new collection record into the Collections table.
     *
     * @param collection The collection object containing all details to be inserted.
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
            System.err.println("Error inserting collection: " + ex.getMessage());
        }
    }

    /**
     * Retrieves all collections from the Collections table.
     *
     * @return A list of Collection objects representing all collections in the database.
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
            System.err.println("Error retrieving collections: " + e.getMessage());
        }
        return collections;
    }

    /**
     * Retrieves the collection ID based on the given user and collection name.
     *
     * @param user           The user associated with the collection.
     * @param collectionName The name of the collection.
     * @return The ID of the collection if found; otherwise, -1.
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
            System.err.println("Error retrieving collection ID: " + e.getMessage());
        }
        return collectionId;
    }

    /**
     * Retrieves all collections associated with a specific user.
     *
     * @param user The user whose collections are to be retrieved.
     * @return A list of Collection objects associated with the given user.
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
            System.err.println("Error retrieving collections for user: " + e.getMessage());
        }
        return collections;
    }
}