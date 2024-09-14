package com.example.cab302assessment10b0101;

import com.example.cab302assessment10b0101.exceptions.ErrorMessage;
import com.example.cab302assessment10b0101.model.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.stage.Modality;
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

        bookDAO = new BookDAO();
        bookDAO.createTable();

        collectionDAO = new CollectionDAO();
        collectionDAO.createTable();

        try {
            // Load the login.fxml file and set it as the main scene
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/cab302assessment10b0101/fxml/login.fxml"));
            Scene scene = new Scene(loader.load());

            // Set up the primary stage (main window)
            primaryStage.setTitle("LibraHome - Home Library Catalogue");
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }

        // Initialize TestHandler, which is used for testing databases ------------ TEST CODE TO BE REMOVED UPON FUNCTIONING FRONT END
        // testHandler = new TestHandler(bookDAO, collectionDAO);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
