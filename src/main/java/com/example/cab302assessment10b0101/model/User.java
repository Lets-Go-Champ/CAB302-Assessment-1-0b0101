package com.example.cab302assessment10b0101.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


/**
 * The User class represents a user entity in the library management system.
 * Each user has a unique ID, a username, a password, and a collection of book collections.
 */
public class User {
    private int id;
    private String username;
    private String password;

    private ObservableList<Collection> collections;

    /**
     * Constructs a new User object with a specified ID, username, and password.
     *
     * @param id       The ID of the user.
     * @param userName The username of the user.
     * @param password The password of the user.
     */
    public User(int id, String userName, String password) {
        this.id = id;
        this.username = userName;
        this.password = password;
        this.collections = FXCollections.observableArrayList();
    }

    /**
     * Constructs a new User object without specifying an ID, typically used for new entries.
     * The ID is auto-incremented by the database.
     *
     * @param userName The username of the user.
     * @param password The password of the user.
     */
    public User(String userName, String password) {
        this.username = userName;
        this.password = password;
        this.collections = FXCollections.observableArrayList();
    }

    // Getters for program functions
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

    // Setters for program functions
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

    // toString for testing purposes
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
