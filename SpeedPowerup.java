/* SpeedPowerup.java 
 * Matthew Pechen-Berg and Peter Jang
 * January 17th, 2020
 * Class that handles the speed Powerup pick up and activation
 */
public class SpeedPowerup extends SpawnedPowerup {
  
  //Constructor for SpeedPowerup 
  SpeedPowerup(int row, int col) {
    super(row, col, 2);
    
  }//end of SpeedPowerup Constructor
  
  /** Gives the Player the powerup and the ability to use the Powerup
    *@param playerGatherer Player the player who got powerup
    */
  public void getPowerup(Player playerGatherer) {
    
    //Declare variable
    int playerFreeSlot;
    
    //Initialize variable
    playerFreeSlot = playerGatherer.availablePowerupSlot();
    
    //if player has a free slot
    if (playerFreeSlot != -1) {
      
      //Give Player the Powerup
      playerGatherer.gatherPowerup(playerFreeSlot, new SpeedPowerup(-1,-1));
      
      //Display message stating Player has picked up speed boost
      System.out.println("Player " + playerGatherer.getPlayerNumber() + " picked up speed boost.");
      
    }//end if structure for if player has a free slot
    
  }//end of getPowerup()
  
  /** Activates the Powerup
    *@param playerActivator  Player the Player who activated Powerup
    *@param otherPlayer      Player the other Player
    *@param gameMap          Map the game map
    */
  public void activatePowerup(Player playerActivator, Player otherPlayer, Map gameMap) {
    
    //Display message stating Player has activated speed boost
    System.out.println("Player " + playerActivator.getPlayerNumber() + " activated a speed boost.");
    
    //Activate Powerup
    playerActivator.startSpeedBoost();
    
  } //end of activatePowerup() 
  
}//end of SpeedPowerup class