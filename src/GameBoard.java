import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GameBoard extends JPanel implements ActionListener{

    // properties of gameBoard and variables required for gameBoard
    int height=400;
    int width=400;
    int x[]=new int[height*width];
    int y[]=new int[height*width];
    int dots=3;
    int appleX=100;
    int appleY=100;
    int dotSize=10;
    Image apple;
    Image body;
    Image head;
    boolean leftDirection=false;
    boolean rightDirection=false;
    boolean upDirection=false;
    boolean downDirection=true;
    Timer timer;
    int delay=200;
    boolean play=true;
    int score=0;

    //constructor
    public GameBoard(){
        addKeyListener(new TAdapter());
        setFocusable(true);
        setPreferredSize(new Dimension(width,height));
        setBackground(Color.BLACK);
        loadImage();
        initGame();
    }
    //for starting game
    public void initGame(){
        dots=3;
        for(int i=0;i<dots;i++){
            x[i]=50+i*dotSize;
            y[i]=50;
        }
        timer=new Timer(delay,this);
        timer.start();
    }

    //for loading images used in game
    private  void loadImage(){

        ImageIcon image_apple=new ImageIcon("src/resources/apple.png");
        apple=image_apple.getImage();

        ImageIcon image_body=new ImageIcon("src/resources/dot.png");
        body=image_body.getImage();

        ImageIcon image_head=new ImageIcon("src/resources/head.png");
        head=image_head.getImage();

    }

    //repaint
    @Override
    public void paintComponent(Graphics graphics){

       super.paintComponent(graphics);
       if(play){
           graphics.drawString("SCORE:"+score,300,20);
           graphics.drawImage(apple,appleX,appleY,this);
           for(int i=0;i<dots;i++){
               if(i==0){
                   graphics.drawImage(head,x[0],y[0],this);
               }
               else{
                   graphics.drawImage(body,x[i],y[i],this);
               }
           }
           Toolkit.getDefaultToolkit().sync();
       }
       else{
           gameOver(graphics);
       }
    }

    //movement of objects in game
    private void move(){
        for(int i=dots-1;i>0;i--){
            x[i]=x[i-1];
            y[i]=y[i-1];
        }
        if(leftDirection){
            x[0]-=dotSize;
        }
        if(rightDirection){
            x[0]+=dotSize;
        }
        if(upDirection){
            y[0]-=dotSize;
        }
        if(downDirection){
            y[0]+=dotSize;
        }
    }

    //locating apple
    private void locateApple(){
        int r= (int) (Math.random()*39);
        appleX=r*dotSize;

        r= (int) (Math.random()*39);
        appleY=r*dotSize;

    }

    //if snake eats apple
    private void checkApple(){
        if(x[0]==appleX&&y[0]==appleY){
            dots++;
            score+=5;
            locateApple();
        }
    }
    //if collision happens
    private void checkCollision(){
        if(x[0]<0||x[0]>=width||y[0]<0||y[0]>=height){
            play=false;
        }
        for(int i=dots-1;i>=3;i--){
            if(x[0]==x[i]&&y[0]==y[i]){
                play=false;
                break;
            }
        }
    }

    //when game completes
    private void gameOver(Graphics graphics){
        String msg="GAME OVER!!   "+"  Your Score:"+score;
        Font small=new Font("Helvetica",Font.BOLD,14);
        FontMetrics metrics=getFontMetrics(small);
        graphics.setColor(Color.WHITE);
        graphics.setFont(small);
        graphics.drawString(msg,(width-metrics.stringWidth(msg))/2,height/2);

    }

    @Override
    public void actionPerformed(ActionEvent actionEvent){
        if(play) {
            checkCollision();
            checkApple();
            move();
        }
        repaint();
    }

    //keys for movement of snake
    public class TAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent keyEvent){
            int key=keyEvent.getKeyCode();
            if(key==KeyEvent.VK_LEFT&&(!rightDirection)){
                leftDirection=true;
                upDirection=false;
                downDirection=false;
            }
            if(key==KeyEvent.VK_RIGHT&&(!leftDirection)){
                rightDirection=true;
                upDirection=false;
                downDirection=false;
            }
            if(key==KeyEvent.VK_UP&&(!downDirection)){
                leftDirection=false;
                upDirection=true;
                rightDirection=false;
            }
            if(key==KeyEvent.VK_DOWN&&(!upDirection)){
                leftDirection=false;
                rightDirection=false;
                downDirection=true;
            }
        }
    }
}
