package com.example.cab302assessment10b0101.controllers;

import com.example.cab302assessment10b0101.model.Book;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class BookDetailsController {

    @FXML
    private ImageView coverImageView;

    @FXML
    private Label titleLabel;

    @FXML
    private Label authorLabel;

    @FXML
    private Label genreLabel;

    @FXML
    private Label pubDateLabel;

    @FXML
    private Label isbnLabel;

    @FXML
    private Label descriptionLabel;

    @FXML
    private Label notesLabel;

    public void initialize() {
        // Initialization code if needed
    }

//    public void setBook(Book book) {
//        titleLabel.setText("Title: " + book.getTitle());
//        authorLabel.setText("Author: " + book.getAuthor());
//        genreLabel.setText("Genre: " + book.getGenre());
//        pubDateLabel.setText("Publication Date: " + book.getPublicationDate());
//        isbnLabel.setText("ISBN: " + book.getIsbn());
//        descriptionLabel.setText("Description: " + book.getDescription());
//        notesLabel.setText("Notes: " + book.getNotes());
//
//        if (book.getCoverImageUrl() != null) {
//            Image coverImage = new Image(book.getCoverImageUrl());
//            coverImageView.setImage(coverImage);
//        }
//    }
}

