package com.example.cab302assessment10b0101.controllers;

import com.example.cab302assessment10b0101.model.Book;
import javafx.beans.property.ObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class BookDetailsController {

    @FXML
    private Label titleLabel;

    @FXML
    private Label authorLabel;

    @FXML
    private Label descriptionLabel;

    @FXML
    private ImageView bookCoverImage;

    @FXML
    private Label publicationDateLabel;

    @FXML
    private Label notesTextArea;

    private ObjectProperty<Book> selectedBook;

    public void setData(Book book){
        titleLabel.setText(book.getTitle());
        System.out.println(book.getTitle());
        authorLabel.setText(book.getAuthor());
        descriptionLabel.setText(book.getDescription());
        bookCoverImage.setImage(book.getImage());
    }
}



