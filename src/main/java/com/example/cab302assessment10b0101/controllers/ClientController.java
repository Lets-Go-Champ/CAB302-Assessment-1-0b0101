package com.example.cab302assessment10b0101.controllers;

import com.example.cab302assessment10b0101.model.ViewManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import com.example.cab302assessment10b0101.views.MenuOptions;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.beans.value.ChangeListener;
public class ClientController implements Initializable {

    @FXML
    private BorderPane mainBorderPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        ViewManager.getInstance().getViewFactory().getUserSelectedMenuItem().addListener((observableValue, oldVal, newVal) -> {
            switch (newVal) {
                case ADDBOOK: mainBorderPane.setCenter(ViewManager.getInstance().getViewFactory().getAddBookView());
                    break;
                case ADDCOLLECTION: mainBorderPane.setCenter(ViewManager.getInstance().getViewFactory().getAddCollectinView());
                    break;
                case BOOKDETAILS: mainBorderPane.setCenter(ViewManager.getInstance().getViewFactory().getBookDetailsView());
                    break;
                default: mainBorderPane.setCenter(ViewManager.getInstance().getViewFactory().getMyBooksView());
                    break;
            }
        });
    }
}
