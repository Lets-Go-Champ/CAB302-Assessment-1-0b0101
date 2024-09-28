package com.example.cab302assessment10b0101.controllers;

import com.example.cab302assessment10b0101.model.Loan;
import com.example.cab302assessment10b0101.views.LenderCellFactory;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ResourceBundle;

public class LendingPageController implements Initializable {

    public ListView<Loan> loanListView;




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    setData();
    loanListView.getItems();
    loanListView.setCellFactory(e -> new LenderCellFactory());
    }

    private void setData(){

    }
}
