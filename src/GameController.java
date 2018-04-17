 //Course:         ITI 1121 A
 //Author:         Junhan Liu                                   
 //Assignment:     #3                                      
 //Student number: 7228243                                


import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.ActionListener;
import java.awt.event.*;

public class GameController implements ActionListener{
  
  private GameModel gameModel;
  private GameView gameView;
  private boolean flag = true;
  private boolean flag2 = true;


    public GameController(int size) {
// REPLACE THE BODY OF THIS METHOD WITH YOUR OWN IMPLEMENTATION
      gameModel = new GameModel(size);
      gameView = new GameView(gameModel, this);
      
      start();
    }

 
    public void start(){
// REPLACE THE BODY OF THIS METHOD WITH YOUR OWN IMPLEMENTATION
    }

 
    public void reset(){
// REPLACE THE BODY OF THIS METHOD WITH YOUR OWN IMPLEMENTATION
      gameModel.reset();
      gameView.getBoardView().update();
      gameView.disableUndo();
      gameView.disableRedo();
    }
    
    public void undo(){
      //undo the previous step the user made
      //call GameModel.lastStep() to pop the previous int[][] state
      //and call BoardView.update() to update the dots
      
      gameModel.lastStep();
      gameView.getBoardView().update();
      
      if(gameModel.getRedoStackSize() != 0){ gameView.enableRedo();}
      if( ! gameModel.undoButton() ){gameView.disableUndo();}

    }
    
    public void redo(){
      //redo the undo step
      gameModel.undoTheUndo();
      gameView.getBoardView().update();
      
      //when user redo, he cannot undo the redo
      //when redoStack is empty, disable redo and undo button
      
      if(gameModel.getRedoStackSize() != 0){ gameView.enableRedo();}
      else if(gameModel.getRedoStackSize() == 0){
        gameView.disableRedo();gameView.disableUndo();}
    }
    
    public void actionPerformed(ActionEvent e){
      
      DotButton button = (DotButton)e.getSource();
      
      int row = button.getRow();
      int col = button.getColumn();
      int steps = gameModel.getNumberOfSteps();
      
      if( isCapture() ){
        
        gameView.getBoardView().update();
        result(true);
      }
      else{
      
        if(gameModel.getCurrentStatus(col, row) == 0){
          gameModel.select(col, row);
          
          int r = moveBlueDot();
          
          if(gameModel.getUndoStackSize() != 0){gameView.enableUndo();}
          
          gameView.getBoardView().update();
          
          if( r == -1){
            result(false);
          }
          else if( r == 1 ){
            result(true);
          }
        }
      }
    }
    
    
    
    
    
    private void result(boolean won) {
     Object[] options = { "Quit", "Play Again" };
     int response = 0;
     if(won) {
      response = JOptionPane.showOptionDialog(null, 
        "Congratulation, you won in " + gameModel.getNumberOfSteps() 
        + " steps!\r\nWould you like to play again?", 
        "Won", JOptionPane.YES_NO_OPTION, 
        JOptionPane.QUESTION_MESSAGE, 
        null, options, options[0]);

     }
     else {
      response = JOptionPane.showOptionDialog(null, 
        "You lost!\r\nWould you like to play again?", 
        "Lost", JOptionPane.YES_NO_OPTION, 
        JOptionPane.QUESTION_MESSAGE, 
        null, options, options[0]);
     }
     if(response == 0) {
      gameView.setVisible(false);
      gameView.dispose();
      System.exit(0);
     }
     else {
      reset();
     }        
    }
    
    
    
