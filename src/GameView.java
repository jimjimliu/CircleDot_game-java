 //Course:         ITI 1121 A
 //Author:         Junhan Liu                                   
 //Assignment:     #3                                      
 //Student number: 7228243                                


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class GameView extends JFrame {
  
  private GameModel gameModel;
  private GameController gameController;
  private BoardView boardView;
  private JButton reset;
  private JButton quit;
  private JButton undo;
  private JButton redo;
  private JButton replay;
  private JPanel control;
  


    public GameView(GameModel model, GameController gameController) {
 // REPLACE THE BODY OF THIS METHOD WITH YOUR OWN IMPLEMENTATION
      
      this.gameModel = model;
      this.gameController = gameController;
      
      setTitle("Dot!");
      setDefaultCloseOperation(EXIT_ON_CLOSE);
      setSize(550, 600);
      //setLocation(400, 200);
      setLayout(new BorderLayout());
      
      boardView = new BoardView(gameModel, gameController);
      add(boardView, BorderLayout.CENTER);
      
      reset = new JButton("reset");
      reset.setSize(60,24);
      quit = new JButton("quit");
      quit.setSize(60,24);
      undo = new JButton("undo");
      undo.setSize(60,24);
      undo.setEnabled(false);
      redo = new JButton("redo");
      redo.setSize(60,24);
      redo.setEnabled(false);
      replay = new JButton("re-play");
      replay.setSize(60, 24);
      
      control = new JPanel();
      control.setLayout(new FlowLayout());
      control.add(reset);
      control.add(quit);
      control.add(undo);
      control.add(redo);
      control.add(replay);
      
      add(control, BorderLayout.SOUTH);
      
      reset.addActionListener(new ActionListener() {
        
        public void actionPerformed(ActionEvent e){
          
          GameView.this.gameController.reset();
        }
      });
      
      quit.addActionListener(new ActionListener() {
        
        public void actionPerformed(ActionEvent e){
          
          System.exit(0);
        }
      });
      
      undo.addActionListener(new ActionListener() {
        
        public void actionPerformed(ActionEvent e){
          
          GameView.this.gameController.undo();
        }
      });
      
      
      redo.addActionListener(new ActionListener(){
        
        public void actionPerformed(ActionEvent e){
          
          GameView.this.gameController.redo();
        }
      });
      
      replay.addActionListener(new ActionListener() {
        
        public void actionPerformed(ActionEvent e){
          
          gameModel.firstStep();
          boardView.update();
        }});
          
      
      setVisible(true);
      boardView.update();
      pack();
 

    }
    
    public void disableUndo(){
        //disable the Button
        undo.setEnabled(false);
        repaint();
      }
      
    public void enableUndo(){
        //enable the button
        undo.setEnabled(true);
        repaint();
      }
    
    public void disableRedo(){
        //disable the Button
        redo.setEnabled(false);
        repaint();
      }
      
    public void enableRedo(){
        //enable the button
        redo.setEnabled(true);
        repaint();
      }



    public BoardView getBoardView(){
 // REPLACE THE BODY OF THIS METHOD WITH YOUR OWN IMPLEMENTATION
      
      return boardView;
   }

}
