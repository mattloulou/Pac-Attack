/* SlowEnemyPowerup.java 
 * Matthew Pechen-Berg and Peter Jang
 * January 17th, 2020
 * Class that  handles the slow enemy Powerup pick up and activation
 */

public class SlowEnemyPowerup extends SpawnedPowerup {
  
  //Constructor for the SlowEnemyPowerup
  SlowEnemyPowerup(int row, int col) {
    super(row, col, 3);
    
  }//end of SlowEnemyPowerup Constructor
  
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
      playerGatherer.gatherPowerup(playerFreeSlot, new SlowEnemyPowerup(-1,-1));
     
      //Display message stating Player has picked up Powerup
      System.out.println("Player " + playerGatherer.getPlayerNumber() + " picked up a slow enemy powerup.");
      
    }//end if structure for if player has a free slot
  }//end of getPowerup()
  
  /** Activates the Powerup
    *@param playerActivator  Player the Player who activated Powerup
    *@param otherPlayer      Player the other Player
    *@param gameMap          Map the game map
    */
  public void activatePowerup(Player playerActivator, Player otherPlayer, Map gameMap) {
    
    //Display message stating Player has activated slow enemy Powerup
    System.out.println("Player " + playerActivator.getPlayerNumber() + " activated a slow enemy powerup.");
    
    //Activate Powerup
    otherPlayer.startSlowEffect();
    
  }//end of activatePowerup()
  
}//end of SlowEnemyPowerup class