package com.example.cab302assessment10b0101;

import com.example.cab302assessment10b0101.model.*;
import com.example.cab302assessment10b0101.views.ViewFactory;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class LibraHomeApp extends Application {

    // Data Access Objects for interacting with SQLite tables
    private UserDAO userDAO;
    private BookDAO bookDAO;
    private CollectionDAO collectionDAO;

    // Instantiate TestHandler, which is used for testing databases ------------ TEST CODE TO BE REMOVED UPON FUNCTIONING FRONT END
    // private TestHandler testHandler;


    @Override
    public void start(Stage primaryStage) {
        // Initialize UserDAO and create the Users table
        userDAO = new UserDAO();
        userDAO.createTable();

       // bookDAO = new BookDAO();
        BookDAO.getInstance().createTable();

        //collectionDAO = new CollectionDAO();
        CollectionDAO.getInstance().createTable();

        ViewManager.getInstance().getViewFactory().getLoginScreen();

        // Initialize TestHandler, which is used for testing databases ------------ TEST CODE TO BE REMOVED UPON FUNCTIONING FRONT END
        // testHandler = new TestHandler(bookDAO, collectionDAO);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
