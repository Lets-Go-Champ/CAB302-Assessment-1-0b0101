package com.example.cab302assessment10b0101.model;

import java.util.ArrayList;
import java.util.List;

public class MockUserDAO implements UserDAOInterface {

    private List<User> users = new ArrayList<>();
    private int currentId = 1;  // Simulate auto-incrementing ID

    @Override
    public void insert(User user) {
        user.setId(currentId++);  // Set an auto-incremented ID
        users.add(user);
    }

    @Override
    public void update(User user) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId() == user.getId()) {
                users.set(i, user);  // Replace the user in the list
                break;
            }
        }
    }

    @Override
    public void delete(int id) {
        users.removeIf(user -> user.getId() == id);  // Remove user by ID
    }

    @Override
    public List<User> getAll() {
        return new ArrayList<>(users);  // Return a copy of the list
    }

    @Override
    public User getById(int id) {
        return users.stream()
                .filter(user -> user.getId() == id)
                .findFirst()
                .orElse(null);  // Find user by ID
    }
}

