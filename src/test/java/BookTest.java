import com.example.cab302assessment10b0101.model.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BookTest {

    private Book book;

    @BeforeEach
    public void setUp() {
        // Initialize a Book object with all the required inputs
        byte[] image = new byte[0]; // Example of initializing an empty byte array for the image
        book = new Book("Test Collection", "The Great Test", 12, "Jackson",
                "A thrilling adventure", "01-01-2020", "BestPublisher",
                500, "My favourite book", image);
    }

    @Test
    public void testGetCollectionName() {
        assertEquals("Test Collection", book.getCollectionName());
    }

    @Test
    public void testSetCollectionName() {
        book.setCollectionName("Non-Fiction Collection");
        assertEquals("Non-Fiction Collection", book.getCollectionName());
    }

    @Test
    public void testGetTitle() {
        assertEquals("The Great Test", book.getTitle());
    }

    @Test
    public void testSetTitle() {
        book.setTitle("Another Great Test");
        assertEquals("Another Great Test", book.getTitle());
    }

    @Test
    public void testGetId() {
        assertEquals(12, book.getId());
    }

    @Test
    public void testSetId() {
        book.setId(20);
        assertEquals(20, book.getId());
    }

    @Test
    public void testGetAuthor() {
        assertEquals("Jackson", book.getAuthor());
    }

    @Test
    public void testSetAuthor() {
        book.setAuthor("John Doe");
        assertEquals("John Doe", book.getAuthor());
    }

    @Test
    public void testGetDescription() {
        assertEquals("A thrilling adventure", book.getDescription());
    }

    @Test
    public void testSetDescription() {
        book.setDescription("A mystery novel");
        assertEquals("A mystery novel", book.getDescription());
    }

    @Test
    public void testGetPublicationDate() {
        assertEquals("01-01-2020", book.getPublicationDate());
    }

    @Test
    public void testSetPublicationDate() {
        book.setPublicationDate("12-12-2022");
        assertEquals("12-12-2022", book.getPublicationDate());
    }

    @Test
    public void testGetPublisher() {
        assertEquals("BestPublisher", book.getPublisher());
    }

    @Test
    public void testSetPublisher() {
        book.setPublisher("AnotherPublisher");
        assertEquals("AnotherPublisher", book.getPublisher());
    }

    @Test
    public void testGetPages() {
        assertEquals(500, book.getPages());
    }

    @Test
    public void testSetPages() {
        book.setPages(600);
        assertEquals(600, book.getPages());
    }

    @Test
    public void testGetNotes() {
        assertEquals("My favourite book", book.getNotes());
    }

    @Test
    public void testSetNotes() {
        book.setNotes("Updated notes");
        assertEquals("Updated notes", book.getNotes());
    }

    @Test
    public void testGetImage() {
        assertNotNull(book.getImage()); // Ensure the image is not null
    }

    @Test
    public void testSetImage() {
        byte[] newImage = {1, 2, 3}; // Example of setting a new image
        book.setImage(newImage);
        assertArrayEquals(newImage, book.getImage());
    }

    // Boundary Test: Ensuring the number of pages cannot be negative
    @Test
    public void testSetNegativePages() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            book.setPages(-50);
        });
        assertEquals("Number of pages cannot be negative", exception.getMessage());
    }
}
