package com.example.cab302assessment10b0101.controllers;

import com.example.cab302assessment10b0101.model.Loan;
import com.example.cab302assessment10b0101.model.LoanDAO;
import com.example.cab302assessment10b0101.model.LoanService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LendingCellController implements Initializable {
    @FXML
    public Label loanBookTitle;
    @FXML
    public Label loanBookAuthor;
    @FXML
    public Label loanBorrowerName;
    @FXML
    public Button rescindLoanBtn;
    @FXML
    public ImageView loanBookCoverImage;

    private final Loan loan;
    private final LoanService loanService;

    public LendingCellController(Loan loan, LoanService loanService) {
        this.loan = loan;
        this.loanService = loanService; // Initialize the loanService
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loanBookTitle.setText(loan.getBook().getTitle());
        loanBookAuthor.setText(loan.getBook().getAuthor());
        loanBorrowerName.setText(loan.getBorrower());
        loanBookCoverImage.setImage(loan.getBook().getImage()); // Set ImageView to the book's cover image.

        rescindLoanBtn.setOnAction(event -> {
            try {
                handleRescindLoan(loan);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void handleRescindLoan(Loan loan) throws SQLException {
        loanService.removeLoan(loan);
    }
}
