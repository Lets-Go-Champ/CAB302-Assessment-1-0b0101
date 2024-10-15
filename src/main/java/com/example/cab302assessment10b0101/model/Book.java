package com.example.cab302assessment10b0101.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;
import java.io.ByteArrayInputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * The Book class represents a book entity in the library management system.
 * It includes properties such as book ID, collection ID, title, ISBN, author, description,
 * publication date, publisher, pages, notes, and an image representing the book cover.
 */
public class Book {
    private int bookId;
    private int collectionId;
    private StringProperty title;
    private StringProperty isbn;
    private StringProperty author;
    private StringProperty description;
    private StringProperty publicationDate;
    // Method to get publication date as LocalDate
    public LocalDate getPublicationDateAsLocalDate() {
        if (publicationDate == null || publicationDate.get() == null) return null; // Check if the property is null or empty
        return LocalDate.parse(publicationDate.get(), DateTimeFormatter.ISO_DATE); // Use .get() to retrieve the String value
    }
    private StringProperty publisher;
    private IntegerProperty pages;
    private StringProperty notes;
    private byte[] image;
    private StringProperty readingStatus;

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
     * @param readingStatus   The current reading status of the book.
     */
    public Book(int bookId, int collectionId, String title, String isbn, String author, String description, String publicationDate, String publisher, Integer pages, String notes, byte[] image, String readingStatus) {
        this.bookId = bookId;
        this.collectionId = collectionId;
        this.title = new SimpleStringProperty(title);
        this.isbn = new SimpleStringProperty(isbn);
        this.author = new SimpleStringProperty(author);
        this.description = new SimpleStringProperty(description);
        this.publicationDate = new SimpleStringProperty(publicationDate);
        this.publisher = new SimpleStringProperty(publisher);
        this.pages = new SimpleIntegerProperty(pages);
        this.notes = new SimpleStringProperty(notes);
        this.image = image;
        this.readingStatus = new SimpleStringProperty(readingStatus);
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
     * @param readingStatus   The current reading status of the book.
     */
    public Book(int collectionId, String title, String isbn, String author, String description, String publicationDate, String publisher, Integer pages, String notes, byte[] image, String readingStatus) {
        this.collectionId = collectionId;
        this.title = new SimpleStringProperty(title);
        this.isbn = new SimpleStringProperty(isbn);
        this.author = new SimpleStringProperty(author);
        this.description = new SimpleStringProperty(description);
        this.publicationDate = new SimpleStringProperty(publicationDate);
        this.publisher = new SimpleStringProperty(publisher);
        this.pages = new SimpleIntegerProperty(pages);
        this.notes = new SimpleStringProperty(notes);
        this.image = image;
        this.readingStatus = new SimpleStringProperty(readingStatus);
    }

    // Getters for program functions

    /**
     * Gets the title of the book.
     * @return The title as a String.
     */
    public String getTitle() {
        return title.get();
    }

    /**
     * Gets the unique identifier of the book.
     * @return The book ID as an int.
     */
    public int getId() {
        return bookId;
    }

    /**
     * Gets the ISBN of the book.
     * @return The ISBN as an int.
     */
    public String getISBN() {
        return isbn.get();
    }

    /**
     * Gets the ISBN as a String.
     * @return The ISBN as a String.
     */
    public String getISBNAsString() {
        return String.valueOf(isbn.get());
    }

    /**
     * Gets the ID of the collection this book belongs to.
     * @return The collection ID as an int.
     */
    public int getCollectionId() {
        return collectionId;
    }

    /**
     * Gets the author of the book.
     * @return The author as a String.
     */
    public String getAuthor() {
        return author.get();
    }

    /**
     * Gets the description of the book.
     * @return The description as a String.
     */
    public String getDescription() {
        return description.get();
    }

    /**
     * Gets the publication date of the book.
     * @return The publication date as a String.
     */
    public String getPublicationDate() {
        return publicationDate.get();
    }

    /**
     * Gets the publisher of the book.
     * @return The publisher as a String.
     */
    public String getPublisher() {
        return publisher.get();
    }

    /**
     * Gets the number of pages in the book.
     * @return The number of pages as an int.
     */
    public int getPages() {
        return pages.get();
    }

    /**
     * Gets additional notes about the book.
     * @return The notes as a String.
     */
    public String getNotes() {
        return notes.get();
    }

    /**
     * Gets the number of pages as a String.
     * @return The number of pages as a String.
     */
    public String getPagesAsString() {
        return String.valueOf(pages.get());
    }

    /**
     * Gets the image as a byte array.
     * @return The image as a byte array.
     */
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

    /**
     * Gets the current reading status of the book.
     * @return The reading status as a String.
     */
    public String getReadingStatus() {
        return readingStatus.get();
    }

    // Setters for program functions

    /**
     * Sets the title of the book.
     * @param title The title to set as a StringProperty.
     */
    public void setTitle( StringProperty title) {
        this.title = title;
    }

    /**
     * Sets the unique identifier of the book.
     * @param id The ID to set as an int.
     */
    public void setId(int id) {
        this.bookId = id;
    }

    /**
     * Sets the ISBN of the book.
     * @param isbn The ISBN to set as an IntegerProperty.
     */
    public void setISBN(StringProperty isbn) {
        this.isbn = isbn;
    }

    /**
     * Sets the author of the book.
     * @param author The author to set as a StringProperty.
     */
    public void setAuthor( StringProperty author) {
        this.author = author;
    }

    /**
     * Sets the description of the book.
     * @param description The description to set as a StringProperty.
     */
    public void setDescription( StringProperty description) {
        this.description = description;
    }

    /**
     * Sets the publication date of the book.
     * @param publicationDate The publication date to set as a StringProperty.
     */
    public void setPublicationDate( StringProperty publicationDate) {
        this.publicationDate = publicationDate;
    }

    /**
     * Sets the publisher of the book.
     * @param publisher The publisher to set as a StringProperty.
     */
    public void setPublisher( StringProperty publisher) {
        this.publisher = publisher;
    }

    /**
     * Sets the number of pages in the book.
     * @param pages The number of pages to set as an IntegerProperty.
     */
    public void setPages(SimpleIntegerProperty pages) {
        if (pages.get() < 0) {
            throw new IllegalArgumentException("Number of pages cannot be negative");
        }
        this.pages = pages;
    }

    /**
     * Sets additional notes about the book.
     * @param notes The notes to set as a StringProperty.
     */
    public void setNotes(StringProperty notes) {
        this.notes = notes;
    }

    /**
     * Sets the image associated with the book.
     * @param image The image as a byte array.
     */
    public void setImage(byte[] image) {
        this.image = image;
    }

    /**
     * Sets the current reading status of the book.
     * @param readingStatus The reading status to set as a StringProperty.
     */
    public void setreadingStatus(StringProperty readingStatus) {
        this.readingStatus = readingStatus;
    }
}
