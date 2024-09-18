import com.example.cab302assessment10b0101.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    private User user;

    // Set up before each test.
    @BeforeEach
    public void setUp() {
        // Initialize a User object before each test
        user = new User(1, "testUser", "testPassword");
    }

    // Test getID method.
    @Test
    public void testGetId() {
        assertEquals(1, user.getId());
    }

    // Test get username method.
    @Test
    public void testGetUsername() {
        assertEquals("testUser", user.getUsername());
    }

    @Test
    public void testSetUsername() {
        user.setUserName("newUser");
        assertEquals("newUser", user.getUsername());
    }

    // Test getpassword method.
    @Test
    public void testGetPassword() {
        assertEquals("testPassword", user.getPassword());
    }

    // Test set password method.
    @Test
    public void testSetPassword() {
        user.setPassword("newPassword");
        assertEquals("newPassword", user.getPassword());
    }

    // Test for the constructor without id (auto-increment case)
    @Test
    public void testConstructorWithoutId() {
        User userWithoutId = new User("newUser", "newPassword");
        // User's without ID set to 0
        assertEquals(0, userWithoutId.getId());
        assertEquals("newUser", userWithoutId.getUsername());
        assertEquals("newPassword", userWithoutId.getPassword());
    }

    // Test to string method.
    @Test
    public void testToString() {
        String expectedString = "User{id=1, userName='testUser', password='testPassword'}";
        assertEquals(expectedString, user.toString());
    }
}