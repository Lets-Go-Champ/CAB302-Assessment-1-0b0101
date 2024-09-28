package com.example.cab302assessment10b0101.views;

import com.example.cab302assessment10b0101.controllers.LendingCellController;
import com.example.cab302assessment10b0101.model.Loan;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;

public class LenderCellFactory extends ListCell<Loan> {
    @Override
    protected void updateItem(Loan loan, boolean empty){
        super.updateItem(loan, empty);
        if(empty){
            setText(null);
            setGraphic(null);
        } else{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/cab302assessment10b0101/fxml/LenderCell.fxml"));
            LendingCellController lendingCellController = new LendingCellController(loan);
            loader.setController(lendingCellController);
            setText(null);
            try{
                setGraphic(loader.load());
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
