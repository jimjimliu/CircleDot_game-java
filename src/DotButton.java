 //Course:         ITI 1121 A
 //Author:         Junhan Liu                                   
 //Assignment:     #3                                      
 //Student number: 7228243                                


import javax.swing.*;
import java.awt.*;


public class DotButton extends JButton {
  
  private int row;
  private int column;
  private int type;

  
    public DotButton(int row, int column, int type) {
// REPLACE THE BODY OF THIS METHOD WITH YOUR OWN IMPLEMENTATION
      this.row = row;
      this.column = column;
      this.type = type;
      
      String filename = "";
      
      if(type == 0){
        
        filename = "data/ball-0.png";
      }
      else if(type == 1){
        
        filename = "data/ball-1.png";
      }
      else if(type == 2){
        
        filename = "data/ball-2.png";
      }
      
      ImageIcon icon = new ImageIcon(filename);
      setIcon(icon);
      setContentAreaFilled(false);
      setBorder(null);
    }




    public void setType(int type) {
// REPLACE THE BODY OF THIS METHOD WITH YOUR OWN IMPLEMENTATION
      this.type = type;
    }

 
    public int getRow() {
// REPLACE THE BODY OF THIS METHOD WITH YOUR OWN IMPLEMENTATION
      
      return this.row;
    }

  

    public int getColumn() {
// REPLACE THE BODY OF THIS METHOD WITH YOUR OWN IMPLEMENTATION
      return this.column;
    }
}
