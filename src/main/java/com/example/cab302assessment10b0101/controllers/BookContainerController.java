package com.example.cab302assessment10b0101.controllers;

import com.example.cab302assessment10b0101.model.Book;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ResourceBundle;
import java.net.URL;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class BookContainerController implements Initializable{

    private List<Book> books;

    @FXML
    private GridPane bookContainer;

    @Override
    public void initialize(URL location, ResourceBundle resources){
        books = new ArrayList<>(books());
        int columns = 0;
        int rows = 1;
        try {
            for (Book book : books) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/com/example/cab302assessment10b0101/fxml/book.fxml"));
                VBox bookBox = fxmlLoader.load();
                BookController bookController = fxmlLoader.getController();
                bookController.setCard(book);

                if (columns == 4) {
                    columns = 0;
                    ++rows;
                }

                bookContainer.add(bookBox, columns++, rows);
                GridPane.setMargin(bookBox, new Insets(10));
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<Book> books() {
        List<Book> list = new ArrayList<>();
        Book book = new Book();
        book.setAuthor("John Ronald Reuel Tolkien");
        book.setTitle("The Fellowship of the Ring");
        book.setCoverImage("/com/example/cab302assessment10b0101/images/The-Fellowship-Of-The-Ring-Book-Cover-by-JRR-Tolkien_1-480.jpg");
        list.add(book);
        return list;
    }


}

