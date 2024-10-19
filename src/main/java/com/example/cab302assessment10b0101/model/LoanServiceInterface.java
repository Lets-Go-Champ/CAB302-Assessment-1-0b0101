package com.example.cab302assessment10b0101.model;

import javafx.collections.ObservableList;

/**
 * The LoanServiceInterface defines the contract for mock LoanService functionality.
 * It specifies methods for managing loans, including loading, refreshing,
 * adding, and removing loans, for unit testing purposes.
 */
public interface LoanServiceInterface {

    /**
     * Loads all loans.
     * Updates the observable list of loans.
     */
    void loadLoans();

    /**
     * Refreshes the loan list by reloading loans.
     */
    void refreshLoans();

    /**
     * Provides access to the observable list of loans.
     *
     * @return The observable list of loans.
     */
    ObservableList<Loan> getLoans();

    /**
     * Adds a new loan.
     *
     * @param loan The Loan object to be added.
     */
    void addLoan(Loan loan);

    /**
     * Removes a loan.
     *
     * @param loan The Loan object to be removed.
     */
    void removeLoan(Loan loan);
}
