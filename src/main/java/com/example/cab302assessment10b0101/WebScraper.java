package com.example.cab302assessment10b0101;

import com.example.cab302assessment10b0101.controllers.BookDetailsController;
import com.example.cab302assessment10b0101.model.Book;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

import java.io.IOException;

public class WebScraper {

    public void showBookDetails(Book book) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/cab302assessment10b0101/fxml/BookDetailsPage.fxml"));
            Scene scene = new Scene(loader.load());

            BookDetailsController controller = loader.getController();
//            controller.setBook(book);

            Stage detailsStage = new Stage();
            detailsStage.setTitle("Book Details");
            detailsStage.setScene(scene);
            detailsStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Could not load book details");
            alert.setContentText("An error occurred while trying to display the book details.");
            alert.showAndWait();
        }
    }
}
