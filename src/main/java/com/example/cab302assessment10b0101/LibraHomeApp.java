package com.example.cab302assessment10b0101;

import com.example.cab302assessment10b0101.model.*;
import javafx.application.Application;
import javafx.stage.Stage;

public class LibraHomeApp extends Application {

    @Override
    public void start(Stage primaryStage) {

        // Initialize UserDAO and create the Users table
        UserDAO.getInstance().createTable();

        // Initialize BookDAO and create the Users table
        BookDAO.getInstance().createTable();

        // Initialize CollectionDAO and create the Users table
        CollectionDAO.getInstance().createTable();

        // Initialize ViewFactor and create the Users table
        ViewManager.getInstance().getViewFactory().getLoginScreen();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
