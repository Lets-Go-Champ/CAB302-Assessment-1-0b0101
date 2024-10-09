import com.example.cab302assessment10b0101.model.Loan;
import com.example.cab302assessment10b0101.model.Book;

import org.junit.jupiter.api.*;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The LoanTest class contains unit tests for the Loan class.
 * It verifies that the Loan class's getters, setters, and utility methods function as expected.
 */
public class LoanTest {
    private Loan loan;
    private Book book;

    /**
     * Sets up a sample Book object and Loan object before all tests.
     * This method is called once before each test case to ensure test isolation.
     */
    @BeforeEach
    public void setUp() {
        book = new Book(1, "Effective Java", 123456, "Joshua Bloch", "Definitely a Book", "2008-05-08", "Addison-Wesley", 416, "A must-read", null, "Unread");
        loan = new Loan(1, "John Doe", "1234567890", book, LocalDate.now());
    }

    /**
     * Tests the getters of the Loan class to ensure they return the correct values.
     */
    @Test
    public void testGetters() {
        assertEquals(1, loan.getUserId());
        assertEquals("John Doe", loan.getBorrower());
        assertEquals("1234567890", loan.getBorrowerContact());
        assertEquals(book, loan.getBook());
        assertEquals(LocalDate.now(), loan.getDate());
    }


    /**
     * Tests the setters of the Loan class by updating values and verifying the changes.
     */
    @Test
    public void testSetters() {
        loan.setUserId(2);
        loan.setBorrower("Jane Doe");
        loan.setBorrowerContact("0987654321");
        Book newBook = new Book(2, "Clean Code", 789101, "Robert C. Martin", "One of the books of all time", "2008-08-11", "Prentice Hall", 464, "Essential for developers", null, "Unread");
        loan.setBook(newBook);
        loan.setDate(LocalDate.now().plusDays(1));

        assertEquals(2, loan.getUserId());
        assertEquals("Jane Doe", loan.getBorrower());
        assertEquals("0987654321", loan.getBorrowerContact());
        assertEquals(newBook, loan.getBook());
        assertEquals(LocalDate.now().plusDays(1), loan.getDate());
    }

    /**
     * Tests the getDateAsString method to ensure it returns the date in string format.
     */
    @Test
    public void testDateAsString() {
        String dateStr = loan.getDateAsString();
        assertEquals(LocalDate.now().toString(), dateStr);
    }
}