package com.example.cab302assessment10b0101.model;

/**
 * The Collection class represents a collection of books associated with a specific user
 * in the library management system. Each collection has a unique ID, a user ID, a name,
 * and an optional description.
 */
public class Collection {
    private int collectionId;
    private int userId;
    private String collectionName;
    private String collectionDescription;

    /**
     * Constructs a new Collection object with a specified collection ID, user ID, name, and description.
     *
     * @param collectionId         The unique ID of the collection.
     * @param userId               The ID of the user associated with the collection.
     * @param collectionName       The name of the collection.
     * @param collectionDescription The description of the collection.
     */
    public Collection(int collectionId, int userId, String collectionName, String collectionDescription) {
        this.collectionId = collectionId;
        this.userId = userId;
        this.collectionName = collectionName;
        this.collectionDescription = collectionDescription;
    }

    /**
     * Constructs a new Collection object without specifying a user ID, typically used for new entries.
     *
     * @param collectionName       The name of the collection.
     * @param collectionDescription The description of the collection.
     */
    public Collection(String collectionName, String collectionDescription) {
        this.collectionName = collectionName;
        this.collectionDescription = collectionDescription;
    }

    /**
     * Constructs a new Collection object with a specified user ID, name, and description.
     *
     * @param userId               The ID of the user associated with the collection.
     * @param collectionName       The name of the collection.
     * @param collectionDescription The description of the collection.
     */
    public Collection(Integer userId, String collectionName, String collectionDescription) {
        this.userId = userId;
        this.collectionName = collectionName;
        this.collectionDescription = collectionDescription;
    }

    // Getters for program functions
    public int getId() {
        return collectionId;
    }

    public void setId(int id) {
        this.collectionId = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getCollectionName() {
        return collectionName;
    }

    // Setters for program functions
    public void setCollectionName(String collectionName) {
        this.collectionName = collectionName;
    }

    public String getCollectionDescription() {
        return collectionDescription;
    }

    public void setCollectionDescription(String description) {
        this.collectionDescription = description;
    }

    // toString for testing purposes
    @Override
    public String toString() {
        return collectionName;
    }
}
