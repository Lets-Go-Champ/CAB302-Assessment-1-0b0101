package com.example.cab302assessment10b0101.model;

public class User {
    private int id;
    private String username;
    private String password;

    public User(int id, String userName, String password) {
        this.id = id;
        this.username = userName;
        this.password = password;
    }

    public User(String userName, String password) {
        // Since the id is auto-incremented, it is nice to have a constructor without it
        this.username = userName;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    // Setter for ID (only used by MockUserDAO in testing)
    public void setId(int id) {
        this.id = id;
    }


    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setUserName(String userName) {
        this.username = userName;
    }

    public void setPassword(String password) {
        this.password = password;
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
