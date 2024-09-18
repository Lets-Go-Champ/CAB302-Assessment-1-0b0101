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


    public void setData(Book book){
        System.out.println("Initializing BookCellController for book: " + book.getTitle());
        bookTitle.setText(book.getTitle());
        bookAuthor.setText(book.getAuthor());
        bookCoverImage.setImage(book.getImage());
    }


}