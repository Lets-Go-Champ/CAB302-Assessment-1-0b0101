package com.example.cab302assessment10b0101.controllers;
import com.example.cab302assessment10b0101.views.MenuOptions;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import com.example.cab302assessment10b0101.model.ViewManager;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UserMenuController implements Initializable {
    @FXML
    private Button myBooksBtn;
    @FXML
    private Button addBookBtn;
    @FXML
    private Button addCollectionBtn;
    @FXML
    private Button lendingBtn;
    @FXML
    private Button logoutBtn;

    @FXML
    private void onMyBooksClicked(){
        ViewManager.getInstance().getViewFactory().getUserSelectedMenuItem().set(MenuOptions.MYBOOKS);
    }
    @FXML
    private void onAddBookClicked(){
        ViewManager.getInstance().getViewFactory().getUserSelectedMenuItem().set(MenuOptions.ADDBOOK);
    }
    @FXML
    private void onAddCollectionClicked(){
        ViewManager.getInstance().getViewFactory().getUserSelectedMenuItem().set(MenuOptions.ADDCOLLECTION);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
       // addListeners();
    }

    @FXML
    private void onLogoutClicked() {
        showLogoutConfirmation();
    }

    private void showLogoutConfirmation() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/cab302assessment10b0101/fxml/LogoutConfirmation.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Confirm Logout");
            stage.initModality(Modality.APPLICATION_MODAL); // Makes this pop-up modal
            stage.showAndWait(); // Waits for the pop-up to close before returning to the main window
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}