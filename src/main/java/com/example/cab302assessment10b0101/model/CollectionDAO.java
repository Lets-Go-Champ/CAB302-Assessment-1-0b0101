package com.example.cab302assessment10b0101.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CollectionDAO {

    private Connection connection;

    public CollectionDAO() {
        connection = DatabaseConnector.getInstance();
    }

    // Create the Collection table if it doesn't already exist
    public void createTable() {
        try {
            Statement createTable = connection.createStatement();
            createTable.execute(
                    "CREATE TABLE IF NOT EXISTS Collections (" +
                            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                            "collectionName TEXT NOT NULL," +
                            "collectionDescription" +
                            ");"
            );
        } catch (SQLException ex) {
            System.err.println(ex);
        }
    }

    // Insert a new book into the Books table
    public void insert(Collection collection) {
        try {
            PreparedStatement insertCollection = connection.prepareStatement(
                    "INSERT INTO Collections (id, collectionName, collectionDescription) " +
                            "VALUES (?, ?, ?);"
            );
            insertCollection.setInt(1, collection.getId());
            insertCollection.setString(2, collection.getCollectionName());
            insertCollection.setString(3, collection.getCollectionDescription());
            insertCollection.execute();
        } catch (SQLException ex) {
            System.err.println(ex);
        }
    }

    // Retrieve all books from the Books table
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