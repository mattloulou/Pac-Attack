/* Player.java 
 * Matthew Pechen-Berg and Peter Jang
 * January 17th, 2020
 * this class allows for the creation of players and for them to be effected by other objects
 */

//Graphics and GUI imports
import java.awt.Graphics;
import java.awt.Color;

public class Player extends AnimatedWorldObject {
  
  //declare private variables
  private boolean hasSpeedBoost;
  private boolean hasSlowEffect;
  private int playerNumber; 
  private int score;
  private int mostRecentDirection;
  private long speedStartMS;
  private long slowStartMS;
  private boolean [] playerMovement;
  private boolean [] hasPowerup;
  private SpawnedPowerup [] powerupStorage;
  
  //constructor for the Player class
  Player(int row, int col, int playerNumber, String spritesheetName) {
    super(col * 32, row * 32, row, col, 4, 4, spritesheetName, 0, 1, -1, 100, false);
    this.playerNumber = playerNumber;
    this.hasSpeedBoost = false;
    this.hasSlowEffect = false;
    this.score = 0;
    this.powerupStorage = new SpawnedPowerup[2];
    this.hasPowerup = new boolean[2];
    this.mostRecentDirection = -1;
  } //end of constructor for Player
  
  /**
   * checks to see if the player has any powerup slots available
   *@return the integer indicating which slot is available to be used
   */ 
  public int availablePowerupSlot() {
    
    //check which slot is available
    if (!this.hasPowerup[0]) {
      return 0; 
    } else if (!this.hasPowerup[1]) {
      return 1;
    } else {
      return -1 ; //return -1 when no slots are available
    } //end of if structure
  } //end of availablePowerupSlot()
  
  /** places the gathered powerup in an empty powerup slot
    *@param index        the integer index of which slot to place the powerup in
    *@param newPowerup   the SpawnedPowerup that the player is receiving
    */  
  public void gatherPowerup(int index, SpawnedPowerup newPowerup) {
    
    //fill the slot with the powerup
    this.hasPowerup[index] = true;
    this.powerupStorage[index] = newPowerup;
    
  } //end of gatherPowerup()
  
  /** activates a 5-second speed boost for the player
    */  
  public void startSpeedBoost() {
    
    //if the player does not have a speed boost active, give them half movement speed (doubles their speed)
    if (!hasSpeedBoost) {
      this.setBaseMovementSpeed(this.getBaseMovementSpeed() / 2);
      this.setXFrameChange(this.getXFrameChange() * 2);
      this.setYFrameChange(this.getYFrameChange() * 2);
    } //end of if
    
    //reset the speed time for 5 more seconds
    this.speedStartMS = System.currentTimeMillis();
    this.hasSpeedBoost = true;
    
  } //end of startSpeedBoost()
  
  /** activates a 5-second slow effect for the player
    */    
  public void startSlowEffect() {
    
    //if the player does not have a slow effect active, give them double movement speed (halfs their speed)
    if (!hasSlowEffect) {
      this.setBaseMovementSpeed(this.getBaseMovementSpeed() * 2);
      this.setXFrameChange(this.getXFrameChange() / 2);
      this.setYFrameChange(this.getYFrameChange() / 2);
    } //end of if
    
    //reset the slow time for 5 more seconds
    this.slowStartMS = System.currentTimeMillis();
    this.hasSlowEffect = true;
    
  } //end of startSlowEffect()
  
  /** gets the integer value of the player's score
    *@return the integer value of the player's score
    */      
  public int getScore() {
    return this.score; 
  } //end of getScore()
  
  /** increases the player's score by a certain amount
    *@param the integer amount that the score will be increased by
    */        
  public void increaseScore(int amount) {
    this.score = this.score + amount;
  } //end of increaseScore()
 
  /** gets the integer value of the player's number
    *@return the integer value of the player's number
    */     
  public int getPlayerNumber() {
    return this.playerNumber; 
  } //end of getPlayerNumber()

  /** gets the integer value of the player's most recent direction
    *@return the integer value of the player's most recent direction
    */    
  public int getMostRecentDirection() {
    return this.mostRecentDirection;
  } //end of getMostRecentDirection()
  
