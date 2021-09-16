/* PointPellet.java 
 * Matthew Pechen-Berg and Peter Jang
 * January 17th, 2020
 * Class that reward Players with points for collecting pellets
 */

public class PointPellet extends SpawnedPowerup {
  
  //Declare private variables
  private int pointReward;
  private int pelletSize;
  
  //Constructor for the PointPellet class
  PointPellet(int row, int col, int pelletSize) {
    super(row, col, pelletSize);
    
    //Required for constructor 
    this.pelletSize = pelletSize;
    this.createPointReward();
    
  }//end of PointPellet Constructor
  
  /** Creates point rewards for the different pellets
    */
  private void createPointReward() {
    
    //if pellet is large, point reward = 25 points
    if (pelletSize == 1) {
      this.pointReward = 25;
      
    //else point reward = 10 points  
    } else { 
     this.pointReward = 10; 
     
    }//end if structure for determining reward amount
    
  }//end of createPointReward()
  
  /** Gives Player the powerup which increases Players score
    *@param playerGatherer Player who got powerup 
    */
  public void getPowerup(Player playerGatherer) {
    playerGatherer.increaseScore(this.pointReward);
  }//end of getPowerup()
  
}//end of PointPellet class