import com.example.cab302assessment10b0101.model.User;
import com.example.cab302assessment10b0101.model.Collection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

    // Test getCollections method.
    @Test
    public void testGetCollections() {
        ObservableList<Collection> collections = user.getCollections();
        assertNotNull(collections);  // Ensure the collection list is not null
        assertEquals(0, collections.size());  // Initially, the collection list should be empty
    }

    // Test setCollections method.
    @Test
    public void testSetCollections() {
        ObservableList<Collection> newCollections = FXCollections.observableArrayList();
        newCollections.add(new Collection("Collection 1", "Description 1"));
        user.setCollections(newCollections);

        assertEquals(1, user.getCollections().size());
        assertEquals("Collection 1", user.getCollections().get(0).getCollectionName());
    }

    // Test addCollection method.
    @Test
    public void testAddCollection() {
        Collection collection = new Collection("Test Collection", "Test Description");
        user.addCollection(collection);

        assertEquals(1, user.getCollections().size());
        assertEquals("Test Collection", user.getCollections().get(0).getCollectionName());
    }
}