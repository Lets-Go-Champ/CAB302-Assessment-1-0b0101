package com.example.cab302assessment10b0101.controllers;

import com.example.cab302assessment10b0101.model.Book;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class BookCellController implements Initializable {

    private final Book book;


    @FXML
    private VBox box;

    @FXML
    private ImageView bookCoverImage;

    @FXML
    private Label bookTitle;

    @FXML
    private Label bookAuthor;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        System.out.println("Initializing BookCellController for book: " + book.getTitle());
        bookTitle.setText(book.getTitle());
        bookAuthor.setText(book.getAuthor());
        bookCoverImage.setImage(book.getImage());

    }

    public BookCellController(Book book){
        this.book = book;
    }


}
