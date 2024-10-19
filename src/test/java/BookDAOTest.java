import com.example.cab302assessment10b0101.model.Book;
import com.example.cab302assessment10b0101.model.MockBookDAO;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The BookDAOTest class contains unit tests for the MockBookDAO, which is used to simulate
 * the behavior of BookDAO in a controlled test environment. These tests validate the core
 * operations of the DAO such as inserting, updating, and retrieving books from the mock data store.
 */
public class BookDAOTest {

    private MockBookDAO mockBookDAO;

    /**
     * Sets up the MockBookDAO instance before each test.
     * This method initializes the mock DAO to ensure each test starts with a fresh instance.
     */
    @BeforeEach
    public void setUp() {
        mockBookDAO = new MockBookDAO();  // Use mock DAO for testing
    }

    /**
     * Tests the insertion of a single book into the MockBookDAO and verifies that
     * the book is correctly stored and retrievable from the mock data store.
     */
    @Test
    public void testInsertBook() {
        Book book = new Book(3, "Test Title", "1234567890", "Test Author", "Test Description",
                "2020-01-01", "Test Publisher", 500, "Test Notes", null, "Unread");
        mockBookDAO.insert(book);

        // Retrieve the inserted book
        ObservableList<Book> books = mockBookDAO.getAll();
        assertEquals(1, books.size());
        assertEquals("Test Title", books.get(0).getTitle());
        assertEquals("Test Author", books.get(0).getAuthor());
    }

    /**
     * Tests the retrieval of all books from the MockBookDAO.
     * Ensures that multiple inserted books are correctly stored and retrievable.
     */
    @Test
    public void testGetAllBooks() {
        // Insert multiple books
        mockBookDAO.insert(new Book(12, "Book 1", "1234567890", "Author 1", "Description 1",
                "2020-01-01", "Publisher 1", 500, "Notes 1", null, "Unread"));
        mockBookDAO.insert(new Book(13, "Book 2", "2234567890", "Author 2", "Description 2",
                "2020-01-01", "Publisher 2", 500, "Notes 2", null, "Read"));

        // Retrieve all books
        ObservableList<Book> books = mockBookDAO.getAll();
        assertEquals(2, books.size());

        // Verify the books' data
        assertEquals("Book 1", books.get(0).getTitle());
        assertEquals("Book 2", books.get(1).getTitle());
    }

    /**
     * Tests retrieving books from the MockBookDAO by collection ID.
     * Ensures that books belonging to a specific collection can be filtered and retrieved correctly.
     */
    @Test
    public void testGetAllByCollection() {
        // Insert books with the same collectionId
        mockBookDAO.insert(new Book(1, "Book 1", "1234567890", "Author 1", "Description 1",
                "2020-01-01", "Publisher 1", 500, "Notes 1", null, "Read"));
        mockBookDAO.insert(new Book(1, "Book 2", "2234567890", "Author 2", "Description 2",
                "2020-01-01", "Publisher 2", 500, "Notes 2", null, "Read"));

        // Retrieve all books by collectionId
        ObservableList<Book> books = mockBookDAO.getAllByCollection(1);
        assertEquals(2, books.size());

        // Verify the books' data
        assertEquals("Book 1", books.get(0).getTitle());
        assertEquals("Book 2", books.get(1).getTitle());
    }

    /**
     * Tests the insertion of a book with null fields (e.g., author, description, notes).
     * Verifies that books with missing data can still be inserted and retrieved.
     */    @Test
    public void testInsertBookWithNullFields() {
        Book book = new Book(7, "Test Title", "1234567890", null, null,
                "2020-01-01", "Test Publisher", 500, null, null, "Unread");
        mockBookDAO.insert(book);

        ObservableList<Book> books = mockBookDAO.getAll();
        assertEquals(1, books.size());
        assertNull(books.get(0).getAuthor());
        assertNull(books.get(0).getDescription());
        assertNull(books.get(0).getNotes());
    }

    /**
     * Tests updating the details of a book in the MockBookDAO.
     * Ensures that book attributes like title and page count can be updated and retrieved correctly.
     */
    @Test
    public void testUpdateBook() {
        // Insert a book
        Book book = new Book(1, "Book 1", "1234567890", "Author 1", "Description 1",
                "2020-01-01", "Publisher 1", 500, "Notes 1", null, "Unread");
        mockBookDAO.insert(book);

        // Update the book's title and pages
        book.setTitle(new SimpleStringProperty("Updated Title"));
        book.setPages(new SimpleIntegerProperty(600));
        mockBookDAO.update(book);

        // Retrieve the updated book
        ObservableList<Book> books = mockBookDAO.getAll();
        assertEquals(1, books.size());
        assertEquals("Updated Title", books.get(0).getTitle());
        assertEquals(600, books.get(0).getPages());
    }



