package com.example.cab302assessment10b0101.model;

// Book Java!

public class Book {
    private int id;
    private String title;
    private String author;
    private String description;
    private String publicationDate;
    private String publisher;
    private int pages;
    private String notes;

    public Book(int id, String title, String author, String description, String publicationDate, String publisher, int pages, String notes) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.description = description;
        this.publicationDate = publicationDate;
        this.publisher = publisher;
        this.pages = pages;
        this.notes = notes;
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

    public String getPublicationDate() { return publicationDate; }

    public String getPublisher() {
        return publisher;
    }

    public int getPages() {
        return pages;
    }

    public String getNotes() {
        return notes;
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

    public void setPublicationDate(String publicationDate) { this.publicationDate = publicationDate; }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public void setNotes(String notes) {
        this.notes = notes;
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
                '}';
    }
}
