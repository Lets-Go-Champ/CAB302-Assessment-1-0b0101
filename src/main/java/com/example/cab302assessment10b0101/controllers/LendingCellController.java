package com.example.cab302assessment10b0101.controllers;

import com.example.cab302assessment10b0101.model.Loan;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class LendingCellController implements Initializable {
    public Label loanBookTitle;
    public Label loanBookAuthor;
    public Label loanBorrowerName;
    public Button rescindLoanBtn;

    private final Loan loan;

    public LendingCellController(Loan loan){
        this.loan = loan;
    }

    @Override public void initialize(URL url, ResourceBundle resourceBundle){

    }
}
