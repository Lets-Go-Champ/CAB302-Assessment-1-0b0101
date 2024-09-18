package com.example.cab302assessment10b0101.model;

public class Collection {
    private int collectionId;
    private int userId;
    private String collectionName;
    private String collectionDescription;

    public Collection(int collectionId, int userId, String collectionName, String collectionDescription) {
        this.collectionId = collectionId;
        this.userId = userId;
        this.collectionName = collectionName;
        this.collectionDescription = collectionDescription;
    }


    public Collection(String collectionName, String collectionDescription) {
        this.collectionName = collectionName;
        this.collectionDescription = collectionDescription;
    }

    public Collection(String collectionName, String collectionDescription, Integer userId) {
        this.collectionName = collectionName;
        this.collectionDescription = collectionDescription;
        this.userId = userId;
    }

    public int getId() {return collectionId; }

    public void setId(int id) {this.collectionId = id;}

    public int getUserId() {return userId; }

    public void setUserId(int userId) {this.userId = userId; }

    public String getCollectionName() {
        return collectionName;
    }

    public void setCollectionName(String collectionName) {
        this.collectionName = collectionName;
    }

    public String getCollectionDescription() {
        return collectionDescription;
    }

    public void setCollectionDescription(String description) {
        this.collectionDescription = description;
    }

    /*
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", collectionName='" + collectionName + '\'' +
                ", collectionDescription='" + collectionDescription + '\'' +
                '}';
    }
    */

}
