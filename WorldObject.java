/* WorldObject.java
 * Matthew Pechen-Berg and Peter Jang
 * January 17th, 2020
 * the base class that almost all other objects will inherit from. It contains many of the most essential pieces of data
 */

//Graphics and GUI imports
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

//File imports
import java.io.*;

public abstract class WorldObject {
  
  //declare private class variables
  private int offsetX;
  private int offsetY;
  private int spritesheetRows;
  private int spritesheetCols;
  private int imageWidth;
  private int imageHeight;
  private double x;
  private double y;
  private String spritesheetName;
  private BufferedImage [][] sprites;
  
  //constructor for the WolrdObject class
  WorldObject(int x, int y, int spritesheetRows, int spritesheetCols, String spritesheetName) {
    this.offsetX = 240;
    this.offsetY = 98;
    this.spritesheetRows = spritesheetRows;
    this.spritesheetCols = spritesheetCols;
    this.imageWidth = 32;
    this.imageHeight = 32;
    this.x = x;
    this.y = y;
    this.spritesheetName = spritesheetName;
    this.sprites = new BufferedImage[this.spritesheetRows][this.spritesheetCols];
    this.loadSprites();
  } //end of WorldObject constructor
  
  /** loads the spritesheet for the world object
    */
  public void loadSprites() {
    try { //credits of spritesheet loading go to Mr. Mangat
      
      //create temporary spritesheet
      BufferedImage spritesheet = ImageIO.read(new File(this.spritesheetName)); 
      
      //loop through each individual sprite in the spritsheet and store the individual sprites
      for (int i = 0; i < this.spritesheetRows; i++) {
        for (int j = 0; j < this.spritesheetCols; j++) {
          this.sprites[i][j] = spritesheet.getSubimage(j*this.imageWidth,i*this.imageHeight,
                                                       this.imageWidth,this.imageHeight);
          
        } //end of inner for loops
      } //end of outer for loops
      
    } catch (Exception e) {System.out.println(spritesheetName + "spritesheet encountered an error while loading.");};
  } //end of loadSprites()
  
  /** gets the value of the x position offset
    *@return the integer value of the x offset
    */  
  public int getOffsetX() {
    return this.offsetX;
  } //end of getOffsetX()
  
  /** gets the value of the y position offset
    *@return the integer value of the y offset
    */    
  public int getOffsetY() {
    return this.offsetY;
  } //end of getOffsetY()
  
  /** gets the value of the desired sprite
    *@param row  the integer row of the sprite requested
    *@param col  the integer col of the sprite requested
    *@return     the BufferedImage sprite that was desired
    */      
  public BufferedImage getSprite(int row, int col) {
    return this.sprites[row][col];
  } //end of getSprite()
  
  /** gets the double value of the x position of the object
    *@return the double value of the x position
    */      
  public double getX() {
    return this.x; 
  } //end of getX()
  
  /** sets the double value of the x position of the object
    *@param newX the double value of what the x position will be set to
    */      
  public void setX(double newX) {
    this.x = newX;
  } //end of setX()
  
  /** gets the double value of the y position of the object
    *@return the double value of the y position
    */      
  public double getY() {
    return this.y; 
  } //end of getY()
  
  /** sets the double value of the y position of the object
    *@param newY the double value of what the y position will be set to
    */        
  public void setY(double newY) {
    this.y = newY;
  }  //end of setY()
  
} //end of WorldObject class