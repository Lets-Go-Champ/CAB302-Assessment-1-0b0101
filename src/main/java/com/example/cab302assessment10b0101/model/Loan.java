package com.example.cab302assessment10b0101.model;

import javafx.beans.property.*;
import java.time.LocalDate;

/**
 * The Loan class represents a loan record in the system.
 * It stores information about the user, borrower, borrowed book, and loan date.
 * This class uses JavaFX property bindings to allow the data to be easily bound to UI elements.
 */
public class Loan {
    // Property bindings for loan attributes
    private final IntegerProperty id = new SimpleIntegerProperty(this, "id"); // Unique loan ID
    private final IntegerProperty userId = new SimpleIntegerProperty(this, "userId"); // ID of the user who owns the loan
    private final StringProperty borrower = new SimpleStringProperty(this, "borrower"); // Name of the borrower
    private final StringProperty borrowerContact = new SimpleStringProperty(this, "borrowerContact"); // Contact details of the borrower
    private final ObjectProperty<Book> book = new SimpleObjectProperty<>(this, "book"); // Book associated with the loan
    private final ObjectProperty<LocalDate> date = new SimpleObjectProperty<>(this, "date"); // Date of the loan

    /**
     * Constructor to create a new Loan instance with the specified details.
     *
     * @param userId           The ID of the user who owns the loan.
     * @param borrowerName     The name of the borrower.
     * @param borrowerContact  The contact details of the borrower.
     * @param book             The book associated with the loan.
     * @param date             The date when the loan was made.
     */
    public Loan(int userId, String borrowerName, String borrowerContact, Book book, LocalDate date) {
        this.userId.set(userId);
        this.borrower.set(borrowerName);
        this.borrowerContact.set(borrowerContact);
        this.book.set(book);
        this.date.set(date);
    }

    /**
     * Gets the loan ID.
     *
     * @return The loan ID.
     */
    public int getId() {
        return id.get();
    }

    /**
     * Sets the loan ID.
     *
     * @param id The loan ID to set.
     */
    public void setId(int id) {
        this.id.set(id);
    }

    /**
     * Gets the user ID associated with the loan.
     *
     * @return The user ID.
     */
    public int getUserId() {
        return userId.get();
    }

    /**
     * Sets the user ID associated with the loan.
     *
     * @param userId The user ID to set.
     */
    public void setUserId(int userId) {
        this.userId.set(userId);
    }

    /**
     * Gets the name of the borrower.
     *
     * @return The borrower's name.
     */
    public String getBorrower() {
        return borrower.get();
    }

    /**
     * Sets the name of the borrower.
     *
     * @param borrower The borrower's name to set.
     */
    public void setBorrower(String borrower) {
        this.borrower.set(borrower);
    }

    /**
     * Gets the borrower's contact details.
     *
     * @return The borrower's contact.
     */
    public String getBorrowerContact() {
        return borrowerContact.get();
    }

    /**
     * Sets the contact details of the borrower.
     *
     * @param borrowerContact The contact details to set.
     */
    public void setBorrowerContact(String borrowerContact) {
        this.borrowerContact.set(borrowerContact);
    }

    /**
     * Gets the book associated with the loan.
     *
     * @return The loaned book.
     */
    public Book getBook() {
        System.out.println("getBook() called. Book = " + book.get() + "which has an ID of: (calling book.getId() = )" + book.get().getId());
        return book.get();
    }

    /**
     * Sets the book associated with the loan.
     *
     * @param book The book to set.
     */
    public void setBook(Book book) {
        this.book.set(book);
    }

    /**
     * Gets the loan date.
     *
     * @return The loan date.
     */
    public LocalDate getDate() {
        return date.get();
    }

    /**
     * Sets the loan date.
     *
     * @param date The date to set.
     */
    public void setDate(LocalDate date) {
        this.date.set(date);
    }

    /**
     * Gets the loan date as a string.
     *
     * @return The loan date in string format.
     */
    public String getDateAsString() {
        return date.get().toString();
    }
}