import com.example.cab302assessment10b0101.model.Collection;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * The CollectionTest class contains unit tests for the Collection class.
 */
public class CollectionTest {
    private Collection collection;

    /**
     * Sets up a sample Collection object before each test.
     * This method is called before each test case to initialize the Collection object with test data.
     */
    @BeforeEach
    public void setUp() {
        collection = new Collection(1, "Fantasy", "A collection of fantasy books");
    }

    /**
     * Tests the getCollectionName method to ensure it returns the correct collection name.
     */
    @Test
    public void testGetCollectionName() {
        assertEquals("Fantasy", collection.getCollectionName());
    }

    /**
     * Tests the setCollectionName method by updating the collection name and checking if the change is reflected.
     */
    @Test
    public void testSetCollectionName() {
        collection.setCollectionName("Non-Fiction");
        assertEquals("Non-Fiction", collection.getCollectionName());
    }

    /**
     * Tests the getDescription method to ensure it returns the correct collection description.
     */
    @Test
    public void testGetDescription() {
        assertEquals("A collection of fantasy books", collection.getCollectionDescription());
    }

    /**
     * Tests the setDescription method by changing the description and verifying the update.
     */
    @Test
    public void testSetDescription() {
        collection.setCollectionDescription("A collection of non-fiction books");
        assertEquals("A collection of non-fiction books", collection.getCollectionDescription());
    }

    /**
     * Tests the getUserId method to ensure it returns the correct user ID.
     */
    @Test
    public void testGetUserId() {
        assertEquals(1, collection.getUserId());
    }

    /**
     * Tests the setUserId method by updating the user ID and checking if the change is reflected.
     */
    @Test
    public void testSetUserId() {
        collection.setUserId(3);
        assertEquals(3, collection.getUserId());
    }

    /**
     * Tests the getCollectionId method to ensure it returns the correct collection ID.
     */
    @Test
    public void testGetCollectionId() {
        assertEquals(0, collection.getId());
    }

    /**
     * Tests the setCollectionId method by updating the collection ID and checking if the change is reflected.
     */
    @Test
    public void testSetCollectionId() {
        collection.setId(10);
        assertEquals(10, collection.getId());
    }

    /**
     * Tests the validation of setting an empty collection name.
     * Ensures that setting an empty name throws an IllegalArgumentException.
     */
    @Test
    public void testSetEmptyName() {
        // Assert that IllegalArgumentException is thrown when setting an empty collection name
        assertThrows(IllegalArgumentException.class, () -> {
            collection.setCollectionName("");
        });
    }
}