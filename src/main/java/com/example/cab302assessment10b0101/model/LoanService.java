package com.example.cab302assessment10b0101.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * The LoanService class provides services for managing loans, including loading,
 * refreshing, adding, and removing loans. It acts as an intermediary between the
 * LoanDAO (data access layer) and the UI, ensuring that loan data stays up-to-date.
 */
public class LoanService {

    // Instance of LoanDAO to interact with the database
    private final LoanDAO loanDAO;

    // ID of the current user, used to filter loans by user
    private final int currentUserId; // Add this to keep track of the current user

    // Observable list of loans, allowing easy data binding with the UI
    private final ObservableList<Loan> loans = FXCollections.observableArrayList();

    /**
     * Constructor for the LoanService. It initializes the LoanDAO and sets the
     * current user ID. It also loads loans for the specified user.
     *
     * @param userId The ID of the current user.
     */
    public LoanService(int userId) {
        this.loanDAO = LoanDAO.getInstance();
        this.currentUserId = userId;
        loadLoans();
    }

    /**
     * Loads all loans associated with the current user from the database
     * and updates the observable list of loans.
     */
    public void loadLoans() {
        loans.setAll(loanDAO.getAllLoansByUser(currentUserId));
    }

    /**
     * Refreshes the loan list by reloading loans from the database.
     * Ensures that the loan data remains up-to-date.
     */
    public void refreshLoans() {
        loans.setAll(loanDAO.getAllLoansByUser(currentUserId));
    }

    /**
     * Provides access to the observable list of loans, allowing the UI to
     * bind to it and reflect changes automatically.
     *
     * @return The observable list of loans.
     */
    public ObservableList<Loan> getLoans() {
        return loans;
    }

    /**
     * Adds a new loan to the database and updates the observable list to
     * reflect the change.
     *
     * @param loan The Loan object to be added.
     */
    public void addLoan(Loan loan) {
        loanDAO.insertLoan(loan);
        loans.add(loan);
    }

    /**
     * Removes a loan from the database and updates the observable list.
     * The loan list is refreshed after deletion to ensure data consistency.
     *
     * @param loan The Loan object to be removed.
     */
    public void removeLoan(Loan loan) {
        loanDAO.deleteLoan(loan);
        loans.remove(loan);
        refreshLoans();
    }
}