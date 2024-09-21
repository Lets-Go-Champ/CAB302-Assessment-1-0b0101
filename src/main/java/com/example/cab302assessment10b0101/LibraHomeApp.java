package com.example.cab302assessment10b0101;

import com.example.cab302assessment10b0101.model.*;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * The LibraHomeApp class is the main entry point for the library management system application.
 * It extends the JavaFX Application class and initializes the necessary data access objects
 * and the application's initial view upon startup.
 */
public class LibraHomeApp extends Application {

    /**
     * The start method is called when the application is launched. It initializes the necessary
     * data access objects and sets up the initial user interface.
     *
     * @param primaryStage The primary stage for this application, onto which the application
     *                     scene can be set. This is the main window of the application.
     */
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
}
