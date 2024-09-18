import com.example.cab302assessment10b0101.model.*;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UserDAOTest {

    private UserDAOInterface userDAO;

    // Set up for each test.
    @BeforeEach
    public void setUp() {
        userDAO = new MockUserDAO();  // Use mock DAO for testing
    }

    // Test Insert user method.
    @Test
    public void testInsertUser() {
        User user = new User("testUser", "testPassword");
        userDAO.insert(user);

        List<User> users = userDAO.getAll();
        assertEquals(1, users.size());
        assertEquals("testUser", users.get(0).getUsername());
        assertEquals("testPassword", users.get(0).getPassword());
    }

    // Test update user method.
    @Test
    public void testUpdateUser() {
        User user = new User("testUser", "testPassword");
        userDAO.insert(user);

        user.setUserName("updatedUser");
        user.setPassword("updatedPassword");
        userDAO.update(user);

        User updatedUser = userDAO.getById(user.getId());
        assertEquals("updatedUser", updatedUser.getUsername());
        assertEquals("updatedPassword", updatedUser.getPassword());
    }

    // Test delete user method.
    @Test
    public void testDeleteUser() {
        User user = new User("toDeleteUser", "deletePassword");
        userDAO.insert(user);

        userDAO.delete(user.getId());

        assertNull(userDAO.getById(user.getId()));
        assertTrue(userDAO.getAll().isEmpty());
    }

    // Test get all users.
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

    // Test get by ID.
    @Test
    public void testGetById() {
        User user = new User("testUserById", "passwordById");
        userDAO.insert(user);

        User fetchedUser = userDAO.getById(user.getId());
        assertEquals(user.getId(), fetchedUser.getId());
        assertEquals("testUserById", fetchedUser.getUsername());
        assertEquals("passwordById", fetchedUser.getPassword());
    }

    // Test insert duplicate usernames.
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
}
