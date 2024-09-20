package com.example.cab302assessment10b0101.controllers;

import com.example.cab302assessment10b0101.model.Book;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

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
        if (book != null){
            System.out.println("Populating book details for: " + book.getTitle() + " | Thread: " + Thread.currentThread().getName());  // Ensure it's the JavaFX thread
            bookTitle.setText(book.getTitle());
            bookAuthor.setText(book.getAuthor());
            bookCoverImage.setImage(book.getImage());
        }

    }
}