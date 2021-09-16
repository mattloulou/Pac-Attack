/* GameFrame.java
 * Matthew Pechen-Berg and Peter Jang
 * January 17th, 2020
 * the main game frame that the game is ran on
 * credits for the outline of this .java file go to Mr. Mangat's demo code
 */

//import necessary classes
import javax.swing.JFrame;
import java.awt.*; 

//Graphics and GUI imports
import javax.swing.JFrame;
import javax.swing.JPanel;

//Keyboard imports
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

class GameFrame extends JFrame { 
  
  private String [] playerNames;
  
  //set game FPS
  public static final int FPS = 60;
  
  //declare and initialize static variables
  public static final int SCREEN_WIDTH = 1280;
  public static final int SCREEN_HEIGHT = 800;
  
  //declare and initialize colours
  public static Color redColour = new Color(231, 68, 46);
  public static Color greenColour = new Color(113, 234, 87);
  
  //declaration of some class variables (non-static)
  private GameAreaPanel gamePanel; 
  private Font gameFont;
  private String playerOneScoreText;
  private String playerTwoScoreText;
  private final int BORDER_WIDTH = 4;
  private int totalRows = 21;
  private int totalCols = 25; 
  private int mainGameOffsetX = (SCREEN_WIDTH - totalCols * 32) / 2;
  private int mainGameOffsetY = (SCREEN_HEIGHT - totalRows * 32) - 28;
  private int playerOnePriorityDirection = -1;
  private int playerTwoPriorityDirection = -1;
  
  //declare and initialize time-related variables
  private int secondsElapsed;
  private int minutesElapsed;
  private long lastFrameMS = System.currentTimeMillis();
  private long lastPelletSpawnMS = System.currentTimeMillis();
  private long lastPowerupSpawnMS = System.currentTimeMillis();
  private long currentMS = System.currentTimeMillis();
  private long gameStartTimeMS = System.currentTimeMillis();
  private String elapsedTimeText;
  
  //instantiate game objects
  private Player playerOne = new Player(19, 1, 1, "Images/RedPacMan.png");
  private Player playerTwo = new Player(1, 23, 2, "Images/GreenPacMan.png");
  private Map levelArena = new Map(StartingFrame.gameMap, "Images/BlueBorders.png", 5, 8);
  private boolean [] playerOneMovement = new boolean [4];
  private boolean [] playerTwoMovement = new boolean [4];
  public int player1Score = playerOne.getScore();
  public int player2Score = playerTwo.getScore();
  public boolean playGame = true;
  
  //Constructor - this runs first
  GameFrame(String[] playerNames) { 
    
    super("Pac Man Versus");
    // Set the frame to full screen 
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
    this.setUndecorated(false);  //Set to true to remove title bar
    this.setResizable(false);
    
    this.playerNames = playerNames;
    
    //Set up the game panel (where we put our graphics)
    gamePanel = new GameAreaPanel();
    this.add(new GameAreaPanel());
    
    //set up key listeners
    MyKeyListener keyListener = new MyKeyListener();
    this.addKeyListener(keyListener);
    
    this.requestFocusInWindow(); //make sure the frame has focus   
    
    //initial spawning
    levelArena.spawnPointPellets(playerOne, playerTwo);
    levelArena.spawnGrabbablePowerup(playerOne, playerTwo);
    this.setVisible(true);
    
    //Start the game loop in a separate thread
    Thread t = new Thread(new Runnable() { public void run() { updateActions(); }}); //start the gameLoop 
    t.start();
    
    //load font
    this.loadFont();
    
  } //End of Constructor
  
  /** updates the game every single frame
    */    
  public void updateActions() {
    
    //while the game is still being played
    while(playGame){
      
      //update player and map actions
      playerOne.updateActions(levelArena,playerOneMovement,playerOnePriorityDirection);
      playerTwo.updateActions(levelArena,playerTwoMovement,playerTwoPriorityDirection);
      levelArena.gatherPowerups(playerOne);
      levelArena.gatherPowerups(playerTwo);
      
      //check for a win
      boolean win = isWin(player1Score, player2Score);
 
      //get current time
      currentMS = System.currentTimeMillis();
      
      //check if powerups should be spawned
      if (currentMS - lastPowerupSpawnMS > 5000) {
        lastPowerupSpawnMS += 5000;
        levelArena.spawnGrabbablePowerup(playerOne, playerTwo);
      }
      
      //check if pellets should be respawned
      if (currentMS - lastPelletSpawnMS > 60000) {
        lastPelletSpawnMS += 60000;
        levelArena.spawnPointPellets(playerOne, playerTwo);
      }
      
      //when the game is not yet over
      if (!win) {
        
        //get player scores for end screen
        player1Score = playerOne.getScore();
        player2Score = playerTwo.getScore();
        
      //when the game is over  
      } else {
        
        //create the end frame
        new EndFrame(playerOne.getScore(), playerTwo.getScore(), playerNames, elapsedTimeText);
        this.dispose();
        playGame = false;
        System.out.println("Printing to Text File");
      }
      
      //calculate sleep duration
      currentMS = System.currentTimeMillis();
      try{ Thread.sleep(16 - (currentMS - lastFrameMS));} catch (Exception exc){}  //delay
      this.repaint();
      lastFrameMS = System.currentTimeMillis(); //update last time sleep happened
    }    
  } //end of updateActions()
  
