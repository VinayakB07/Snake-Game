import javax.swing.*;

public class SnakeGame extends JFrame{

    private  GameBoard board;

    //constructor
    public SnakeGame(){

      board=new GameBoard();
      add(board);
      pack();

      setResizable(false);
      setVisible(true);
      setLocationRelativeTo(null);


    }
    public static void main(String[] args){

        //creating object of SnakeGame class
        SnakeGame snakeGame=new SnakeGame();
    }
}
