 //Course:         ITI 1121 A
 //Author:         Junhan Liu                                   
 //Assignment:     #3                                      
 //Student number: 7228243                                


import java.util.Random;
import java.util.ArrayList;
import java.util.Stack;
import javax.swing.JOptionPane;
import java.util.*;


public class GameModel implements Cloneable{


  public static final int AVAILABLE  = 0;
  public static final int SELECTED  = 1;
  public static final int DOT   = 2;
  
  private Point blue;
  private Point initialBlue;
  private int[][] state;
  private int size;
  private int step;
  private Random rand = new Random();
  
  private LinkedStack<int[][]> cloneStack = new LinkedStack<int[][]>();
  private LinkedStack<int[][]> redoStack = new LinkedStack<int[][]>();
  private LinkedStack<Point> blueStack = new LinkedStack<Point>();
  private LinkedStack<Point> redoBlueStack = new LinkedStack<Point>();
  private static int nameCounter = 0;
  private int[][][] cloneObject;
  
  private int[][] initialModel;
  
  private static int timer = 0;
  private boolean flag = true;
  

    public GameModel(int size) {
// REPLACE THE BODY OF THIS METHOD WITH YOUR OWN IMPLEMENTATION
      this.size = size;
      step = 0;
      state = new int[size][size];
      
      reset();
      
    }


    public void reset(){

 // REPLACE THE BODY OF THIS METHOD WITH YOUR OWN IMPLEMENTATION
      for (int i=0; i<size;i++){
        
        for (int j=0; j<size; j++){
          
          state[i][j] = AVAILABLE;
        }
      }
      
      //set blue point
      
      int x = 0, y = 0;     
     if((size % 2) == 0) {
      x = ((size - 2) / 2) + rand.nextInt(2);
      y = ((size - 2) / 2) + rand.nextInt(2);
     }
     else {
      x = ((size - 3) / 2) + rand.nextInt(3);
      y = ((size - 3) / 2) + rand.nextInt(3);
     }
     blue = new Point(x, y);
     initialBlue = blue;////////////
     state[blue.getY()][blue.getX()] = DOT;
     
     //set orange points
     
     int num = size*size / 10;
     
     ArrayList<Point> point = new ArrayList<Point>();
     
     for (int i =0; i<size; i++){
       
       for (int j=0; j<size; j++){
         
         if( (i != blue.getX()) && (j != blue.getY())){
           
           point.add(new Point(i, j));
         }
       }
     }
     
     for (int i = 0; i<num; i++){
       
       int p = rand.nextInt(point.size());
       
       Point pt = point.get(p);
       
       state[pt.getY()][pt.getX()] = SELECTED;
       
       point.remove(p);
     }
     
     initialModel = new int[size][size];
     ////////
     for (int i =0; i< size; i++){
        for (int j =0; j<size; j++){
          
          initialModel[i][j] = state[i][j];
        }
      }
     ////////
       cloneStack = new LinkedStack<int[][]>();
       redoStack = new LinkedStack<int[][]>();     
       cloneObject = new int[1000][size][size];
       nameCounter = 0;
   
       
       try{
         clone();//初始的时候需要讲最开始的位置复制下来
         }
       catch(CloneNotSupportedException e){
         e.printStackTrace();
       }
       
       step = 0;
       
    }
    
    
    public Object clone() throws CloneNotSupportedException{
      
      //System.arraycopy(state, 0, newState, 0, size);
      //create a deep copy, 
      //System.arraycopy still pointing to the same address
      //arraycopy 还是会将两个 list 指向同一个地址，处理这个问题
      //using for loop to copy each location
      
      for (int i =0; i< size; i++){
        for (int j =0; j<size; j++){  
          cloneObject[nameCounter] [i][j] = state[i][j];
        }
      }
      
      cloneStack.push( cloneObject[nameCounter] );
      
      blueStack.push( blue );
      
      nameCounter++;
         
      return cloneObject[nameCounter];    
    }
    
