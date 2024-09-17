package com.example.cab302assessment10b0101.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;

public class Book {
    private StringProperty collectionName;
    private int id;
    private StringProperty title;
    private StringProperty author;
    private StringProperty description;
    private StringProperty publicationDate;
    private StringProperty publisher;
    private IntegerProperty pages;
    private StringProperty notes;
    private byte[] image; // New field for storing images

    public Book(StringProperty collectionName, StringProperty title, int id, StringProperty author, StringProperty description, StringProperty publicationDate, StringProperty publisher, IntegerProperty pages, StringProperty notes, byte[] image) {
        this.collectionName = collectionName;
        this.title = title;
        this.id = id;
        this.author = author;
        this.description = description;
        this.publicationDate = publicationDate;
        this.publisher = publisher;
        this.pages = pages;
        this.notes = notes;
        this.image = image;
    }

    public StringProperty getCollectionName() {
        return collectionName;
    }

    public  StringProperty getTitle() {
        return title;
    }

    public int getId() {
        return id;
    }

    public  StringProperty getAuthor() {
        return author;
    }

    public  StringProperty getDescription() {
        return description;
    }

    public  StringProperty getPublicationDate() {
        return publicationDate;
    }

    public  StringProperty getPublisher() {
        return publisher;
    }

    public IntegerProperty getPages() {
        return pages;
    }

    public  StringProperty getNotes() {
        return notes;
    }

    public byte[] getImage() {
        return image;
    }

    public void setCollectionName( StringProperty collectionName) {
        this.collectionName = collectionName;
    }

    public void setTitle( StringProperty title) {
        this.title = title;
    }

    public void setId(int id) {
        this.id = id;
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

    /*
    @Override
    public String toString() {
        return "Book{" +
                "Collection=" + collectionName +
                ", title='" + title + '\'' +
                ", id / ISBN='" + id + '\'' +
                ", author='" + author + '\'' +
                ", description='" + description + '\'' +
                ", publicationDate=" + publicationDate +
                ", publisher='" + publisher + '\'' +
                ", pages=" + pages +
                ", notes='" + notes + '\'' +
                ", An image may be attached, but it cannot be displayed here'" + '\'' +
                '}';
    }
    */

}
