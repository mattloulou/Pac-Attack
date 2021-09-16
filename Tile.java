/* Tile.java
 * Matthew Pechen-Berg and Peter Jang
 * January 17th, 2020
 * This class provides the tile objects that make up each wall or floor grid position in the game
 */

//image imports
import java.awt.image.BufferedImage;

//graphic imports
import java.awt.Graphics;

public class Tile {
  
  //declare private class variables
  private boolean isWall;
  private int offsetX;
  private int offsetY;
  private int col;
  private int row;
  private BufferedImage tileImage;
  
  //constructor for the Tile class
  Tile(boolean isWall, int row, int col, BufferedImage tileImage, int offsetX, int offsetY) {
    this.isWall = isWall;
    this.row = row;
    this.col = col;
    this.tileImage = tileImage;
    this.offsetX = offsetX;
    this.offsetY = offsetY;
  } //end of constructor for the Tile class
  
  /** gets the integer value of the tile column
    *@return the integer value of the tile's column
    */  
  public int getCol() {
    return this.col;
  } //end of getCol()
  
  /** sets the integer value of the tile column
    *@param newCol the integer value of the new column for the tile
    */  
  public void setCol(int newCol) {
    this.col = newCol;
  } //end of setCol()
  
  /** gets the integer value of the tile row
    *@return the integer value of the tile's row
    */  
  public int getRow() {
    return this.row;
  } //end of setCol()
  
  /** sets the integer value of the tile row
    *@param newCol the integer value of the new row for the tile
    */    
  public void setRow(int newRow) {
    this.row = newRow;
  } //end of setRow
  
  /** gets the BufferedImage image of the tile's chosen sprite
    *@return the BufferedImage image of the tile's chosen sprite
    */  
  public BufferedImage getImage() {
    return this.tileImage; 
  } //end of getImage()
  
  /** draws the tile's sprite at it's fixed location
    */    
  public void drawTile(Graphics g) {
    g.drawImage(this.tileImage, this.offsetX+this.col*32, this.offsetY+this.row*32, 32, 32, null);
  } //end of drawTile()
  
  /** gets the boolean value of if the tile is a wall or not
    *@return the boolean value of if the tile is or is not a wall
    */    
  public boolean getIsWall() {
    return this.isWall;
  } //end of getIsWall()
  
} //end of Tile class