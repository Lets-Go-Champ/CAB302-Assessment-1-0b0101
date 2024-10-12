package com.example.cab302assessment10b0101.controllers;

import com.example.cab302assessment10b0101.model.ViewManager;
import com.example.cab302assessment10b0101.views.MenuOptions;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * The ClientController class is responsible for managing the main layout of the client interface.
 * It uses a BorderPane layout and dynamically updates the center of the layout based on the user's menu selection.
 * Implements the initialize interface to initialize the controller after the root element has been processed.
 */
public class ClientController implements Initializable {

    //This is a reference to the BorderPane element in the FXML file which is the main layout container.
    @FXML
    private BorderPane mainBorderPane;

    @FXML
    private FontAwesomeIconView profileIcon;

    /**
     * Initializes the controller class. This method is called automatically after the FXML file has been loaded.
     * It sets up a listener to listen to the user's selected menu item and updates the center of the BorderPane accordingly.
     *
     * @param url            The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        // Add the listener to listen for changed to the selected menu item. Based on the value of the new selected menu item the center of the BorderPane is updated to show the corresponding view.
        ViewManager.getInstance().getViewFactory().getUserSelectedMenuItem().addListener((observableValue, oldVal, newVal) -> {

            // Error Catcher to handle newVal if there is issues
            if (newVal == null) {
                return; // Exit early if newVal is null
            }

           //Determines which view to update the center to based on the selected menu item
            switch (newVal) {
                //If Add book is clicked, update border pane center to display add book view
                case ADDBOOK: mainBorderPane.setCenter(ViewManager.getInstance().getViewFactory().getAddBookView());
                    break;
                //If Add collection is clicked, update border pane center to display add collection view
                case ADDCOLLECTION: mainBorderPane.setCenter(ViewManager.getInstance().getViewFactory().getAddCollectionView());
                    break;
                //If user clicks on a book, update border pane center to display the book details page
                case BOOKDETAILS: mainBorderPane.setCenter(ViewManager.getInstance().getViewFactory().getBookDetailsView());
                    break;
                //If user clicks on edit book, update border pane center to display the edit book details page
                case EDITBOOKDETAILS: mainBorderPane.setCenter(ViewManager.getInstance().getViewFactory().getEditBookDetailsView());
                    break;
                //If user clicks on lending, update border pane center to display the lending page
                case LENDING: mainBorderPane.setCenter(ViewManager.getInstance().getViewFactory().getLendingView());
                    break;
                //If user clicks on profile, update border pane center to display the user details page
                case PROFILE: mainBorderPane.setCenter(ViewManager.getInstance().getViewFactory().getProfileView());
                    break;
                //Update border pane center to display my books page view on default
                default: mainBorderPane.setCenter(ViewManager.getInstance().getViewFactory().getMyBooksView());
                    break;
            }
        });
        profileIcon.setOnMouseClicked(event -> getProfileView());

    }
    public void getProfileView() {
        ViewManager.getInstance().getViewFactory().getUserSelectedMenuItem().set(MenuOptions.PROFILE);
    }
}
