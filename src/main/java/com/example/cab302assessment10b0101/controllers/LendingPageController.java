package com.example.cab302assessment10b0101.controllers;

import com.example.cab302assessment10b0101.model.LoanService;
import com.example.cab302assessment10b0101.model.UserManager;
import javafx.fxml.FXML;
import javafx.scene.control.TabPane;

public class LendingPageController {

    private final LoanService loanService;
    public LoanViewController loanViewController;
    public AddLenderController addLenderController;


    @FXML
    public TabPane lendingTabPane;

    public void initialize() {
        loanViewController.setLoanService(loanService);
        addLenderController.setLoanService(loanService);
    }

    public LendingPageController(){
        this.loanService = new LoanService(UserManager.getInstance().getCurrentUser().getId());
    }

    public void refreshLoans() {
        loanService.refreshLoans();
    }

}