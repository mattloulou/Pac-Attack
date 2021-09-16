/* Map.java
 * Matthew Pechen-Berg and Peter Jang
 * January 17th, 2020
 * the class that stores all of the tiles and manages different events that happen in the map
 */

//Graphics and GUI imports
import java.awt.Graphics;

//Misc. imports
import java.util.ArrayList;

public class Map extends WorldObject{
  
  //declare private variables and initialize some
  private int numCols;
  private int numRows;
  private int [][] initialMap;
  private int [][] spawnedPowerupsMap;
  private Tile [][] arenaMap;
  private Bombs bombCollection;
  private ArrayList<SpawnedPowerup> spawnedPowerups;
  private ArrayList<SpawnedProjectile> spawnedLasers;
  
  //constructor for the Map class
  Map(int [][] initialMap, String spritesheetName, int spritesheetRows, int spritesheetCols) {
    super(0, 0, spritesheetRows, spritesheetCols, spritesheetName);
    this.numCols = 25;
    this.numRows = 21;
    this.initialMap = initialMap;
    this.spawnedPowerupsMap = new int [21][25];
    this.spawnedPowerups = new ArrayList<SpawnedPowerup>();
    this.bombCollection = new Bombs(this.getOffsetX(), this.getOffsetY());
    this.spawnedLasers = new ArrayList<SpawnedProjectile>();
    this.generateArenaMap(); //create the map
  } //end of Map class constructor
  
  /** establishes a grid of Tiles that will serve to be the map that is played on
    */
  public void generateArenaMap() {
    
    //initialize the Tile array to null Tiles
    this.arenaMap = new Tile[numRows][numCols];
    
    //declare and initialize a variable
    int spritesheetRow = 0;
    
    //loop through each integer in the initial integer tile map
    for (int i = 0; i < numRows; i++) {
      for (int j = 0; j < numCols; j++) {
        
        //CREATE THE APPROPRIATE TILES BASED ON THEIR SURROUNDINGS
        
        //Check if the tile slot is not a wall tile;
        if (initialMap[i][j] == 0) {
          this.arenaMap[i][j] = new Tile(false, i, j, this.getSprite(4,0), this.getOffsetX(), this.getOffsetY());
          
          //When a position is a tile, check what its surroundings are to use the correct sprite for it
        } else {
          
          //CHECK WHICH DIRECTIONS IT CONNECTS TO, AND WHAT SPRITESHEET ROW TO USE
          
          //intialize and declare some variables
          boolean opensLeft = false;
          boolean opensDown = false;
          boolean opensUp = false;
          boolean opensRight = false;
          
          //when it has a right opening
          if (j < numCols-1 && initialMap[i][j+1] == 1) { 
            opensRight = true;
            spritesheetRow = 3;
          }
          
          //when it has an up opening
          if (i > 0 && initialMap[i-1][j] == 1) {
            opensUp = true;
            spritesheetRow = 2;
          }
          
          //when it has a down opening
          if (i < numRows-1 && initialMap[i+1][j] == 1) {
            opensDown = true;
            spritesheetRow = 1;
          }
          
          //when it has a left opening
          if (j > 0 && initialMap[i][j-1] == 1) {
            opensLeft = true;
            spritesheetRow = 0;
          }
          
          //CHECK WHICH COLUMN POSITION TO USE FROM THE SPRITE SHEET
          
          //initialize and declare a variable
          int spritesheetCol = (int) Math.pow(2,3-spritesheetRow) -1;
          
          //based on how the spritesheet was created, subtract certain values from the column position based on what secondary openings the wall tile has (secondary means not the one found in the first part)
          if (opensDown && spritesheetRow < 1) {
            spritesheetCol -= 4;
          }
          if (opensUp && spritesheetRow < 2) {
            spritesheetCol -= 2;
          }
          if (opensRight && spritesheetRow < 3) {
            spritesheetCol -= 1;
          } //end of if statements that find the spritesheet column
          
          //Create the appropriate tile with the appropriate image
          this.arenaMap[i][j] = new Tile(true, i, j, this.getSprite(spritesheetRow,spritesheetCol), this.getOffsetX(), this.getOffsetY());
          
        } //end of if structure that creates the tiles
      }//end of inner for loop
    }//end of outer for loop
    
  } //end of generateArenaMap()
  
  /** draws every tile in the grid of Tile objects
    *@param graphic the Graphics tool that allows the tiles to be drawn on screen
    */    
  public void drawMap(Graphics graphic) {
    
    //loop through each tile
    for (int i = 0; i < numRows; i++) {
      for (int j = 0; j < numCols; j++) {
        
        //draw the tile
        this.arenaMap[i][j].drawTile(graphic);
      } //end of inner loop
    } //end of outer loop
    
  } //end of drawMap()
  
