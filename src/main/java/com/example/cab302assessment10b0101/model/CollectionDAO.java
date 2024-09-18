package com.example.cab302assessment10b0101.model;

import java.sql.*;

public class CollectionDAO {
    private static CollectionDAO instance;
    private static Connection connection;

    private CollectionDAO() {
        connection = DatabaseConnector.getInstance();
    }

    public static synchronized CollectionDAO getInstance(){
        if(instance == null){
            instance = new CollectionDAO();
        }
        return instance;
    }

    // Create the Collection table if it doesn't already exist
    public void createTable() {
        try {
            Statement createTable = connection.createStatement();
            createTable.execute(
                    "CREATE TABLE IF NOT EXISTS Collections (" +
                            "collectionId INTEGER PRIMARY KEY AUTOINCREMENT," + // Auto-increment the ID
                            "collectionName TEXT NOT NULL," +
                            "userId INTEGER," +
                            "collectionDescription TEXT," + // Description can now be optional
                            "FOREIGN KEY (userId) REFERENCES Users(userId)" +
                            ");"
            );
        } catch (SQLException ex) {
            System.err.println(ex);
        }
    }

    // Insert a new collection into the Collections table
    public void insert(Collection collection) {
        try {
            PreparedStatement insertCollection = connection.prepareStatement(
                    "INSERT INTO Collections (collectionName, collectionDescription, userId) " + // No need to insert the id
                            "VALUES (?, ?, ?);"
            );
            insertCollection.setInt(1, UserManager.getInstance().getCurrentUser().getId());
            insertCollection.setString(2, collection.getCollectionName());
            insertCollection.setString(3, collection.getCollectionDescription());
            insertCollection.execute();
        } catch (SQLException ex) {
            System.err.println(ex);
        }
    }

    /*
    // Retrieve all collections from the Collections table
    public List<Collection> getAll() {
        List<Collection> collections = new ArrayList<>();
        try {
            Statement getAll = connection.createStatement();
            ResultSet rs = getAll.executeQuery("SELECT * FROM Collections");
            while (rs.next()) {
                collections.add(
                        new Collection(
                                rs.getInt("id"),
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
    */

}