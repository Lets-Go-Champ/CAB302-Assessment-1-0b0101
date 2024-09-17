package com.example.cab302assessment10b0101.controllers;

import com.example.cab302assessment10b0101.model.Book;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;


public class MyBooksController implements Initializable {

    private ListView<Book> bookListView;

    @FXML
    public ChoiceBox<String> collectionChoiceBox;

    @FXML
    public TextField titleTextField;

    @FXML
    public TextField isbnTextField;

    @FXML
    public TextField authorTextField;


    private void setUpData() {
        // TODO add functionality here
    }

    @FXML
    private void handleMyBooks() {
    }

    public void initialize(URL url, ResourceBundle resourceBundle){
        setUpData();
        //bookListView.setCellFactory(this::);

    }

}
