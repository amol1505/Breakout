import java.util.ArrayList;
import javafx.scene.paint.*;
import javafx.application.Platform;
import javafx.scene.media.AudioClip;
// The model represents all the actual content and functionality of the app
// For Breakout, it manages all the game objects that the View needs
// (the bat, ball, bricks, and the score), provides methods to allow the Controller
// to move the bat (and a couple of other fucntions - change the speed or stop 
// the game), and runs a background process (a 'thread') that moves the ball 
// every 20 milliseconds and checks for collisions 
/**
 * <h2>Model class</h2>
 * <p>The model class manages all the game objects required by the View class as well as providing methods to
 * allow for the movement of bat and other functions such as changing the speed of the game which is represented by 'fast'.
 * It also runs a thread that moves the ball and monitors any collisions every 20 milliseconds. 
 * Without the model class, all the content and functionality assocciated would fail to exist. </p>
 * 
 * @author Amol Dhaliwal
 * @version 1.0
 */
public class Model 
{
    // First,a collection of useful values for calculating sizes and layouts etc.

    public static int B              = 6;      // Border round the edge of the panel
    public static int M              = 40;     // Height of menu bar space at the top

    public static int BALL_SIZE      = 30;     // Ball side
    public static int BRICK_WIDTH    = 50;     // Brick size
    public static int BRICK_HEIGHT   = 30;

    public static int BAT_MOVE       = 5;      // Distance to move bat on each keypress
    public static int BALL_MOVE      = 3;      // Units to move the ball on each step

    public static int HIT_BRICK      = 50;     // Score for hitting a brick
    

    // The other parts of the model-view-controller setup
    View view;
    Controller controller;
    
    /*
     * Created an AudioClip object which stores the wav file
     * Converts it to a string format so can be interpreted and played 
     */
    public AudioClip note = new AudioClip(this.getClass().getResource("BrickImpact.wav").toString());
    // The game 'model' - these represent the state of the game
    // and are used by the View to display it
    public GameObj ball;                // The ball
    public ArrayList<Brick> bricks;   // The bricks
    public GameObj bat;                 // The bat
    public static int score = 0;               // The score
    private int lives = 3; // Number of lives available
    
    // variables that control the game 
    public boolean gameRunning = true;  // Set false to stop the game
    public boolean fast = false;        // Set true to make the ball go faster

    // initialisation parameters for the model
    public int width;                   // Width of game
    public int height;                  // Height of game

    // CONSTRUCTOR - needs to know how big the window will be
    public Model( int w, int h )
    /** 
     * @param w width
     * @param h height
     * Assigns width and height to the value of w and h while logging the constructor method
     */
    {
        Debug.trace("Model::<constructor>");  
        width = w; 
        height = h;


    }
   
    // Initialise the game - reset the score and create the game objects 
    public void initialiseGame()
    /**
     * Creates game objects as well as resetting the score
     */
    {     
        /*
         * Properties of game objects being assigned below as well as lives and score being assigned values
         */
        score = 0;
        lives = 3;
        ball   = new GameObj(width/2, height/2, BALL_SIZE, BALL_SIZE, Color.BLACK );
        bat    = new GameObj(width/2, height - BRICK_HEIGHT*3/2, BRICK_WIDTH*3, 
            BRICK_HEIGHT/4, Color.GRAY);
        bricks = new ArrayList<>();
   
        int WALL_TOP = 100;                     // how far down the screen the wall starts
        int NUM_BRICKS = width/BRICK_WIDTH;     // how many bricks fit on screen
        /*
         * Creates 3 rows of different coloured bricks with the help of a for loop
         * if statements used to manipulate the properties of certain rows
         */
        for (int r=0; r < 3; r++) {
        if (r == 0) {
            for (int i=0; i < NUM_BRICKS; i++) {
                Brick brick = new Brick(BRICK_WIDTH*i, WALL_TOP, BRICK_WIDTH-20, BRICK_HEIGHT, Color.GREEN);
                bricks.add(brick);  
        }
    }
        else if (r==1) {
            for (int i=0; i < NUM_BRICKS; i++) {
                Brick brick = new Brick(BRICK_WIDTH*i, WALL_TOP+BRICK_HEIGHT+10, BRICK_WIDTH-20, BRICK_HEIGHT, Color.YELLOW);
                bricks.add(brick); 
        }
    }
        else {
            for (int i=0; i < NUM_BRICKS; i++) {
                Brick brick = new Brick(BRICK_WIDTH*i, WALL_TOP+(BRICK_HEIGHT*2)+20, BRICK_WIDTH-20, BRICK_HEIGHT, Color.BLUE);
                bricks.add(brick);  
        }
    }       
}
}

    // Animating the game
    // The game is animated by using a 'thread'. Threads allow the program to do 
    // two (or more) things at the same time. In this case the main program is
    // doing the usual thing (View waits for input, sends it to Controller,
    // Controller sends to Model, Model updates), but a second thread runs in 
    // a loop, updating the position of the ball, checking if it hits anything
    // (and changing direction if it does) and then telling the View the Model 
    // changed.
    
    // When we use more than one thread, we have to take care that they don't
    // interfere with each other (for example, one thread changing the value of 
    // a variable at the same time the other is reading it). We do this by 
    // SYNCHRONIZING methods. For any object, only one synchronized method can
    // be running at a time - if another thread tries to run the same or another
    // synchronized method on the same object, it will stop and wait for the
    // first one to finish.
    
