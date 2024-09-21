import com.example.cab302assessment10b0101.model.DatabaseConnector;

import org.junit.jupiter.api.Test;
import java.sql.Connection;
import static org.junit.jupiter.api.Assertions.*;

/**
 * The DatabaseConnectorTest class contains unit tests for the DatabaseConnector class.
 */
public class DatabaseConnectorTest {

    /**
     * Tests that the singleton instance of DatabaseConnector is always the same.
     */
    @Test
    public void testSingletonInstance() {
        Connection conn1 = DatabaseConnector.getInstance();
        Connection conn2 = DatabaseConnector.getInstance();

        // Ensure both connections are not null
        assertNotNull(conn1);
        assertNotNull(conn2);

        // Ensure both connections are the same instance
        assertEquals(conn1, conn2);
    }

    /**
     * Tests that a connection to the database is successfully established.
     */
    @Test
    public void testConnectionEstablished() {
        Connection connection = DatabaseConnector.getInstance();

        // Ensure the connection is not null
        assertNotNull(connection);

        try {
            assertTrue(connection.isValid(2)); // Timeout of 2 seconds to check validity
        } catch (Exception e) {
            fail("Exception thrown during connection validity check: " + e.getMessage());
        }
    }
}
