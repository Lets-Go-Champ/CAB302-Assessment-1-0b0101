import com.example.cab302assessment10b0101.model.User;
import com.example.cab302assessment10b0101.model.Collection;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * The UserTest class contains unit tests for the User class.
 */
public class UserTest {

    // Instance variable for User
    private User user;

    /**
     * Sets up the User object before each test.
     * This method initializes the User instance used in the tests.
     */
    @BeforeEach
    public void setUp() {
        // Initialize a User object before each test
        user = new User(1, "testUser", "testPassword");
    }

    /**
     * Tests the getId method to ensure it returns the correct user ID.
     */
    @Test
    public void testGetId() {
        assertEquals(1, user.getId());
    }

    /**
     * Tests the getUsername method to ensure it returns the correct username.
     */
    @Test
    public void testGetUsername() {
        assertEquals("testUser", user.getUsername());
    }

    /**
     * Tests the setUserName method by updating the username and checking the change.
     */
    @Test
    public void testSetUsername() {
        user.setUserName(new SimpleStringProperty("newUser") {
        });
        assertEquals("newUser", user.getUsername());
    }

    /**
     * Tests the setUsername(String) method to ensure it correctly updates the username.
     */
    @Test
    public void testSetUsernameWithString() {
        user.setUsername("anotherUser");
        assertEquals("anotherUser", user.getUsername());
    }

    /**
     * Tests the getPassword method to ensure it returns the correct password.
     */
    @Test
    public void testGetPassword() {
        assertEquals("testPassword", user.getPassword());
    }

    /**
     * Tests the setPassword method by updating the password and checking the change.
     */
    @Test
    public void testSetPassword() {
        user.setPassword("newPassword");
        assertEquals("newPassword", user.getPassword());
    }

    /**
     * Tests the constructor that does not take an ID (for auto-increment cases).
     */
    @Test
    public void testConstructorWithoutId() {
        User userWithoutId = new User("newUser", "newPassword");
        // User's without ID set to 0
        assertEquals(0, userWithoutId.getId());
        assertEquals("newUser", userWithoutId.getUsername());
        assertEquals("newPassword", userWithoutId.getPassword());
    }

    /**
     * Tests the getCollections method to ensure it returns the correct collection list.
     */
    @Test
    public void testGetCollections() {
        ObservableList<Collection> collections = user.getCollections();
        assertNotNull(collections);  // Ensure the collection list is not null
        assertEquals(0, collections.size());  // Initially, the collection list should be empty
    }

    /**
     * Tests the setCollections method to ensure it sets the collection list correctly.
     */
    @Test
    public void testSetCollections() {
        ObservableList<Collection> newCollections = FXCollections.observableArrayList();
        newCollections.add(new Collection(user.getId(), "Collection 1", "Description 1"));
        user.setCollections(newCollections);

        assertEquals(1, user.getCollections().size());
        assertEquals("Collection 1", user.getCollections().get(0).getCollectionName());
    }

    /**
     * Tests the addCollection method to ensure it adds a collection correctly.
     */
    @Test
    public void testAddCollection() {
        Collection collection = new Collection(user.getId(), "Test Collection", "Test Description");
        user.addCollection(collection);

        assertEquals(1, user.getCollections().size());
        assertEquals("Test Collection", user.getCollections().get(0).getCollectionName());
    }
}