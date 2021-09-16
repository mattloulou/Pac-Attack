/* Bombs.java
 * Matthew Pechen-Berg and Peter Jang
 * January 17th, 2020
 * a class that acts as a storage container for all of the deployed bomb power ups
 */

//gui and misc. imports
import java.util.ArrayList;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.awt.Graphics;

//File imports
import java.io.*; 

public class Bombs { 
  
  //declare class private variables and initialize some
  private int offsetX;
  private int offsetY;
  private ArrayList<Long> deployedBombTimers;
  private ArrayList<Integer> deployedBombOwners;
  private ArrayList<Point> deployedBombLocations; 
  private BufferedImage [] bombSprites;
  private BufferedImage fireSprite;
  
  // constructor for Bombs
  Bombs(int offsetX, int offsetY) {
    this.offsetX = offsetX;
    this.offsetY = offsetY;
    this.deployedBombTimers = new ArrayList<Long>();
    this.deployedBombOwners = new ArrayList<Integer>();
    this.deployedBombLocations = new ArrayList<Point>();
    this.bombSprites = new BufferedImage [2];
    this.loadFireSprites();
    this.loadBombSprites();
  } //end of Bombs constructor
  
  /**
   * loads the sprites for the different coloured bombs
   */   
  public void loadBombSprites() {
    try {
      
      //load in a temporary sprite sheet to store the bombs
      BufferedImage spritesheet = ImageIO.read(new File("Images/BombSprites.png"));
      
      //loop through the two sub images
      for (int i = 0; i < 2; i++) {
        
        //get the individual sprite
        this.bombSprites[i] = spritesheet.getSubimage(i*32,0,32,32);
        
      }//end of for loop
    } catch (Exception e) {System.out.println("Bombs spritesheet encountered an error while loading.");};
  } //end of loadBombSprites() 
  
  /**
   * loads the fire effect sprite
   */    
  public void loadFireSprites() {
    try {
      
      //load the sprite
      fireSprite = ImageIO.read(new File("Images/FireEffect.png"));
      
    } catch (Exception e) {System.out.println("Bombs spritesheet encountered an error while loading.");};
  } //end of loadFireSprites() 
  
  /**
   * deploys a bomb at the position of the powerup user
   *@param bombOwner the Player that placed the bomb down
   */    
  public void deployBomb(Player bombOwner) {
    
    //get information about the bomb place time, placer, and where it was placed
    deployedBombTimers.add(System.currentTimeMillis());
    deployedBombOwners.add(bombOwner.getPlayerNumber());
    deployedBombLocations.add(new Point(bombOwner.getCol() * 32, bombOwner.getRow() * 32));
    
  } //end of deployBomb() 
  
  /**
   * checks the timers of the bombs placed
   *@param playerOne the first player of the game
   *@param playerTwo the second player of the game
   *@param gameMap   the map that both players are playing on
   *@param g         the Graphics tool that allows the tiles to be drawn on screen
   */    
  public void checkBombTimers(Player playerOne, Player playerTwo, Map gameMap, Graphics g) {
    
    //loop through the different deployed bombs
    for (int i = 0; i < deployedBombOwners.size(); i++) {
      
      //check to see if the bomb's fuse has ran out
      if (System.currentTimeMillis() - deployedBombTimers.get(i) > 1000) {
        
        //explode the bomb if it has
        this.explodeBomb(i, playerOne, playerTwo, gameMap, g);
        
      } //end of if
    } //end of for loop
  } //end of checkBombTimers() 
  
  /**
   * explodes a bomb, deleting it, and killing the enemy player if they are in radius
   *@param index     the index of the bomb being exploded
   *@param player1   the first player of the game
   *@param player2   the second player of the game
   *@param gameArena the map that both players are playing on
   *@param g         the Graphics tool that allows the tiles to be drawn on screen
   */    
  public void explodeBomb(int index, Player player1, Player player2, Map gameArena, Graphics g) {
    
    //get the references to the players locally
    Player otherPlayer;
    Player bombOwnerPlayer;
    
    //check to see which player owns the bomb and which can be the victim
    if (deployedBombOwners.get(index) == player1.getPlayerNumber()) {
      otherPlayer = player2;
      bombOwnerPlayer = player1;
    } else {
     otherPlayer = player1; 
     bombOwnerPlayer = player2;
    } //end of if structure
    
    //draw fire effects in the radius of the bomb explosion
    drawFireEffects(g, gameArena, otherPlayer, (int) (deployedBombLocations.get(index).getX()/32), 
                    (int) (deployedBombLocations.get(index).getY()/32));
    
    //check if other player was killed
    if (Math.sqrt(Math.abs(Math.pow(deployedBombLocations.get(index).getX()/32 - otherPlayer.getCol(), 2) 
                             + Math.pow(deployedBombLocations.get(index).getY()/32 - otherPlayer.getRow(), 2))) < 4) {
      
      //kill the player
      gameArena.killPlayer(otherPlayer, bombOwnerPlayer);
    }
    
    //delete the bomb information
    deployedBombTimers.remove(index);
    deployedBombOwners.remove(index);
    deployedBombLocations.remove(index);
    
  } //end of explodeBomb() 
  
  /**
   * draw all of the placed down bombs on the map
   *@param g         the Graphics tool that allows the tiles to be drawn on screen
   */ 
  public void drawPlacedBombs(Graphics g) {
    
    //loop through each placed bomb
    for (int i = 0; i < deployedBombOwners.size(); i++) {
      
      //draw the bomb
      g.drawImage(bombSprites[deployedBombOwners.get(i) - 1], this.offsetX + (int) this.deployedBombLocations.get(i).getX(), 
                  this.offsetY + (int) this.deployedBombLocations.get(i).getY(), 32, 32, null);
    
    } //end of for loop
  } //end of drawPlacedBombs() 
  
  /**
   * draws the fire tile effects around the radius of the exploded bomb
   *@param g            the Graphics tool that allows the tiles to be drawn on screen
   *@param levelArena   the map that the players are playing on
   *@param deadPlayer   the player that has died from the bomb
   *@param explosionCol the column that the explosion centre is on
   *@param explosionRow the row that the explosion centre is on
   */   
  public void drawFireEffects(Graphics g, Map levelArena, Player deadPlayer, int explosionCol, int explosionRow) {
    
    //loop through each tile on the map
    for (int i = 0; i < 21; i++) {
      for (int j = 0; j < 25; j++) {
        
        //check if the tile is not a wall and within radius of the bomb
        if (!levelArena.getIsTileWall(i, j) 
              && Math.sqrt(Math.abs(Math.pow(j - explosionCol, 2) + Math.pow(i - explosionRow, 2))) < 4) { 
          
          //draw fire effect
          g.drawImage(fireSprite, j * 32 + levelArena.getOffsetX(), i * 32 + levelArena.getOffsetY(), 32, 32, null);
          
        }//end of if
      } //end of inner for loop
    } //end of outer for loop
  } //end of drawFireEffects() 
  
} //end of Bombs class