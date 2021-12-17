

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * The test class ModelTest.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class ModelTest
{
    /**
     * Default constructor for test class ModelTest
     */
    public ModelTest()
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
    Model model;
    @Test
    public void testModelGeneral()
    {assertEquals(6, model.B);
     assertEquals(0, model.score);  
     assertEquals(40, model.M);  
    }
    @Test
    public void testModelBrick()
    {assertEquals(50, model.BRICK_WIDTH); 
     assertEquals(30, model.BRICK_HEIGHT); 
     assertEquals(50, model.HIT_BRICK); 
    }
    @Test
    public void testModelBall()
    {assertEquals(30, model.BALL_SIZE); 
     assertEquals(3, model.BALL_MOVE); 
     assertEquals(50, model.HIT_BRICK); 
    }
    @Test
    public void testModelBat()
    {assertEquals(5, model.BAT_MOVE); 
    }
    
}
