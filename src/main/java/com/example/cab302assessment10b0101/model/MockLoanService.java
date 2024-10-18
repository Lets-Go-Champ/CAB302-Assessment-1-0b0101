package com.example.cab302assessment10b0101.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * The MockLoanService class simulates the functionality of LoanService for unit testing purposes.
 * It uses MockLoanDAO to handle loan operations in-memory.
 * This class implements LoanServiceInterface.
 */
public class MockLoanService implements LoanServiceInterface {
    private final MockLoanDAO mockLoanDAO;
    private final int currentUserId;
    private final ObservableList<Loan> loans = FXCollections.observableArrayList();

    /**
     * Constructor for the MockLoanService. It initializes the MockLoanDAO and sets the
     * current user ID.
     *
     * @param userId The ID of the current user.
     * @param mockLoanDAO The mock DAO used for testing.
     */
    public MockLoanService(int userId, MockLoanDAO mockLoanDAO) {
        this.currentUserId = userId;
        this.mockLoanDAO = mockLoanDAO;
        loadLoans();
    }

    /**
     * Loads all loans associated with the current user from the mock DAO.
     * This method updates the observable list of loans.
     */
    @Override
    public void loadLoans() {
        loans.clear();
        // Load all loans from MockLoanDAO for the current user
        loans.addAll(mockLoanDAO.getAllLoansByUser(currentUserId));
    }


    /**
     * Refreshes the loan list by reloading loans from the mock DAO.
     * Ensures that the loan data remains up-to-date in testing.
     */
    @Override
    public void refreshLoans() {
        loadLoans();  // For the mock service, we just reload the in-memory data.
    }



    /**
     * Provides access to the observable list of loans for testing.
     * This allows tests to verify the state of loans.
     *
     * @return The observable list of loans.
     */
    @Override
    public ObservableList<Loan> getLoans() {
        return loans;
    }

    /**
     * Adds a new loan to the mock DAO for testing.
     * Updates the observable list to reflect the change.
     *
     * @param loan The Loan object to be added.
     */
    @Override
    public void addLoan(Loan loan) {
        mockLoanDAO.insertLoan(loan);  // Add loan to MockLoanDAO
        loans.add(loan);  // Update the observable list
    }

    /**
     * Removes a loan from the mock DAO for testing.
     * The loan list is refreshed after deletion to ensure data consistency.
     *
     * @param loan The Loan object to be removed.
     */
    @Override
    public void removeLoan(Loan loan) {
        mockLoanDAO.deleteLoan(loan);  // Remove loan from MockLoanDAO
        loans.remove(loan);  // Remove from observable list
        refreshLoans();  // Refresh to ensure consistency
    }
}
