/* EndFrame.java 
 * Matthew Pechen-Berg and Peter Jang
 * January 17th, 2020
 * EndFrame displays the endFrame where the winner, scores and time will be displayed. The option to play the game again will also be given.
 */

//Import necessary classes
import javax.swing.JFrame;
import java.awt.*; 
import javax.swing.JPanel;
import java.awt.Toolkit;
import java.awt.Graphics;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.io.*;

//Keyboard imports
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

class EndFrame extends JFrame { 
  
  //Declaring class variables
  private int player1Score;
  private int player2Score;
  private EndScreen endScreen;
  private Font endFont;
  private String elapsedTimeText;
  private String[] playerNames;
  
  //Declare and initialize static variables
  public static final int SCREEN_WIDTH = 1280;
  public static final int SCREEN_HEIGHT = 800;
  
  JFrame endFrame;
  
  //Constructor - this runs first
  EndFrame(int player1Score, int player2Score, String[] playerNames, String elapsedTimeText) {
    super("Pac-Attack End Screen");
    
    //Required for constructor 
    this.endFrame = this;
    this.player1Score = player1Score;
    this.player2Score = player2Score;
    this.playerNames = playerNames;
    this.elapsedTimeText = elapsedTimeText;
    
    //Configure EndFrame
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
    this.setUndecorated(false);  //Set to true to remove title bar
    this.setLocationRelativeTo(null); //start the frame in the center of the screen
    this.setResizable(false);
    
    //Create a JButton for the rulesFrame
    JButton playAgainButton = new JButton("PLAY AGAIN");
    playAgainButton.setBounds(540,650,200,50); //method found from http://www.java2s.com/Code/Java/Swing-JFC/LayingOutComponentsUsingAbsoluteCoordinates.htm
    playAgainButton.setBackground(Color.WHITE);
    playAgainButton.setForeground(Color.BLACK);
    playAgainButton.addActionListener(new PlayAgainButtonListener());
    
    //Set up the game panel (where we put our graphics)
    this.add(playAgainButton);
    endScreen = new EndScreen();
    ImageIcon endImage = new ImageIcon ("Images/EndingScreen.png"); 
    JLabel endLabel = new JLabel(endImage);
    this.add(endLabel);
    
    //add EndScreen to EndFrame
    this.add(new EndScreen());
    
    //Load font
    this.loadFont();
    
    //Set EndFrame to Visible
    this.setVisible(true);
    
    //Create a separate thread
    Thread t = new Thread(new Runnable() { public void run() { loadFont(); }}); 
    
    //Start thread
    t.start();
    
  } //End of Constructor
  
  /** --------- INNER CLASSES ------------- **/
  
  //Inner class for the the EndFrame - This is where all the drawing of the screen occurs
  private class EndScreen extends JPanel {
    
    /** Paints all the images and text
      *@param g all graphics components
      */
    public void paintComponent(Graphics g) {   
      super.paintComponent(g); //required 
      
      //Declare and initialize image
      Image image = Toolkit.getDefaultToolkit().getImage("Images/EndingScreen.png");
      
      //Draw Image
      g.drawImage(image, 0, 0, this);
      
      //Setting graphic components
      g.setColor(Color.WHITE);
      g.setFont(endFont.deriveFont(Font.PLAIN,36f));
      g.drawString("Player 1", 215, 150);
      g.drawString("Player 2", 935, 150);
      
      //if player1 wins, display that player1 won
      if (player1Score > player2Score) {
        
        //Draw text on EndFrame
        g.drawString("Winner!", 215, 500);
        g.drawString("Loser", 965, 500);
        
      }else if(player1Score == player2Score) { //else if player1 and player2 tie, display that they tied
        
        //Draw text on EndFrame
        g.drawString("Tie!",590,500);
        
      //Else player2 wins, display that player2 won  
      }else{
        
        //Draw text on EndFrame
        g.drawString("Loser", 240, 500);
        g.drawString("Winner", 945, 500);
        
      }//end if structure for who drawing text for who won
      
      //Draw player scores and winners time
      g.drawString("Score: "+player1Score, 200, 550);
      g.drawString("Score: "+player2Score, 925, 550);
      g.drawString("Time: "+elapsedTimeText,540,550);
      
      try {
        printGameLog(playerNames, elapsedTimeText, player1Score, player2Score);
      }catch(Exception e){}
      
    }//end of paintComponent
    
  }//end of EndScreen class
  
  /** Loads custom font
      */
  public void loadFont() {
    try{
      
      //set font to custom font
      endFont = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("crackman.ttf"));
    } catch(Exception e) {System.out.println("Error loading font");}//Display error message if there was an error loading font
    
  }//end of loadFont
  
  //This is an inner class that is used to detect a PlayAgainButton press
 class PlayAgainButtonListener implements ActionListener {  //this is the required class definition
   
   /** Performs actions
      *@param event mouse event
      */ 
   public void actionPerformed(ActionEvent event)  {  
      System.out.println("Back To Main Menu");
      endFrame.dispose();//Dispose endFrame
      new StartingFrame(); //create a new Frame (another file that extends JFrame)

    }//end of actionPerformed

  }//end of BackButtonListener class
 
 public void printGameLog(String[] playerNames, String elapsedTimeText, int player1Score, int player2Score) throws Exception {
    
    //Create a new File called 'GameLog.txt'
    File gameFile = new File("GameLog.txt");
    
    //Create a PrinterWriter and associate it with 'GameLog.txt'
    PrintWriter scoreOutput = new PrintWriter(gameFile);
    
    //Save a title in the file
    scoreOutput.println("Pac-Attack Game Log:\n");
    
    //Save the scores onto the text file
    scoreOutput.println("Username:          Score:          Time:");
    scoreOutput.printf("\n%-10s %14s %14s",playerNames[0], player1Score,elapsedTimeText);
    scoreOutput.printf("\n%-10s %14s %14s",playerNames[1], player2Score, elapsedTimeText);
   
    scoreOutput.close();
 }
  
}//end of EndFrame class