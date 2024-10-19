package com.example.cab302assessment10b0101.model;

import com.example.cab302assessment10b0101.Utility.AlertManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.sql.*;
import java.time.LocalDate;

/**
 * The LoanDAO class provides data access methods for interacting
 * with the Loans table in the database.
 */
public class LoanDAO {

    // DAO for accessing loan data
    private static LoanDAO instance;

    // Database connection
    private static Connection connection;


    /**
     * Constructor to initialize the database connection and dependencies.
     */
    private LoanDAO() {
        connection = DatabaseConnector.getInstance();
    }

    /**
     * Provides the singleton instance of LoanDAO. If the instance does not exist,
     * it creates a new one.
     *
     * @return The singleton instance of LoanDAO.
     */
    public static synchronized LoanDAO getInstance() {
        if (instance == null) {
            instance = new LoanDAO();
        }
        return instance;
    }

    /**
     * Creates the Loans table in the database if it does not already exist.
     * The table contains loan-related information, including user and book details.
     */
    public void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS Loans (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "userId INTEGER NOT NULL," +
                "bookId INTEGER," +
                "borrowerName TEXT NOT NULL," +
                "borrowerContact TEXT," +
                "issueDate TEXT," +
                "FOREIGN KEY (bookId) REFERENCES Books(bookId) ON DELETE CASCADE" +
                ");";

        try (Statement createTable = connection.createStatement()) {
            createTable.execute(sql);
        } catch (SQLException ex) {
            AlertManager.getInstance().showAlert("Error: ", "Failed to create Loan Table.", Alert.AlertType.ERROR);
        }
    }

    /**
     * Inserts a new loan into the Loans table.
     *
     * @param loan The Loan object to be inserted into the database.
     */
    public void insertLoan(Loan loan) {
        String sql = "INSERT INTO Loans (userId, bookId, borrowerName, borrowerContact, issueDate) " +
                "VALUES (?, ?, ?, ?, ?);";

        try (PreparedStatement insertLoan = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            // Set loan details in the prepared statement
            insertLoan.setInt(1, loan.getUserId());
            insertLoan.setInt(2, loan.getBook().getId());
            insertLoan.setString(3, loan.getBorrower());
            insertLoan.setString(4, loan.getBorrowerContact());
            insertLoan.setString(5, loan.getDateAsString());
            insertLoan.executeUpdate();

            // Retrieve the generated loan ID and set it in the Loan object
            try (ResultSet generatedKeys = insertLoan.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    loan.setId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException ex) {
            AlertManager.getInstance().showAlert("Error: ", "Failed to insert Loan into the Database.", Alert.AlertType.ERROR);
        }
    }

    /**
     * Retrieves all loans associated with a specific user from the Loans table.
     *
     * @param userId The ID of the user whose loans are to be retrieved.
     * @return An ObservableList of Loan objects associated with the user.
     */
    public ObservableList<Loan> getAllLoansByUser(int userId) {
        ObservableList<Loan> loans = FXCollections.observableArrayList();

        String query = "SELECT * FROM Loans WHERE userId = ?";
        try (PreparedStatement getAll = connection.prepareStatement(query)) {
            getAll.setInt(1, userId);

            ResultSet rs = getAll.executeQuery();

            // Loop through the result set and create Loan objects
            while (rs.next()) {
                Book book = BookDAO.getInstance().getBookById(rs.getInt("bookId"));
                Loan loan = new Loan(
                        rs.getInt("userId"),
                        rs.getString("borrowerName"),
                        rs.getString("borrowerContact"),
                        book,
                        LocalDate.parse(rs.getString("issueDate"))
                );
                loans.add(loan);
            }
        } catch (SQLException e) {
            AlertManager.getInstance().showAlert("Error: ", "Failed to retrieve Loans from the Database.", Alert.AlertType.ERROR);
        }
        return loans;
    }

    /**
     * Deletes a loan record from the Loans table.
     *
     * @param loan The Loan object to be deleted.
     * @throws SQLException If a database access error occurs, or the SQL statement fails to execute.
     */
    public void deleteLoan(Loan loan) throws SQLException{
        String sql = "DELETE FROM Loans WHERE userId = ? AND bookId = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            // Set user ID and book ID in the prepared statement1
            pstmt.setInt(1, loan.getUserId());
            pstmt.setInt(2, loan.getBook().getId());

            // Execute the delete statement
            pstmt.executeUpdate();
        } catch (SQLException e) {
            AlertManager.getInstance().showAlert("Error: ", "Failed to delete Loan from the Database.", Alert.AlertType.ERROR);
        }
    }
}