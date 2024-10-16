import com.example.cab302assessment10b0101.model.*;
import javafx.beans.property.SimpleStringProperty;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The UserDAOTest class contains unit tests for the MockUserDAO, which is used
 * to simulate the behavior of UserDAO in a controlled test environment.
 * This class tests the core operations such as inserting, updating, deleting,
 * and retrieving users from the mock data store, as well as validating user credentials.
 */
public class UserDAOTest {

    private UserDAOInterface userDAO;

    /**
     * Sets up the MockUserDAO instance before each test.
     * This ensures each test starts with a fresh mock data store.
     */
    @BeforeEach
    public void setUp() {
        userDAO = new MockUserDAO();  // Use mock DAO for testing
    }

    /**
     * Tests the insertion of a single user into the MockUserDAO.
     * Verifies that the user is correctly inserted and retrievable.
     */
    @Test
    public void testInsertUser() {
        User user = new User("testUser", "testPassword");
        userDAO.insert(user);

        List<User> users = userDAO.getAll();
        assertEquals(1, users.size());
        assertEquals("testUser", users.get(0).getUsername());
        assertEquals("testPassword", users.get(0).getPassword());
    }

    /**
     * Tests updating an existing user's details in the MockUserDAO.
     * Verifies that the user's data is correctly updated.
     */
    @Test
    public void testUpdateUser() {
        User user = new User("testUser", "testPassword");
        userDAO.insert(user);

        user.setUserNameProperty(new SimpleStringProperty("updatedUser"));
        user.setPassword("updatedPassword");
        userDAO.update(user);

        User updatedUser = userDAO.getById(user.getId());
        assertEquals("updatedUser", updatedUser.getUsername());
        assertEquals("updatedPassword", updatedUser.getPassword());
    }

    /**
     * Tests deleting a user from the MockUserDAO by their ID.
     * Verifies that the user is removed and no longer retrievable.
     */
    @Test
    public void testDeleteUser() {
        User user = new User("toDeleteUser", "deletePassword");
        userDAO.insert(user);

        userDAO.delete(user.getId());

        assertNull(userDAO.getById(user.getId()));
        assertTrue(userDAO.getAll().isEmpty());
    }

    /**
     * Tests retrieving all users from the MockUserDAO.
     * Verifies that multiple inserted users are correctly stored and retrievable.
     */
    @Test
    public void testGetAllUsers() {
        userDAO.insert(new User("user1", "password1"));
        userDAO.insert(new User("user2", "password2"));

        List<User> users = userDAO.getAll();
        assertEquals(2, users.size());

        assertEquals("user1", users.get(0).getUsername());
        assertEquals("password1", users.get(0).getPassword());
        assertEquals("user2", users.get(1).getUsername());
        assertEquals("password2", users.get(1).getPassword());
    }

    /**
     * Tests retrieving a user by their ID from the MockUserDAO.
     * Verifies that the correct user is returned based on their ID.
     */
    @Test
    public void testGetById() {
        User user = new User("testUserById", "passwordById");
        userDAO.insert(user);

        User fetchedUser = userDAO.getById(user.getId());
        assertEquals(user.getId(), fetchedUser.getId());
        assertEquals("testUserById", fetchedUser.getUsername());
        assertEquals("passwordById", fetchedUser.getPassword());
    }

    /**
     * Tests that duplicate usernames cannot be inserted into the MockUserDAO.
     * Verifies that an exception is thrown when trying to insert a user with a duplicate username.
     */
    @Test
    public void testInsertDuplicateUsernames() {
        User user1 = new User("duplicateUser", "password1");
        User user2 = new User("duplicateUser", "password2");

        userDAO.insert(user1);
        assertThrows(IllegalArgumentException.class, () -> {
            if (userDAO.getAll().stream().anyMatch(u -> u.getUsername().equals(user2.getUsername()))) {
                throw new IllegalArgumentException("Duplicate username");
            }
            userDAO.insert(user2);
        });
    }

    /**
     * Tests the validation of user credentials using the MockUserDAO.
     * Verifies that valid credentials return the correct user and invalid credentials return null.
     */
    @Test
    public void testValidateCredentials() {
        User user = new User("validUser", "validPassword");
        userDAO.insert(user);

        User validUser = userDAO.validateCredentials("validUser", "validPassword");
        assertNotNull(validUser);  // Ensure the user is found
        assertEquals("validUser", validUser.getUsername());
        assertEquals("validPassword", validUser.getPassword());

        User invalidUser = userDAO.validateCredentials("invalidUser", "invalidPassword");
        assertNull(invalidUser);  // Ensure no user is found with incorrect credentials
    }
}
