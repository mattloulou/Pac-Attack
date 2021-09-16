/* AnimatedWorldObject.java
 * Matthew Pechen-Berg and Peter Jang
 * January 17th, 2020
 * the base class that all moving animated objects will inherit from (like players and projectiles)
 */

//Graphics and GUI imports
import java.awt.image.BufferedImage;
import java.awt.Graphics;

public abstract class AnimatedWorldObject extends WorldObject {
  
  //declare private class variables
  private boolean isMoving;
  private boolean attemptToMove;
  private boolean isTeleporting;
  private boolean targetTileState;
  
  //declaring general movement variables
  private int initialDirection;
  private int spriteRow;
  private int spriteCol;
  private int spritesheetRowMin;
  private int spritesheetRowMax;
  private int spriteDurationMS; //how long each fram is, in milliseconds
  private int framePeriodMS;
  private int baseMovementSpeed; //time in MS to travel one tile
  private int rowChange;
  private int colChange;
  private int col;
  private int row;
  private int direction;
  private int totalTileRows;
  private int totalTileCols;
  
  //declare time-related variables
  private long lastFrameUpdateMS;
  private long deathTimeMS;
  private long lastMovementTimeMS; //last recorded time in milliseconds
  
  //declaring more general movement variables
  private double xFrameChange;
  private double yFrameChange; 
  private double targetX;
  private double targetY;
  
  //declare variables for collision
  private Point topLeft;
  private Point bottomRight;
  
  //constructor for the AnimatedWolrdObject class
  AnimatedWorldObject(int x, int y, int row, int col, int spritesheetRows, int spritesheetCols, String spritesheetName, int spritesheetRowMin, int spritesheetRowMax, int direction, int baseMovementSpeed, boolean attemptToMove) {
    super(x, y, spritesheetRows, spritesheetCols, spritesheetName);
    this.row = row;
    this.col = col;
    this.direction = direction;
    this.spritesheetRowMin = spritesheetRowMin;
    this.spritesheetRowMax = spritesheetRowMax;
    this.baseMovementSpeed = baseMovementSpeed;
    this.attemptToMove = attemptToMove;
    
    //initialize some values
    this.isMoving = false;
    this.isTeleporting = false;
    this.spriteDurationMS = 167;
    this.spriteRow = 0;
    this.spriteCol = 0;
    this.deathTimeMS = 0;
    this.totalTileRows = 21;
    this.totalTileCols = 25;
    this.framePeriodMS = 16;
    
    //initialize time values
    this.lastFrameUpdateMS = System.currentTimeMillis();
    this.lastMovementTimeMS = System.currentTimeMillis();
    
    //Set initial sprite direction
    this.initialDirection = this.getDirection();
    
  } //end of constructor for AnimatedWorldObject

  /** gets the boolean value of if the object is trying to move or not
    *@return the boolean value of if the object is trying to move or not
    */      
  public boolean getAttemptToMove() {
    return this.attemptToMove; 
  } //end of getAttemptToMove()
  
  /** sets the boolean value of if the object is attempting to move or not
    *@param newAttemptToMove the boolean value of if the object is now attempting to move or not
    */      
  public void setAttemptToMove(boolean newAttemptToMove) {
    this.attemptToMove = newAttemptToMove; 
  } //end of setAttemptToMove()
  
  /** gets the integer value of the object's base movement speed
    *@return the integer value of the object's base movement speed
    */    
  public int getBaseMovementSpeed() {
    return this.baseMovementSpeed;
  } //end of getBaseMovementSpeed()
  
  /** sets the integer value of the object's base movement speed
    *@param newMovementSpeed the new integer value of the object's base movement speed
    */    
  public void setBaseMovementSpeed(int newMovementSpeed) {
    this.baseMovementSpeed = newMovementSpeed;
  } //end of setBaseMovementSpeed()
  
  /** gets the double value of the object's frame change along the x axis per frame
    *@return the double value of the object's frame change along the x axis per frame
    */    
  public double getXFrameChange() {
    return this.xFrameChange;
  } //end of getXFrameChange()
  
  /** sets the double value of the object's frame change along the x axis per frame
    *@param newXFrameChange the double value of the object's frame change along the x axis per frame
    */      
  public void setXFrameChange(double newXFrameChange) {
    this.xFrameChange = newXFrameChange;
  } //end of setXFrameChange()
  
  /** gets the double value of the object's frame change along the y axis per frame
    *@return the double value of the object's frame change along the y axis per frame
    */    
  public double getYFrameChange() {
    return this.yFrameChange;
  } //end of getYFrameChange()
  
