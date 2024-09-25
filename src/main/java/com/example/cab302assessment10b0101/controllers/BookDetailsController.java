package com.example.cab302assessment10b0101.controllers;

import com.example.cab302assessment10b0101.model.Book;
import com.example.cab302assessment10b0101.model.ViewManager;
import com.example.cab302assessment10b0101.views.MenuOptions;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

/**
 * The BookDetailsController class is responsible for displaying detailed information about a selected book.
 * It binds the details of a Book object (title, author, description, etc.) to the corresponding UI elements in the view.
 */
public class BookDetailsController {

    //FXML UI components that are linked to the corresponding components in the view
    @FXML
    private Label mainTitle; //Label to display title of the page

    @FXML
    private Label titleLabel; //Label to display the book title

    @FXML
    private Label authorLabel; //Label to display the book author

    @FXML
    private Label descriptionLabel; //Label to display the book description

    @FXML
    private ImageView bookCoverImage; //ImageView to display the book cover image

    @FXML
    private Label publicationDateLabel; //Label to display the book publication date

    @FXML
    private Label publisherLabel; //Label to display the book publisher

    @FXML
    private Label pagesLabel; //Label to display the book's number of pages

    @FXML
    private Label notesLabel; //Label to display book's notes

    @FXML
    private Label isbnLabel; //Label to display the book's ISBN

    /**
     * Sets the book data of a given book to be displayed in the UI.
     * This method updates each UI element with the corresponding data from the Book object.
     *
     * @param book The Book object whose details are to be displayed.
     */
    public void setData(Book book){
        mainTitle.setText(book.getTitle()); //Set the main page title to the book title
        publicationDateLabel.setText(book.getPublicationDate()); //Set the publication date label to the book's publication date
        publisherLabel.setText(book.getPublisher()); //Set the publisher label to the book's publisher
        titleLabel.setText(book.getTitle()); //Set the book title label to the book title
        isbnLabel.setText(book.getISBNAsString()); //Set the ISBN label to the book's ISBN
        authorLabel.setText(book.getAuthor()); //Set the author label to the book's author
        pagesLabel.setText(book.getPagesAsString()); //Set the pages label to the book's number of pages
        notesLabel.setText(book.getNotes()); //Set the notes label to the book's notes
        descriptionLabel.setText(book.getDescription()); //Set the description label to the book's description
        bookCoverImage.setImage(book.getImage()); //Set ImageView to the book's cover image.
    }

    @FXML
    private void handleEditBookButton(){
        ViewManager.getInstance().getViewFactory().getUserSelectedMenuItem().set(MenuOptions.EDITBOOKDETAILS);
    }
}



