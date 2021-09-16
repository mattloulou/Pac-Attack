/* LaserPowerup.java 
 * Matthew Pechen-Berg and Peter Jang
 * January 17th, 2020
 * Class that handles the Laser powerup pick up and activation
 */

public class LaserPowerup extends SpawnedPowerup {
  
  //Constructor for LaserPowerup 
  LaserPowerup(int row, int col) {
    super(row, col, 5);
    
  }//end of LaserPowerup Constructor
  
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
      playerGatherer.gatherPowerup(playerFreeSlot, new LaserPowerup(playerGatherer.getRow(),playerGatherer.getCol()));
      
      //Display message stating Player has picked up Laser gun
      System.out.println("Player " + playerGatherer.getPlayerNumber() + " picked up a laser gun.");
      
    }//end if structure for if player has a free slot
    
  }//end of getPowerup()
  
  /** Activates the Powerup
    *@param playerActivator  Player the Player who activated Powerup
    *@param otherPlayer      Player the other Player
    *@param gameMap          Map the game map
    */
  public void activatePowerup(Player playerActivator, Player otherPlayer, Map gameMap) {
    
    //Display message stating Player has used Laser gun
    System.out.println("Player " + playerActivator.getPlayerNumber() + " used their laser gun.");
    
    //Activate Powerup
    gameMap.shootLaser(playerActivator);
    
  }//end of activatePowerup()
  
}//end of LaserPowerup class