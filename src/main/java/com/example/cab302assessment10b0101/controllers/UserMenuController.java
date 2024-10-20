package com.example.cab302assessment10b0101.controllers;

import com.example.cab302assessment10b0101.Utility.AlertManager;
import com.example.cab302assessment10b0101.model.UserManager;
import com.example.cab302assessment10b0101.views.MenuOptions;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
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
     * This method is called when the "Lending" button is clicked.
     * It sets the selected menu item to the LENDING view.
     */
    @FXML
    private void onLendingClicked(){
        ViewManager.getInstance().getViewFactory().getUserSelectedMenuItem().set(MenuOptions.LENDING);
    }

    /**
     * This method is called when the "Add Collection" button is clicked.
     * It sets the selected menu item to the ADDCOLLECTION view.
     */
    @FXML
    private void onAddCollectionClicked(){
        ViewManager.getInstance().getViewFactory().getUserSelectedMenuItem().set(MenuOptions.ADDCOLLECTION);
    }

    /**
     * This method is called when the "Logout" button is clicked.
     * It closes the current stage (window) and shows a logout success alert.
     */
    @FXML
    private void onLogoutClicked() {
        // Immediately return to Login screen and reset any session data before returning to login
        resetUserData();
        goToLoginPage();
        // Show logout success popup
        showLogoutSuccessAlert();
    }

    /**
     * Displays a confirmation alert indicating successful logout.
     */
    private void showLogoutSuccessAlert() {
        AlertManager.getInstance().showAlert("Logout Successful", "You have been logged out successfully.", Alert.AlertType.INFORMATION);
    }

    /**
     * Resets the current user and other session-related data.
     */
    private void resetUserData() {
        UserManager.getInstance().setCurrentUser(null);
        ViewManager.getInstance().getViewFactory().getUserSelectedMenuItem().set(null);
    }

    /**
     * Navigates back to the login page after successful logout.
     */
    private void goToLoginPage() {
        Stage stage = (Stage) myBooksBtn.getScene().getWindow();
        ViewManager.getInstance().getViewFactory().closeStage(stage);
        ViewManager.getInstance().getViewFactory().getLoginScreen();  // Show the login screen again
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
    public void initialize(URL url, ResourceBundle resourceBundle){}
}