    private boolean isCapture() {
     Point blueDot = gameModel.getCurrentDot();
     // neighbors of blue dot
     ArrayList<Point> pts = getNeighbors(blueDot.getX(), blueDot.getY());
     if(pts.size() < 6) return false;

  // temp arraylist
  ArrayList<Point> tmp = new ArrayList<Point>();
  tmp.add(new Point(blueDot.getX(), blueDot.getY()));
  
     // available neighbors of blue dot
     ArrayDeque<Point> q = new ArrayDeque<Point>();
  for(int i = 0; i < pts.size(); i++) {
   Point pt = pts.get(i);
   if(gameModel.getCurrentStatus(pt.getX(), pt.getY()) == GameModel.AVAILABLE) {
    q.push(pt);
    tmp.add(pt);
   }      
  }
  
  // loop find,
  while(q.size() > 0) {
   Point p = q.pop();
      // neighbors of available button,
      ArrayList<Point> pts1 = getNeighbors(p.getX(), p.getY());
      if(pts1.size() < 6) {
       return false;
      }

      // add available button to queue,
      for(int i = 0; i < pts1.size(); i++) {
    Point pt = pts1.get(i);
    if(gameModel.getCurrentStatus(pt.getX(), pt.getY()) == GameModel.AVAILABLE) {
     if(containInList(tmp, pt) == false) {
      q.push(pt);
      tmp.add(pt);
     }
    }
      }
  }
     
     return true;
    }
    
    private boolean containInList(ArrayList<Point> lst, Point pt) {
     for(int i = 0; i < lst.size(); i++) {
      Point p = lst.get(i);
      if((p.getX() == pt.getX()) && (p.getY() == pt.getY())) {
       return true;
      }
     }
     return false;
    }


    
    
    
    
    private int moveBlueDot() {
      
     Point blueDot = gameModel.getCurrentDot();
     // neighbors of blue dot
     ArrayList<Point> pts = getNeighbors(blueDot.getX(), blueDot.getY());
     
     // available neighbors of blue dot
     ArrayList<Point> pts1 = new ArrayList<Point>();
     for(int i = 0; i < pts.size(); i++) {
       Point pt = pts.get(i);
       if(gameModel.getCurrentStatus(pt.getX(), pt.getY()) == GameModel.AVAILABLE) {
         pts1.add(pt);
       }      
     }
  
     Random rand = new Random(new Date().getTime());
     if(pts.size() < 6) {
       if(pts1.size() == 0) return -1;
   
       int n = pts.size() - pts1.size();
       int p = rand.nextInt(6) + 1;
       if(p <= n) {
         gameModel.setCurrentDot(-1, 0);
         return -1;
       }
     }

     if(pts1.size() > 0) {      
      int p = rand.nextInt(pts1.size());
      Point pt = pts1.get(p);
      gameModel.setCurrentDot(pt.getX(), pt.getY());
      return 0;
     }
     
     return 1;
    }
    
    // get neighbors of point
    private ArrayList<Point> getNeighbors(int x, int y) {
     int size = gameModel.getSize();
     ArrayList<Point> pts = new ArrayList<Point>();     
     if((x < 0) || (y < 0) || (x >= size) || (y >= size)) return pts;
     
     int r = y - 1;
     if(r >= 0) {
      // even row
      if((r % 2) == 0) {
       int c1 = x, c2 = x + 1;
       pts.add(new Point(c1, r));
       if(c2 < size) pts.add(new Point(c2, r));
      }
      // odd row
      else {
       int c1 = x - 1, c2 = x;
       if(c1 >= 0) pts.add(new Point(c1, r));
       pts.add(new Point(c2, r));
      }
     }
     
     r = y;
     if((x - 1) >= 0) pts.add(new Point(x - 1, r));
     if((x + 1) < size) pts.add(new Point(x + 1, r));
     
     r = y + 1;
     if(r < size) {
      // even row
      if((r % 2) == 0) {
       int c1 = x, c2 = x + 1;
       pts.add(new Point(c1, r));
       if(c2 < size) pts.add(new Point(c2, r));
      }
      // odd row
      else {
       int c1 = x - 1, c2 = x;
       if(c1 >= 0) pts.add(new Point(c1, r));
       pts.add(new Point(c2, r));
      }
     }
 
     return pts;
    }   
}

