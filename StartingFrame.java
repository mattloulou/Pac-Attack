/* StartingFrame.java 
 * Matthew Pechen-Berg and Peter Jang
 * January 17th, 2020
 * StartingFrame collects Player usernames and creates the starting menu for our game, "Pac-Attack". 
 *             The starting frame allows the Players to see rules, powerups, exit and play the game.
 * 
 * Credits for the outline of this java file Mr.Mangat's demo code
 */

//Import necessary classes
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.ImageIcon;
import java.util.Scanner;
import java.io.*;

public class StartingFrame extends JFrame { 
  
  //Declare JFrame
  JFrame thisFrame;
  
  //Declaring gameMap[][] 
  public static int [][] gameMap = new int [21][25];
  
  //Declare playerNames []
  public static String [] playerNames = new String[2];
  
  //Constructor for StartingFrame
  StartingFrame() { 
    
    //Passes the indicator arguements to parent class
    super("Pac-Attack Start Screen");
    
    //Required for constructor 
    this.thisFrame = this;
    
    //Configure the window
    this.setSize(1280,800);    
    this.setLocationRelativeTo(null); //start the frame in the center of the screen
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
    this.setResizable (false);
    
    //Create a Play JButton for the frame
    JButton startButton = new JButton("PLAY");
    startButton.setBounds(540,500,200,100); //method found from http://www.java2s.com/Code/Java/Swing-JFC/LayingOutComponentsUsingAbsoluteCoordinates.htm
    startButton.setBackground(Color.WHITE);
    startButton.setForeground(Color.BLACK);
    startButton.addActionListener(new StartButtonListener());
    
    //Create a Rules JButton for the frame
    JButton rulesButton = new JButton("RULES");
    rulesButton.setBounds(880,600,200,50); //method found from http://www.java2s.com/Code/Java/Swing-JFC/LayingOutComponentsUsingAbsoluteCoordinates.htm
    rulesButton.setBackground(Color.WHITE);
    rulesButton.setForeground(Color.BLACK);
    rulesButton.addActionListener(new RulesButtonListener());
    
    //Create a Exit JButton for the frame
    JButton exitButton = new JButton("EXIT");
    exitButton.setBounds(200,600,200,50); //method found from http://www.java2s.com/Code/Java/Swing-JFC/LayingOutComponentsUsingAbsoluteCoordinates.htm
    exitButton.setBackground(Color.WHITE);
    exitButton.setForeground(Color.BLACK);
    exitButton.addActionListener(new ExitButtonListener());
    
    //Create an JLabel and ImageIcon for the frame
    ImageIcon rulesImage = new ImageIcon ("Images/Pac-AttackMainScreen.png"); 
    JLabel rulesLabel = new JLabel(rulesImage);
    
    //add the JButtons to the frame
    this.add(startButton);
    this.add(rulesButton);
    this.add(exitButton);
    
    //add the JLabel to the frame
    this.add(rulesLabel);
    
    //Set frame as visible
    this.setVisible(true);
    
  }//end of StartingFrame Constructor 
  
  //This is an inner class that is used to detect startButton press
  class StartButtonListener implements ActionListener {  //this is the required class definition
    
    /** Creates startButton functionality
      *@param event ActionEvent input
      */
    public void actionPerformed(ActionEvent event)  { 
      
      //Do not start game and display messafe if usernames have not been inputted, else start the game
      if (playerNames[0].length() <=0 || playerNames[1].length() <=0) {
        System.out.println("Please enter your usernames in console before starting the game.");
      }else {
        System.out.println("\nStarting new Game");
        thisFrame.dispose();//Dispose of StartingFrame
        new GameFrame(playerNames);//Create GameFrame
      }//end if structure for not starting game without usernames
      
    }//end of actionPerformed()
    
  }//end of class
  
  //This is an inner class that is used to detect the rulesButton press
  class RulesButtonListener implements ActionListener {  //this is the required class definition
    
    /** Creates rulesButton functionality
      *@param event ActionEvent input
      */
    public void actionPerformed(ActionEvent event)  {  
      System.out.println("Displaying Rules");
      thisFrame.setVisible(false);//Hide StartingFrame
      new RulesFrame(); //create RulesFrame
      
    }//end of actionPerformed()
    
  }//end of class
  
  //This is an inner class that is used to detect the exitButton press
  class ExitButtonListener implements ActionListener {  //this is the required class definition
    
    /** Creates exitButton functionality
      *@param event ActionEvent input
      */
    public void actionPerformed(ActionEvent event)  {  
      System.out.println("Exiting Game");
      thisFrame.dispose();//Dispose of StartingFrame (quit the game)
      
    }//end of actionPerformed()
    
  }//end of class
  
  //Main starts this application
  public static void main(String[] args) throws Exception{ 
    
    //Create new Scanner for file "Maps.txt"
    Scanner fileIn = new Scanner(new File("Maps.txt"));
    
    for (int i = 0; i < 21; i++) {
      for (int j = 0; j < 25; j++) {
        gameMap[i][j] = fileIn.nextInt();
      }
    }
    fileIn.close();
    
    //Create StartingFrame
    new StartingFrame();
    
    //Create a new Scanner object
    Scanner input = new Scanner(System.in);
    
    //Initialize and Declare some variables
    boolean invalidName = false;//true if usernames are invalid
    playerNames[0] = "";
    playerNames[1] = "";
    
    //Enter do loop and keep looping until Player1 has inputted username
    do {
      
      //Ask and get for Player1 username
      System.out.print("Player 1, please enter your username (Max 10 Characters): ");
      playerNames[0] = input.nextLine();
      
      //if username is over 10 characters, display that input is invalid and set invalidName to true
      if (playerNames[0].length() > 10){
        invalidName = true;
        System.out.println("\nInvalid entry, please re-enter a username under 11 Characters.\n");
      }else{//else set invalidName to false
        invalidName = false;
      }//end of if structure for valid Player username
      
    }while(invalidName);//End do while loop
    
    //Enter do loop and keep looping until Player2 has inputted username
    do {
      
      //Ask and get for Player2 username
      System.out.print("\nPlayer 2, please enter your username (Max 10 Characters): ");
      playerNames[1] = input.nextLine();
      
      //if username is over 10 characters, display that input is invalid and set invalidName to true
      if (playerNames[1].length() > 10){
        invalidName = true;
        System.out.println("\nInvalid entry, please re-enter a username under 11 Characters.");
      
      //else set invalidName to false
      }else {
        invalidName = false;
      }//end of if structure for valid Player username
      
    }while(invalidName);//End do while loop
    
    //Display Message stating that they may now play game
    System.out.println("\nThank you, you may now start the game!");
    
    input.close();
  }//end of main
  
}//end of class