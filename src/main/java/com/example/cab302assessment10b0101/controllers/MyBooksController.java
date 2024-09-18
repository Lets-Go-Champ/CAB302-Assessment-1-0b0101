package com.example.cab302assessment10b0101.controllers;

import com.example.cab302assessment10b0101.model.Book;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import com.example.cab302assessment10b0101.model.BookDAO;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MyBooksController implements Initializable {

    @FXML
    private ObservableList<Book> bookListView;

    @FXML
    private ChoiceBox<String> collectionChoiceBox;

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
    }/*
        ObservableList<Book> books = BookDAO.getInstance().getAll();
        System.out.println("Number of books retrieved: " + books.size());


        int columns = 0;
        int rows = 1;
        int maxColumns = 4;
        try {
            for (Book book : books) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/com/example/cab302assessment10b0101/fxml/Book.fxml"));
                VBox bookBox = fxmlLoader.load();
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
    }
