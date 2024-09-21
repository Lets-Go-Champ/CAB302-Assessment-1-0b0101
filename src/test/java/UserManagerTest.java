import com.example.cab302assessment10b0101.model.User;
import com.example.cab302assessment10b0101.model.UserManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * The UserManagerTest class contains unit tests for the UserManager class.
 */
public class UserManagerTest {

    private UserManager userManager;
    private User testUser;

    /**
     * Sets up the UserManager and a test User object before each test.
     * This method initializes the objects that will be used in the tests.
     */
    @BeforeEach
    public void setUp() {
        userManager = UserManager.getInstance();
        testUser = new User(1, "testUser", "testPassword");
    }

    /**
     * Tests that the UserManager instance is a singleton.
     * This ensures that only one instance of UserManager exists.
     */
    @Test
    public void testSingletonInstance() {
        UserManager anotherInstance = UserManager.getInstance();
        assertSame(userManager, anotherInstance);  // Ensure both instances are the same
    }

    /**
     * Tests the setCurrentUser method by setting a user and verifying the current user.
     */
    @Test
    public void testSetCurrentUser() {
        userManager.setCurrentUser(testUser);
        assertEquals(testUser, userManager.getCurrentUser());
    }

    /**
     * Tests the getCurrentUser method to ensure it returns the correct user.
     */
    @Test
    public void testGetCurrentUser() {
        userManager.setCurrentUser(testUser);
        User retrievedUser = userManager.getCurrentUser();
        assertEquals(testUser, retrievedUser);
    }

    /**
     * Tests the logOut method to ensure it correctly logs out the user.
     */
    @Test
    public void testLogOut() {
        userManager.setCurrentUser(testUser);  // Set the user first
        userManager.logOut();  // Then log out
        assertNull(userManager.getCurrentUser());  // After logging out, current user should be null
    }

    /**
     * Tests switching between users using the setCurrentUser method.
     */
    @Test
    public void testSwitchUser() {
        User anotherUser = new User(2, "anotherUser", "anotherPassword");
        userManager.setCurrentUser(testUser);  // Set the first user
        assertEquals(testUser, userManager.getCurrentUser());

        // Switch to a new user
        userManager.setCurrentUser(anotherUser);
        assertEquals(anotherUser, userManager.getCurrentUser());
    }
}
