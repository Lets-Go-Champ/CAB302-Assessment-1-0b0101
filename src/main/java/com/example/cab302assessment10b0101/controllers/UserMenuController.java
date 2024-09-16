package com.example.cab302assessment10b0101.controllers;

import com.example.cab302assessment10b0101.views.MenuOptions;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import com.example.cab302assessment10b0101.model.ViewManager;
import javafx.scene.layout.BorderPane;
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

    private void addListeners(){
        myBooksBtn.setOnAction(actionEvent -> onMyBooksClicked());
        addBookBtn.setOnAction(actionEvent -> onAddBookClicked());
        addCollectionBtn.setOnAction(actionEvent -> onAddCollectionClicked());
    }
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
        addListeners();
    }


}
    /*
    @FXML
    private Button myBooksButton;

    @FXML
    private Button addBookButton;

    @FXML
    public void initialize() {
        // Add any necessary initialization logic here
    }

    @FXML
    public void handleMyBooksButtonAction() throws IOException {
        switchScene("/com/example/cab302assessment10b0101/fxml/MyBooks.fxml", myBooksButton);
    }

    @FXML
    public void handleAddBookButtonAction() throws IOException {
        switchScene("/com/example/cab302assessment10b0101/fxml/AddBookManually.fxml", addBookButton);
    }

    private void switchScene(String fxmlPath, Node eventSource) throws IOException {
        // Get the resource using getClassLoader to ensure the path is found
        URL resource = getClass().getResource(fxmlPath);

        // Check if the resource is null (meaning it could not be found)
        if (resource == null) {
            throw new IOException("FXML file not found at: " + fxmlPath);
        }

        FXMLLoader loader = new FXMLLoader(resource);
        Parent root = loader.load();  // Ensure the FXML file is loaded correctly
        Stage stage = (Stage) eventSource.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

     */

