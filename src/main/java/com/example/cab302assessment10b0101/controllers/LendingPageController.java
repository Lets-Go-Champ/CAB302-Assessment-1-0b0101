package com.example.cab302assessment10b0101.controllers;

import com.example.cab302assessment10b0101.model.LoanService;
import com.example.cab302assessment10b0101.model.UserManager;
import javafx.fxml.FXML;
import javafx.scene.control.TabPane;


/**
 * The LendingPageController class manages the lending page of the application.
 * It connects the loan view and lender addition controllers with the loan service.
 */
public class LendingPageController {

    // Service used to manage loan operations
    private final LoanService loanService;

    // References to the controllers for the loan view and adding lenders
    public LoanViewController loanViewController;
    public AddLenderController addLenderController;

    // FXML UI element linked to the lending page's TabPane
    @FXML
    public TabPane lendingTabPane;

    /**
     * Initializes the controllers with the shared LoanService instance.
     * Ensures both the loan view and add lender controllers can access loan data.
     */
    public void initialize() {
        loanViewController.setLoanService(loanService);
        addLenderController.setLoanService(loanService);
    }

    /**
     * Constructor for LendingPageController. Initializes the loan service
     * using the ID of the currently logged-in user.
     */
    public LendingPageController(){
        this.loanService = new LoanService(UserManager.getInstance().getCurrentUser().getId());
    }

    /**
     * Refreshes the loans by calling the refreshLoans method on the LoanService.
     */
    public void refreshLoans() {
        loanService.refreshLoans();
    }
}