    public void lastStep(){
      //get one the last step gameModel;
      ////////
      if( cloneStack.isEmpty() ){    
        firstStep();
        flag = false;
      }
      ////////
      else{
        flag = true;
        int[][] toBeClone = null;
        
        try{
          toBeClone = cloneStack.pop();}
        catch(EmptyStackException e){
          JOptionPane.showMessageDialog(null, "you cannot undo anymore");
          System.out.println("The stack is empty");
        }
        
        for (int i =0; i< size; i++){
          for (int j =0; j<size; j++){  
            state[i][j] = toBeClone[i][j];
          }
      }
      //reset the blue dot accordingly
      blue = blueStack.pop();/////
      //keep track of the blue dot using another stack
      redoBlueStack.push(blue);
      
      redoStack.push( toBeClone );
      }
    }
    
    public void firstStep(){
      //get the very begining gameModel
      //when replay, goes back to the evry beginning of the game
      
      for (int i =0; i< size; i++){
        for (int j =0; j<size; j++){
          
          state[i][j] = initialModel[i][j];
        }
      }  
      blue = initialBlue;
      cloneStack = new LinkedStack<int[][]>();
    }
    
    public void undoTheUndo(){
      //redo last step, create another stack
      //push the each undo state[][] into redo stack
      //then pop the redo stack to undo the undo
      //after redo, user cannot undo the redo anymore
      //clear the cloneStack
      //when redoStack is empty, disable the undo/redo button
      
      //想办法在这里把blue point保留住，处理一下
      
      int[][] temp = null;
      
      try{
        temp = redoStack.pop();
      }
      catch(EmptyStackException e){
        JOptionPane.showMessageDialog(null, "you cannot redo anymore");
        System.err.println("The redoStack is empty");
      }
      
      for (int i =0; i< size; i++){
        for (int j =0; j<size; j++){
          
          state[i][j] = temp[i][j];
        }
      } 
      
      Point bluePoint = redoBlueStack.pop();
      
      blue = bluePoint;
      
      cloneStack = new LinkedStack<int[][]>();
    }

   
    public int getSize(){
 // REPLACE THE BODY OF THIS METHOD WITH YOUR OWN IMPLEMENTATION
      return this.size;
   }
 
    public int getCurrentStatus(int i, int j){
// REPLACE THE BODY OF THIS METHOD WITH YOUR OWN IMPLEMENTATION
      return state[j][i];
    }

  
    public void select(int i, int j){
// REPLACE THE BODY OF THIS METHOD WITH YOUR OWN IMPLEMENTATION
      if(state[j][i] != AVAILABLE) return;
     if((blue.getX() == i) && (blue.getY() == j)) return;
     
     state[j][i] = SELECTED;
     step++;
    }

  
    public void setCurrentDot(int i, int j){
// REPLACE THE BODY OF THIS METHOD WITH YOUR OWN IMPLEMENTATION
       if(i == -1) {
      state[blue.getY()][blue.getX()] = AVAILABLE;
      blue = new Point(i, j);
      return;
     }
     if(state[j][i] != AVAILABLE) return;
     if((blue.getX() == i) && (blue.getY() == j)) return;
     
     state[blue.getY()][blue.getX()] = AVAILABLE;

     blue = new Point(i, j);
     state[blue.getY()][blue.getX()] = DOT;
     
     //after the method called
     //
     try{
       clone();//这个方法是每点一次，就将本次的位置复制一遍，push到stack中去
     }catch(CloneNotSupportedException e){
       e.printStackTrace();
     }
     //
   }
  
    public Point getCurrentDot(){
// REPLACE THE BODY OF THIS METHOD WITH YOUR OWN IMPLEMENTATION
      return blue;
    }
  
    public int getNumberOfSteps(){
// REPLACE THE BODY OF THIS METHOD WITH YOUR OWN IMPLEMENTATION
      return this.step;
    }
    
    public int getRedoStackSize(){
      
      return redoStack.size();
    }
    
    public int getUndoStackSize(){
      return cloneStack.size();
    }
    
    public boolean undoButton(){
      
      return flag;
    }

}
