package com.example.cab302assessment10b0101;

import com.example.cab302assessment10b0101.model.Book;
import com.example.cab302assessment10b0101.model.Collection;
import com.example.cab302assessment10b0101.model.BookDAO;
import com.example.cab302assessment10b0101.model.CollectionDAO;

import java.util.Scanner;


public class TestHandler {

    private BookDAO bookDAO;
    private CollectionDAO collectionDAO;

    public TestHandler(BookDAO bookDAO, CollectionDAO collectionDAO) {
        this.bookDAO = bookDAO;
        this.collectionDAO = collectionDAO;
    }

    // Method to add a book via command line input
    public void addBook() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Book ID / ISBN:");
        int id = Integer.parseInt(scanner.nextLine());
        System.out.println("Enter Book Title:");
        String title = scanner.nextLine();
        System.out.println("Enter Author:");
        String author = scanner.nextLine();
        System.out.println("Enter Description:");
        String description = scanner.nextLine();
        System.out.println("Enter Publication Year:");
        String publicationDate = scanner.nextLine();
        System.out.println("Enter Publisher:");
        String publisher = scanner.nextLine();
        System.out.println("Enter Number of Pages:");
        int pages = Integer.parseInt(scanner.nextLine());
        System.out.println("Enter Notes:");
        String notes = scanner.nextLine();

        Book book = new Book(id, title, author, description, publicationDate, publisher, pages, notes);
        bookDAO.insert(book);
        System.out.println("Book added successfully!");
    }

    // Method to view all books via command line output
    public void viewBooks() {
        System.out.println("Books in the Database:");
        for (Book book : bookDAO.getAll()) {
            System.out.println(book);
        }
    }

    // Method to add a collection via command line input
    public void addCollection() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Collection Name:");
        String collectionName = scanner.nextLine();
        System.out.println("Enter Collection Description:");
        String collectionDescription = scanner.nextLine();

        Collection collection = new Collection(collectionName, collectionDescription);
        collectionDAO.insert(collection);
        System.out.println("Collection added successfully!");
    }

    // Method to view all collections via command line output
    public void viewCollections() {
        System.out.println("Collections in the Database:");
        for (Collection collection : collectionDAO.getAll()) {
            System.out.println(collection);
        }
    }

    // Method to handle console commands
    public void handleConsoleCommands() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter 'addbook' to add a new book and 'viewbooks' to see all books or enter 'addcollection' to add a new collection and 'viewcollections' to see all collections or 'exit' to leave the console mode.");

        while (true) {
            String command = scanner.nextLine().trim().toLowerCase();
            switch (command) {
                case "addbook":
                    addBook();
                    break;
                case "viewbooks":
                    viewBooks();
                    break;
                case "addcollection":
                    addCollection();
                    break;
                case "viewcollections":
                    viewCollections();
                    break;
                case "exit":
                    System.out.println("Exiting console mode.");
                    return;  // Exit the method, allowing the JavaFX application to continue
                default:
                    System.out.println("Unknown command. Use 'addbook', 'viewbooks', 'addcollection', 'viewcollections', or 'exit'.");
            }
        }
    }
}
