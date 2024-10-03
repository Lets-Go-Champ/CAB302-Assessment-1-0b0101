package com.example.cab302assessment10b0101.controllers;

import com.example.cab302assessment10b0101.model.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import org.apache.commons.validator.routines.EmailValidator;

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

        if (validLoanFields()) {
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


    }

    private void clearFields() {
        firstNameField.clear();
        lastNameField.clear();
        emailField.clear();
        addressField.clear();
        bookNameField.clear();
    }

    private boolean validLoanFields() {
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String contactField = emailField.getText();
        String address = addressField.getText();
        String bookName = bookNameField.getText();

        if (firstName == null || firstName.isEmpty()) {
            String noFirstNameErrorMsg = "First name cannot be empty";
            showAlert("Error: First Name Field", noFirstNameErrorMsg, Alert.AlertType.ERROR);
            return false;
        }

        if (lastName == null || lastName.isEmpty()) {
            String noLastNameErrorMsg = "Last name cannot be empty";
            showAlert("Error: Last Name Field", noLastNameErrorMsg, Alert.AlertType.ERROR);
            return false;
        }

        if (contactField == null || contactField.isEmpty()) {
            showAlert("Error: Contact Field", "Contact cannot be empty", Alert.AlertType.ERROR);
            return false;
        }
        else if (!isValidEmail(contactField)){
            String invalidContactErrorMsg = "Invalid Contact format. Please make sure its in the form of username@example.com ";
            showAlert("Error: Contact Field", invalidContactErrorMsg, Alert.AlertType.ERROR);
            return false;
        }

        if (address == null || address.isEmpty()) {
            String noAddressErrorMsg = "Address cannot be empty";
            showAlert("Error: Address Field", noAddressErrorMsg, Alert.AlertType.ERROR);
            return false;
        }

        if (bookName.isEmpty()){
            String noBookErrorMsg = "Book cannot be empty";
            showAlert("Error: Book Field", noBookErrorMsg, Alert.AlertType.ERROR);
            return false;
        } else if (BookDAO.getInstance().getBookByName(bookName) == null) {
            String noBookMatchErrorMsg = "That book does not exist in your collection";
            showAlert("Error: Book Field",noBookMatchErrorMsg, Alert.AlertType.ERROR);
            return false;
        }

        return true;
    }


    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private boolean isValidEmail(String email) {
        EmailValidator validator = EmailValidator.getInstance();
        return validator.isValid(email);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setMainController(mainController);
    }
}