  /** generates and adds point pellets to the map where spaces not near the players are available
    *@param player1 the Player controlling the red figure
    *@param player2 the Player controlling the green figure
    */      
  public void spawnPointPellets(Player player1, Player player2) {
    
    //declare a variable
    int randomSize;
    
    //loop through each tile in the grid to look for appropriate spawning locations
    for (int i = 0; i < numRows; i++) {
      for (int j = 0; j < numCols; j++) {
        
        //check if the tile location is valid for a point pellet spawn
        if (spawnedPowerupsMap[i][j] == 0 && initialMap[i][j] == 0 
              && Math.sqrt(Math.abs(Math.pow(i - player1.getRow(),2) + Math.pow(j - player1.getCol(),2))) > 1 
              && Math.sqrt(Math.abs(Math.pow(i - player2.getRow(),2) + Math.pow(j - player2.getCol(),2))) > 1) {
          
          //if the location is valid, add a point pellet to the map
          spawnedPowerupsMap[i][j] = 1;
          randomSize = ((int) (Math.random() * 15)) / 14; //random chance for a large pellet
          spawnedPowerups.add(new PointPellet(i, j, randomSize));
        } //end of checking for valid spawn location if statement
      } //end of inner for loop
    } //end of outer for loop
  } //end of spawnPointPellets()
  
  /** draw all of the powerups on the map
    *@param graphic the Graphics tool that allows the tiles to be drawn on screen
    */   
  public void drawMapPowerups(Graphics graphic) {
    
    //loop through each powerup
    for (int i = 0; i < this.spawnedPowerups.size(); i++) {
      graphic.drawImage(this.spawnedPowerups.get(i).getSpriteImage(), this.getOffsetX() + (int) this.spawnedPowerups.get(i).getX(),
                        this.getOffsetY() + (int) this.spawnedPowerups.get(i).getY(), 32, 32, null);
      
    } //end of for loop
  } //end of drawMapPowerups()
  
  /** remove the desired powerup from the powerup list using index
    *@param index the integer index of the powerup to be removed
    */     
  public void removePowerup(int index) {
    
    //remove the powerup
    this.spawnedPowerupsMap[this.spawnedPowerups.get(index).getRow()][this.spawnedPowerups.get(index).getCol()] = 0; //set the position to unoccupied
    this.spawnedPowerups.remove(index);
  } //end of removePowerup()
  
  /** remove the desired powerup from the powerup list using row and column
    *@param row the integer row of the powerup that is to be removed
    *@param col the integer col of the powerup that is to be removed
    */     
  public void removePowerup(int row, int col) {
    
    //loop through each powerup spawned
    for (int i = 0; i < this.spawnedPowerups.size(); i++) {
      
      //check if the powerup has the same col and row as the one being hunted
      if (this.spawnedPowerups.get(i).getX() == 32 * col && this.spawnedPowerups.get(i).getY() == 32 * row) {
        this.removePowerup(i); //remove it if it does
        
      }//end of if statement
    } //end of for loop
    
  } //end of removePowerup()
  
  /** gather any powerups that the player passes through for themselves
    *@param player the Player who gathered the powerup
    */   
  public void gatherPowerups(Player player) {
    
    //loop through each powerup in the list of them
    for (int i = 0; i < this.spawnedPowerups.size(); i++) {
      
      //if the powerup is withing range of the player
      if (Math.sqrt(Math.abs(Math.pow(this.spawnedPowerups.get(i).getX() - player.getX(),2) 
                               + Math.pow(this.spawnedPowerups.get(i).getY() - player.getY(),2))) < 10) {
        
        //give the player the powerup and delete it
        this.spawnedPowerups.get(i).getPowerup(player);
        this.removePowerup(i);
      } //end of if statement
    }//end of loop
  } //end of gatherPowerups()
  
