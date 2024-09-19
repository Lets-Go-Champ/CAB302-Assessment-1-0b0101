package com.example.cab302assessment10b0101.views;
import com.example.cab302assessment10b0101.controllers.BookDetailsController;
import com.example.cab302assessment10b0101.controllers.ClientController;
import com.example.cab302assessment10b0101.model.Book;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ViewFactory {

    private final ObjectProperty<MenuOptions> userSelectedMenuItem;
    private final ObjectProperty<Book> userSelectedBook;

    private AnchorPane myBooksView;
    private AnchorPane addCollectionView;
    private AnchorPane addBookView;
    private AnchorPane booksDetailsView;

    public ViewFactory(){

        this.userSelectedMenuItem = new SimpleObjectProperty<>();
        this.userSelectedBook = new SimpleObjectProperty<>();
    }

    public ObjectProperty<MenuOptions> getUserSelectedMenuItem(){
        return userSelectedMenuItem;
    }
    public ObjectProperty<Book> getUserSelectedBook(){
        return userSelectedBook;
    }

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
        if (addCollectionView == null) {
            try{
                addCollectionView = new FXMLLoader(getClass().getResource("/com/example/cab302assessment10b0101/fxml/AddCollection.fxml")).load();
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        return addCollectionView;
    }

    public AnchorPane getAddBookView(){
        if (addBookView == null) {
            try{
                addBookView = new FXMLLoader(getClass().getResource("/com/example/cab302assessment10b0101/fxml/AddBookManually.fxml")).load();
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        return addBookView;
    }


    public AnchorPane getBookDetailsView() {
        if (booksDetailsView == null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/cab302assessment10b0101/fxml/BookDetailsPage.fxml"));
                booksDetailsView = loader.load();

                BookDetailsController controller = loader.getController();
                userSelectedBook.addListener((observable, oldBook, newBook) -> {
                    if (controller != null && newBook != null) {
                        controller.setData(newBook); // Set the new book data in the controller
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return booksDetailsView;
    }


    public void getLoginScreen() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/cab302assessment10b0101/fxml/login.fxml"));
        createStage(loader);
    }

    public void getClientScreen(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/cab302assessment10b0101/fxml/Client.fxml"));
        ClientController clientController = new ClientController();
        loader.setController(clientController);
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
