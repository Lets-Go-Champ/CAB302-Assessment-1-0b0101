package com.example.cab302assessment10b0101.controllers;

import com.example.cab302assessment10b0101.model.Book;
import com.example.cab302assessment10b0101.model.BookDAO;
import com.example.cab302assessment10b0101.model.ViewManager;
import com.example.cab302assessment10b0101.views.MenuOptions;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

public class BookDetailsController {

    @FXML
    private Label titleLabel;
    @FXML
    private Label authorLabel;
    @FXML
    private Label publicationDateLabel;
    @FXML
    private Label isbnLabel;
    @FXML
    private Label descriptionLabel;
    @FXML
    private ImageView coverImageView;

    private BookDAO bookDAO = BookDAO.getInstance();

    // Method to set up book details on the UI
    public void setBookDetails(int bookId) {
        Book book = bookDAO.getBookById(bookId);
        if (book != null) {
            titleLabel.setText(book.getTitle());
            authorLabel.setText(book.getAuthor());
            isbnLabel.setText(String.valueOf(book.getId()));
            descriptionLabel.setText(book.getDescription());
            publicationDateLabel.setText(book.getPublicationDate());

            if (book.getImage() != null) {
                InputStream imageStream = new ByteArrayInputStream(book.getImage());
                Image image = new Image(imageStream);
                coverImageView.setImage(image);
            } else {
                coverImageView.setImage(null);
            }
        }
    }


    @FXML
    private void onMyBooksClicked(){
        ViewManager.getInstance().getViewFactory().getUserSelectedMenuItem().set(MenuOptions.MYBOOKS);
    }
    @FXML
    private void onAddBookClicked(){
        ViewManager.getInstance().getViewFactory().getUserSelectedMenuItem().set(MenuOptions.ADDBOOK);
    }
    @FXML
    private void onAddCollectionClicked(){
        ViewManager.getInstance().getViewFactory().getUserSelectedMenuItem().set(MenuOptions.ADDCOLLECTION);
    }

    public void onLendingClicked(ActionEvent actionEvent) {
    }

    @FXML
    private void onLogoutClicked() {
        showLogoutConfirmation();
    }

    @FXML
    private void onEditClicked() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/cab302assessment10b0101/fxml/EditBook.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Edit Book");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onDeleteClicked() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to delete this book?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Implement deletion logic here
            System.out.println("Book deleted.");
        }
    }

    @FXML
    private void showLogoutConfirmation() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/cab302assessment10b0101/fxml/LogoutConfirmation.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Confirm Logout");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }







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


