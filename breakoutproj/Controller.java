
import javafx.scene.input.KeyEvent;


/**
 * <h2>Controller class</h2>
 * <p>Receives KeyPress events from the graphical user interface via the KeyEventHandler 
 * which allows it to map the keys onto methods in model which can be called appropriately</p>
 * @author Amol Dhaliwal
 * @version 1.0
 */
public class Controller
{
  //constructor method for both classes created
  public Model model;
  public View  view;
  
  
  public Controller()
  /**
   * Constructor method to print a debugging message if necessary
   */
  {
    Debug.trace("Controller::<constructor>");
  }
  
  
  public void userKeyInteraction(KeyEvent event )
  /**
   * <p>Method called by the View to respond to key presses in the graphical user interface where
   * the controller decides what keys are converted into commands and run in the model.</p>
   */
  {
    Debug.trace("Controller::userKeyInteraction: keyCode = " + event.getCode() );
      
    switch ( event.getCode() )             
    {
      case LEFT:                     // Left Arrow
        model.moveBat( -1);          // move bat left
        break;
      case RIGHT:                    // Right arrow
        model.moveBat( +1 );         // Move bat right
        break;
      case F :
        // Very fast ball movement
        model.setFast(true);
        break;
      case N :
        // Normal speed ball movement
        model.setFast(false);
        break;
      case S :
        // stop the game
        model.setGameRunning(false);
        break;
    }
  }
}
