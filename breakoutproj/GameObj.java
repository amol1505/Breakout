import javafx.scene.paint.*;

// An object in the game, represented as a rectangle, with a position,
// a colour, and a direction of movement.
public class GameObj
/**
 * <h2>GameObj class</h2>
 * <p>Class which holds variables for a game object that can affect its properties as well as 
 * providing methods which determines how a game object reacts to a collision.</p>
 * @author Amol Dhaliwal
 * @version 1.0
 */
{
    // state variables for a game object
    public boolean visible  = true;     // Can see (change to false when the brick gets hit)
    public int topX   = 0;              // Position - top left corner X
    public int topY   = 0;              // position - top left corner Y
    public int width  = 0;              // Width of object
    public int height = 0;              // Height of object
    public Color colour;                // Colour of object
    public int   dirX   = 1;            // Direction X (1 or -1)
    public int   dirY   = 1;            // Direction Y (1 or -1)


    public GameObj( int x, int y, int w, int h, Color c )
    /** 
     * Stores the five variables that a game object requires 
     * @param x position- top left corner x
     * @param y position-top left corner y
     * @param w width
     * @param h height
     * @param c colour
     */
    {
        topX   = x;       
        topY = y;
        width  = w; 
        height = h; 
        colour = c;
    }

    // move in x axis
    public void moveX( int units )
    /**
     * determines how game object moves in x axis
     * @param units distance
     */
    {
        topX += units * dirX;
    }

    // move in y axis
    public void moveY( int units )
    /**
     * determines how game object moves in y axis
     * @param units distance
     */
    {
        topY += units * dirY;
    }

    
    public void changeDirectionX()
    /**
     * method which changes the direction of movement with regards to the x axis (-1, 0 or +1)
     */
    {
        dirX = -dirX;
    }

    
    public void changeDirectionY()
    /**
     * method which changes the direction of movement with regards to the y axis (-1, 0 or +1)
     */
    {
        dirY = -dirY;
    }

    // Detect collision between this object and the argument object
    // It's easiest to work out if they do NOT overlap, and then
    // return the negative (with the ! at the beginning)
    public boolean hitBy( GameObj obj )
    /**
     * Method that determines collision between objects 
     * @param obj A game object
     */
    {
        return ! ( topX >= obj.topX+obj.width     ||
            topX+width <= obj.topX         ||
            topY >= obj.topY+obj.height    ||
            topY+height <= obj.topY );

    }

}
