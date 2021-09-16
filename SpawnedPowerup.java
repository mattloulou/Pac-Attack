/* SpawnedPowerup.java 
 * Matthew Pechen-Berg and Peter Jang
 * January 17th, 2020
 * Class is a template for other powerups in the game
 */

//Import necessary classes
import java.awt.image.BufferedImage;

public abstract class SpawnedPowerup extends WorldObject {
  
  //declare private variables
  private int spritesheetNumber;
  private int row;
  private int col;
  private BufferedImage sprite;
  
  //Constructor for SpawnedPowerup
  SpawnedPowerup(int row, int col, int spritesheetNumber) {
    super(col * 32, row * 32, 1, 10, "Images/Powerups.png");
    
    //Required for constructor 
    this.row = row;
    this.col = col;
    this.spritesheetNumber = spritesheetNumber;
    this.sprite = this.getSprite(0,spritesheetNumber);
    
  }//end of SpawnedPowerup Constructor
  
  /** Gets the sprite image
    *@return the sprite
    */
  public BufferedImage getSpriteImage() {
   return this.sprite; 
  }//end of getSpriteImage()
  
  /** Placeholder for methods used by the inherited powerup classes
    *@param playerGatherer Player who got the powerup
    */
  public void getPowerup(Player playerGatherer) {
  }//end of getPowerup()
 
  /** Placeholder for methods used by the inherited powerup classes
    *@param playerActivator  Player the Player who activated Powerup
    *@param otherPlayer      Player the other Player
    *@param gameMap          Map the game map
    */
  public void activatePowerup(Player playerActivator, Player otherPlayer, Map gameMap) {
  }//end of activatePowerup()  
  
  /** Gets the int value of the column
    *@return the int column value
    */
  public int getCol() {
    return this.col;
  }//end getCol()
  
  /** Gets the int value of the row
    *@return the int row value
    */
  public int getRow() {
    return this.row;
  }//end getRow()
  
}//end of SpawnedPowerup class