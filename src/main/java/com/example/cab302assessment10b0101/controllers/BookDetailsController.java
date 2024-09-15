package com.example.cab302assessment10b0101.controllers;

import com.example.cab302assessment10b0101.model.Book;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class BookDetailsController {

    @FXML
    private Label titleLabel;
    @FXML
    private Label authorLabel;
    @FXML
    private Label genreLabel;
    @FXML
    private Label publicationDateLabel;
    @FXML
    private Label isbnLabel;
    @FXML
    private Label descriptionLabel;
    @FXML
    private ImageView coverImageView;

//    public void setBook(Book book) {
//        titleLabel.setText(book.getTitle());
//        authorLabel.setText(book.getAuthor());
//        genreLabel.setText(book.getGenre());
//        publicationDateLabel.setText(book.getPublicationDate());
//        isbnLabel.setText(book.getIsbn());
//        descriptionLabel.setText(book.getDescription());
//        coverImageView.setImage(new Image(book.getCoverImage()));
//    }
}


