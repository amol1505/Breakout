import javafx.application.Application;
import javafx.stage.Stage;

//  breakoutJavaFX project Main class
// The code here creates the breakout game, but without any bricks.
// The lab class exercise (with tutor support) is to add a row of bricks and make them
// disappear when the ball hits them.
// The assessment project is to add further functionality as discussed in lectures and 
// seminars. Tutors may not help directly with this but will talk you through examples and
// answer questions.
/** 
 * <h2>Main class</h2>
 * <p>The main method is the entry point of the Breakout application as it is the main function executed 
 * and able to communicate with the view and model classes by constructor methods</p>
 * @author Amol Dhaliwal
 * @version 1.0
 */

public class Main extends Application
{
    public static void main( String args[] )
    /** 
     * @return Launch initialises the system and leads to calling the start method by itself,
     * meaning that its the first method to be executed and is only used when launching from the command line. 
     * @param String args[] - supplied command line arguments as an array of string objects
     * 
     */
    {
        // The main method only gets used when launching from the command line
        // launch initialises the system and then calls start
        // In BlueJ, BlueJ calls start itself
        launch(args);
    }
    public static int H = 800;         // Height of window pixels 
    public static int W = 600;         // Width  of window pixels 
    public void start(Stage window) 
    /**
     * @return Sets up objects for other classes so that they can be linked to one another,
     * ultimately leading to the user interface container being defined which is present within the view class.
     * The model is also initiated with the help of the start method.
     * @version 1.0
     */
    {
        

        // set up debugging and print initial debugging message
        Debug.set(true);             
        Debug.trace("breakoutJavaFX starting"); 
        Debug.trace("Main::start"); 

        // Create the Model, View and Controller objects
        Model model = new Model(W,H);
        View  view  = new View(W,H);
        Controller controller  = new Controller();

        /*Link them together so they can talk to each other
        * Also each one has instance variables for the other two
        */
        model.view = view;
        model.controller = controller;
        controller.model = model;
        controller.view = view;
        view.model = model;
        view.controller = controller;

        // start up the GUI (view), and then tell the model to initialise itself
        // and start the game running
        view.start(window); 
		model.initialiseGame();					   
        

        // application is now running
        Debug.trace("breakoutJavaFX running"); 
    }
}