  /** spawn a non point pellet powerup on a random tile not near the players
    *@param player1 the Player controlling the red figure
    *@param player2 the Player controlling the green figure
    */     
  public void spawnGrabbablePowerup(Player player1, Player player2) {
    
    //initialize and declare some variables
    boolean spawnedPowerup = false;
    int randomCol;
    int randomRow;
    int randomPowerup = (int) (Math.random() * 4) + 2; //get the index number of a random non-point powerup
    
    //look for a random non-wall tile to spawn the new powerup not near a player
    while (!spawnedPowerup) {
      randomCol = (int) (Math.random() * numCols);
      randomRow = (int) (Math.random() * numRows);
      
      //if an empty tile has space for a powerup to be spawned
      if (spawnedPowerupsMap[randomRow][randomCol] != 2 && initialMap[randomRow][randomCol] == 0 
            && Math.sqrt(Math.abs(Math.pow(randomRow - player1.getRow(),2) + Math.pow(randomCol - player1.getCol(),2))) > 1 
            && Math.sqrt(Math.abs(Math.pow(randomRow - player2.getRow(),2) + Math.pow(randomCol - player2.getCol(),2))) > 1) {
        
        //remove the point pellet if there is one
        this.removePowerup(randomRow, randomCol);
        
        //update the powerup information for that tile
        spawnedPowerup = true;
        spawnedPowerupsMap[randomRow][randomCol] = 2;
        
        //spawn the correct powerup at the position
        if (randomPowerup == 2) {
          spawnedPowerups.add(new SpeedPowerup(randomRow, randomCol));
        } else if (randomPowerup == 3) {
          spawnedPowerups.add(new SlowEnemyPowerup(randomRow, randomCol));
        } else if (randomPowerup == 4) {
          spawnedPowerups.add(new BombPowerup(randomRow, randomCol));
        } else if (randomPowerup == 5) {
          spawnedPowerups.add(new LaserPowerup(randomRow, randomCol));
        } //end of powerup spawning decision structure
      } //end of valid spawning tile if structure
    } //end of while loop
    
  } //end of spawnGrabbablePowerup()
  
  
  /** draws all of the placed bombs on the map
    *@param graphic the Graphics tool that allows the bombs to be drawn on screen
    */      
  public void drawMapElements(Graphics graphic) {
    
    //draw all the placed bombs
    this.bombCollection.drawPlacedBombs(graphic);
    
  } //end of drawMapElements()
  
  /** update most of the map information regarding the players, lasers, and bombs
    *@param player1 the Player controlling the red figure
    *@param player2 the Player controlling the green figure
    *@param g the Graphics tool that allows the bombs to be drawn on screen
    */        
  public void updateMap(Player player1, Player player2, Graphics g) {
    
    //update general information about the players, lasers, and bombs deployed
    this.checkPlayerRespawns(player1, player2);
    this.checkPlayerRespawns(player2, player1);
    this.bombCollection.checkBombTimers(player1, player2, this, g);
    this.drawLasers(g);
    this.updateLasers(player1, player2);
    
  }//end of updateMap()
  
  
  /** checks to see if a player has respawned from the dead
    *@param deadPlayer  the Player that is being checked to see if it has died and respawned
    *@param otherPlayer the Player that is not the one being directly checked
    */    
  public void checkPlayerRespawns(Player deadPlayer, Player otherPlayer) {
    
    //check to see if the potentially dead player has completed their respawn duration
    if (System.currentTimeMillis() - deadPlayer.getDeathTimer() > 5000 && deadPlayer.getDeathTimer() != 0) {
      
      //updates the conditions of the player if that is true
      deadPlayer.setDeathTimer(0);
      boolean respawnedPlayer = false;
      
      //declares some variables
      int randomCol;
      int randomRow;
      
      //look for a random non-wall tile to respawn the player
      while (!respawnedPlayer) {
        
        //generate random tile location
        randomCol = (int) (Math.random() * numCols);
        randomRow = (int) (Math.random() * numRows);
        
        //check if the player has not spawned on a powerup, a wall, or too close to their enemy
        if (spawnedPowerupsMap[randomRow][randomCol] != 2 && initialMap[randomRow][randomCol] == 0 
              && Math.sqrt(Math.abs(Math.pow(randomRow - otherPlayer.getRow(),2) 
              + Math.pow(randomCol - otherPlayer.getCol(),2))) > 3) {
          
          //when location is valid, update their position data to reflect their new location
          respawnedPlayer = true;
          deadPlayer.setRow(randomRow);
          deadPlayer.setCol(randomCol);
          deadPlayer.setX(randomCol * 32);
          deadPlayer.setY(randomRow * 32);
          deadPlayer.setDirection(-1);
          deadPlayer.setIsMoving(false);
        } //end of inner if statement
      } //end of while loop
      
      //log a message to the console
      System.out.println("Player " + deadPlayer.getPlayerNumber() + " has respawned");
      
    } //end of outer respawen if statement
  }//end of checkPlayerRespawns()
  
