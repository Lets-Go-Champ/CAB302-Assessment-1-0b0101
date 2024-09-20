package com.example.cab302assessment10b0101.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class User {
    private int id;
    private String username;
    private String password;

    private ObservableList<Collection> collections;

    public User(int id, String userName, String password) {
        this.id = id;
        this.username = userName;
        this.password = password;
        this.collections = FXCollections.observableArrayList();
    }

    public User(String userName, String password) {
        // Since the id is auto-incremented, it is nice to have a constructor without it
        this.username = userName;
        this.password = password;
        this.collections = FXCollections.observableArrayList();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public ObservableList<Collection> getCollections() {
        return collections;
    }

    public void setCollections(ObservableList<Collection> collections) {
        this.collections = collections;
    }

    public void setUserName(String userName) {
        this.username = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void addCollection(Collection collection) {
        collections.add(collection);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
