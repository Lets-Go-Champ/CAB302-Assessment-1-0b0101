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

    // Getters and setters for program functions

    /**
     * Gets the unique ID of the collection.
     *
     * @return The ID of the collection.
     */
    public int getId() {
        return collectionId;
    }

    /**
     * Sets the unique ID of the collection.
     *
     * @param id The new ID of the collection.
     */
    public void setId(int id) {
        this.collectionId = id;
    }

    /**
     * Gets the user ID associated with the collection.
     *
     * @return The ID of the user.
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Sets the user ID associated with the collection.
     *
     * @param userId The new user ID.
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Gets the name of the collection.
     *
     * @return The name of the collection.
     */
    public String getCollectionName() {
        return collectionName;
    }

    /**
     * Sets the name of the collection.
     *
     * @param collectionName The new name of the collection.
     */
    public void setCollectionName(String collectionName) {
        if (collectionName == null || collectionName.trim().isEmpty()) {
            throw new IllegalArgumentException();
        }
        this.collectionName = collectionName;
    }

    /**
     * Gets the description of the collection.
     *
     * @return The description of the collection.
     */
    public String getCollectionDescription() {
        return collectionDescription;
    }

    /**
     * Sets the description of the collection.
     *
     * @param description The new description of the collection.
     */
    public void setCollectionDescription(String description) {
        this.collectionDescription = description;
    }


    /**
     * Returns a string representation of the collection for testing purposes.
     *
     * @return The name of the collection.
     */
    @Override
    public String toString() {
        return collectionName;
    }


}
