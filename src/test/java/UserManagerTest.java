import com.example.cab302assessment10b0101.model.User;
import com.example.cab302assessment10b0101.model.UserManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserManagerTest {

    private UserManager userManager;
    private User testUser;


    @BeforeEach
    public void setUp() {
        // Initialize the UserManager instance before each test
        userManager = UserManager.getInstance();
        testUser = new User(1, "testUser", "testPassword");
    }

    // Test that the instance of UserManager is a singleton
    @Test
    public void testSingletonInstance() {
        UserManager anotherInstance = UserManager.getInstance();
        assertSame(userManager, anotherInstance);  // Ensure both instances are the same
    }

    // Test set current user method.
    @Test
    public void testSetCurrentUser() {
        userManager.setCurrentUser(testUser);
        assertEquals(testUser, userManager.getCurrentUser());
    }

    // Test get current user method.
    @Test
    public void testGetCurrentUser() {
        userManager.setCurrentUser(testUser);
        User retrievedUser = userManager.getCurrentUser();
        assertEquals(testUser, retrievedUser);
    }

    // Test logging out
    @Test
    public void testLogOut() {
        userManager.setCurrentUser(testUser);  // Set the user first
        userManager.logOut();  // Then log out
        assertNull(userManager.getCurrentUser());  // After logging out, current user should be null
    }

    // Test setting and getting a different user
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
