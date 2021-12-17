import javafx.scene.paint.*;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * The test class BrickTest.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class BrickTest
{
    /**
     * Default constructor for test class BrickTest
     */
    public BrickTest()
    {
        brick = new Brick(0, 0, 0, 0, Color.RED);
    }

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @Before
    public void setUp()
    {
    }
    Brick brick;
    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @After
    public void tearDown()
    {
    }
    @Test
    public void testBrick() {
        
        assertEquals(0, brick.topX);
        assertEquals(0, brick.topY);
        assertEquals(0, brick.width);
        assertEquals(0, brick.height);
        assertEquals(Color.RED, brick.colour);
}
}