  /** sets the double value of the object's frame change along the y axis per frame
    *@param newYFrameChange the double value of the object's frame change along the y axis per frame
    */        
  public void setYFrameChange(double newYFrameChange) {
    this.yFrameChange = newYFrameChange;
  } //end of setYFrameChange()
  
  /** gets the boolean value of if the object is moving
    *@return the boolean value of if the object is moving
    */    
  public boolean getIsMoving() {
    return this.isMoving; 
  } //end of getIsMoving()
  
  /** sets the boolean value of if the object is moving
    *@param input the new boolean value of if the object is moving
    */     
  public void setIsMoving(boolean input) {
    this.isMoving = input; 
  } //end of setIsMoving()
  
  /** gets the integer total number of tile rows in the map
    *@return the integer total number of tile rows in the map
    */   
  public int getTotalTileRows() {
    return this.totalTileRows;
  } //end of getTotalTileRows()
  
  /** gets the integer total number of tile columns in the map
    *@return the integer total number of tile columns in the map
    */     
  public int getTotalTileCols() {
    return this.totalTileCols;
  } //end of getTotalTileCols()
  
  /** creates the top left and bottom right points of the object for collision purposes
    *@return the integer total number of tile columns in the map
    */       
  public void createPoints(double x1, double y1, double x2, double y2) {
    this.topLeft = new Point(x1, y1);
    this.bottomRight = new Point(x2, y2);
  } //end of createPoints()
  
  /** gets the column integer value of the object
    *@return the column integer value of the object
    */   
  public int getCol() { 
    return this.col;
  } //end of getCol()
  
  /** sets the column integer value of the object
    *@param newCol the new column integer value of the object
    */   
  public void setCol(int newCol) {
    this.col = newCol;
  } //end of setCol()
  
  /** gets the row integer value of the object
    *@return the row integer value of the object
    */    
  public int getRow() {
    return this.row;
  } //end of getRow()
  
  /** sets the row integer value of the object
    *@param newRow the new row integer value of the object
    */     
  public void setRow(int newRow) {
    this.row = newRow;
  } //end of setRow()
  
  /** increments the spritesheet row value of the object by 1 position, overflowing down to 0 if it goes above the max
    */       
  public void incrementSpriteRow() {
    this.spriteRow = (this.spriteRow + 1) % (this.spritesheetRowMax + 1) + this.spritesheetRowMin;
  } //end of incrementSpriteRow()
  
  /** gets the integer value of the object's direction
    *@return the integer value of the object's direction
    */      
  public int getDirection() {
    return this.direction;
  } //end of getDirection()
  
  /** sets the integer value of the object's direction
    *@param newDirection the new integer value of the object's direction
    */     
  public void setDirection(int newDirection) {
    this.direction = newDirection;
  } //end of setDirection()
  
  /** gets the long value of the object's death timer in milliseconds
    *@return the long value of the object's death timer in milliseconds
    */      
  public long getDeathTimer() {
    return this.deathTimeMS;
  } //end of getDeathTimer()
  
  /** sets the long value of the object's death timer in milliseconds to the current time
    */       
  public void setDeathTimer() {
    this.deathTimeMS = System.currentTimeMillis();
  } //end of setDeathTimer()
  
  /** sets the long value of the object's death timer in milliseconds to the given time
    *@param newTime the new integer value of the object's death timer in milliseconds
    *@return the long value of the object's death timer in milliseconds
    */       
  public void setDeathTimer(int newTime) {
    this.deathTimeMS = newTime;
  } //end of setDeathTimer()
  
  /** gets the integer value of the object's spritsheet row
    *@return the integer value of the object's spritsheet row
    */   
  public int getSpriteRow() {
    return this.spriteRow;
  } //end of getSpriteRow()
  
  /** increments the spritesheet column value of the object by 1 position, overflowing down to 0 if it goes above the max
    */      
  public void incrementSpriteCol() {
    this.spriteCol = (this.spriteCol + 1) % 4;
  } //end of incrementSpriteCol()
  
  /** gets the integer value of the object's spritsheet column
    *@return the integer value of the object's spritsheet column
    */     
  public int getSpriteCol() {
    return this.spriteCol;
  } //end of getSpriteCol()  

  /** sets the integer value of the object's current spritesheet column
    *@param newCol the new integer value of the object's current spritesheet column
    */   
  public void setSpriteCol(int newCol) {
    this.spriteCol = newCol; 
  } //end of setSpriteCol()
  
  /** gets the sprite that the objet should currently be using
    *@return the BufferedImage image that the object is currently using
    */     
  public BufferedImage getCurrentSprite() {
    return this.getSprite(this.spriteRow, this.spriteCol);
  } //end of getCurrentSprite()
  
