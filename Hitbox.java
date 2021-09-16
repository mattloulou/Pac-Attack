/* Hitbox.java 
 * Matthew Pechen-Berg and Peter Jang
 * January 17th, 2020
 * Class that handles the collisions between objects
 */

public abstract class Hitbox {
  
  /** Checks for collision (overlap) between two objects
    *@param topLeft1      Point the topLeft coordinates for object 1
    *@param bottomRight1  Point the bottomRight coordinates for object 1
    *@param topLeft2      Point the topleft coordinates for object 2
    *@param bottomRight2  Point the bottomRight coordinates for object 2
    *return               false if no overlap, true if there is
    */
  public static boolean checkOverlap(Point topLeft1, Point bottomRight1, Point topLeft2, Point bottomRight2) {
    
    //if there is no overlap
    if (topLeft1.getX() > bottomRight2.getX() || bottomRight1.getX() < topLeft2.getX() 
          || topLeft1.getY() > bottomRight2.getY() || bottomRight1.getY() < topLeft2.getY()) {
      
      return false; 
      
    }//end if structure for determining if there is overlap
    
    return true; 
    
  }//end of checkOverlap()
  
}//end of Hitbox class