package com.example.cab302assessment10b0101.controllers;

import com.example.cab302assessment10b0101.model.Loan;
import com.example.cab302assessment10b0101.model.LoanService;
import com.example.cab302assessment10b0101.model.UserManager;
import com.example.cab302assessment10b0101.views.LenderCellFactory;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class LoanViewController implements Initializable {

    @FXML
    private ListView<Loan> loanListView;

    @FXML
    private VBox emptyStateView;

    private LoanService loanService;


    public void setMainController(LendingPageController mainController) {
        this.loanService = new LoanService(UserManager.getInstance().getCurrentUser().getId());
        loadLoans();
    }

    public void setLoanService(LoanService loanService) {
        this.loanService = loanService;
        loadLoans();
    }

    private void loadLoans() {
        loanListView.setItems(loanService.getLoans());
        // Set up the bindings after loading loans
        setupBindings();
    }

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loanListView.setCellFactory(e -> new LenderCellFactory(loanService));
    }
}