package com.example.cab302assessment10b0101.model;
import com.example.cab302assessment10b0101.model.MockLoanDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.stream.Collectors;
import java.util.*;

/**
 * The MockLoanDAO class is a mock implementation of the LoanDAOInterface.
 * It simulates LoanDAO's behavior for unit testing, storing Loan objects in-memory
 * and providing methods to mimic database operations.
 */
public class MockLoanDAO implements LoanDAOInterface {

    private final List<Loan> loans = new ArrayList<>();
    private int currentId = 1; // Simulate auto-incrementing ID

    /**
     * Inserts a new loan into the mock data store, assigning an auto-incremented ID.
     *
     * @param loan The Loan object to be inserted.
     */
    @Override
    public void insertLoan(Loan loan) {
        loan.setId(currentId++);
        loans.add(loan);
    }

    /**
     * Retrieves all loans associated with a specific user ID.
     *
     * @param userId The ID of the user whose loans are to be retrieved.
     * @return An ObservableList of Loan objects belonging to the specified user.
     */
    @Override
    public ObservableList<Loan> getAllLoansByUser(int userId) {
        List<Loan> userLoans = loans.stream()
                .filter(loan -> loan.getUserId() == userId)
                .collect(Collectors.toList());
        return FXCollections.observableArrayList(userLoans);
    }

    /**
     * Deletes a specific loan from the mock data store.
     *
     * @param loan The Loan object to be deleted.
     */
    @Override
    public void deleteLoan(Loan loan) {
        loans.removeIf(existingLoan ->
                existingLoan.getUserId() == loan.getUserId() &&
                        existingLoan.getBook().getBookId() == loan.getBook().getBookId());
    }

    /**
     * Retrieves the loan ID based on user ID and book ID, or returns -1 if not found.
     *
     * @param userId The user ID associated with the loan.
     * @param bookId The book ID associated with the loan.
     * @return The ID of the loan if found, otherwise -1.
     */
    @Override
    public int getLoanIdByUserAndBook(int userId, int bookId) {
        return loans.stream()
                .filter(loan -> loan.getUserId() == userId && loan.getBook().getBookId() == bookId)  // Use getBookId() here
                .map(Loan::getId)
                .findFirst()
                .orElse(-1);
    }

}