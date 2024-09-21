import com.example.cab302assessment10b0101.model.Collection;
import com.example.cab302assessment10b0101.model.MockCollectionDAO;
import com.example.cab302assessment10b0101.model.User;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * The CollectionDAOTest class contains unit tests for the MockCollectionDAO,
 * which imitates the behavior of the actual CollectionDAO. It verifies the correct
 * functionality of collection operations such as inserting, retrieving collections,
 * and getting collection IDs.
 */
public class CollectionDAOTest {

    private MockCollectionDAO mockCollectionDAO;
    private User testUser;

    /**
     * Sets up the test environment by initializing the MockCollectionDAO and a test user.
     */
    @BeforeEach
    public void setUp() {
        mockCollectionDAO = new MockCollectionDAO();  // Initialize the mock DAO for testing
        testUser = new User(1, "testUser", "testPassword");  // Create a test user
    }

    /**
     * Tests inserting and retrieving collections by user.
     */
    @Test
    public void testGetCollectionsByUser() {
        // Insert multiple collections
        mockCollectionDAO.insert(new Collection(testUser.getId(), "Collection 1", "Description 1"));
        mockCollectionDAO.insert(new Collection(2, "Collection 2", "Description 2"));  // Collection for another user

        // Retrieve collections by user
        List<Collection> userCollections = mockCollectionDAO.getCollectionsByUser(testUser);
        assertEquals(1, userCollections.size());
        assertEquals("Collection 1", userCollections.get(0).getCollectionName());
    }

    /**
     * Tests retrieving collectionId by user and collection name.
     */
    @Test
    public void testGetCollectionIdByUserAndCollectionName() {
        Collection collection = new Collection(testUser.getId(), "My Collection", "My Description");
        mockCollectionDAO.insert(collection);

        int collectionId = mockCollectionDAO.getCollectionsIDByUserAndCollectionName(testUser, "My Collection");
        assertEquals(collection.getId(), collectionId);
    }

    /**
     * Tests inserting a new collection.
     */
    @Test
    public void testInsertCollection() {
        Collection collection = new Collection("Test Collection", "Test Description");
        mockCollectionDAO.insert(collection);

        // Retrieve the inserted collection
        List<Collection> collections = mockCollectionDAO.getAll();
        assertEquals(1, collections.size());
        assertEquals("Test Collection", collections.get(0).getCollectionName());
        assertEquals("Test Description", collections.get(0).getCollectionDescription());
    }

    /**
     * Tests retrieving all collections.
     */
    @Test
    public void testGetAllCollections() {
        // Insert multiple collections
        mockCollectionDAO.insert(new Collection("Collection 1", "Description 1"));
        mockCollectionDAO.insert(new Collection("Collection 2", "Description 2"));

        // Retrieve all collections
        List<Collection> collections = mockCollectionDAO.getAll();
        assertEquals(2, collections.size());

        // Verify the collections' data
        assertEquals("Collection 1", collections.get(0).getCollectionName());
        assertEquals("Collection 2", collections.get(1).getCollectionName());
    }

    /**
     * Tests inserting a collection with a null description.
     */
    @Test
    public void testInsertCollectionWithNullDescription() {
        Collection collection = new Collection("Test Collection", null);
        mockCollectionDAO.insert(collection);

        List<Collection> collections = mockCollectionDAO.getAll();
        assertEquals(1, collections.size());
        assertNull(collections.get(0).getCollectionDescription());
    }

    /**
     * Tests inserting multiple collections.
     */
    @Test
    public void testInsertMultipleCollections() {
        mockCollectionDAO.insert(new Collection("Test Collection 1", "Description 1"));
        mockCollectionDAO.insert(new Collection("Test Collection 2", "Description 2"));

        List<Collection> collections = mockCollectionDAO.getAll();
        assertEquals(2, collections.size());
        assertEquals("Test Collection 1", collections.get(0).getCollectionName());
        assertEquals("Test Collection 2", collections.get(1).getCollectionName());
    }

    /**
     * Tests inserting a collection with an empty name.
     */
    @Test
    public void testInsertCollectionWithEmptyName() {
        Collection collection = new Collection("", "Description with empty name");
        mockCollectionDAO.insert(collection);

        List<Collection> collections = mockCollectionDAO.getAll();
        assertEquals(1, collections.size());
        assertEquals("", collections.get(0).getCollectionName());
    }

    /**
     * Tests inserting collections with duplicate names.
     */
    @Test
    public void testInsertDuplicateCollectionNames() {
        mockCollectionDAO.insert(new Collection("Duplicate Collection", "First Description"));
        mockCollectionDAO.insert(new Collection("Duplicate Collection", "Second Description"));

        List<Collection> collections = mockCollectionDAO.getAll();
        assertEquals(2, collections.size());
    }

    /**
     * Tests inserting a collection with a very long name.
     */
    @Test
    public void testInsertCollectionWithLongName() {
        String longName = "This is a very long collection name that exceeds typical limits...";
        Collection collection = new Collection(longName, "Description for long name");
        mockCollectionDAO.insert(collection);

        List<Collection> collections = mockCollectionDAO.getAll();
        assertEquals(1, collections.size());
        assertEquals(longName, collections.get(0).getCollectionName());
    }
}
