import com.example.cab302assessment10b0101.model.*;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * The LoanServiceTest class contains unit tests for the MockLoanService.
 * It verifies the correct functionality of loan operations such as adding, removing,
 * loading, and refreshing loans using MockLoanService and MockLoanDAO.
 */
public class LoanServiceTest {

    private LoanServiceInterface loanService; // Use LoanServiceInterface for MockLoanService
    private MockLoanDAO mockLoanDAO;
    private User testUser;
    private Book testBook;

    /**
     * Sets up the MockLoanService instance and initializes the MockLoanDAO,
     * a test user, and a test book before each test.
     */
    @BeforeEach
    public void setUp() {
        testUser = new User(1, "John Doe", "password123");
        testBook = new Book(1, "Effective Java", "1234567890", "Joshua Bloch", "A must-read book",
                "2008-05-08", "SomePublisher", 416, "An essential book for Java developers", null, "Unread");
        mockLoanDAO = new MockLoanDAO(); // Use MockLoanDAO for testing
        loanService = new MockLoanService(testUser.getId(), mockLoanDAO);
    }

    /**
     * Tests that loans are correctly loaded for the current user using the MockLoanService.
     * Verifies that the correct loans are loaded into the observable list.
     */
    @Test
    public void testLoadLoans() {
        // Insert loans for the test user into MockLoanDAO
        Loan loan1 = new Loan(1, testUser.getUsername(), "1234567890", testBook, java.time.LocalDate.now());
        mockLoanDAO.insertLoan(loan1);

        // Use the mock loan service to load loans
        loanService.loadLoans();
        ObservableList<Loan> loans = loanService.getLoans();

        assertEquals(1, loans.size());
        assertEquals(loan1, loans.get(0));
    }

    /**
     * Tests adding a loan through the MockLoanService.
     * Verifies that the new loan is added to both the observable list and the mock DAO.
     */
    @Test
    public void testAddLoan() {
        Loan newLoan = new Loan(1, testUser.getUsername(), "1234567890", testBook, java.time.LocalDate.now());
        loanService.addLoan(newLoan);

        // Verify the loan is added to the observable list
        ObservableList<Loan> loans = loanService.getLoans();
        assertEquals(1, loans.size());
        assertEquals(newLoan, loans.get(0));

        // Verify the loan is added to the MockLoanDAO
        ObservableList<Loan> daoLoans = mockLoanDAO.getAllLoansByUser(testUser.getId());
        assertEquals(1, daoLoans.size());
        assertEquals(newLoan, daoLoans.get(0));
    }

    /**
     * Tests removing a loan through the MockLoanService.
     * Verifies that the loan is removed from both the observable list and the mock DAO.
     */
    @Test
    public void testRemoveLoan() {
        Loan loanToRemove = new Loan(1, testUser.getUsername(), "1234567890", testBook, java.time.LocalDate.now());
        mockLoanDAO.insertLoan(loanToRemove);
        loanService.loadLoans();

        // Remove the loan
        loanService.removeLoan(loanToRemove);

        // Verify the loan is removed from the observable list
        ObservableList<Loan> loans = loanService.getLoans();
        assertEquals(0, loans.size());

        // Verify the loan is removed from the MockLoanDAO
        ObservableList<Loan> daoLoans = mockLoanDAO.getAllLoansByUser(testUser.getId());
        assertEquals(0, daoLoans.size());
    }

    /**
     * Tests that the observable list remains empty if no loans exist for the user.
     * Verifies that an empty list is returned if the user has no loans.
     */
    @Test
    public void testLoadLoansWithNoLoans() {
        // Load loans when no loans exist
        loanService.loadLoans();
        ObservableList<Loan> loans = loanService.getLoans();

        // Ensure the list is empty
        assertTrue(loans.isEmpty());
    }
}