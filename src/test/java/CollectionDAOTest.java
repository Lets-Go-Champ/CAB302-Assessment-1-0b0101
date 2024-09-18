import com.example.cab302assessment10b0101.model.Collection;
import com.example.cab302assessment10b0101.model.MockCollectionDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class CollectionDAOTest {

    private MockCollectionDAO mockCollectionDAO;

    // Set up before each test.
    @BeforeEach
    public void setUp() {
        mockCollectionDAO = new MockCollectionDAO();  // Initialize the mock DAO for testing
    }

    // Test insert new collection.
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

    // Test get all collections.
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

    // Test inserting collection with null description.
    @Test
    public void testInsertCollectionWithNullDescription() {
        Collection collection = new Collection("Test Collection", null);
        mockCollectionDAO.insert(collection);

        List<Collection> collections = mockCollectionDAO.getAll();
        assertEquals(1, collections.size());
        assertNull(collections.get(0).getCollectionDescription());
    }

    // Test inserting multiple collections.
    @Test
    public void testInsertMultipleCollections() {
        mockCollectionDAO.insert(new Collection("Test Collection 1", "Description 1"));
        mockCollectionDAO.insert(new Collection("Test Collection 2", "Description 2"));

        List<Collection> collections = mockCollectionDAO.getAll();
        assertEquals(2, collections.size());
        assertEquals("Test Collection 1", collections.get(0).getCollectionName());
        assertEquals("Test Collection 2", collections.get(1).getCollectionName());
    }

    // Test inserting collection with an empty name.
    @Test
    public void testInsertCollectionWithEmptyName() {
        Collection collection = new Collection("", "Description with empty name");
        mockCollectionDAO.insert(collection);

        List<Collection> collections = mockCollectionDAO.getAll();
        assertEquals(1, collections.size());
        assertEquals("", collections.get(0).getCollectionName());
    }

    // Test inserting duplicate collection names.
    @Test
    public void testInsertDuplicateCollectionNames() {
        mockCollectionDAO.insert(new Collection("Duplicate Collection", "First Description"));
        mockCollectionDAO.insert(new Collection("Duplicate Collection", "Second Description"));

        List<Collection> collections = mockCollectionDAO.getAll();
        assertEquals(2, collections.size());
    }

    // Test inserting collection with very long name.
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
