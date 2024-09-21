package com.example.cab302assessment10b0101.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The MockCollectionDAO class is a mock implementation of the CollectionDAOInterface.
 * It is used to simulate the behavior of the actual CollectionDAO in unit tests.
 * This class stores Collection objects in-memory using a List and provides methods
 * to simulate database operations such as insert, retrieve all collections, and retrieve
 * collections by user.
 */
public class MockCollectionDAO implements CollectionDAOInterface {

    private List<Collection> collections = new ArrayList<>();
    private int currentId = 1;  // Simulate auto-incrementing ID

    /**
     * Inserts a new collection into the mock data store.
     * Simulates the behavior of a real database by assigning an auto-incremented ID.
     *
     * @param collection The Collection object to be inserted.
     */
    @Override
    public void insert(Collection collection) {
        collection.setId(currentId++);  // Simulate auto-incrementing ID
        collections.add(collection);
    }

    /**
     * Retrieves all collections from the mock data store.
     *
     * @return A list of all Collection objects in the mock data store.
     */
    @Override
    public List<Collection> getAll() {
        return new ArrayList<>(collections);  // Return a copy of the list
    }

    /**
     * Retrieves all collections belonging to a specific user from the mock data store.
     * Filters the collections by the user ID.
     *
     * @param user The User object representing the owner of the collections.
     * @return A list of Collection objects that belong to the specified user.
     */
    @Override
    public List<Collection> getCollectionsByUser(User user) {
        // Filter collections by userId
        return collections.stream()
                .filter(collection -> collection.getUserId() == user.getId())
                .collect(Collectors.toList());
    }


    /**
     * Retrieves the ID of a collection belonging to a specific user and with a specific collection name.
     * If no collection is found, -1 is returned.
     *
     * @param user The User object representing the owner of the collection.
     * @param collectionName The name of the collection to search for.
     * @return The ID of the collection if found, otherwise -1.
     */
    @Override
    public int getCollectionsIDByUserAndCollectionName(User user, String collectionName) {
        // Find the collection by userId and collectionName
        return collections.stream()
                .filter(collection -> collection.getUserId() == user.getId() &&
                        collection.getCollectionName().equals(collectionName))
                .map(Collection::getId)
                .findFirst()
                .orElse(-1);  // Return -1 if no collection is found
    }
}
