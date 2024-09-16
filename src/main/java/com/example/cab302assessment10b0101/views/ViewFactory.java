package com.example.cab302assessment10b0101.views;

import com.example.cab302assessment10b0101.controllers.MyBooksController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ViewFactory {

    private AnchorPane myBooksView;
    private AnchorPane addCollectinView;

    public ViewFactory(){}

    public AnchorPane getMyBooksView(){
        if (myBooksView == null) {
            try{
                myBooksView = new FXMLLoader(getClass().getResource("/com/example/cab302assessment10b0101/fxml/BooksDisplay.fxml")).load();
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        return myBooksView;
    }

    public AnchorPane getAddCollectinView(){
        if (myBooksView == null) {
            try{
                myBooksView = new FXMLLoader(getClass().getResource("/com/example/cab302assessment10b0101/fxml/addCollections.fxml")).load();
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        return myBooksView;
    }

    public void getLoginScreen() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/cab302assessment10b0101/fxml/login.fxml"));
        createStage(loader);
    }

    public void getClientScreen(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/cab302assessment10b0101/fxml/Client.fxml"));
        MyBooksController myBooksController = new MyBooksController();
        loader.setController(myBooksController);
        createStage(loader);
    }

    private void createStage(FXMLLoader loader){
        Scene scene = null;
        try {
            scene = new Scene(loader.load());
        } catch (Exception e){
            e.printStackTrace();
        }
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("LibraHome");
        stage.show();
    }

    public void closeStage(Stage stage){
        stage.close();
    }
}
