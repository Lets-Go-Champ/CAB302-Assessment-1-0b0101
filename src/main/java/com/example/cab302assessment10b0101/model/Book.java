package com.example.cab302assessment10b0101.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;

import java.io.ByteArrayInputStream;

public class Book {
    //private IntegerProperty collectionName;
    private int bookId;
    private int collectionId;
    private StringProperty title;
    private IntegerProperty isbn;
    private StringProperty author;
    private StringProperty description;
    private StringProperty publicationDate;
    private StringProperty publisher;
    private IntegerProperty pages;
    private StringProperty notes;
    private byte[] image; // New field for storing images

    public Book(int bookId, int collectionId, String title, int isbn, String author, String description, String publicationDate, String publisher, Integer pages, String notes, byte[] image) {
        this.bookId = bookId;
        this.collectionId = collectionId;
        this.title = new SimpleStringProperty(title);
        this.isbn = new SimpleIntegerProperty(isbn);
        this.author = new SimpleStringProperty(author);
        this.description = new SimpleStringProperty(description);
        this.publicationDate = new SimpleStringProperty(publicationDate);
        this.publisher = new SimpleStringProperty(publisher);
        this.pages = new SimpleIntegerProperty(pages);
        this.notes = new SimpleStringProperty(notes);
        this.image = image;
    }

    public Book(int collectionId, String title, int isbn, String author, String description, String publicationDate, String publisher, Integer pages, String notes, byte[] image) {
        this.collectionId = collectionId;
        this.title = new SimpleStringProperty(title);
        this.isbn = new SimpleIntegerProperty(isbn);
        this.author = new SimpleStringProperty(author);
        this.description = new SimpleStringProperty(description);
        this.publicationDate = new SimpleStringProperty(publicationDate);
        this.publisher = new SimpleStringProperty(publisher);
        this.pages = new SimpleIntegerProperty(pages);
        this.notes = new SimpleStringProperty(notes);
        this.image = image;
    }

    public String getTitle() {
        return title.get();
    }

    public int getId() {
        return bookId;
    }

    public int getISBN() {
        return isbn.get();
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

    public String getPagesAsString() {
        return String.valueOf(pages.get());
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

    public void setTitle( StringProperty title) {
        this.title = title;
    }

    public void setId(int id) {
        this.bookId = id;
    }

    public void setISBN(IntegerProperty isbn) {
        this.isbn = isbn;
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

    public String getISBNAsString() {
        return String.valueOf(isbn.get());
    }
}
