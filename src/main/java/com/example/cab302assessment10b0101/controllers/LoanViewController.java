package com.example.cab302assessment10b0101.controllers;

import com.example.cab302assessment10b0101.model.Loan;
import com.example.cab302assessment10b0101.model.LoanService;
import com.example.cab302assessment10b0101.views.LenderCellFactory;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * The LoanViewController class manages the loan list view within the application.
 * It loads and displays loans in a ListView and updates the view based on the loan data.
 */
public class LoanViewController implements Initializable {

    // FXML UI elements linked to corresponding elements in the view
    @FXML
    private ListView<Loan> loanListView;

    @FXML
    private VBox emptyStateView;

    private LoanService loanService;

    /**
     * Sets the LoanService instance for this controller and loads the loans
     * to display them in the ListView.
     *
     * @param loanService The LoanService instance to set.
     */
    public void setLoanService(LoanService loanService) {
        this.loanService = loanService;
        loadLoans();
    }

    /**
     * Loads the loans from the loan service into the ListView and sets up
     * the necessary UI bindings to reflect the state of the loan data.
     */
    private void loadLoans() {
        loanListView.setItems(loanService.getLoans());
        setupBindings();
    }

    /**
     * Configures UI bindings to control the visibility of the loan list and empty state view.
     * The empty state view is shown when there are no loans, and the ListView is shown otherwise.
     */
    private void setupBindings() {
        // Bind the visibility of the empty state view to whether the loanListView has items
        emptyStateView.visibleProperty().bind(Bindings.createBooleanBinding(
                () -> loanListView.getItems().isEmpty(),
                loanListView.getItems()
        ));
        emptyStateView.managedProperty().bind(emptyStateView.visibleProperty());

        // Bind the visibility of the loanListView to whether it has items
        loanListView.visibleProperty().bind(Bindings.createBooleanBinding(
                () -> !loanListView.getItems().isEmpty(),
                loanListView.getItems()
        ));
        loanListView.managedProperty().bind(loanListView.visibleProperty());
    }

    /**
     * Initializes the LoanViewController when the view is loaded. It sets the cell factory
     * for the loan ListView to use custom loan cells from the LenderCellFactory.
     *
     * @param url            The location used to resolve relative paths for the root object, or null if not known.
     * @param resourceBundle The resources used to localize the root object, or null if not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loanListView.setCellFactory(e -> new LenderCellFactory(loanService));
    }
}