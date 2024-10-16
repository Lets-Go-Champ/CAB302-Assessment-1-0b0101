package com.example.cab302assessment10b0101.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * The User class represents a user entity in the library management system.
 * Each user has a unique ID, a username, a password, and a collection of book collections.
 */
public class User {
    private int id;
    private StringProperty username;
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
        this.username = new SimpleStringProperty(userName);;
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
        this.username = new SimpleStringProperty(userName);
        this.password = password;
        this.collections = FXCollections.observableArrayList();
    }

    // Getters for program functions

    /**
     * Retrieves the user's ID.
     *
     * @return The ID of the user.
     */
    public int getId() {
        return id;
    }

    /**
     * Retrieves the username of the user.
     *
     * @return The username of the user.
     */
    public String getUsername() {
        return username.get();
    }

    /**
     * Retrieves the username property of the user.
     *
     * @return The username of the user.
     */
    public StringProperty getUsernameProperty() {
        return username;
    }

    /**
     * Retrieves the password of the user.
     *
     * @return The password of the user.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Retrieves the collections associated with the user.
     *
     * @return An ObservableList of collections.
     */
    public ObservableList<Collection> getCollections() {
        return collections;
    }

    // Setters for program functions

    /**
     * Sets the user's ID.
     *
     * @param id The new ID of the user.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Sets the collections associated with the user.
     *
     * @param collections The new ObservableList of collections.
     */
    public void setCollections(ObservableList<Collection> collections) {
        this.collections = collections;
    }

    /**
     * Updates the username of the user.
     *
     * @param username The new username of the user.
     */
    public void setUsername(String username) {
        this.username.set(username);
    }


    /**
     * Sets the password of the user.
     *
     * @param password The new password of the user.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Adds a collection to the user's collections.
     *
     * @param collection The collection to be added.
     */
    public void addCollection(Collection collection) {
        collections.add(collection);
    }
}
