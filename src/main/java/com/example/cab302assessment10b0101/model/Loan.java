package com.example.cab302assessment10b0101.model;

import javafx.beans.property.*;

import java.time.LocalDate;

public class Loan {
    private final IntegerProperty id = new SimpleIntegerProperty(this, "id");
    private final IntegerProperty userId = new SimpleIntegerProperty(this, "userId");
    private final StringProperty borrower = new SimpleStringProperty(this, "borrower");
    private final StringProperty borrowerContact = new SimpleStringProperty(this, "borrowerContact");
    private final ObjectProperty<Book> book = new SimpleObjectProperty<>(this, "book");
    private final ObjectProperty<LocalDate> date = new SimpleObjectProperty<>(this, "date");

    public Loan(int userId, String borrowerName, String borrowerContact, Book book, LocalDate date) {
        this.userId.set(userId);
        this.borrower.set(borrowerName);
        this.borrowerContact.set(borrowerContact);
        this.book.set(book);
        this.date.set(date);
    }

    // Getter and setter methods for all properties
    public int getId() { return id.get(); }
    public IntegerProperty idProperty() { return id; }
    public void setId(int id) { this.id.set(id); }

    public int getUserId() { return userId.get(); }
    public IntegerProperty userIdProperty() { return userId; }
    public void setUserId(int userId) { this.userId.set(userId); }

    public String getBorrower() { return borrower.get(); }
    public StringProperty borrowerProperty() { return borrower; }
    public void setBorrower(String borrower) { this.borrower.set(borrower); }

    public String getBorrowerContact() { return borrowerContact.get(); }
    public StringProperty borrowerContactProperty() { return borrowerContact; }
    public void setBorrowerContact(String borrowerContact) { this.borrowerContact.set(borrowerContact); }

    public Book getBook() { return book.get(); }
    public ObjectProperty<Book> bookProperty() { return book; }
    public void setBook(Book book) { this.book.set(book); }

    public LocalDate getDate() { return date.get(); }
    public ObjectProperty<LocalDate> dateProperty() { return date; }
    public void setDate(LocalDate date) { this.date.set(date); }

    public String getDateAsString() {
        return date.get().toString();
    }
}