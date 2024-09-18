import com.example.cab302assessment10b0101.model.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BookTest {

    private Book book;

    // Set up before each test.
    // Sets up a book object with sample data.
    @BeforeEach
    public void setUp() {
        byte[] image = new byte[0]; // Initializing an empty byte array for the image
        book = new Book("Test Collection", "The Great Test", 12, "Jackson",
                "A thrilling adventure", "01-01-2020", "BestPublisher",
                500, "My favourite book", image);
    }

    // Tests the getCollectionName method.
    @Test
    public void testGetCollectionName() {
        assertEquals("Test Collection", book.getCollectionName());
    }

    // Tests the setCollectionName method.
    @Test
    public void testSetCollectionName() {
        book.setCollectionName("Non-Fiction Collection");
        assertEquals("Non-Fiction Collection", book.getCollectionName());
    }

    // Tests the getTitle method.
    @Test
    public void testGetTitle() {
        assertEquals("The Great Test", book.getTitle());
    }

    // Tests the setTitle method.
    @Test
    public void testSetTitle() {
        book.setTitle("Another Great Test");
        assertEquals("Another Great Test", book.getTitle());
    }

    // Tests the getID method.
    @Test
    public void testGetId() {
        assertEquals(12, book.getId());
    }

    // Tests the setID method.
    @Test
    public void testSetId() {
        book.setId(20);
        assertEquals(20, book.getId());
    }

    // Tests the getAuthor method.
    @Test
    public void testGetAuthor() {
        assertEquals("Jackson", book.getAuthor());
    }

    // Tests the setAuthor method.
    @Test
    public void testSetAuthor() {
        book.setAuthor("John Doe");
        assertEquals("John Doe", book.getAuthor());
    }

    // Tests the getDescription method.
    @Test
    public void testGetDescription() {
        assertEquals("A thrilling adventure", book.getDescription());
    }

    // Tests the setDescription method.
    @Test
    public void testSetDescription() {
        book.setDescription("A mystery novel");
        assertEquals("A mystery novel", book.getDescription());
    }

    // Tests the getPublication method.
    @Test
    public void testGetPublicationDate() {
        assertEquals("01-01-2020", book.getPublicationDate());
    }

    // Tests the setPublication method.
    @Test
    public void testSetPublicationDate() {
        book.setPublicationDate("12-12-2022");
        assertEquals("12-12-2022", book.getPublicationDate());
    }

    // Tests the getPublisher method.
    @Test
    public void testGetPublisher() {
        assertEquals("BestPublisher", book.getPublisher());
    }

    // Tests the setPublisher method.
    @Test
    public void testSetPublisher() {
        book.setPublisher("AnotherPublisher");
        assertEquals("AnotherPublisher", book.getPublisher());
    }

    // Tests the getPages method.
    @Test
    public void testGetPages() {
        assertEquals(500, book.getPages());
    }

    // Tests the setPages method.
    @Test
    public void testSetPages() {
        book.setPages(600);
        assertEquals(600, book.getPages());
    }

    // Tests the getNotes method.
    @Test
    public void testGetNotes() {
        assertEquals("My favourite book", book.getNotes());
    }

    // Tests the setNotes method.
    @Test
    public void testSetNotes() {
        book.setNotes("Updated notes");
        assertEquals("Updated notes", book.getNotes());
    }

    // Tests the getImage method.
    @Test
    public void testGetImage() {
        assertNotNull(book.getImage()); // Ensure the image is not null
    }

    // Tests the setImage method.
    @Test
    public void testSetImage() {
        byte[] newImage = {1, 2, 3}; // Example of setting a new image
        book.setImage(newImage);
        assertArrayEquals(newImage, book.getImage());
    }

    // Tests that the page numbers cannot be negative.
    @Test
    public void testSetNegativePages() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            book.setPages(-50);
        });
        assertEquals("Number of pages cannot be negative", exception.getMessage());
    }
}
