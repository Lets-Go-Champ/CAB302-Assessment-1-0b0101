import com.example.cab302assessment10b0101.model.ViewManager;
import com.example.cab302assessment10b0101.views.ViewFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * The ViewManagerTest class contains unit tests for the ViewManager class.
 */
public class ViewManagerTest {

    private ViewManager viewManager;

    /**
     * Sets up the ViewManager instance before each test.
     * This method is called before each test case to initialize the ViewManager instance.
     */
    @BeforeEach
    public void setUp() {
        viewManager = ViewManager.getInstance();
    }

    /**
     * Tests the getInstance method to ensure that it returns the singleton instance of ViewManager.
     */
    @Test
    public void testGetInstance() {
        ViewManager firstInstance = ViewManager.getInstance();
        ViewManager secondInstance = ViewManager.getInstance();

        // Ensure both instances are the same
        assertSame(firstInstance, secondInstance);
    }

    /**
     * Tests the getViewFactory method to ensure it returns a non-null ViewFactory instance.
     */
    @Test
    public void testGetViewFactory() {
        ViewFactory viewFactory = viewManager.getViewFactory();

        // Ensure that the ViewFactory is not null
        assertNotNull(viewFactory);
    }

    /**
     * Tests the singleton pattern by ensuring that only one instance of ViewManager is created.
     */
    @Test
    public void testSingletonPattern() {
        ViewManager firstInstance = ViewManager.getInstance();
        ViewManager secondInstance = ViewManager.getInstance();

        // Check that the same instance is returned both times
        assertSame(firstInstance, secondInstance);

        // Ensure that the same instance is used throughout the test
        assertEquals(firstInstance, secondInstance);
    }
}
