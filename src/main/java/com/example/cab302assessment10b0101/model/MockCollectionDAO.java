package com.example.cab302assessment10b0101.model;

import java.util.ArrayList;
import java.util.List;

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
}
