package com.example.cab302assessment10b0101.controllers;

import com.example.cab302assessment10b0101.model.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class AddLenderController implements Initializable {

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField addressField;

    @FXML
    private TextField bookNameField;

    private LoanService loanService;

    private LendingPageController mainController;

    public void setMainController(LendingPageController mainController) {
        this.mainController = mainController;
    }

    public void setLoanService(LoanService loanService) {
        this.loanService = loanService;
    }

    @FXML
    private void handleAddLender() {
        String borrowerName = firstNameField.getText() + " " + lastNameField.getText();
        System.out.println("This is the borrowerName: " + firstNameField.getText() + lastNameField.getText());
        String borrowerContact = emailField.getText();
        System.out.println("This is the borrowerContact: " + emailField.getText());
        Book book = BookDAO.getInstance().getBookByName(bookNameField.getText());
        System.out.println("This is the book: " + book);
        LocalDate currentDate = LocalDate.now();

        Loan newLoan = new Loan(UserManager.getInstance().getCurrentUser().getId(), borrowerName, borrowerContact, book, currentDate);
        loanService.addLoan(newLoan);

        clearFields();

        if (mainController != null) {
            mainController.refreshLoans();
        }

    }

    private void clearFields() {
        firstNameField.clear();
        lastNameField.clear();
        emailField.clear();
        addressField.clear();
        bookNameField.clear();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setMainController(mainController);
    }
}