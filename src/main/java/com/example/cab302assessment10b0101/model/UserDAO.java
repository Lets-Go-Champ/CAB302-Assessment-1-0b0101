package com.example.cab302assessment10b0101.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    private static UserDAO instance;
    private static Connection connection;

    private UserDAO() {
        connection = DatabaseConnector.getInstance();
    }

    public static synchronized UserDAO getInstance(){
        if(instance == null){
            instance = new UserDAO();
        }
        return instance;
    }

    public void createTable() {
        try {
            Statement createTable = connection.createStatement();
            createTable.execute(
                    "CREATE TABLE IF NOT EXISTS Users ("
                            + "userId INTEGER PRIMARY KEY AUTOINCREMENT, "
                            + "username VARCHAR NOT NULL, "
                            + "password VARCHAR NOT NULL "
                            + ")"
            );
        } catch (SQLException ex) {
            System.err.println(ex);
        }
    }

    public void insert(User user) {
        try {
            PreparedStatement insertUser = connection.prepareStatement(
                    "INSERT INTO Users (username, password) VALUES (?, ?)"
            );
            insertUser.setString(1, user.getUsername());
            insertUser.setString(2, user.getPassword());
            insertUser.execute();
        } catch (SQLException ex) {
            System.err.println(ex);
        }
    }

    public void update(User user) {
        try {
            PreparedStatement updateUser = connection.prepareStatement(
                    "UPDATE users SET username = ?, password = ?, WHERE id = ?"
            );
            updateUser.setString(1, user.getUsername());
            updateUser.setString(2, user.getPassword());
            updateUser.setInt(3, user.getId());
            updateUser.execute();
        } catch (SQLException ex) {
            System.err.println(ex);
        }
    }

    public void delete(int id) {
        try {
            PreparedStatement deleteUser = connection.prepareStatement("DELETE FROM Users WHERE id = ?");
            deleteUser.setInt(1, id);
            deleteUser.execute();
        } catch (SQLException ex) {
            System.err.println(ex);
        }
    }

    public List<User> getAll() {
        List<User> user = new ArrayList<>();
        try {
            Statement getAll = connection.createStatement();
            ResultSet rs = getAll.executeQuery("SELECT * FROM Users");
            while (rs.next()) {
                user.add(
                        new User(
                                rs.getInt("id"),
                                rs.getString("username"),
                                rs.getString("password")
                        )
                );
            }
        } catch (SQLException ex) {
            System.err.println(ex);
        }
        return user;
    }

    public User getById(int id) {
        try {
            PreparedStatement getUser = connection.prepareStatement("SELECT * FROM Users WHERE id = ?");
            getUser.setInt(1, id);
            ResultSet rs = getUser.executeQuery();
            if (rs.next()) {
                return new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password")
                );
            }
        } catch (SQLException ex) {
            System.err.println(ex);
        }
        return null;
    }

    public void close() {
        try {
            connection.close();
        } catch (SQLException ex) {
            System.err.println(ex);
        }
    }
}