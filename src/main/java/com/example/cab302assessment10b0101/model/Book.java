package com.example.cab302assessment10b0101.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
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

    public Book(String collectionName, String title, int id, String author, String description, String publicationDate, String publisher, Integer pages, String notes, byte[] image) {
        this.collectionName = new SimpleStringProperty(collectionName);
        this.title = new SimpleStringProperty(title);
        this.id = id;
        this.author = new SimpleStringProperty(author);
        this.description = new SimpleStringProperty(description);
        this.publicationDate = new SimpleStringProperty(publicationDate);
        this.publisher = new SimpleStringProperty(publisher);
        this.pages = new SimpleIntegerProperty(pages);
        this.notes = new SimpleStringProperty(notes);
        this.image = image;
    }

    public String getCollectionName() {
        return collectionName.get();
    }

    public String getTitle() {
        return title.get();
    }

    public int getId() {
        return id;
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