  /** updates the objects currently selected sprite
    */     
  public void updateSpriteImage() {
    
    //check if the duration of a single sprite frame has passed
    if (System.currentTimeMillis() - this.lastFrameUpdateMS > this.spriteDurationMS) {
      
      //update the sprites row if it has
      this.lastFrameUpdateMS += this.spriteDurationMS;
      this.incrementSpriteRow();
      
    }//end of if structure
  } //end of updateSpriteImage()
  
  /** attempts to move the given object
    *@param gameMap the Map that the game is being played on
    */    
  public void movementAttempt(Map gameMap) {
    
    //check if the player is trying to move
    if (this.attemptToMove) {
      
      //Update sprite direction
      if (this.direction != -1) {
        this.setSpriteCol(this.getDirection());
      }
      
      //declare some variables
      this.reinitializeMovementVariables();
      
      //determine the column and position of target tile based on movement direction
      this.determineColRowChanges();
      
      //update how many pixels the player should move per frame 
      this.xFrameChange = this.colChange * (32 / (this.baseMovementSpeed / this.framePeriodMS));
      this.yFrameChange = this.rowChange * (32 / (this.baseMovementSpeed / this.framePeriodMS));
      
      //determine the target coordinates
      this.targetX += (this.colChange * 32);
      this.targetY += (this.rowChange * 32);
      
      //set teleporting to true by default
      this.isTeleporting = true;
      
      //check if the player is trying to enter a teleporter tile, and make them move appropriately
      this.updateTeleportation();
      
      // if the object is not teleporting
      if (!this.isTeleporting) {
        
        //check if the tile the player is trying to move to is a wall or not (checks collision)
        this.targetTileState = gameMap.getIsTileWall(this.getRow() + this.rowChange, this.col + this.colChange);
        
        //if the target is not a wall, move the object
        if (!this.targetTileState) {
          this.isMoving = true;
        }
        
      //if the object is not teleporting
      } else {
        
        //make it move
        this.isMoving = true; 
        
      }//end of else
      
      //stop the object from attempting to move
      this.attemptToMove = false;
      
    } //end of outer if structure 
  } //end of movementAttempt()
  
  /** updates the teleportation status of the object when it is moving through a teleporter
    */    
  public void updateTeleportation() {
    
    //check if it is moving through the left teleporter
    if (this.col == 0 && this.row == this.totalTileRows/2 && this.direction == 0) {
      this.setX(this.getX() + this.totalTileCols * 32);
      this.targetX += (this.totalTileCols * 32);
      this.col = totalTileCols;
      
    //check if it is moving through the right teleporter  
    } else if (this.col == totalTileCols - 1 && this.getRow() == this.totalTileRows/2 && this.direction == 2) {
      this.setX(this.getX() - this.totalTileCols * 32);
      this.targetX -= (this.totalTileCols * 32);
      this.col = -1;
      
    //check if it is moving through the up teleporter    
    } else if (this.col == totalTileCols/2 && this.getRow() == 0 && this.direction == 3) {
      this.setY(this.getY() + this.totalTileRows * 32);
      this.targetY += (this.totalTileRows * 32);
      this.row = totalTileRows;
      
    //check if it is moving through the down teleporter    
    } else if (this.col == totalTileCols/2 && this.getRow() == this.totalTileRows - 1 && this.direction == 1) {
      this.setY(this.getY() - this.totalTileRows * 32);
      this.targetY -= (this.totalTileRows * 32);
      this.row = -1;
      
      //set teleporting to false if it is not moving through any
    } else {
      this.isTeleporting = false;
      
    } //end of if structure
    
  } //end of updateTeleportation()
  
  /** updates the object's values for their change in column and row upon moving
    */    
  public void determineColRowChanges() {
    
    //check which direction the object is moving, and set row and col change appropriately
    if (this.getDirection() == 0) {
      this.colChange = -1;
    } else if (this.getDirection() == 1) {
      this.rowChange = 1;
    } else if (this.getDirection() == 2) {
      this.colChange = 1;
    } else {
      this.rowChange = -1;
    }  //end of if structure
    
  } //end of determineColRowChanges()
  
  /** reinitialize many of the variables used by the object when moving between tiles
    */    
  public void reinitializeMovementVariables() {
    
    //reset many of the variables
    this.rowChange = 0;
    this.colChange = 0;
    this.xFrameChange = 0;
    this.yFrameChange = 0;
    this.targetX = this.getX();
    this.targetY = this.getY();
  } //end of reinitializeMovementVariables()
  
