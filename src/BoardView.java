 //Course:         ITI 1121 A
 //Author:         Junhan Liu                                   
 //Assignment:     #3                                      
 //Student number: 7228243                                


import java.awt.*;
import javax.swing.*;


public class BoardView extends JPanel {
  
  private GameModel gameModel;
  private GameController gameController;
    


    public BoardView(GameModel gameModel, GameController gameController) {
// REPLACE THE BODY OF THIS METHOD WITH YOUR OWN IMPLEMENTATION
      
      this.gameModel = gameModel;
      this.gameController = gameController;
      
      setLayout(new GridLayout(gameModel.getSize(), 1));
    }


    public void update(){
  // REPLACE THE BODY OF THIS METHOD WITH YOUR OWN IMPLEMENTATION
      
      removeAll();
      
      int n = gameModel.getSize();
      
      for (int y = 0; y<n; y++){
        
        JPanel p = new JPanel(new FlowLayout());
        
        if(y % 2 != 0){
          
          p.setBorder(new javax.swing.border.EmptyBorder(0, 20, 0, 0));
        }
        
        for (int x =0; x<n; x++){
          
          int state = gameModel.getCurrentStatus(x, y);
          
          DotButton button = new DotButton(y, x, state);
          
          button.addActionListener(gameController);
          
          p.add(button);
        }
        add(p);
      }
      
      updateUI();
   
 }

}
