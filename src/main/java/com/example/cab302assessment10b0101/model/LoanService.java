package com.example.cab302assessment10b0101.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public class LoanService {
    private final LoanDAO loanDAO;
    private int currentUserId; // Add this to keep track of the current user

    public LoanService(int userId) {
        this.loanDAO = LoanDAO.getInstance();
        this.currentUserId = userId;
        loadLoans();
    }

    private final ObservableList<Loan> loans = FXCollections.observableArrayList();

    public void loadLoans() {
        System.out.println("Loading Loans for user + " +  currentUserId);

        loans.setAll(loanDAO.getAllLoansByUser(currentUserId));
    }

    public void refreshLoans() {
        loans.setAll(loanDAO.getAllLoansByUser(currentUserId));
    }

    public ObservableList<Loan> getLoans() {
        return loans;
    }


    public void addLoan(Loan loan) {
        loanDAO.insertLoan(loan);
        loans.add(loan);
    }

    public void removeLoan(Loan loan) throws SQLException {
        loanDAO.deleteLoan(loan);
        loans.remove(loan);
        refreshLoans();
    }

    public void setCurrentUserId(int userId) {
        this.currentUserId = userId;
        loadLoans(); // Reload loans for the new user
    }
}