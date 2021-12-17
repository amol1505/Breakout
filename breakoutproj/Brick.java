import java.util.ArrayList;
import javafx.scene.paint.*;
import javafx.application.Platform;
/**
 * <h2>Brick class</h2>
 * <p>Class which holds variables for the bricks which can affect its properties as well as 
 * providing methods which determines how they reacts to a collision. All methods have
 * been inherited off the GameObj class.</p>
 * @author Amol Dhaliwal
 * @version 1.0
 */
public class Brick extends GameObj
{
    
    /**
     * Constructor for objects of class Brick
     */
     public Brick(int x, int y, int w, int h, Color c )
    {
        /*
         * Super is called as the parent constructor has parameters
         */
        super(0,        
        0,
        0, 
        0, 
        c);
        
        topX   = x;       
        topY = y;
        width  = w; 
        height = h; 
        colour = c;
    
}

}