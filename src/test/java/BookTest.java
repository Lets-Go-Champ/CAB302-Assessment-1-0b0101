import com.example.cab302assessment10b0101.model.Book;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The BookTest class contains unit tests for the Book class.
 */
public class BookTest {

    private Book book;


    /**
     * Sets up a sample Book object before each test.
     * This method is called before each test case to initialize the Book object with test data.
     */
    @BeforeEach
    public void setUp() {
        byte[] image = new byte[0];  // Initializing an empty byte array for the image
        book = new Book(1, 1, "The Great Test", "1234567890", "Jackson",
                "A thrilling adventure", "01-01-2020", "BestPublisher",
                500, "My favourite book", image, "unread");
    }

    /**
     * Tests the getTitle method to ensure it returns the correct title.
     */
    @Test
    public void testGetTitle() {
        assertEquals("The Great Test", book.getTitle());
    }

    /**
     * Tests the setTitle method by updating the title and checking if the change is reflected.
     */
    @Test
    public void testSetTitle() {
        StringProperty newTitle = new SimpleStringProperty("Another Great Test");
        book.setTitle(newTitle);
        assertEquals("Another Great Test", book.getTitle());
    }

    /**
     * Tests the getId method to verify it returns the correct book ID.
     */
    @Test
    public void testGetId() {
        assertEquals(1, book.getId());
    }

    /**
     * Tests the setId method by setting a new ID and ensuring it updates correctly.
     */
    @Test
    public void testSetId() {
        book.setId(2);
        assertEquals(2, book.getId());
    }

    /**
     * Tests the getISBN method to ensure it returns the correct ISBN.
     */
    @Test
    public void testGetISBN() {
        // Use a string to represent the ISBN
        assertEquals("1234567890", book.getISBN());
    }

    /**
     * Tests the setISBN method by updating the ISBN and verifying the change.
     */
    @Test
    public void testSetISBN() {
        SimpleStringProperty newISBN = new SimpleStringProperty("6543212378");
        book.setISBN(newISBN);
        assertEquals("6543212378", book.getISBN());
    }

    /**
     * Tests the getAuthor method to check it returns the correct author name.
     */
    @Test
    public void testGetAuthor() {
        assertEquals("Jackson", book.getAuthor());
    }

    /**
     * Tests the setAuthor method by updating the author name and verifying the update.
     */
    @Test
    public void testSetAuthor() {
        StringProperty newAuthor = new SimpleStringProperty("John Doe");
        book.setAuthor(newAuthor);
        assertEquals("John Doe", book.getAuthor());
    }

    /**
     * Tests the getDescription method to ensure it returns the correct description.
     */
    @Test
    public void testGetDescription() {
        assertEquals("A thrilling adventure", book.getDescription());
    }

    /**
     * Tests the setDescription method by changing the description and verifying the update.
     */
    @Test
    public void testSetDescription() {
        StringProperty newDescription = new SimpleStringProperty("A mystery novel");
        book.setDescription(newDescription);
        assertEquals("A mystery novel", book.getDescription());
    }

    /**
     * Tests the getPublicationDate method to verify it returns the correct publication date.
     */
    @Test
    public void testGetPublicationDate() {
        assertEquals("01-01-2020", book.getPublicationDate());
    }

    /**
     * Tests the setPublicationDate method by setting a new date and checking the change.
     */
    @Test
    public void testSetPublicationDate() {
        StringProperty newDate = new SimpleStringProperty("12-12-2022");
        book.setPublicationDate(newDate);
        assertEquals("12-12-2022", book.getPublicationDate());
    }

    /**
     * Tests the getPublisher method to ensure it returns the correct publisher name.
     */
    @Test
    public void testGetPublisher() {
        assertEquals("BestPublisher", book.getPublisher());
    }

    /**
     * Tests the setPublisher method by updating the publisher name and verifying the change.
     */
    @Test
    public void testSetPublisher() {
        StringProperty newPublisher = new SimpleStringProperty("AnotherPublisher");
        book.setPublisher(newPublisher);
        assertEquals("AnotherPublisher", book.getPublisher());
    }

    /**
     * Tests the getPages method to check if it returns the correct number of pages.
     */
    @Test
    public void testGetPages() {
        assertEquals(500, book.getPages());
    }

    /**
     * Tests the setPages method by setting a new number of pages and ensuring it updates correctly.
     */
    @Test
    public void testSetPages() {
        SimpleIntegerProperty newPages = new SimpleIntegerProperty(600);
        book.setPages(newPages);
        assertEquals(600, book.getPages());
    }

    /**
     * Tests the getNotes method to verify it returns the correct notes.
     */
    @Test
    public void testGetNotes() {
        assertEquals("My favourite book", book.getNotes());
    }

    /**
     * Tests the setNotes method by updating the notes and checking if the change is reflected.
     */
    @Test
    public void testSetNotes() {
        StringProperty newNotes = new SimpleStringProperty("Updated notes");
        book.setNotes(newNotes);
        assertEquals("Updated notes", book.getNotes());
    }

    /**
     * Tests the getImage method to ensure it returns a non-null image.
     */
    @Test
    public void testGetImage() {
        assertNotNull(book.getImage());  // Ensure the image is not null
    }

    /**
     * Tests the setImage method by setting a new image byte array and verifying the update.
     */
    @Test
    public void testSetImage() {
        byte[] newImage = {1, 2, 3};
        book.setImage(newImage);
        assertArrayEquals(newImage, book.getBytes());  // Compare byte arrays
    }

    /**
     * Tests the validation of setting a negative number of pages.
     * Ensures that setting negative pages throws an IllegalArgumentException.
     * This exception may have to be changed depending on what we are using.
     */
    @Test
    public void testSetNegativePages() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            SimpleIntegerProperty negativePages = new SimpleIntegerProperty(-50);
            book.setPages(negativePages);
        });
        assertEquals("Number of pages cannot be negative", exception.getMessage());
    }
}