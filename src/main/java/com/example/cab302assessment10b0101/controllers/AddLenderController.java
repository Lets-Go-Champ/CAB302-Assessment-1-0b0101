package com.example.cab302assessment10b0101.controllers;

import com.example.cab302assessment10b0101.Utility.AlertManager;
import com.example.cab302assessment10b0101.model.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import org.apache.commons.validator.routines.EmailValidator;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

/**
 * The AddLenderController class manages the functionality for adding a new lender.
 * It captures user input from the UI, validates it, and interacts with the data model
 * to create a new loan record.
 */
public class AddLenderController implements Initializable {

    // FXML UI elements bound to the corresponding elements in the view
    @FXML
    private TextField firstNameField; // Input field for the lender's first name

    @FXML
    private TextField lastNameField; // Input field for the lender's last name

    @FXML
    private TextField emailField; // Input field for the lender's email address

    @FXML
    private TextField addressField; // Input field for the lender's address

    @FXML
    private TextField bookNameField; // Input field for the book name being lent

    // Dependencies used for loan management
    private LoanService loanService; //service for handling loan operations
    private LendingPageController mainController; //reference to the main lenging page controller

    /**
     * Sets the reference to the main controller for refreshing the loan view.
     *
     * @param mainController The LendingPageController instance to set.
     */
    public void setMainController(LendingPageController mainController) {
        this.mainController = mainController;
    }

    /**
     * Sets the LoanService instance to be used for loan operations.
     *
     * @param loanService The LoanService instance to set.
     */
    public void setLoanService(LoanService loanService) {
        this.loanService = loanService;
    }

    /**
     * Handles the action of adding a new lender. It validates user input, creates a loan record,
     * clears the input fields, and refreshes the loan view if applicable.
     */
    @FXML
    private void handleAddLender() {
        // Check if all required fields are valid
        if (validLoanFields()) {
            // Concatenate first and last names to create the lender's full name
            String borrowerName = firstNameField.getText() + " " + lastNameField.getText();
            System.out.println("This is the borrowerName: " + borrowerName);

            // Get the lender's email contact
            String borrowerContact = emailField.getText();
            System.out.println("This is the borrowerContact: " + borrowerContact);

            // Retrieve the book object by name from the database
            Book book = BookDAO.getInstance().getBookByName(bookNameField.getText());
            System.out.println("This is the book: " + book);

            // Get the current date to record when the loan was made
            LocalDate currentDate = LocalDate.now();

            // Create a new Loan object with the relevant details
            Loan newLoan = new Loan(UserManager.getInstance().getCurrentUser().getId(), borrowerName, borrowerContact, book, currentDate);

            // Add the new loan to the system using the loan service
            loanService.addLoan(newLoan);

            // Clear input fields after successfully adding the loan
            clearFields();

            // Refresh the loan view if the main controller is set
            if (mainController != null) {
                mainController.refreshLoans();
            }
        }
    }

    /**
     * Clears all input fields after a loan has been added or cancelled.
     */
    private void clearFields() {
        firstNameField.clear();
        lastNameField.clear();
        emailField.clear();
        addressField.clear();
        bookNameField.clear();
    }

    /**
     * Validates the input fields for adding a loan. Ensures that required fields are not empty
     * and that the email is in a valid format.
     *
     * @return true if all fields are valid, false otherwise.
     */
    private boolean validLoanFields() {
        // Retrieve input from the form fields
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String contactField = emailField.getText();
        String address = addressField.getText();
        String bookName = bookNameField.getText();

        // Validate that the first name is not empty
        if (firstName == null || firstName.isEmpty()) {
            AlertManager.getInstance().showAlert("Error: First Name Field", "First name cannot be empty", Alert.AlertType.ERROR);
            return false;
        }

        // Validate that the last name is not empty
        if (lastName == null || lastName.isEmpty()) {
            AlertManager.getInstance().showAlert("Error: Last Name Field", "Last name cannot be empty", Alert.AlertType.ERROR);
            return false;
        }

        // Validate that the email field is not empty and contains a valid email format
        if (contactField == null || contactField.isEmpty()) {
            AlertManager.getInstance().showAlert("Error: Contact Field", "Contact cannot be empty", Alert.AlertType.ERROR);
            return false;
        } else if (!isValidEmail(contactField)) {
            AlertManager.getInstance().showAlert("Error: Contact Field", "Invalid contact format. Use username@example.com", Alert.AlertType.ERROR);
            return false;
        }

        // Validate that the address is not empty
        if (address == null || address.isEmpty()) {
            AlertManager.getInstance().showAlert("Error: Address Field", "Address cannot be empty", Alert.AlertType.ERROR);
            return false;
        }

        // Validate that the book name is not empty and exists in the collection
        if (bookName.isEmpty()) {
            AlertManager.getInstance().showAlert("Error: Book Field", "Book cannot be empty", Alert.AlertType.ERROR);
            return false;
        } else if (BookDAO.getInstance().getBookByName(bookName) == null) {
            AlertManager.getInstance().showAlert("Error: Book Field", "That book does not exist in your collection", Alert.AlertType.ERROR);
            return false;
        }

        return true;
    }

    /**
     * Validates the given email using the EmailValidator from Apache Commons.
     *
     * @param email The email to validate.
     * @return true if the email is valid, false otherwise.
     */
    private boolean isValidEmail(String email) {
        EmailValidator validator = EmailValidator.getInstance();
        return validator.isValid(email);
    }

    /**
     * Initializes the controller when the view is loaded. Sets the main controller if available.
     *
     * @param url            The location used to resolve relative paths for the root object, or null if not known.
     * @param resourceBundle The resources used to localize the root object, or null if not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setMainController(mainController);
    }
}