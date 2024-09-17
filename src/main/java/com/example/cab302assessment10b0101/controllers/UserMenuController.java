package com.example.cab302assessment10b0101.controllers;
import com.example.cab302assessment10b0101.views.MenuOptions;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import com.example.cab302assessment10b0101.model.ViewManager;
import java.net.URL;
import java.util.ResourceBundle;

public class UserMenuController implements Initializable {
    @FXML
    private Button myBooksBtn;
    @FXML
    private Button addBookBtn;
    @FXML
    private Button addCollectionBtn;
    @FXML
    private Button lendingBtn;
    @FXML
    private Button logoutBtn;

    /*
    private void addListeners(){
        myBooksBtn.setOnAction(actionEvent -> onMyBooksClicked());
        addBookBtn.setOnAction(actionEvent -> onAddBookClicked());
        addCollectionBtn.setOnAction(actionEvent -> onAddCollectionClicked());
    }
    */

    @FXML
    private void onMyBooksClicked(){
        ViewManager.getInstance().getViewFactory().getUserSelectedMenuItem().set(MenuOptions.MYBOOKS);
    }
    @FXML
    private void onAddBookClicked(){
        ViewManager.getInstance().getViewFactory().getUserSelectedMenuItem().set(MenuOptions.ADDBOOK);
    }
    @FXML
    private void onAddCollectionClicked(){
        ViewManager.getInstance().getViewFactory().getUserSelectedMenuItem().set(MenuOptions.ADDCOLLECTION);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
       // addListeners();
    }

}
/*
    @FXML
    private Button myBooksButton;

    @FXML
    private Button addBookButton;

    @FXML
    private Button logoutButton;

    @FXML
    public void initialize() {
        // Add any necessary initialization logic here
        logoutButton.setOnAction(event -> handleLogoutAction());
    }

    @FXML
    public void handleMyBooksButtonAction() throws IOException {
        switchScene("/com/example/cab302assessment10b0101/fxml/MyBooks.fxml", myBooksButton);
    }

    @FXML
    public void handleAddBookButtonAction() throws IOException {
        switchScene("/com/example/cab302assessment10b0101/fxml/AddBookManually.fxml", addBookButton);
    }

    private void switchScene(String fxmlPath, Node eventSource) throws IOException {
        // Get the resource using getClassLoader to ensure the path is found
        URL resource = getClass().getResource(fxmlPath);

        // Check if the resource is null (meaning it could not be found)
        if (resource == null) {
            throw new IOException("FXML file not found at: " + fxmlPath);
        }

        FXMLLoader loader = new FXMLLoader(resource);
        Parent root = loader.load();  // Ensure the FXML file is loaded correctly
        Stage stage = (Stage) eventSource.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    @FXML
    public void handleLogoutAction() {
        showLogoutConfirmation();
    }

    private void showLogoutConfirmation() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/cab302assessment10b0101/fxml/LogoutConfirmation.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Logout Confirmation");
            stage.initModality(Modality.APPLICATION_MODAL); // This makes the pop-up modal
            stage.showAndWait(); // This waits for the pop-up to close before returning to the main window
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
*/