    /**
     * Tests inserting a book with excessively long string fields.
     * Verifies that the MockBookDAO can handle books with long titles and author names.
     */
    @Test
    public void testInsertBookWithLongStrings() {
        String longTitle = "This is a very long title that exceeds the usual length...";
        String longAuthor = "Author with a very long name that goes beyond normal limits...";
        Book book = new Book(1, longTitle, "1234567890", longAuthor, null,
                "2020-01-01", "Test Publisher", 500, "Test Notes", null, "Unread");
        mockBookDAO.insert(book);

        ObservableList<Book> books = mockBookDAO.getAll();
        assertEquals(1, books.size());
        assertEquals(longTitle, books.get(0).getTitle());
        assertEquals(longAuthor, books.get(0).getAuthor());
    }

    /**
     * Tests inserting a book with negative page count.
     * Verifies that the MockBookDAO can handle books with invalid (negative) page numbers.
     */
    @Test
    public void testInsertBookWithNegativePages() {
        Book book = new Book(1, "Test Title", "1234567890", "Test Author",
                "Test Description", "2020-01-01", "Test Publisher",
                -100, "Test Notes", null, "Unread");
        mockBookDAO.insert(book);

        ObservableList<Book> books = mockBookDAO.getAll();
        assertEquals(1, books.size());
        assertTrue(books.get(0).getPages() < 0);
    }

    /**
     * Tests inserting multiple books with the same ID into the MockBookDAO.
     * Ensures that multiple books with the same ID can coexist in the mock data store.
     */
    @Test
    public void testInsertBooksWithSameId() {
        Book book1 = new Book(1, "Test Title 1", "1234567890", "Test Author",
                "Test Description", "2020-01-01", "Test Publisher", 500, "Test Notes", null, "Unread");
        Book book2 = new Book(1, "Test Title 2", "1234567890", "Test Author",
                "Test Description", "2020-01-01", "Test Publisher", 500, "Test Notes", null, "Unread");

        mockBookDAO.insert(book1);
        mockBookDAO.insert(book2);

        ObservableList<Book> books = mockBookDAO.getAll();
        assertEquals(2, books.size());
    }

    /**
     * Tests inserting a book with an invalid reading status.
     * Verifies that the MockBookDAO can handle books with an invalid reading status (not Read, Unread, or Reading).
     */
    @Test
    public void testInsertBookWithInvalidReadingStatus() {
        Book book = new Book(1, "Test Title", "1234567890", "Test Author",
                "Test Description", "2020-01-01", "Test Publisher",
                -100, "Test Notes", null, "Readed");
        mockBookDAO.insert(book);

        ObservableList<Book> books = mockBookDAO.getAll();
        assertEquals(1, books.size());
        assertTrue(books.get(0).getPages() < 0);
    }

    /**
     * Tests retrieving a book by its ID from the MockBookDAO.
     * Verifies that the correct book is returned based on its ID.
     */
    @Test
    public void testGetBookById() {
        // Insert a book
        Book book = new Book(1, "Test Title", "1234567890", "Test Author", "Test Description",
                "2020-01-01", "Test Publisher", 500, "Test Notes", null, "Unread");
        mockBookDAO.insert(book);

        // Retrieve the book by its ID
        Book retrievedBook = mockBookDAO.getBookById(book.getId());
        assertNotNull(retrievedBook);
        assertEquals(book.getId(), retrievedBook.getId());
        assertEquals("Test Title", retrievedBook.getTitle());
    }

    /**
     * Tests deleting a book by its title from the MockBookDAO.
     * Verifies that the book is correctly removed from the mock data store.
     */
    @Test
    public void testDeleteBook() {
        // Insert a book
        Book book = new Book(1, "Test Title", "1234567890", "Test Author", "Test Description",
                "2020-01-01", "Test Publisher", 500, "Test Notes", null, "Unread");
        mockBookDAO.insert(book);

        // Delete the book by its title
        mockBookDAO.deleteBook("Test Title");

        // Verify the book is no longer in the mock data store
        ObservableList<Book> books = mockBookDAO.getAll();
        assertEquals(0, books.size());
    }
}
