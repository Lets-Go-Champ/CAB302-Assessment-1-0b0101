package com.example.cab302assessment10b0101.views;

import com.example.cab302assessment10b0101.controllers.LendingCellController;
import com.example.cab302assessment10b0101.model.Loan;
import com.example.cab302assessment10b0101.model.LoanService;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;

public class LenderCellFactory extends ListCell<Loan> {

    private final LoanService loanService;

    public LenderCellFactory(LoanService loanService) {
        this.loanService = loanService;
    }

    @Override
    protected void updateItem(Loan loan, boolean empty) {
        super.updateItem(loan, empty);
        if (empty || loan == null) {
            setText(null);
            setGraphic(null);
        } else {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/cab302assessment10b0101/fxml/LenderCell.fxml"));
                LendingCellController lendingCellController = new LendingCellController(loan, loanService);
                loader.setController(lendingCellController);
                setGraphic(loader.load());
            } catch (Exception e) {
                e.printStackTrace();
                setText("Error loading cell");
            }
        }
    }
}