    // Start the animation thread
    public void startGame()
    /**
     * Method to create a thread which runs the runGame method and dies when its finished.
     * The thread also starts whenever the method is called.
     */
    {
        
        Thread t = new Thread( this::runGame );     // create a thread runnng the runGame method
        t.setDaemon(true);                          // Tell system this thread can die when it finishes
        t.start();                                  // Start the thread running
        
    }   
    
    // The main animation loop
    
    public void runGame()
    /**
     * Method with a 'oop which keeps updating the game state every 20 milliseconds while its running to be representative of the current Model state
     */
    {
        try
        {
            // set gameRunning true - game will stop if it is set false (eg from main thread)
            setGameRunning(true);
            while (getGameRunning())
            {
                updateGame();                        // update the game state
                modelChanged();                      // Model changed - refresh screen
                Thread.sleep( getFast() ? 10 : 20 ); // wait a few milliseconds
            }
        } catch (Exception e) 
        { 
            Debug.error("Model::runAsSeparateThread error: " + e.getMessage() );
        }
    }
  
    // updating the game - this happens about 50 times a second to give the impression of movement
    public synchronized void updateGame()
    /**
     * Updates the game regularly to make the movement within the application seem apparent to the user
     */
    {
        // move the ball one step (the ball knows which direction it is moving in)
        ball.moveX(BALL_MOVE);                      
        ball.moveY(BALL_MOVE);
        // get the current ball possition (top left corner)
        int x = ball.topX;  
        int y = ball.topY;
        // Deal with possible edge of board hit
        if (x >= width - B - BALL_SIZE)  ball.changeDirectionX();
        if (x <= 0 + B)  ball.changeDirectionX();
        if (y >= height - B - BALL_SIZE)  // Bottom
        { 
            ball.changeDirectionY(); 
            
            minusLives( -1);
            if (lives < 1){
                setGameRunning(false);
            }
        }
        if (y <= 0 + M)  ball.changeDirectionY();

       // check whether ball has hit a (visible) brick
        boolean hit = false;
        if (lives <1 || score == 1800) {
            note.stop();
            fast = false;
            setGameRunning(false);
        }
        /*
         * For loop that manipulates the way certain bricks react to a collision with the ball
         * Blue bricks will disappear immediately if already visible and has a collision
         * Other colour bricks will turn red if visible and has a collision before disappearing on second collision
         * A sound effect is also played on every collision 
         * Points only added when brick disappears
         */
        for (Brick brick: bricks) {
                if (brick.visible && brick.hitBy(ball)) {
                hit = true;
                note.play();
                
                if (brick.colour== Color.BLUE || brick.colour== Color.RED){
                brick.visible = false;      // set the brick invisible
                addToScore( HIT_BRICK );    // add to score for hitting a brick
                }
            
                else {
                    brick.colour = Color.RED;
                }
                    
           
        }    
        }    

        if (hit)
            ball.changeDirectionY();

        // check whether ball has hit the bat
        if ( ball.hitBy(bat) )
            ball.changeDirectionY();
    }

    // This is how the Model talks to the View
    // Whenever the Model changes, this method calls the update method in
    // the View. It needs to run in the JavaFX event thread, and Platform.runLater 
    // is a utility that makes sure this happens even if called from the
    // runGame thread
    public synchronized void modelChanged()
    /**
     * Informs the view class that the state of model has changed 
     */
    {
        Platform.runLater(view::update);
    }
    
    
    // Methods for accessing and updating values
    // these are all synchronized so that the can be called by the main thread 
    // or the animation thread safely
    
    // Change game running state - set to false to stop the game
    public synchronized void setGameRunning(Boolean value)
    /**
     * Method to control whether the game remains running
     * @param true
     * @param false
     */
    {  
        gameRunning = value;
    }
    
    // Return game running state
    public synchronized Boolean getGameRunning()
    /**
     * @return the running game state
     */
    {  
        return gameRunning;
    }

    // Change game speed - false is normal speed, true is fast
    public synchronized void setFast(Boolean value)
    /**
     * Method to allow for the adjustment of game speed
     */
    {  
        fast = value;
    }
    
    // Return game speed - false is normal speed, true is fast
    public synchronized Boolean getFast()
    /**
     * @return the speed of the game
     * False indicates normal speed while true indicates fast
     */
    {  
        return(fast);
    }

    // Return bat object
    public synchronized GameObj getBat()
    /**
     * @return bat object
     */
    {
        return(bat);
    }
    
    // return ball object
    public synchronized GameObj getBall()
    /**
     * @return ball object
     */
    {
        return(ball);
    }
    
    // return bricks
    public synchronized ArrayList<Brick> getBricks()
    /**
     * @return bricks
     */
    {
        return(bricks);
    }
    
    // return score
    public synchronized int getScore()
    /**
     * @return the score
     */
    {
        return(score);
    }
    
     // update the score
    public synchronized void addToScore(int n) 
    /**
     * method to update the score
     * @param n integer
     */
    {
        score += n;        
    }
    
    public synchronized int getLives()
    /**
     * @return lives
     */
    {
        return(lives);
    }
    public synchronized void minusLives(int n)  
    /**
     * Method to update lives
     * @param n integer
     */
    {
        lives += n;        
    }
    // move the bat one step - -1 is left, +1 is right
    public synchronized void moveBat( int direction )
    /**
     * Method to move the bat by a fixed distance and log the distance 
     */
    {        
        int dist = direction * BAT_MOVE;    // Actual distance to move
        Debug.trace( "Model::moveBat: Move bat = " + dist );
        bat.moveX(dist);
    }
}   
    