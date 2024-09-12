package com.example.cab302assessment10b0101.model;

public class Collection {
    private int id;
    private String collectionName;
    private String description;

    public Collection(int id, String collectionName, String description) {
        this.id = id;
        this.collectionName = collectionName;
        this.description = description;
    }
    public Collection(String collectionName, String description) {
        this.collectionName = collectionName;
        this.description = description;
    }

    public int getId() {return id; }

    public void setId(int id) {this.id = id;}

    public String getCollectionName() {
        return collectionName;
    }

    public void setCollectionName(String collectionName) {
        this.collectionName = collectionName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
