import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.example.cab302assessment10b0101.model.*;

/**
 * The LoanServiceTest class contains unit tests for the LoanService.
 * It verifies the correct functionality of loan operations such as adding, removing,
 * loading, and refreshing loans using MockLoanDAO.
 */
public class LoanServiceTest {

    private LoanService loanService;
    private MockLoanDAO mockLoanDAO;
    private User testUser;
    private Book testBook;

    /**
     * Sets up the LoanService instance and initializes the MockLoanDAO,
     * a test user, and a test book before each test.
     */
    @BeforeEach
    public void setUp() {
        testUser = new User(1, "John Doe", "password123");
        testBook = new Book(1, "Effective Java", "1234567890", "Joshua Bloch", "A must-read book",
                "2008-05-08", "SomePublisher", 416, "An essential book for Java developers", null, "Unread");
        mockLoanDAO = new MockLoanDAO(); // Use mock DAO for testing
        loanService = new LoanService(testUser.getId()); // Initialize LoanService with test user
    }

    /**
     * Tests that loans are correctly loaded for the current user using the LoanService.
     * Verifies that the correct loans are loaded into the observable list.
     */
    @Test
    public void testLoadLoans() {
        // Insert loans for the test user
        Loan loan1 = new Loan(1, testUser.getUsername(), "1234567890", testBook, java.time.LocalDate.now());
        mockLoanDAO.insertLoan(loan1);

        // Use the loan service to load loans
        loanService.loadLoans();
        ObservableList<Loan> loans = loanService.getLoans();

        assertEquals(1, loans.size());
        assertEquals(loan1, loans.get(0));
    }

    /**
     * Tests that the observable list is correctly refreshed when the loans are reloaded.
     * Verifies that the updated loans are reflected in the observable list.
     */
    @Test
    public void testRefreshLoans() {
        // Insert loans
        Loan loan1 = new Loan(1, testUser.getUsername(), "1234567890", testBook, java.time.LocalDate.now());
        mockLoanDAO.insertLoan(loan1);

        // Add a new loan to mock DAO after loading the first one
        Loan loan2 = new Loan(2, testUser.getUsername(), "0987654321", testBook, java.time.LocalDate.now());
        mockLoanDAO.insertLoan(loan2);

        // Use loan service to refresh loans
        loanService.refreshLoans();
        ObservableList<Loan> loans = loanService.getLoans();

        assertEquals(2, loans.size());
        assertTrue(loans.contains(loan1));
        assertTrue(loans.contains(loan2));
    }

    /**
     * Tests adding a loan through the LoanService.
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

        // Verify the loan is added to the mock DAO
        ObservableList<Loan> daoLoans = mockLoanDAO.getAllLoansByUser(testUser.getId());
        assertEquals(1, daoLoans.size());
        assertEquals(newLoan, daoLoans.get(0));
    }

    /**
     * Tests removing a loan through the LoanService.
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

        // Verify the loan is removed from the mock DAO
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
