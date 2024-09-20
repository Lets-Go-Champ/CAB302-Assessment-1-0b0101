import com.example.cab302assessment10b0101.model.Collection;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class CollectionTest {
    private Collection collection;

    @BeforeEach
    public void setUp() {
        collection = new Collection("Fantasy", "A collection of fantasy books");
    }

    @Test
    public void testGetCollectionName() {
        assertEquals("Fantasy", collection.getCollectionName());
    }

    @Test
    public void testSetCollectionName() {
        collection.setCollectionName("Non-Fiction");
        assertEquals("Non-Fiction", collection.getCollectionName());
    }

    @Test
    public void testGetDescription() {
        assertEquals("A collection of fantasy books", collection.getCollectionDescription());
    }

    @Test
    public void testSetDescription() {
        collection.setCollectionDescription("A collection of non-fiction books");
        assertEquals("A collection of non-fiction books", collection.getCollectionDescription());
    }

    // Test for getting the userId
    @Test
    public void testGetUserId() {
        assertEquals(0, collection.getUserId());
    }

    // Test for setting the userId
    @Test
    public void testSetUserId() {
        collection.setUserId(3);
        assertEquals(3, collection.getUserId());
    }

    // Test for getting the collectionId
    @Test
    public void testGetCollectionId() {
        assertEquals(0, collection.getId());
    }

    // Test for setting the collectionId
    @Test
    public void testSetCollectionId() {
        collection.setId(10);
        assertEquals(10, collection.getId());
    }


    // Boundary test: Ensuring the collection name cannot be empty
    @Test
    public void testSetEmptyName() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            collection.setCollectionName("");
        });
        assertEquals("Collection name cannot be empty", exception.getMessage());
    }

}