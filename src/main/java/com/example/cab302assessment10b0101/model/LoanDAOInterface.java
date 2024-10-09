package com.example.cab302assessment10b0101.model;

import javafx.collections.ObservableList;

/**
 * The LoanDAOInterface defines the contract for data access operations
 * related to Loan objects. It specifies methods for inserting new loans,
 * retrieving loans by user, deleting loans, and finding loan IDs by user and book.
 */
public interface LoanDAOInterface {

    /**
     * Inserts a new loan into the data store.
     *
     * @param loan The Loan object to be inserted.
     */
    void insertLoan(Loan loan);

    /**
     * Retrieves all loans associated with a specific user ID.
     *
     * @param userId The ID of the user whose loans to retrieve.
     * @return An ObservableList of Loan objects for the specified user.
     */
    ObservableList<Loan> getAllLoansByUser(int userId);

    /**
     * Deletes a specific loan from the data store.
     *
     * @param loan The Loan object to be deleted.
     */
    void deleteLoan(Loan loan);

    /**
     * Retrieves the loan ID by user and book.
     *
     * @param userId The ID of the user associated with the loan.
     * @param bookId The ID of the book associated with the loan.
     * @return The loan ID if found, otherwise -1.
     */
    int getLoanIdByUserAndBook(int userId, int bookId);
}