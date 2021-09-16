/* Point.java 
 * Matthew Pechen-Berg and Peter Jang
 * January 17th, 2020
 * Class that helps organize pairs of x and y values
 */

public class Point {
 
  //Declare private variables
  private double x;
  private double y;
 
  //Constructor for the Point class
  Point(double x, double y) {
    
   //Required for constructor
   this.x = x;
   this.y = y;
   
  }//end of Point constructor 
  
  /** Gets the x value
    *@return the x value
    */
  public double getX() {
   return this.x; 
   
  }//end of getX()
  
  /** Sets the x value
    *@param newX double the new x value
    */
  public void setX(double newX) {
    this.x = newX;
    
  }//end of setX()
  
  /** Gets the y value
    *@return the y value
    */
  public double getY() {
   return this.y; 
   
  }//end of getY()
  
  /** Sets the y value
    *@param newY double the new y value
    */
  public void setY(double newY) {
    this.y = newY;
    
  }//end of setY()
  
}//end of Point class