  /** moves the object in the appropriate direction when called
    */    
  public void moveObject() {
    
    //checks to see if the object is moving
    if (this.isMoving) {
      
      // check if one single frame has passed
      if (System.currentTimeMillis() - lastMovementTimeMS > 16) {
        
        //update the x and y value when it should
        lastMovementTimeMS += 16;
        this.setX(this.getX() + this.xFrameChange);
        this.setY(this.getY() + this.yFrameChange);
        
      } //end of if structure
      
      //when the player moves past the desired position, send them to it and end the movement.
      if (((this.direction == 0 || this.direction == 3) && (this.getX() < this.targetX || this.getY() < this.targetY)) || ((this.direction == 1 || this.direction == 2) && (this.getX() > this.targetX || this.getY() > this.targetY))) {
        
        //update many variables
        this.setX(this.targetX);
        this.setY(this.targetY);
        this.setRow(this.getRow() + this.rowChange);
        this.col += this.colChange;
        this.isMoving = false;
        this.isTeleporting = false;
        if (this.baseMovementSpeed == 40) {
          this.attemptToMove = true;
        } //end if
        
      } //end larger if structure
    } //end of isMoving if structure 
    
  } //end of moveObject()
  
  /** draws all of the animated object that dont have a fixed direction each time (ie. players)
    *@param graphic the Graphics tool that allows the tiles to be drawn on screen 
    */    
  public void drawObject(Graphics graphic) {
    
    //when the object is not dead
    if (this.deathTimeMS == 0) {
      
      //draw the current position of the object
      graphic.drawImage(this.getCurrentSprite(), (int) this.getX() + this.getOffsetX(), (int) this.getY() + this.getOffsetY(), 32, 32, null);
      
      //draw a second image of the object when they are teleporting to make it look like they are smoothly going through the teleporter
      if (this.isTeleporting) {
        
        //when object is moving through left teleporter
        if (this.direction == 0) {
          graphic.drawImage(this.getCurrentSprite(), (int) this.getX() + this.getOffsetX() - (this.totalTileCols * 32), (int) this.getY() + this.getOffsetY(), 32, 32, null);
        
        //when object is moving through down teleporter
        } else if (this.direction == 1) {
          graphic.drawImage(this.getCurrentSprite(), (int) this.getX() + this.getOffsetX(), (int) this.getY() + this.getOffsetY() + (this.totalTileRows * 32), 32, 32, null);
        
        //when object is moving through right teleporter
        } else if (this.direction == 2) {
          graphic.drawImage(this.getCurrentSprite(), (int) this.getX() + this.getOffsetX() + (this.totalTileCols * 32), (int) this.getY() + this.getOffsetY(), 32, 32, null);
        
        //when object is moving through up teleporter
        } else {
          graphic.drawImage(this.getCurrentSprite(), (int) this.getX() + this.getOffsetX(), (int) this.getY() + this.getOffsetY() - (this.totalTileRows * 32), 32, 32, null);
        } //end of if structure
        
      } //end of the teleporting section
    } //end of drawing objects
    
  } //end of drawObject()
  
  
  /** draws all of the animated object that do have a fixed direction each time (ie. projectiles)
    *@param graphic the Graphics tool that allows the tiles to be drawn on screen 
    */      
  public void drawProjectile(Graphics graphic) {
    
    //when the object is not dead
    if (this.deathTimeMS == 0) {
      
      //draw the current position of the object
      graphic.drawImage(this.getSprite(0, this.initialDirection), (int) this.getX() + this.getOffsetX(), (int) this.getY() + this.getOffsetY(), 32, 32, null);
      
      //draw a second image of the object when they are teleporting to make it look like they are smoothly going through the teleporter
      if (this.isTeleporting) {
        
        //when object is moving through left teleporter
        if (this.direction == 0) {
          graphic.drawImage(this.getSprite(0, this.initialDirection), (int) this.getX() + this.getOffsetX() - (this.totalTileCols * 32), (int) this.getY() + this.getOffsetY(), 32, 32, null);
        
        //when object is moving through down teleporter
        } else if (this.direction == 1) {
          graphic.drawImage(this.getSprite(0, this.initialDirection), (int) this.getX() + this.getOffsetX(), (int) this.getY() + this.getOffsetY() + (this.totalTileRows * 32), 32, 32, null);
        
        //when object is moving through right teleporter
        } else if (this.direction == 2) {
          graphic.drawImage(this.getSprite(0, this.initialDirection), (int) this.getX() + this.getOffsetX() + (this.totalTileCols * 32), (int) this.getY() + this.getOffsetY(), 32, 32, null);
        
        //when object is moving through up teleporter
        } else {
          graphic.drawImage(this.getSprite(0, this.initialDirection), (int) this.getX() + this.getOffsetX(), (int) this.getY() + this.getOffsetY() - (this.totalTileRows * 32), 32, 32, null);
        } //end of if structure
        
      } //end of the teleporting section
    } //end of drawing objects
    
  } //end of drawProjectile()
  
} //end of AnimatedWorldObject class