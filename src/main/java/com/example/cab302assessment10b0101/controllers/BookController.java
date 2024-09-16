package com.example.cab302assessment10b0101.controllers;

import com.example.cab302assessment10b0101.model.Book;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.util.Objects;

public class BookController {

    @FXML
    private VBox box;

    @FXML
    private ImageView bookCoverImage;

    @FXML
    private Label bookTitle;

    @FXML
    private Label bookAuthor;

    public void setCard(Book book){
        Image image = new Image(getClass().getResourceAsStream((book.getCoverImage())));
        bookCoverImage.setImage(image);
        bookTitle.setText(book.getTitle());
        bookAuthor.setText(book.getAuthor());
    }
}
