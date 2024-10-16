package com.example.cab302assessment10b0101.controllers;

import com.example.cab302assessment10b0101.model.Loan;
import com.example.cab302assessment10b0101.model.LoanService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * The LendingCellController class manages the UI components within each loan cell
 * in the lending list view. It displays information about a loan, including the book
 * title, author, borrower name, and book cover image. The controller also provides
 * functionality to rescind (remove) the loan via an associated button.
 */
public class LendingCellController implements Initializable {

    // FXML UI elements bound to the corresponding elements in the view
    @FXML
    public Label loanBookTitle; // Label to display the book's title

    @FXML
    public Label loanBookAuthor; // Label to display the book's author

    @FXML
    public Label loanBorrowerName; // Label to display the borrower's name

    @FXML
    public Button rescindLoanBtn; // Button to rescind (remove) the loan

    @FXML
    public ImageView loanBookCoverImage; // ImageView to display the book's cover image

    // The loan and loan service used by this controller
    private final Loan loan; //the loan object associated with this cell
    private final LoanService loanService; //Service for managing loan operations

    /**
     * Constructor for LendingCellController, initializing the loan and loanService instances.
     *
     * @param loan        The loan object to be displayed in the cell.
     * @param loanService The LoanService instance for managing loan operations.
     */
    public LendingCellController(Loan loan, LoanService loanService) {
        this.loan = loan;
        this.loanService = loanService;
    }

    /**
     * Initializes the controller when the view is loaded. Populates the UI elements
     * with loan details and sets up the action for the rescind loan button.
     *
     * @param url            The location used to resolve relative paths for the root object, or null if not known.
     * @param resourceBundle The resources used to localize the root object, or null if not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // Set Loan Details
        loanBookTitle.setText(loan.getBook().getTitle()); //Display the book's title
        loanBookAuthor.setText(loan.getBook().getAuthor()); //Display the book's author
        loanBorrowerName.setText(loan.getBorrower()); //Display the borrower's name
        loanBookCoverImage.setImage(loan.getBook().getImage()); // Set ImageView to the book's cover image.

        // Handle rescinding Loan
        rescindLoanBtn.setOnAction(event -> {
            try {
                handleRescindLoan(loan);
            } catch (SQLException e) {
                System.out.println("Error initializing the Lending view controller: " + e.getMessage());
            }
        });
    }

    /**
     * Handles the logic to rescind (remove) a loan. This method removes the specified
     * loan from the database using the LoanService.
     *
     * @param loan The loan to be removed.
     * @throws SQLException If there is an error accessing the database.
     */
    private void handleRescindLoan(Loan loan) throws SQLException {
        loanService.removeLoan(loan);
    }
}
