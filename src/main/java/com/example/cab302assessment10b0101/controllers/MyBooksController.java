package com.example.cab302assessment10b0101.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;


public class MyBooksController {

    @FXML
    public ComboBox<String> collectionComboBox;

    @FXML
    public TextField titleTextField;

    @FXML
    public TextField isbnTextField;

    @FXML
    public TextField authorTextField;

    @FXML
    public void initialize() {
        // TODO add functionality here
        setupEventHandlers();
    }

    private void setupEventHandlers() {
        // TODO add functionality here
    }

    @FXML
    private void handleMyBooks() {
    }

}
