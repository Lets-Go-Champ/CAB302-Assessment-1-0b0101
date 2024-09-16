package com.example.cab302assessment10b0101.model;

public class Book {
    private int id;
    private String title;
    private String author;
    private String description;
    private String publicationDate;
    private String publisher;
    private int pages;
    private String notes;
    private byte[] image; // New field for storing images

    public Book(int id, String title, String author, String description, String publicationDate, String publisher, int pages, String notes, byte[] image) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.description = description;
        this.publicationDate = publicationDate;
        this.publisher = publisher;
        this.pages = pages;
        this.notes = notes;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getDescription() {
        return description;
    }

    public String getPublicationDate() {
        return publicationDate;
    }

    public String getPublisher() {
        return publisher;
    }

    public int getPages() {
        return pages;
    }

    public String getNotes() {
        return notes;
    }

    public byte[] getImage() {
        return image;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPublicationDate(String publicationDate) {
        this.publicationDate = publicationDate;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", description='" + description + '\'' +
                ", publicationDate=" + publicationDate +
                ", publisher='" + publisher + '\'' +
                ", pages=" + pages +
                ", notes='" + notes + '\'' +
                ", An image may be attached, but it cannot be displayed here'" + '\'' +
                '}';
    }
}
