package com.example.cab302assessment10b0101.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;

import java.io.ByteArrayInputStream;

public class Book {
    //private IntegerProperty collectionName;
    private int collectionId;
    private int bookId;
    private StringProperty title;
    private StringProperty author;
    private StringProperty description;
    private StringProperty publicationDate;
    private StringProperty publisher;
    private IntegerProperty pages;
    private StringProperty notes;
    private byte[] image; // New field for storing images

    public Book(int collectionId, int id, String title, String author, String description, String publicationDate, String publisher, Integer pages, String notes, byte[] image) {
        //this.collectionName = new SimpleStringProperty(collectionName);
        this.collectionId = id;
        this.bookId = id;
        this.title = new SimpleStringProperty(title);
        this.author = new SimpleStringProperty(author);
        this.description = new SimpleStringProperty(description);
        this.publicationDate = new SimpleStringProperty(publicationDate);
        this.publisher = new SimpleStringProperty(publisher);
        this.pages = new SimpleIntegerProperty(pages);
        this.notes = new SimpleStringProperty(notes);
        this.image = image;
    }
/*
    public String getCollectionName() {
        return collectionName.get();
    }
    */


    public String getTitle() {
        return title.get();
    }

    public int getId() {
        return bookId;
    }

    public int getCollectionId() {
        return collectionId;
    }

    public String getAuthor() {
        return author.get();
    }

    public String getDescription() {
        return description.get();
    }

    public String getPublicationDate() {
        return publicationDate.get();
    }

    public String getPublisher() {
        return publisher.get();
    }

    public int getPages() {
        return pages.get();
    }

    public String getNotes() {
        return notes.get();
    }

    public byte[] getBytes() {
        return image;
    }

    public Image getImage() {
        if(image != null){
            return new Image(new ByteArrayInputStream(image));
        }
        return null;
    }
/*
    public void setCollectionName( StringProperty collectionName) {
        this.collectionName = collectionName;
    }
*/
    public void setTitle( StringProperty title) {
        this.title = title;
    }

    public void setId(int id) {
        this.bookId = id;
    }

    public void setCollectionId(int collectionId) {
        this.collectionId = collectionId;
    }

    public void setAuthor( StringProperty author) {
        this.author = author;
    }

    public void setDescription( StringProperty description) {
        this.description = description;
    }

    public void setPublicationDate( StringProperty publicationDate) {
        this.publicationDate = publicationDate;
    }

    public void setPublisher( StringProperty publisher) {
        this.publisher = publisher;
    }

    public void setPages(IntegerProperty pages) {
        this.pages = pages;
    }

    public void setNotes(StringProperty notes) {
        this.notes = notes;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

}
