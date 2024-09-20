package com.example.cab302assessment10b0101.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MockCollectionDAO implements CollectionDAOInterface {

    private List<Collection> collections = new ArrayList<>();
    private int currentId = 1;  // Simulate auto-incrementing ID

    @Override
    public void insert(Collection collection) {
        collection.setId(currentId++);  // Simulate auto-incrementing ID
        collections.add(collection);
    }

    @Override
    public List<Collection> getAll() {
        return new ArrayList<>(collections);  // Return a copy of the list
    }

    @Override
    public List<Collection> getCollectionsByUser(User user) {
        // Filter collections by userId
        return collections.stream()
                .filter(collection -> collection.getUserId() == user.getId())
                .collect(Collectors.toList());
    }

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
