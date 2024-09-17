package com.example.cab302assessment10b0101.controllers;

import com.example.cab302assessment10b0101.model.Book;
import com.example.cab302assessment10b0101.views.BookCellFactory;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import com.example.cab302assessment10b0101.model.BookDAO;
import java.net.URL;
import java.util.ResourceBundle;

public class MyBooksController implements Initializable {

    @FXML
    private ListView<Book> bookListView;

    @FXML
    private ChoiceBox<String> collectionChoiceBox;

    @FXML
    private TextField titleTextField;

    @FXML
    private TextField isbnTextField;

    @FXML
    private TextField authorTextField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setUpData();
    }

    private void setUpData() {
        ObservableList<Book> books = BookDAO.getInstance().getAll();
        System.out.println("Number of books retrieved: " + books.size());
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
}