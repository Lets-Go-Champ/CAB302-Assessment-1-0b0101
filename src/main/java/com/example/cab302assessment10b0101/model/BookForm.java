package com.example.cab302assessment10b0101.model;

import com.example.cab302assessment10b0101.Utility.AlertManager;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * The BookForm class is a parents class for AddBookManually and EditBookDetails.
 * It outlines functionality that is shared between both child classes.
 */
public class BookForm {

    /**
     * Allows the user to upload an image file (JPG or PNG) using a file chooser
     * and displays the uploaded image in a confirmation dialog.
     *
     * @return The uploaded {@link Image}, or {@code null} if an error occurs.
     */
    protected Image uploadImage() {
        try {
            // FileChooser for uploading a book image.
            FileChooser fileChooser = new FileChooser();

            // Create a window to upload the image
            Stage dialogStage = new Stage();

            // Set allowed file types
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("JPG Files", "*.jpg"),
                    new FileChooser.ExtensionFilter("PNG Files", "*.png")
            );
            File selectedFile = fileChooser.showOpenDialog(dialogStage);

            // Display the image that was uploaded
            Image image = new Image(String.valueOf(selectedFile));
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(300);
            imageView.setFitHeight(400);

            // Display success message with an image preview
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Image Loaded Successfully!");
            alert.setHeaderText("The selected image was successfully loaded.");
            alert.setGraphic(imageView);
            alert.showAndWait();
            return image;

        } catch ( Exception e ) {
            AlertManager.getInstance().showAlert("Error", "Could not load an image.", Alert.AlertType.ERROR);
            return null;
        }
    }

    /**
     * Converts the image file at the specified path to a byte array.
     * @param imagePath The file path of the image.
     * @return A byte array of the image or an empty array if conversion fails.
     */
    protected byte[] imageToBytes(String imagePath) {
        try {
            Path path = Paths.get(imagePath);
            return Files.readAllBytes(path);
        } catch ( Exception e ) {
            AlertManager.getInstance().showAlert("Error", "Could not convert the image to a byte array.", Alert.AlertType.ERROR); return new byte[0];
        }
    }
}