  /** load the font for the game
    */      
  public void loadFont() {
    try{
      
      //get the game font
      gameFont = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("crackman.ttf"));
      
    //catch exceptions  
    } catch(Exception e) {System.out.println("Error loading font");}
  } //end of loadFont()

  /** check if there is a win in the game
    *@param playerOneScore the first player's score
    *@param playerTwoScore the second player's score 
    *@return boolean       the value of if the game is over or not yet 
    */      
  public static boolean isWin(int playerOneScore, int playerTwoScore) {
    
    //evaluates the score of both players to see if either one has won
    if ( playerOneScore >= 10000 || playerTwoScore >= 10000){
      return true;
    }else {
      return false;
      
    }//end of if
  } //end of isWin()
  
  /** --------- INNER CLASSES ------------- **/
  
  // Inner class for the the game area - This is where all the drawing of the screen occurs
  private class GameAreaPanel extends JPanel {
    
    /** paints all of the graphics onto the screen each frame
    *@param g the Graphics tool that allows the tiles to be drawn on screen
    */  
    public void paintComponent(Graphics g) {   
      super.paintComponent(g); //required
      
      //draw the main game object-related drawings
      setDoubleBuffered(true);
      g.setColor(Color.BLUE);
      levelArena.drawMap(g);
      levelArena.drawMapPowerups(g);
      playerTwo.drawObject(g);
      playerOne.drawObject(g);
      levelArena.drawMapElements(g);
      levelArena.updateMap(playerOne, playerTwo, g);
      
      //draw the game outline
      g.setColor(Color.BLACK);
      g.fillRect(mainGameOffsetX - BORDER_WIDTH, mainGameOffsetY - BORDER_WIDTH, BORDER_WIDTH, SCREEN_WIDTH 
                   - mainGameOffsetY);
      g.fillRect(mainGameOffsetX + (32 * (totalCols)), mainGameOffsetY - BORDER_WIDTH, BORDER_WIDTH, SCREEN_WIDTH 
                   - mainGameOffsetY);
      g.fillRect(mainGameOffsetX, mainGameOffsetY - BORDER_WIDTH, 32 * (totalCols), BORDER_WIDTH);
      
      //draw the the red side rectangles
      g.setColor(redColour);
      g.fillRect(0, 0, mainGameOffsetX - BORDER_WIDTH, SCREEN_HEIGHT);
      g.fillRect(mainGameOffsetX - BORDER_WIDTH, 0, 16 * (totalCols) + BORDER_WIDTH, mainGameOffsetY - BORDER_WIDTH);
      
      //draw the greed side rectangles
      g.setColor(greenColour);
      g.fillRect(mainGameOffsetX + 16 * (totalCols), 0, 16 * (totalCols) + BORDER_WIDTH, mainGameOffsetY - BORDER_WIDTH);
      g.fillRect(mainGameOffsetX + 32 * (totalCols) + BORDER_WIDTH, 0, mainGameOffsetX - BORDER_WIDTH, SCREEN_HEIGHT);
      
      //draw player 1 name
      g.setColor(Color.WHITE);
      g.setFont(gameFont.deriveFont(Font.PLAIN,36f));
      g.drawString(playerNames[0], 10, 40);
      
      //draw player 2 name
      g.drawString(playerNames[1], SCREEN_WIDTH - g.getFontMetrics().stringWidth(playerNames[1]) - 10, 40);
      
      //draw current time
      minutesElapsed = (int) ((System.currentTimeMillis() - gameStartTimeMS) / 1000) / 60;
      secondsElapsed = (int) (System.currentTimeMillis() - gameStartTimeMS) / 1000 - (60 * minutesElapsed);
      
      //calculate elapsed time and draw it
      elapsedTimeText = String.format("%02d:%02d", minutesElapsed, secondsElapsed);
      g.drawString(elapsedTimeText, (SCREEN_WIDTH - g.getFontMetrics().stringWidth(elapsedTimeText)) / 2, 40);
      
      //draw player 1 points
      playerOneScoreText = String.format("%05d", playerOne.getScore());
      g.drawString(playerOneScoreText, (SCREEN_WIDTH - g.getFontMetrics().stringWidth(playerOneScoreText)) / 2 - 100, 80);
      
      //draw player 2 points
      playerTwoScoreText = String.format("%05d", playerTwo.getScore());
      g.drawString(playerTwoScoreText, (SCREEN_WIDTH - g.getFontMetrics().stringWidth(playerTwoScoreText)) / 2 + 100, 80);
      
      //draw the players' power up slots
      playerOne.drawPowerupSlots(g);
      playerTwo.drawPowerupSlots(g);
      
      //draw the powerup titles
      g.drawString("Power-Ups",13 ,350);
      g.drawString("Power-Ups",1056 ,350);
      
    }
  }
  
  // -----------  Inner class for the keyboard listener - this detects key presses and runs the corresponding code
  private class MyKeyListener implements KeyListener {
    
    /** check for keys being typed
      *@param event the keys being types
      */    
    public void keyTyped(KeyEvent event) {  
    }

    /** check for keys being pressed
      *@param event the keys being pressed
      */      
    public void keyPressed(KeyEvent event) {
      
      //PLAYER 1 CONTROLS
      
      //If 'A' is pressed
      if (KeyEvent.getKeyText(event.getKeyCode()).equals("A")) {  
        playerOneMovement[0] = true;
        playerOnePriorityDirection = 0;
        
      //If 'S' is pressed  
      } else if (KeyEvent.getKeyText(event.getKeyCode()).equals("S")) {  
        playerOneMovement[1] = true;
        playerOnePriorityDirection = 1;
        
      //If 'D' is pressed  
      } else if (KeyEvent.getKeyText(event.getKeyCode()).equals("D")) {  
        playerOneMovement[2] = true;
        playerOnePriorityDirection = 2;
        
      //If 'W' is pressed  
      } else if (KeyEvent.getKeyText(event.getKeyCode()).equals("W")) {  
        playerOneMovement[3] = true;
        playerOnePriorityDirection = 3;
      } //end of if structure
      
      //If 'Q' is pressed
      if (KeyEvent.getKeyText(event.getKeyCode()).equals("Q")) {
        playerOne.activatePowerup(0, playerTwo, levelArena);
      }
      
      //If 'Space' is pressed
      if (KeyEvent.getKeyText(event.getKeyCode()).equals("Space")) {
        playerOne.activatePowerup(1, playerTwo, levelArena);
      }
      
      //PLAYER 2 CONTROLS
      
      //If left arrow key is pressed
      if (event.getKeyCode() == KeyEvent.VK_LEFT) { 
        playerTwoMovement[0] = true;
        playerTwoPriorityDirection = 0;
        
      //If down arrow key is pressed  
      } else if (event.getKeyCode() == KeyEvent.VK_DOWN) {
        playerTwoMovement[1] = true;
        playerTwoPriorityDirection = 1;
        
      //If right arrow key is pressed  
      } else if (event.getKeyCode() == KeyEvent.VK_RIGHT) {  
        playerTwoMovement[2] = true;
        playerTwoPriorityDirection = 2;
        
      //If up arrow key is pressed  
      } else if (event.getKeyCode() == KeyEvent.VK_UP) { 
        playerTwoMovement[3] = true;
        playerTwoPriorityDirection = 3;
      } //end of if structure
      
      //If ',' is pressed
      if (KeyEvent.getKeyText(event.getKeyCode()).equals("Comma")) {
        playerTwo.activatePowerup(0, playerOne, levelArena);
      }
      
      //If '.' is pressed
      if (KeyEvent.getKeyText(event.getKeyCode()).equals("Period")) {
        playerTwo.activatePowerup(1, playerOne, levelArena);
      }
      
    }//end of keyPressed()   
    
    /** check for keys being released
      *@param event the keys being released
      */      
    public void keyReleased(KeyEvent event) {
      
      //PLAYER 1 CONTROLS
      
      //If 'A' is pressed
      if (KeyEvent.getKeyText(event.getKeyCode()).equals("A")) { 
        playerOneMovement[0] = false;
        playerOnePriorityDirection = -1;
        
      //If 'S' is pressed  
      } else if (KeyEvent.getKeyText(event.getKeyCode()).equals("S")) {  
        playerOneMovement[1] = false;
        playerOnePriorityDirection = -1;
        
      //If 'D' is pressed  
      } else if (KeyEvent.getKeyText(event.getKeyCode()).equals("D")) {  
        playerOneMovement[2] = false;
        playerOnePriorityDirection = -1;
        
      //If 'W' is pressed  
      } else if (KeyEvent.getKeyText(event.getKeyCode()).equals("W")) {  
        playerOneMovement[3] = false;
        playerOnePriorityDirection = -1;
      } //end of if structure
      
      //PLAYER 2 CONTROLS
      
      //If left arrow key is pressed
      if (event.getKeyCode() == KeyEvent.VK_LEFT) {  
        playerTwoMovement[0] = false;
        playerTwoPriorityDirection = -1;
        
      //If down arrow key is pressed    
      } else if (event.getKeyCode() == KeyEvent.VK_DOWN) {  
        playerTwoMovement[1] = false;
        playerTwoPriorityDirection = -1;
        
      //If right arrow key is pressed    
      } else if (event.getKeyCode() == KeyEvent.VK_RIGHT) { 
        playerTwoMovement[2] = false;
        playerTwoPriorityDirection = -1;
        
      //If up arrow key is pressed    
      } else if (event.getKeyCode() == KeyEvent.VK_UP) {  
        playerTwoMovement[3] = false;
        playerTwoPriorityDirection = -1;
        
      } //end of if structure
      
    } //end of keyRelease()
  } //end of keyboard listener
  
}//end of GameFrame class