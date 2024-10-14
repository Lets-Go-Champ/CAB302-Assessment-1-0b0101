package com.example.cab302assessment10b0101.views;

import com.example.cab302assessment10b0101.controllers.LendingCellController;
import com.example.cab302assessment10b0101.model.Loan;
import com.example.cab302assessment10b0101.model.LoanService;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;

/**
 * The LenderCellFactory class is responsible for generating custom cells
 * in a ListView that display loan data. It extends the ListCell class and
 * leverages JavaFX's FXMLLoader to load FXML views for each loan entry.
 */
public class LenderCellFactory extends ListCell<Loan> {

    // Instance of LoanService to manage loan operations
    private final LoanService loanService;

    /**
     * Constructor for LenderCellFactory. It initializes the factory with a LoanService
     * instance to allow interaction with the loan data.
     *
     * @param loanService The LoanService instance used to manage loans.
     */
    public LenderCellFactory(LoanService loanService) {
        this.loanService = loanService;
    }

    /**
     * Updates the content of each cell in the ListView. If the cell is not empty,
     * it loads the loan data into a custom FXML view. If the cell is empty or null,
     * it clears the content.
     *
     * @param loan  The Loan object to display in the cell.
     * @param empty Indicates whether the cell is empty.
     */
    @Override
    protected void updateItem(Loan loan, boolean empty) {
        // Call the sueprclass method
        super.updateItem(loan, empty);

        // Clear the cell content if empty or no loan is provided
        if (empty || loan == null) {
            setText(null);
            setGraphic(null);
        } else {
            try {
                // Load the FXML file for the custom loan cell
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/cab302assessment10b0101/fxml/LenderCell.fxml"));

                // Initialize the controller with the loan and loan service
                LendingCellController lendingCellController = new LendingCellController(loan, loanService);
                loader.setController(lendingCellController);

                // Set the loaded FXML content as the cell's graphic
                setGraphic(loader.load());
            } catch (Exception e) {
                // Handle any exceptions during the loading process
                System.out.println("Error updating item in LenderCellFactory: " + e.getMessage());
                setText("Error loading cell");
            }
        }
    }
}
