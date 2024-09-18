import com.example.cab302assessment10b0101.model.Book;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BookTest {

    private Book book;

    // Set up before each test.
    // Sets up a book object with sample data.
    @BeforeEach
    public void setUp() {
        byte[] image = new byte[0];  // Initializing an empty byte array for the image
        book = new Book(1, 1, "The Great Test", 123456, "Jackson",
                "A thrilling adventure", "01-01-2020", "BestPublisher",
                500, "My favourite book", image);
    }

    // Tests the getTitle method.
    @Test
    public void testGetTitle() {
        assertEquals("The Great Test", book.getTitle());
    }

    // Tests the setTitle method.
    @Test
    public void testSetTitle() {
        StringProperty newTitle = new SimpleStringProperty("Another Great Test");
        book.setTitle(newTitle);
        assertEquals("Another Great Test", book.getTitle());
    }

    // Tests the GetID method.
    @Test
    public void testGetId() {
        assertEquals(1, book.getId());
    }

    // Tests the SetID method.
    @Test
    public void testSetId() {
        book.setId(2);
        assertEquals(2, book.getId());
    }

    // Tests the GetISBN method.
    @Test
    public void testGetISBN() {
        assertEquals(123456, book.getISBN());
    }

    // Tests the setISBN method.
    @Test
    public void testSetISBN() {
        SimpleIntegerProperty newISBN = new SimpleIntegerProperty(654321);
        book.setISBN(newISBN);
        assertEquals(654321, book.getISBN());
    }

    // Tests the getAuthor method.
    @Test
    public void testGetAuthor() {
        assertEquals("Jackson", book.getAuthor());
    }

    // Tests the setAuthor method.
    @Test
    public void testSetAuthor() {
        StringProperty newAuthor = new SimpleStringProperty("John Doe");
        book.setAuthor(newAuthor);
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
        StringProperty newDescription = new SimpleStringProperty("A mystery novel");
        book.setDescription(newDescription);
        assertEquals("A mystery novel", book.getDescription());
    }

    // Tests the getPublicationDate method.
    @Test
    public void testGetPublicationDate() {
        assertEquals("01-01-2020", book.getPublicationDate());
    }

    // Tests the setPublicationDate method.
    @Test
    public void testSetPublicationDate() {
        StringProperty newDate = new SimpleStringProperty("12-12-2022");
        book.setPublicationDate(newDate);
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
        StringProperty newPublisher = new SimpleStringProperty("AnotherPublisher");
        book.setPublisher(newPublisher);
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
        SimpleIntegerProperty newPages = new SimpleIntegerProperty(600);
        book.setPages(newPages);
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
        StringProperty newNotes = new SimpleStringProperty("Updated notes");
        book.setNotes(newNotes);
        assertEquals("Updated notes", book.getNotes());
    }

    // Tests the getImage method.
    @Test
    public void testGetImage() {
        assertNotNull(book.getImage());  // Ensure the image is not null
    }

    // Tests the setImage method.
    @Test
    public void testSetImage() {
        byte[] newImage = {1, 2, 3};
        book.setImage(newImage);
        assertArrayEquals(newImage, book.getBytes());  // Compare byte arrays
    }

    // Test that setting negative pages throws an exception
    @Test
    public void testSetNegativePages() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            SimpleIntegerProperty negativePages = new SimpleIntegerProperty(-50);
            book.setPages(negativePages);
        });
        assertEquals("Number of pages cannot be negative", exception.getMessage());
    }
}