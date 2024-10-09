import com.example.cab302assessment10b0101.model.Loan;
import com.example.cab302assessment10b0101.model.MockLoanDAO;
import com.example.cab302assessment10b0101.model.Book;

import javafx.collections.ObservableList;
import org.junit.jupiter.api.*;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class LoanDAOTest {
    private MockLoanDAO loanDAO;
    private Book book1;
    private Book book2;

    /**
     * Sets up the test environment by initializing the MockLoanDAO and sample Book objects and clears the MockLoanDAO data store..
     * This method is called before each test case to ensure test isolation.
     */
    @BeforeEach
    public void setUp() {
        loanDAO = new MockLoanDAO();
        book1 = new Book(1, "Effective Java", "1234567890", "Joshua Bloch", "Definitely a Book", "2008-05-08", "SomePublisher", 416, "A must-read", null, "Unread");
        book2 = new Book(2, "Clean Code", "1234567890", "Robert C. Martin", "One of the books of all time", "2008-08-11", "SomePublisher", 464, "Essential for developers", null, "Unread");
    }

    /**
     * Tests the insertLoan method by adding a new loan and verifying it is stored correctly.
     */
    @Test
    public void testInsertLoan() {
        Loan loan = new Loan(1, "John Doe", "1234567890", book1, LocalDate.now());
        loanDAO.insertLoan(loan);

        assertEquals(1, loan.getId());
        assertEquals(1, loanDAO.getAllLoansByUser(1).size());
    }

    /**
     * Tests the getAllLoansByUser method by inserting multiple loans and verifying the results.
     */
    @Test
    public void testGetAllLoansByUser() {
        Loan loan1 = new Loan(1, "John Doe", "1234567890", book1, LocalDate.now());
        Loan loan2 = new Loan(1, "Jane Doe", "0987654321", book2, LocalDate.now().plusDays(1));
        loanDAO.insertLoan(loan1);
        loanDAO.insertLoan(loan2);

        ObservableList<Loan> loans = loanDAO.getAllLoansByUser(1);
        assertEquals(2, loans.size());
        assertTrue(loans.contains(loan1));
        assertTrue(loans.contains(loan2));
    }

    /**
     * Tests the deleteLoan method by adding and then removing a loan.
     * Verifies that the loan is no longer in the data store.
     */
    @Test
    public void testDeleteLoan() {
        Loan loan = new Loan(1, "John Doe", "1234567890", book1, LocalDate.now());
        loanDAO.insertLoan(loan);
        loanDAO.deleteLoan(loan);

        ObservableList<Loan> loans = loanDAO.getAllLoansByUser(1);
        assertEquals(0, loans.size());
    }

    /**
     * Tests the getLoanIdByUserAndBook method by retrieving a loan ID based on user ID and book ID.
     */
    @Test
    public void testGetLoanIdByUserAndBook() {
        Loan loan = new Loan(1, "John Doe", "1234567890", book1, LocalDate.now());
        loanDAO.insertLoan(loan);

        int loanId = loanDAO.getLoanIdByUserAndBook(1, book1.getId());
        assertEquals(loan.getId(), loanId);
    }

    /**
     * Tests getLoanIdByUserAndBook method with a non-matching combination.
     * Verifies it returns -1 when no loan exists for the given user and book.
     */
    @Test
    public void testGetLoanIdByUserAndBookNoMatch() {
        Loan loan = new Loan(1, "John Doe", "1234567890", book1, LocalDate.now());
        loanDAO.insertLoan(loan);

        int loanId = loanDAO.getLoanIdByUserAndBook(2, book2.getId());
        assertEquals(-1, loanId);
    }
}