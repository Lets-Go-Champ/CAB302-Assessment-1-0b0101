package com.example.cab302assessment10b0101.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
                            "id INTEGER PRIMARY KEY AUTOINCREMENT," + // Auto-increment the ID
                            "collectionName TEXT NOT NULL," +
                            "collectionDescription TEXT" + // Description can now be optional
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
                    "INSERT INTO Collections (collectionName, collectionDescription) " + // No need to insert the id
                            "VALUES (?, ?);"
            );
            insertCollection.setString(1, collection.getCollectionName());
            insertCollection.setString(2, collection.getCollectionDescription());
            insertCollection.execute();
        } catch (SQLException ex) {
            System.err.println(ex);
        }
    }

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
}