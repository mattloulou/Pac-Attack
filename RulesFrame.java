/* RulesFrame.java 
 * Matthew Pechen-Berg and Peter Jang
 * January 17th, 2020
 * RulesFrame displays the rules and also allows the user to go back to the starting screen or go to the powerups screen.
 */

//Import necessary classes
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.ImageIcon;

public class RulesFrame extends JFrame {
  
  //Declare JFrame
  JFrame rulesFrame;
  
  //Constructor for RulesFrame
  RulesFrame() {
    super("Pac-Attack Rules Screen");
    
    //Required for constructor 
    this.rulesFrame = this;
    
    //Configure rulesFrame
    this.setSize(1280,800);    
    this.setLocationRelativeTo(null); //start the frame in the center of the screen
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
    this.setResizable(false);
    
    //Create a JLabel and ImageIcon for the rulesFrame
    ImageIcon rulesImage = new ImageIcon ("Images/RulesScreen.png"); 
    JLabel rulesLabel = new JLabel(rulesImage);
    
    //Create a Back JButton for the rulesFrame
    JButton backButton = new JButton("BACK");
    backButton.setBounds(540,650,200,50); //method found from http://www.java2s.com/Code/Java/Swing-JFC/LayingOutComponentsUsingAbsoluteCoordinates.htm
    backButton.setBackground(Color.WHITE);
    backButton.setForeground(Color.BLACK);
    backButton.addActionListener(new BackButtonListener());
    
    //Create a Powerups JButton for the rulesFrame that will go to the powerups screen
    JButton powerupsButton = new JButton("POWER-UPS");
    powerupsButton.setBounds(540,550,200,50); //method found from http://www.java2s.com/Code/Java/Swing-JFC/LayingOutComponentsUsingAbsoluteCoordinates.htm
    powerupsButton.setBackground(Color.WHITE);
    powerupsButton.setForeground(Color.BLACK);
    powerupsButton.addActionListener(new PowerUpsButtonListener());
    
    //Add JButtons to the frame
    this.add(powerupsButton);
    this.add(backButton);
    
    //Add the JLabel to the frame
    this.add(rulesLabel);
    
    //Set the frame to visible
    this.setVisible(true); 
    
  }//end of RulesFrame Constructor 
  
  //This is an inner class that is used to detect backButton press
 class BackButtonListener implements ActionListener {  //this is the required class definition
    
   /** Creates backButton functionality
      *@param event ActionEvent input
      */
    public void actionPerformed(ActionEvent event)  {  
      System.out.println("Back To Main Menu");
      rulesFrame.dispose();//Dispose of rulesFrame
      new StartingFrame(); //create a new Frame (another file that extends JFrame)

    }//end of actionPerformed()

  }//end of BackButtonListener class
 
  //This is an inner class that is used to detect a button press
 class PowerUpsButtonListener implements ActionListener {  //this is the required class definition
   
   /** Creates powerupsButton functionality
      *@param event ActionEvent input
      */
   public void actionPerformed(ActionEvent event)  {  
      System.out.println("Power-Ups Menu");
      rulesFrame.setVisible(false);//Hide rulesFrame
      new PowerupsFrame(); //create a new Frame (another file that extends JFrame)

    }//end of actionPerformed()

  }//end of PowerUpsButtonListener class
  
}//end of class