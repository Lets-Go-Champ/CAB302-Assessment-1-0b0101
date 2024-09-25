package com.example.cab302assessment10b0101.controllers;
import com.example.cab302assessment10b0101.views.MenuOptions;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.Initializable;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import com.example.cab302assessment10b0101.model.ViewManager;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * The UserMenuController class manages the user interface for the main menu
 * after a user has logged into the application. It handles user interactions
 * with the menu buttons and navigates to different application views.
 */
public class UserMenuController implements Initializable {

    //FXML fields for linking the UI elements in the view
    @FXML
    private Button myBooksBtn; // Button for my Books view
    @FXML
    private Button addBookBtn; // Button for add book view
    @FXML
    private Button addCollectionBtn; // Button for add collection view
    @FXML
    private Button lendingBtn; // Button for lending view (not implemented yet)
    @FXML
    private Button logoutBtn; // Button for logout

    /**
     * This method is called when the "My Books" button is clicked.
     * It sets the selected menu item to the MYBOOKS view.
     */
    @FXML
    private void onMyBooksClicked(){
        ViewManager.getInstance().getViewFactory().getUserSelectedMenuItem().set(MenuOptions.MYBOOKS);
    }

    /**
     * This method is called when the "Add Book" button is clicked.
     * It sets the selected menu item to the ADDBOOK view.
     */
    @FXML
    private void onAddBookClicked(){
        ViewManager.getInstance().getViewFactory().getUserSelectedMenuItem().set(MenuOptions.ADDBOOK);
    }


    /**
     * This method is called when the "Add Collection" button is clicked.
     * It sets the selected menu item to the ADDCOLLECTION view.
     */
    @FXML
    private void onAddCollectionClicked(){
        ViewManager.getInstance().getViewFactory().getUserSelectedMenuItem().set(MenuOptions.ADDCOLLECTION);
    }

    @FXML
    private void onProfileClicked() {
        ViewManager.getInstance().getViewFactory().getUserSelectedMenuItem().set(MenuOptions.PROFILE);
    }


    /**
     * This method is called when the "Logout" button is clicked.
     * It closes the current stage (window) and shows a logout success alert.
     */
    @FXML
    private void onLogoutClicked(){
        Stage stage = (Stage) myBooksBtn.getScene().getWindow();
        ViewManager.getInstance().getViewFactory().closeStage(stage);
        //ViewManager.getInstance().getViewFactory().getLoginScreen();
        //UserManager.getInstance().setCurrentUser(null);
        showLogoutSuccessAlert();
    }

    /**
     * Displays a confirmation alert indicating successful logout.
     */
    private void showLogoutSuccessAlert() {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Logout Successful");
        alert.setHeaderText(null);  // Optional: Set to null if you don't want a header
        alert.setContentText("You have successfully logged out.");

        // Display the alert and wait for the user to close it
        alert.showAndWait();
    }

    /**
     * Initializes the controller after the FXML has been loaded.
     * Currently, it does not perform any specific actions on initialization.
     *
     * @param url The location used to resolve relative paths for the root object,
     *            or null if the location is not known.
     * @param resourceBundle The resource bundle used to localize the root object,
     *                      or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){

    }
}