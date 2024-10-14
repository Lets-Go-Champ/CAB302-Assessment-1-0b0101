package com.example.cab302assessment10b0101.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;
import java.time.LocalDate;

public class LoanDAO {
    private static LoanDAO instance;
    private static Connection connection;
    private BookDAO bookDAO;

    private LoanDAO() {
        connection = DatabaseConnector.getInstance();
        bookDAO = BookDAO.getInstance();
    }

    public static synchronized LoanDAO getInstance() {
        if (instance == null) {
            instance = new LoanDAO();
        }
        return instance;
    }

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
            System.err.println("Error creating Loans table: " + ex.getMessage());
        }
    }

    public void insertLoan(Loan loan) {
        String sql = "INSERT INTO Loans (userId, bookId, borrowerName, borrowerContact, issueDate) " +
                "VALUES (?, ?, ?, ?, ?);";

        try (PreparedStatement insertLoan = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            insertLoan.setInt(1, loan.getUserId());
            insertLoan.setInt(2, loan.getBook().getId());
            insertLoan.setString(3, loan.getBorrower());
            insertLoan.setString(4, loan.getBorrowerContact());
            insertLoan.setString(5, loan.getDateAsString());
            insertLoan.executeUpdate();

            try (ResultSet generatedKeys = insertLoan.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    loan.setId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException ex) {
            System.err.println("Error inserting loan: " + ex.getMessage());
        }
    }

    public ObservableList<Loan> getAllLoansByUser(int userId) {
        ObservableList<Loan> loans = FXCollections.observableArrayList();

        String query = "SELECT * FROM Loans WHERE userId = ?";
        try (PreparedStatement getAll = connection.prepareStatement(query)) {
            getAll.setInt(1, userId);

            ResultSet rs = getAll.executeQuery();

            while (rs.next()) {
                Book book = bookDAO.getBookById(rs.getInt("bookId"));
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
            System.err.println("Error retrieving loans: " + e.getMessage());
        }
        return loans;
    }

    public Loan getLoanById(int loanId) {
        String query = "SELECT * FROM Loans WHERE id = ?";
        Loan loan = null;
        try (PreparedStatement getAll = connection.prepareStatement(query)) {
            getAll.setInt(1, loanId);

            ResultSet rs = getAll.executeQuery();

            while (rs.next()) {
                Book book = bookDAO.getBookById(rs.getInt("bookId"));
                loan = new Loan(
                        rs.getInt("userId"),
                        rs.getString("borrowerName"),
                        rs.getString("borrowerContact"),
                        book,
                        LocalDate.parse(rs.getString("issueDate"))
                );
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving loans: " + e.getMessage());
        }
        return loan;
    }

    public void deleteLoan(Loan loan) throws SQLException {
        String sql = "DELETE FROM Loans WHERE userId = ? AND bookId = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, loan.getUserId());
            pstmt.setInt(2, loan.getBook().getId());
            int rowsAffected = pstmt.executeUpdate();
            System.out.println("Rows affected by delete: " + rowsAffected);
        } catch (SQLException e) {
            System.err.println("Error deleting Loan: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Retrieves the loan ID based on the user ID and book ID.
     *
     * @param userId The ID of the user who borrowed the book.
     * @param bookId The ID of the borrowed book.
     * @return The loan ID if found, or -1 if no loan exists for the given user and book.
     * @throws SQLException If a database access error occurs.
     */
    public int getLoanIdByUserAndBook(int userId, int bookId) {
        String query = "SELECT id FROM Loans WHERE user_id = ? AND book_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            // Set the bookId value
            pstmt.setInt(1, userId);
            pstmt.setInt(2, bookId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    int loanId = rs.getInt("id");
                    System.out.println("Found loan ID: " + loanId + " for user ID: " + userId + " and book ID: " + bookId);
                    return loanId;
                } else {
                    System.out.println("No loan found for user ID: " + userId + " and book ID: " + bookId);
                    return -1; // Indicate no loan found
                }
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving loan ID: " + e.getMessage());
        }
        return userId;
    }
}