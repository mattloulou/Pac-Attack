/* SpawnedProjectile.java
 * Matthew Pechen-Berg and Peter Jang
 * January 17th, 2020
 * the class that provides functionality to all projectiles in the game
 */

//Graphics and GUI imports
import java.awt.image.BufferedImage;
import java.awt.Graphics;

public class SpawnedProjectile extends AnimatedWorldObject {
  
  //declare class private variables
  private int spritesheetNumber;
  private BufferedImage sprite;
  private boolean isDoneMoving;
  private int owner;
  
  //constructor for the SpawnedProjectile class
  SpawnedProjectile(double x, double y, int row, int col, int spritesheetRows, int spritesheetCols,
                    String spritesheetName, int spritesheetRowMin, int spritesheetRowMax, int direction, int owner) {
    
    super((int) x, (int) y, row, col, spritesheetRows, spritesheetCols, spritesheetName, spritesheetRowMin, 
          spritesheetRowMax, direction, 40, true);
    
    //initialize variables
    this.isDoneMoving = false;
    this.owner = owner;
    
    //create the points
    this.createPoints(this.getX(), this.getY(), this.getX() + 32, this.getY() + 32);
    
  } //end of SpawnedProjectile constructor
  
  /** gets the integer value of the projectile's owner's number
    *@return the integer value of the projectile's owner's number
    */      
  public int getOwner() {
    return this.owner; 
  } //end of getOwner()
  
  /** gets the boolean value of if the projectile is done moving
    *@return the boolean value of if the projectile is done moving
    */        
  public boolean getIsDoneMoving() {
    return this.isDoneMoving;
  } //end of getIsDoneMoving()
  
  /** sets the boolean value of if the projectile is done moving
    *@param newIsDoneMoving the boolean value that will replace the previous isDoneMoving value
    */     
  public void setIsDoneMoving(boolean newIsDoneMoving) {
    this.isDoneMoving = newIsDoneMoving;
  } //end of setIsDoneMoving()
  
  /** updates the movement of the projectile
    *@param gameMap the map that the projectile takes place on
    */      
  public void updateMovement(Map gameMap) { 
    
    //check if the movement the projectile is trying to make is valid
    this.movementAttempt(gameMap);
    
    //indicate the projectile is done moving when it hits a wall
    if (!this.getIsMoving()) {
      this.isDoneMoving = true; 
    }
    
    //when the tile is moving ordinarily, 
    this.moveObject();
    
  } //end of updateMovement()
  
  /** updates all of the actions of the projectile in one method
    *@param gameMap the map that the projectile takes place on
    */    
  public void updateActions(Map levelMap) {
    
    //updates the object
    this.updateMovement(levelMap);
    this.updateSpriteImage();
    
  } //end of updateActions()
  
} //end of SpawnedProjectile class