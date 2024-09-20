package com.example.cab302assessment10b0101.controllers;

import com.example.cab302assessment10b0101.model.*;
import com.example.cab302assessment10b0101.views.MenuOptions;
import javafx.application.Platform;
import javafx.collections.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MyBooksController implements Initializable {

    @FXML
    private ChoiceBox<Collection> collectionsChoiceBox;

    @FXML
    private GridPane bookContainer;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        populateCollections();
        setupEventHandlers();

        Collection defaultCollection = collectionsChoiceBox.getValue();
        if (defaultCollection != null) {
            loadBooks(defaultCollection);
        }
    }

    private void setupEventHandlers() {
        reloadBooksForSelectedCollection();
        collectionsChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                System.out.println(newValue);
                loadBooks(newValue);
            }
        });
    }

    private void handleBookClick(Book book){
        //System.out.println("Book clicked: " + book.getTitle() + " | Thread: " + Thread.currentThread().getName());  // Ensu
        ViewManager.getInstance().getViewFactory().getUserSelectedBook().set(book); // set flag for Book Detail page
        ViewManager.getInstance().getViewFactory().getUserSelectedMenuItem().set(MenuOptions.BOOKDETAILS); // set flag for Book Detail page
        //System.out.println("Book " + book + "was clicked");
    }

    public void reloadBooksForSelectedCollection() {
        Collection selectedCollection = collectionsChoiceBox.getSelectionModel().getSelectedItem();
        if (selectedCollection != null) {
            //System.out.println("Reloading books for collection: " + selectedCollection.getCollectionName());
            loadBooks(selectedCollection); // Force reloading the books for the selected collection
        } else {
            System.out.println("No collection selected.");
        }
    }

    private void loadBooks(Collection collection) {
        //System.out.println("Loading books for collection: " + collection.getCollectionName());
        //System.out.println("which has an ID of: " + collection.getId());
        User currentUser = UserManager.getInstance().getCurrentUser();
        String collectionName = collection.getCollectionName();
        int collectionId = CollectionDAO.getInstance().getCollectionsIDByUserAndCollectionName(currentUser, collectionName);
        //System.out.println("passing: " + collection);
        ObservableList<Book> books = BookDAO.getInstance().getAllByCollection(collectionId);
        //System.out.println(books);

        updateBookGrid(books);
    }


    private void updateBookGrid(ObservableList<Book> books) {
        Platform.runLater(() -> {
            //System.out.println("Updating book grid with " + books.size() + " books.");

            int columns = 0;
            int rows = 1;
            int maxColumns = 4;

            bookContainer.getChildren().clear(); // Clear existing content

            try {
                for (Book book : books) {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("/com/example/cab302assessment10b0101/fxml/Book.fxml"));
                    VBox bookBox = fxmlLoader.load();
                    BookController bookController = fxmlLoader.getController();
                    bookController.setData(book);

                    bookBox.setOnMouseClicked(event -> handleBookClick(book));

                    bookContainer.add(bookBox, columns, rows);
                    GridPane.setMargin(bookBox, new Insets(10));

                    columns++;
                    if (columns == maxColumns) {
                        columns = 0;  // Reset to the first column
                        rows++;       // Move to the next row
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void populateCollections() {
        User currentUser = UserManager.getInstance().getCurrentUser();
        ObservableList<Collection> collections = currentUser.getCollections();
        collectionsChoiceBox.setItems(collections);

        // Optionally set a default value
        if (!collections.isEmpty()) {
            collectionsChoiceBox.getSelectionModel().selectFirst();
        }
    }
}

