package com.example.cab302assessment10b0101.views;

import com.example.cab302assessment10b0101.controllers.BookCellController;
import com.example.cab302assessment10b0101.model.Book;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;

public class BookCellFactory extends ListCell<Book> {


    @Override
    protected void updateItem(Book book, boolean empty) {
        super.updateItem(book, empty);
        if(empty){
            setText(null);
            setGraphic(null);
        }
        else{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/cab302assessment10b0101/fxml/BookCell.fxml"));
            BookCellController bookCellController = new BookCellController(book);
            loader.setController(bookCellController);
            setText(null);
            try {
                setGraphic(loader.load());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
