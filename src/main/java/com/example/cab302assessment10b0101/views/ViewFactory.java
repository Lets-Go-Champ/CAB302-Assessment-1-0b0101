package com.example.cab302assessment10b0101.views;
import com.example.cab302assessment10b0101.controllers.BookDetailsController;
import com.example.cab302assessment10b0101.controllers.ClientController;
import com.example.cab302assessment10b0101.controllers.EditBookDetailsController;
import com.example.cab302assessment10b0101.controllers.MyBooksController;
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
    private MyBooksController myBooksController;

    private AnchorPane myBooksView;
    private AnchorPane addCollectionView;
    private AnchorPane addBookView;
    private AnchorPane booksDetailsView;
    private AnchorPane editBookDetailsView;

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

    public AnchorPane getMyBooksView() {
        if (myBooksView == null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/cab302assessment10b0101/fxml/BooksDisplay.fxml"));
                myBooksView = loader.load();
                myBooksController = loader.getController(); // Store the controller for later use
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            // Refresh the book list when returning to the view
            myBooksController.reloadBooksForSelectedCollection();
        }
        return myBooksView; // Return the AnchorPane directly
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
                // Initialize FXMLLoader and the controller
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/cab302assessment10b0101/fxml/BookDetailsPage.fxml"));
                booksDetailsView = loader.load();

                BookDetailsController controller = loader.getController();

                // Set listener on userSelectedBook to update the BookDetailsController when a new book is selected
                userSelectedBook.addListener((observable, oldBook, newBook) -> {
                    System.out.println("Book changed: " + (newBook != null ? newBook.getTitle() : "null"));

                    if (newBook != null && controller != null) {
                        System.out.println("Setting data on BookDetailsController for book: " + newBook.getTitle());
                        controller.setData(newBook);
                    }
                });

                // Manually trigger the listener logic for the current value of userSelectedBook
                if (userSelectedBook.get() != null) {
                    System.out.println("Manually setting data for the initially selected book: " + userSelectedBook.get().getTitle());
                    controller.setData(userSelectedBook.get());
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return booksDetailsView;
    }

    public AnchorPane getEditBookDetailsView() {
        if (editBookDetailsView == null) {
            try {
                // Initialize FXMLLoader and the controller
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/cab302assessment10b0101/fxml/EditBookDetails.fxml"));
                editBookDetailsView = loader.load();

                EditBookDetailsController controller = loader.getController();

                // Set listener on userSelectedBook to update the EditBookDetailsController when "edit book" is selected
                userSelectedBook.addListener((observable, oldBook, newBook) -> {
                    System.out.println("Book changed: " + (newBook != null ? newBook.getTitle() : "null"));

                    if (newBook != null && controller != null) {
                        //System.out.println("Setting data on BookDetailsController for book: " + newBook.getTitle());
                        controller.populateFields(newBook);
                    }
                });

                // Manually trigger the listener logic for the current value of userSelectedBook
                if (userSelectedBook.get() != null) {
                    //System.out.println("Manually setting data for the initially selected book: " + userSelectedBook.get().getTitle());
                    controller.populateFields(userSelectedBook.get());
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return editBookDetailsView;
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
