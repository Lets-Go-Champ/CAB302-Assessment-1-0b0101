package com.example.cab302assessment10b0101.controllers;

import com.example.cab302assessment10b0101.model.Book;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

/**
 * The BookController class is responsible for displaying book data within a view.
 * It uses FXML to link UI elements, allowing the book's details (title, author, and cover image) to be shown.
 */
public class BookController {

    //FXML UI elements that are linked to the corresponding components in the view
    @FXML
    private ImageView bookCoverImage; //Displays the cover image of the book

    @FXML
    private Label bookTitle; //Displays the book title

    @FXML
    private Label bookAuthor; //Displays the book author


    /**
     * Sets the book data to be displayed in the UI.
     * This method updates the UI elements (title, author, and cover image) with the book's details.
     *
     * @param book The book object containing the details to be displayed.
     */
    public void setData(Book book){
        if (book != null){
            bookTitle.setText(book.getTitle()); //Sets the label to the book's title
            bookAuthor.setText(book.getAuthor()); //Sets the label to the book's author
            bookCoverImage.setImage(book.getImage()); //Sets the image view to the book's cover image
        }

    }
}