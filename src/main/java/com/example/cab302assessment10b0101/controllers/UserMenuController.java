package com.example.cab302assessment10b0101.controllers;
import com.example.cab302assessment10b0101.model.UserManager;
import com.example.cab302assessment10b0101.views.MenuOptions;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
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


    @FXML
    private void onLogoutClicked(){
        Stage stage = (Stage) myBooksBtn.getScene().getWindow();
        ViewManager.getInstance().getViewFactory().closeStage(stage);
        ViewManager.getInstance().getViewFactory().getLoginScreen();
        UserManager.getInstance().setCurrentUser(null);
        showLogoutSuccessAlert();

    }
    private void showLogoutSuccessAlert() {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Logout Successful");
        alert.setHeaderText(null);  // Optional: Set to null if you don't want a header
        alert.setContentText("You have successfully logged out.");

        // Display the alert and wait for the user to close it
        alert.showAndWait();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){

    }

    //@FXML
    //private void onLogoutClicked() {
        //showLogoutConfirmation();
    //}
/*
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
    */

}