  /** kills the selected player, applying dead-related effects to them
    *@param killedPlayer  the Player that is being killed
    *@param otherPlayer   the Player that is not being killed
    */      
  public void killPlayer(Player killedPlayer, Player otherPlayer) {
    
    //log the player killed
    System.out.println(killedPlayer.getPlayerNumber() + " has died");
    
    //change location of the player upon death
    killedPlayer.setDeathTimer();
    killedPlayer.setRow(99);
    killedPlayer.setCol(99);
    killedPlayer.setX(100000);
    killedPlayer.setY(100000);
    
    //increase the opponents score
    otherPlayer.increaseScore(700);
    
  }//end of killPlayer()
  
  /** spawns a laser from the player activating it
    *@param playerShooter the Player shooting the laser beam
    */  
  public void shootLaser(Player playerShooter) {
    
    //declare a variable
    String spritesheetName;
    
    //select the correct spritesheet name based on the player's number
    if (playerShooter.getPlayerNumber() == 1) {
      spritesheetName = "Images/RedLaser.png";
    } else {
      spritesheetName =  "Images/GreenLaser.png";
    } //end of if structure
    
    //spawn the laser shot
    spawnedLasers.add(new SpawnedProjectile(playerShooter.getX(), playerShooter.getY(), playerShooter.getRow(),
                                            playerShooter.getCol(), 2, 4,
                                            spritesheetName, 0, 1, playerShooter.getMostRecentDirection(), playerShooter.getPlayerNumber()));   
    
  }//end of shootLaser()
  
  /** draws all of the lasers that have been spawned
    *@param graphic the Graphics tool that allows the tiles to be drawn on screen
    */    
  public void drawLasers(Graphics g) {
    
    //loop through each spawned laser
    for (int i = 0; i < spawnedLasers.size(); i++) {
      
      //draw the laser
      spawnedLasers.get(i).drawObject(g);
    } //end of for loop
    
  }//end of drawLasers()
  
  
  /** checks to see if any projectiles have collided with a player
    *@param gamePlayer  the Player that is being checked for collision
    *@param otherPlayer the Player that is not being checked for collision
    *@param projectile  the lasers that are being checked for collision with the player
    */    
  public void checkProjectileCollision(Player gamePlayer, Player otherPlayer, SpawnedProjectile projectile) {
    
    //check if the projectile's owner is the not player being checked for collision
    if (projectile.getOwner() != gamePlayer.getPlayerNumber()) {
      
      //check if the laser and targeted player have their collisions overlap
      if (Hitbox.checkOverlap(new Point(projectile.getX() + 5, projectile.getY() + 5), 
                              new Point(projectile.getX() + 22, projectile.getY() + 22), 
                              new Point(gamePlayer.getX() + 5, gamePlayer.getY() + 5), 
                              new Point(gamePlayer.getX() + 22, gamePlayer.getY() + 22))) {
        
        //when their colisions overlap, then kill the player and projectile
        this.killPlayer(gamePlayer, otherPlayer);
        projectile.setIsDoneMoving(true);
        
      } //end of inner if statement
    } //end of outer if statement
  }//end of checkProjectileCollision()
  
  /** updates all of the spawned lasers
    *@param player1 the Player controlling the red figure
    *@param player2 the Player controlling the green figure
    */      
  public void updateLasers(Player playerOne, Player playerTwo) {
    
    //loop through each spawned laser
    for (int i = spawnedLasers.size(); i > 0 ; i--) {
      
      //update their movement and check collisions
      spawnedLasers.get(i-1).updateMovement(this);
      this.checkProjectileCollision(playerOne, playerTwo, spawnedLasers.get(i-1));
      this.checkProjectileCollision(playerTwo, playerOne, spawnedLasers.get(i-1));
      
      //remove collided or used up lasers from list of them
      if (spawnedLasers.get(i-1).getIsDoneMoving()) {
        spawnedLasers.remove(i-1);
      } //end of if
      
    }//end of for loop
    
  }//end of updateLasers()
  
  /** gets the boolean value of if the target Tile is a wall or not
    *@param row the integer value of the row position being checked
    *@param col the integer value of the col position being checked
    *@return    the boolean value of if the Tile is a wall or not
    */    
  public boolean getIsTileWall(int row, int col) {
    return this.arenaMap[row][col].getIsWall();
  } //end of getIsTileWall()
  
  /** gets the collection of bombs being stored by the map
    *@return the Bombs collection of bombs being stored by the map
    */   
  public Bombs getBombCollection() {
    return this.bombCollection; 
  } //end of getBombCollection()
  
} //end of Map class