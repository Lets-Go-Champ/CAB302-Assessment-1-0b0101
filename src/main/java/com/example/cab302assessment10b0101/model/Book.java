package com.example.cab302assessment10b0101.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;
import java.io.ByteArrayInputStream;

/**
 * The Book class represents a book entity in the library management system.
 * It includes properties such as book ID, collection ID, title, ISBN, author, description,
 * publication date, publisher, pages, notes, and an image representing the book cover.
 */
public class Book {
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

    /**
     * Constructs a new Book object with the specified parameters including a book ID.
     *
     * @param bookId          The ID of the book.
     * @param collectionId    The ID of the collection this book belongs to.
     * @param title           The title of the book.
     * @param isbn            The ISB number of the book.
     * @param author          The author of the book.
     * @param description     The description of the book.
     * @param publicationDate The publication date of the book.
     * @param publisher       The publisher of the book.
     * @param pages           The number of pages in the book.
     * @param notes           Additional notes about the book.
     * @param image           The image associated with the book as a byte array.
     */
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


    /**
     * Constructs a new Book object without specifying a book ID, typically used for new entries.
     *
     * @param collectionId    The ID of the collection this book belongs to.
     * @param title           The title of the book.
     * @param isbn            The ISB number of the book.
     * @param author          The author of the book.
     * @param description     The description of the book.
     * @param publicationDate The publication date of the book.
     * @param publisher       The publisher of the book.
     * @param pages           The number of pages in the book.
     * @param notes           Additional notes about the book.
     * @param image           The image associated with the book as a byte array.
     */
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

    // Getters for program functions
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

    /**
     * Converts the image byte array into a JavaFX Image object.
     *
     * @return The image as an Image object, or null if no image is present.
     */
    public Image getImage() {
        if(image != null){
            return new Image(new ByteArrayInputStream(image));
        }
        return null;
    }

    // Setters for program functions
    public void setTitle( StringProperty title) {
        this.title = title;
    }

    public void setId(int id) {
        this.bookId = id;
    }

    public void setISBN(IntegerProperty isbn) {
        this.isbn = isbn;
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
