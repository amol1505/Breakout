import javafx.scene.paint.*;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * <h2>The test class GameObjTest</h2>
 *
 * @author  Amol Dhaliwal
 * @version 1.0
 */
public class GameObjTest
{
    /**
     * Default constructor for test class GameObjTest
     */
    public GameObjTest()
    {
    }

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @Before
    public void setUp()
    {
        gameobj = new GameObj(0, 0, 0, 0, Color.RED);
    }

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @After
    public void tearDown()
    {
    }
    GameObj gameobj;
    @Test
    public void testGameObj() {
        
        assertEquals(0, gameobj.topX);
        assertEquals(0, gameobj.topY);
        assertEquals(0, gameobj.width);
        assertEquals(0, gameobj.height);
        assertEquals(Color.RED, gameobj.colour);
}
}
