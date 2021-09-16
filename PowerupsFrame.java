/* PowerupsFrame.java 
 * Matthew Pechen-Berg and Peter Jang
 * January 17th, 2020
 * PowerupsFrame displays all the powerups and as well allows the user to go back to RulesFrame
 */

//Import necessary classes
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.ImageIcon;

public class PowerupsFrame extends JFrame {
  
  //Declare JFrame
  JFrame powerupsFrame;
  
  //Constructor for PowerupsFrame
  PowerupsFrame() {
    super("Pac-Attack Power-Ups Screen");
    
    //Required for constructor
    this.powerupsFrame = this;
    
    //Configure powerupsFrame
    this.setSize(1280,800);    
    this.setLocationRelativeTo(null); //start the frame in the center of the screen
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
    this.setResizable(false);
    
    //Create a JLabel and ImageIcon for the powerupsFrame
    ImageIcon powerupsImage = new ImageIcon ("Images/PowerUpsScreen.png"); 
    JLabel powerupsLabel = new JLabel(powerupsImage);
    
    //Create a back JButton for the powerupsFrame
    JButton backButton = new JButton("BACK");
    backButton.setBounds(10,10,100,50); //method found from http://www.java2s.com/Code/Java/Swing-JFC/LayingOutComponentsUsingAbsoluteCoordinates.htm
    backButton.setBackground(Color.WHITE);
    backButton.setForeground(Color.BLACK);
    backButton.addActionListener(new BackButtonListener());
    
    //Add JButton to the frame
    this.add(backButton);
    
    //Add the JLabel to the frame
    this.add(powerupsLabel);
    
    //Set the frame to visible
    this.setVisible(true); 
    
  }//end of PowerupsFrame Constructor 
  
 //This is an inner class that is used to detect backButton press
 class BackButtonListener implements ActionListener {  //this is the required class definition
    
   /** Creates backButton functionality
      *@param event ActionEvent input
      */
    public void actionPerformed(ActionEvent event)  {  
      System.out.println("Back To Main Menu");
      powerupsFrame.dispose();//Dispose powerupsFrame
      new RulesFrame(); //create a new Frame (another file that extends JFrame)

    }//end of actionPerformed()

  }//end of BackButtonListener class
 
}//end of PowerupsFrame class