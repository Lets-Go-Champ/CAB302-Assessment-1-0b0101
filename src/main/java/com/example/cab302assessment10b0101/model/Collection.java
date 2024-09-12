package com.example.cab302assessment10b0101.model;

public class Collection {
    private int id;
    private String collectionName;
    private String collectionDescription;

    public Collection(int id, String collectionName, String collectionDescription) {
        this.id = id;
        this.collectionName = collectionName;
        this.collectionDescription = collectionDescription;
    }
    public Collection(String collectionName, String collectionDescription) {
        this.collectionName = collectionName;
        this.collectionDescription = collectionDescription;
    }

    public int getId() {return id; }

    public void setId(int id) {this.id = id;}

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

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", collectionName='" + collectionName + '\'' +
                ", collectionDescription='" + collectionDescription + '\'' +
                '}';
    }
}
