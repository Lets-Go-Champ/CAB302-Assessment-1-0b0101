package com.example.cab302assessment10b0101.views;
import com.example.cab302assessment10b0101.controllers.*;
import com.example.cab302assessment10b0101.model.Book;
import com.example.cab302assessment10b0101.model.UserManager;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * The ViewFactory class is responsible for managing and displaying different views
 * (My Books, Add Collection, Add Book, Book Details, etc.) in the application. It handles
 * loading FXML files, managing controllers, and creating stages for various windows.
 */
public class ViewFactory {

    // Properties for menu options and book selection
    private final ObjectProperty<MenuOptions> userSelectedMenuItem;
    private final ObjectProperty<Book> userSelectedBook;

    // Controllers and views for the various FXML pages
    private AnchorPane myBooksView;
    private AnchorPane addCollectionView;
    private AnchorPane addBookView;
    private AnchorPane booksDetailsView;
    private AnchorPane editBookDetailsView;
    private AnchorPane profileView;
    private AnchorPane lendingView;

    /**
     * Constructor for ViewFactory.
     * Initializes the menu item and book selection properties.
     */
    public ViewFactory(){

        this.userSelectedMenuItem = new SimpleObjectProperty<>();
        this.userSelectedBook = new SimpleObjectProperty<>();
    }

    /**
     * Returns the user's selected menu item property.
     * This property is bound to the currently selected menu option in the UI.
     * @return ObjectProperty<MenuOptions> representing the selected menu item.
     */
    public ObjectProperty<MenuOptions> getUserSelectedMenuItem(){
        return userSelectedMenuItem;
    }

    /**
     * Returns the user's selected book property.
     * This property holds the currently selected book for detailed view.
     * @return ObjectProperty<Book> representing the selected book.
     */
    public ObjectProperty<Book> getUserSelectedBook(){
        return userSelectedBook;
    }

    /**
     * Loads and returns the My Books view.
     * This method reloads the FXML and initializes a fresh MyBooksController each time to ensure
     * that book collections and details are properly refreshed.
     * @return AnchorPane representing the My Books view.
     */
    public AnchorPane getMyBooksView() {
        try {
            // Always reload the FXML to ensure a fresh view and controller each time
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/cab302assessment10b0101/fxml/BooksDisplay.fxml"));
            myBooksView = loader.load();  // Load the view each time
            loader.getController(); // Get a fresh controller instance
        } catch (Exception e) {
            System.out.println("Error loading MyBooksView: " + e.getMessage());
        }
        return myBooksView; // Return the AnchorPane directly
    }

    /**
     * Loads and returns the Add Collection view.
     * Only loads the FXML the first time this method is called. Subsequent calls reuse the same view.
     * @return AnchorPane representing the Add Collection view.
     */
    public AnchorPane getAddCollectionView(){
        if (addCollectionView == null) {
            try{
                addCollectionView = new FXMLLoader(getClass().getResource("/com/example/cab302assessment10b0101/fxml/AddCollection.fxml")).load();
            } catch (Exception e){
                System.out.println("Error loading AddCollectionView: " + e.getMessage());
            }
        }
        return addCollectionView;
    }

    /**
     * Loads and returns the Add Book view.
     * Only loads the FXML the first time this method is called. Subsequent calls reuse the same view.
     * @return AnchorPane representing the Add Book view.
     */
    public AnchorPane getAddBookView(){
        if (addBookView == null) {
            try{
                addBookView = new FXMLLoader(getClass().getResource("/com/example/cab302assessment10b0101/fxml/AddBookManually.fxml")).load();
            } catch (Exception e){
                System.out.println("Error loading AddBookView: " + e.getMessage());
            }
        }
        return addBookView;
    }

    /**
     * Loads and returns the Book Details view.
     * Sets up a listener on the selected book property to update the view whenever the selected book changes.
     * @return AnchorPane representing the Book Details view.
     */
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
                System.out.println("Error loading BookDetailsView: " + e.getMessage());
            }
        }
        return booksDetailsView;
    }

    /**
     * Loads and returns the Edit Book Details view.
     * Loads Edit Book Details view when asked through Book Details page.
     * @return AnchorPane representing the Edit Book Details view.
     */
    public AnchorPane getEditBookDetailsView() {
        if (editBookDetailsView == null) {
            try {
                // Initialize FXMLLoader and the controller
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/cab302assessment10b0101/fxml/editBookDetails.fxml"));
                editBookDetailsView = loader.load();

                EditBookDetailsController controller = loader.getController();

                // Set listener on userSelectedBook to update the EditBookDetailsController when a new book is selected
                userSelectedBook.addListener((observable, oldBook, newBook) -> {
                    if (newBook != null && controller != null) { controller.populateFields(newBook); } });

                // Manually trigger the listener logic for the current value of userSelectedBook
                if (userSelectedBook.get() != null) { controller.populateFields(userSelectedBook.get()); }

            } catch (Exception e) {
                System.out.println("Error loading EditBookDetailsView: " + e.getMessage());
            }
        }
        return editBookDetailsView;
    }

    public AnchorPane getLendingView(){
        try {
            // Always reload the FXML to ensure a fresh view and controller each time
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/cab302assessment10b0101/fxml/main-lending-page.fxml"));
            lendingView = loader.load();  // Load the view each time
            loader.getController(); // Get a fresh controller instance
        } catch (Exception e) {
            System.out.println("Error loading LendingView: " + e.getMessage());
        }
        return lendingView; // Return the AnchorPane directly
    }

    public AnchorPane getProfileView() {
        if (profileView == null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/cab302assessment10b0101/fxml/Profile.fxml"));
                profileView = loader.load();

                ProfileController controller = loader.getController();
                String currentUserUsername = UserManager.getInstance().getCurrentUser().getUsername();
                String currentUserPassword = UserManager.getInstance().getCurrentUser().getPassword();
                controller.populateFields(currentUserUsername, currentUserPassword);
            } catch (Exception e) { e.printStackTrace(); }
        }
        return profileView;
    }

    /**
     * Displays the login screen.
     * Loads the login FXML and creates a new stage for the login window.
     */
    public void getLoginScreen() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/cab302assessment10b0101/fxml/login.fxml"));
        createStage(loader);
    }

    /**
     * Displays the client screen.
     * Loads the client FXML and sets the ClientController to handle the logic.
     */
    public void getClientScreen(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/cab302assessment10b0101/fxml/Client.fxml"));
        ClientController clientController = new ClientController();
        loader.setController(clientController);
        createStage(loader);
    }

    /**
     * Creates and displays a new stage (window) from the given FXMLLoader.
     * This method is used for loading new scenes into a separate window.
     * @param loader FXMLLoader for the FXML file to load.
     */
    private void createStage(FXMLLoader loader){
        Scene scene = null;
        try {
            scene = new Scene(loader.load());
        } catch (Exception e){
            System.out.println("Error creating stage: " + e.getMessage());
        }
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("LibraHome");
        stage.show();
    }

    /**
     * Closes the given stage (window).
     * @param stage The stage to be closed.
     */
    public void closeStage(Stage stage){
        stage.close();
    }
}
