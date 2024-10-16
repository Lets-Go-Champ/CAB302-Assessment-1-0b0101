package com.example.cab302assessment10b0101.controllers;

import com.example.cab302assessment10b0101.model.LoanService;
import com.example.cab302assessment10b0101.model.UserManager;
import javafx.fxml.FXML;
import javafx.scene.control.TabPane;

/**
 * The AddBookPageController class manages the functionalities of the
 * "Add Book" page, allowing users to add books either manually or through a search.
 */
public class AddBookPageController {

    // Controllers for different methods of adding books
    @FXML
    private AddBookManuallyController addBookManuallyController;
    // Uncomment this if you implement the search functionality
    // @FXML
    // private AddBookSearchController addBookSearchController;

    @FXML
    public TabPane lendingTabPane;

    // Service classes that might be used in this controller
    private LoanService loanService;
    private UserManager userManager;

    /**
     * Initializes the controller after its root element has been processed.
     */
    public void initialize() {
        // Initialize services, if necessary
        //loanService = LoanService.getInstance(); // Assuming LoanService follows singleton pattern
        //userManager = UserManager.getInstance(); // Assuming UserManager follows singleton pattern

        // Set up initial states or data bindings
        setupTabs();
    }

    /**
     * Constructor for AddBookPageController.
     */
    public AddBookPageController() {
        // Default constructor can be empty or perform specific actions
    }

    /**
     * Sets up the tabs in the TabPane for adding books.
     */
    private void setupTabs() {
        // This method could be used to set up tabs, e.g., populate with content
        // Example: lendingTabPane.getTabs().add(new Tab("Add Manually", ...));
    }

    /**
     * Example method to handle adding a book manually
     */
    public void handleAddBookManually() {
        // Logic to handle the manual addition of a book
        // This could involve passing data to addBookManuallyController
    }

    /**
     * Example method to handle adding a book via search
     */
    public void handleAddBookSearch() {
        // Logic for adding a book via search
        // This could involve calling methods in addBookSearchController
    }
}