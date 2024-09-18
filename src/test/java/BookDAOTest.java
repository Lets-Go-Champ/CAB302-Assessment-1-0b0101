import com.example.cab302assessment10b0101.model.Book;
import com.example.cab302assessment10b0101.model.MockBookDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BookDAOTest {

    private MockBookDAO mockBookDAO;

    // Set up before each test.
    // Initialises Mock BookDAO.
    @BeforeEach
    public void setUp() {
        mockBookDAO = new MockBookDAO();  // Use mock DAO for testing
    }

    // Tests Insert of a single book into Mock BookDAO.
    @Test
    public void testInsertBook() {
        Book book = new Book(3, "Test Title", 1, "Test Author", "Test Description",
                "2020-01-01", "Test Publisher", 500, "Test Notes", null);
        mockBookDAO.insert(book);

        // Retrieve the inserted book
        List<Book> books = mockBookDAO.getAll();
        assertEquals(1, books.size());
        assertEquals("Test Title", books.get(0).getTitle());
        assertEquals("Test Author", books.get(0).getAuthor());
    }

    @Test
    public void testGetAllBooks() {
        // Insert multiple books
        mockBookDAO.insert(new Book(12, "Book 1", 123, "Author 1", "Description 1",
                "2020-01-01", "Publisher 1", 500, "Notes 1", null));
        mockBookDAO.insert(new Book(13, "Book 2", 1234, "Author 2", "Description 2",
                "2020-01-01", "Publisher 2", 500, "Notes 2", null));

        // Retrieve all books
        List<Book> books = mockBookDAO.getAll();
        assertEquals(2, books.size());

        // Verify the books' data
        assertEquals("Book 1", books.get(0).getTitle());
        assertEquals("Book 2", books.get(1).getTitle());
    }

    // Tests insert with null fields.
    @Test
    public void testInsertBookWithNullFields() {
        Book book = new Book(7, "Test Title", 142, null, null,
                "2020-01-01", "Test Publisher", 500, null, null);
        mockBookDAO.insert(book);

        List<Book> books = mockBookDAO.getAll();
        assertEquals(1, books.size());
        assertNull(books.get(0).getAuthor());
        assertNull(books.get(0).getDescription());
        assertNull(books.get(0).getNotes());
    }

    // Test's insert with long strings.
    @Test
    public void testInsertBookWithLongStrings() {
        String longTitle = "This is a very long title that exceeds the usual length...";
        String longAuthor = "Author with a very long name that goes beyond normal limits...";
        Book book = new Book(1, longTitle, 1, longAuthor, null,
                "2020-01-01", "Test Publisher", 500, "Test Notes", null);
        mockBookDAO.insert(book);

        List<Book> books = mockBookDAO.getAll();
        assertEquals(1, books.size());
        assertEquals(longTitle, books.get(0).getTitle());
        assertEquals(longAuthor, books.get(0).getAuthor());
    }

    // Tests insert with negative pages.
    @Test
    public void testInsertBookWithNegativePages() {
        Book book = new Book(1, "Test Title", 1, "Test Author",
                "Test Description", "2020-01-01", "Test Publisher",
                -100, "Test Notes", null);
        mockBookDAO.insert(book);

        List<Book> books = mockBookDAO.getAll();
        assertEquals(1, books.size());
        assertTrue(books.get(0).getPages() < 0);
    }

    // Tests insert of books with the same ID.
    @Test
    public void testInsertBooksWithSameId() {
        Book book1 = new Book(1, "Test Title 1", 1, "Test Author",
                "Test Description", "2020-01-01", "Test Publisher", 500, "Test Notes", null);
        Book book2 = new Book(1, "Test Title 2", 2, "Test Author",
                "Test Description", "2020-01-01", "Test Publisher", 500, "Test Notes", null);

        mockBookDAO.insert(book1);
        mockBookDAO.insert(book2);

        List<Book> books = mockBookDAO.getAll();
        assertEquals(2, books.size());
    }

}
