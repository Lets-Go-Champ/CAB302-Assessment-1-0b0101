package com.example.cab302assessment10b0101.controllers;

import com.example.cab302assessment10b0101.model.*;
import com.example.cab302assessment10b0101.views.MenuOptions;
import com.example.cab302assessment10b0101.views.ViewFactory;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MyBooksController implements Initializable {

    @FXML
    private ObservableList<Book> bookListView;

    @FXML
    private ChoiceBox<Collection> collectionsChoiceBox;

    @FXML
    private TextField titleTextField;

    @FXML
    private TextField isbnTextField;

    @FXML
    private TextField authorTextField;

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
        collectionsChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                loadBooks(newValue);
            }
        });
    }

    private void handleBookClick(Book book){
        System.out.println("Book clicked: " + book.getTitle() + " | Thread: " + Thread.currentThread().getName());  // Ensu
        ViewManager.getInstance().getViewFactory().getUserSelectedBook().set(book); // set flag for Book Detail page
        ViewManager.getInstance().getViewFactory().getUserSelectedMenuItem().set(MenuOptions.BOOKDETAILS); // set flag for Book Detail page
        System.out.println("Book " + book + "was clicked");
    }


    private void loadBooks(Collection collection) {
        System.out.println("Loading books for collection: " + collection.getCollectionName());

        Task<ObservableList<Book>> loadBooksTask = new Task<>() {
            @Override
            protected ObservableList<Book> call() throws Exception {
                // Fetch books in a background thread
                System.out.println("Fetching books from database for collection: " + collection.getCollectionName());
                return FXCollections.observableArrayList(BookDAO.getInstance().getAllByCollection(collection));
            }
        };

        loadBooksTask.setOnSucceeded(event -> {
            // Update the UI with the new book list
            System.out.println("Books loaded successfully. Number of books: " + loadBooksTask.getValue().size());
            updateBookGrid(loadBooksTask.getValue());
        });

        loadBooksTask.setOnFailed(event -> {
            // Handle errors if necessary
            System.err.println("Failed to load books: " + loadBooksTask.getException());
        });

        new Thread(loadBooksTask).start();
    }


    private void updateBookGrid(ObservableList<Book> books) {
        Platform.runLater(() -> {
            System.out.println("Updating book grid with " + books.size() + " books.");

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

    private void showLoadingIndicator() {
        // Implement this method to show a loading spinner or progress indicator
    }

    private void hideLoadingIndicator() {
        // Implement this method to hide the loading spinner or progress indicator
    }


}

//ObservableList<Book> books = BookDAO.getInstance().getAll();
//System.out.println("Number of books retrieved: " + books.size());

/*
        int columns = 0;
        int rows = 1;
        int maxColumns = 4;
        try {
            for (Book book : books) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/com/example/cab302assessment10b0101/fxml/Book.fxml"));
                VBox bookBox = null;

                bookBox = fxmlLoader.load();
                BookController bookController = fxmlLoader.getController();
                bookController.setData(book);

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
    }

    }

   // private void setData() {


/*
        for (Book book : books) {
            System.out.println("Book: " + book.getTitle() + " by " + book.getAuthor());
        }
        System.out.println("Books: " + books); // This should print the list of books

        if (bookListView != null) {
            bookListView.setItems(books);
            bookListView.setCellFactory(listView -> new BookCellFactory());
            System.out.println("ListView items set. Item count: " + bookListView.getItems().size());
        } else {
            System.err.println("bookListView is null. Check if it's properly defined in your FXML file.");

        }
    }

    @FXML
    private void handleMyBooks() {
        // Your handling code here
    }
    */

