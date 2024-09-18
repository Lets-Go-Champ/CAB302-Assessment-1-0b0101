package com.example.cab302assessment10b0101.model;

import java.util.List;

public interface UserDAOInterface {
    void insert(User user);
    void update(User user);
    void delete(int id);
    List<User> getAll();
    User getById(int id);
}