  /** updates the player's directions based on the players' inputs
    *@param directionPriority the direction that the player should try to move first
    */   
  public void updatePlayerDirection(int directionPriority) {
    
    //check if the player is not moving
    if (!this.getIsMoving()) {
      this.setDirection(-1);
      
      //check the direction with priority first to set the player's desired movement
      if (directionPriority >= 0 && playerMovement[directionPriority]) {
        this.setDirection(directionPriority);
        this.setAttemptToMove(true);
        
       //when it is not the priority direction, attempt to move with the first chosen other direction
      } else {
        for (int i = 0; i < playerMovement.length && this.getDirection() == -1 && i != directionPriority; i++) {
          if (this.playerMovement[i]) {
            this.setDirection(i);
            this.setAttemptToMove(true);
          } //end of if statement
        } //end of for loop
      } //end of inner if structure
    } //end of outer if structure
    
    //get the last direction faced in order to orientate projectiles
    if (this.getDirection() != -1) {
      this.mostRecentDirection = this.getDirection(); 
    }
    
  } //end of updatePlayerDirection()
  
  /** update the position of the player
    *@param gameMap the Map that the player is a part of
    */   
  public void updateMovement(Map gameMap) { 
    
    //check if the movement the player is trying to make is valid
    this.movementAttempt(gameMap);
    
    //when the player is moving ordinarily, update map position
    this.moveObject();
    
  } //end of updateMovement()
  
  /** update the actions of the player
    *@param levelMap          the Map the player is a part of
    *@param newPlayerMovement the integer directions the player is trying to move
    *@param directionPriority the integer direction the player is trying to move first
    */   
  public void updateActions(Map levelMap, boolean [] newPlayerMovement, int directionPriority) {
    
    //when the player is not dead
    if (this.getDeathTimer() == 0) {
      
      //end expired speed effect
      if (this.hasSpeedBoost && System.currentTimeMillis() - this.speedStartMS > 5000) {
        this.setBaseMovementSpeed(this.getBaseMovementSpeed() * 2);
        this.setXFrameChange(this.getXFrameChange() / 2);
        this.setYFrameChange(this.getYFrameChange() / 2);
        this.hasSpeedBoost = false;
      }
      
      //end expired speed effect
      if (this.hasSlowEffect && System.currentTimeMillis() - this.slowStartMS > 5000) {
        this.setBaseMovementSpeed(this.getBaseMovementSpeed() / 2);
        this.setXFrameChange(this.getXFrameChange() * 2);
        this.setYFrameChange(this.getYFrameChange() * 2);
        this.hasSlowEffect = false;
      }
      
      //update player movement methods
      this.playerMovement = newPlayerMovement;
      this.updatePlayerDirection(directionPriority);
      this.updateMovement(levelMap);
      
    } //end of outer if statement
    
    //update the player sprite
    this.updateSpriteImage();
    
  } //end of updateActions()
  
  /** activate the chosen powerup of the player
    *@param powerupSlot the integer powerup slot being activated
    *@param otherPlayer the Player that is the opponent of this player
    *@param gameMap     the map that this player is a part of
    */     
  public void activatePowerup(int powerupSlot, Player otherPlayer, Map gameMap) {
    
    //check if they have a powerup to be used
    if (hasPowerup[powerupSlot] && this.getDeathTimer() == 0) {
      
      //use the powerup
      powerupStorage[powerupSlot].activatePowerup(this, otherPlayer, gameMap);
      hasPowerup[powerupSlot] = false;
      powerupStorage[powerupSlot] = null;
      
    }// end of if structure
  } //end of activatePowerup()
  
  /** draw the player's powerup slots
    *@param graphic the Graphics tool that allows the tiles to be drawn on scree
    */  
  public void drawPowerupSlots(Graphics g) {
    
    //set default x position modifier
    int xPositionModifier = 504 ;
    
    //change the modifier if it is player 1
    if (this.playerNumber == 1) {
      xPositionModifier = -536;
    }
    
    //loop through each powerup slot
    for (int i = 0; i < 2; i++) {
      
      //draw a background square
      g.setColor(Color.WHITE);
      g.fillRect(GameFrame.SCREEN_WIDTH/2 + xPositionModifier, 375+80 * i, 32, 32);
      
      //draw the powerup image if applicable in the slot
      if (hasPowerup[i]) {
        g.drawImage(powerupStorage[i].getSpriteImage(),GameFrame.SCREEN_WIDTH/2 + xPositionModifier, 375+80 * i, 32, 32, null);
      }
      
    } //end of for loop
  } //end of drawPowerupSlots()
  
} //end of Player class