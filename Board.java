package Game.src;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.JPanel;
import javax.swing.Timer;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class Board extends JPanel implements ActionListener {

// TODO: Implement a way for the player to win

// Holds height and width of the window
private final static int BOARDWIDTH = 800;
private final static int BOARDHEIGHT = 800;

// Used to represent pixel size of food & our snake's joints
private final static int PIXELSIZE = 10;

// The total amount of pixels the game could possibly have.
// We don't want less, because the game would end prematurely.
// We don't more because there would be no way to let the player win.

private final static int TOTALPIXELS = (BOARDWIDTH * BOARDHEIGHT)
        / (PIXELSIZE * PIXELSIZE);



// Check to see if the game is running
private static int inGame = 1;
private int oldTime;
private int nowTime;

private boolean fstPlayerWin = false;
private boolean sndPlayerWin = false;
private boolean newRanker = false;
public static boolean check = false;

private boolean fever = true;

// Timer used to record tick times
private Timer timer;

// Used to set game speed, the lower the #, the faster the snake travels
// which in turn
// makes the game harder.
private static int speed = 5;

// Instances of our snake & food so we can use their methods
private Snake snake = new Snake();
private Snake snake2 = new Snake();
private Food food = new Food();
private ArrayList<Poison> poison = new ArrayList<Poison>();

private Collisions collision = new Collisions(snake, snake2, food, poison);
private FeverTime feverTime = new FeverTime(food,  BOARDWIDTH, BOARDHEIGHT, PIXELSIZE, snake, snake2);

// Rank
Rank rk = new Rank();

public Board() {

    addKeyListener(new Keys());
    setBackground(Color.black);
    setFocusable(true);
    timer = new Timer(speed, this);
    setPreferredSize(new Dimension(BOARDWIDTH, BOARDHEIGHT));

    initializeGame();
}

// Used to paint our components to the screen
@Override
protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    if (!check) {
        startGame(g, BOARDWIDTH, BOARDHEIGHT);
    }
    if (check) {
        draw(g);
    }
   
}

// Draw our Snake & Food (Called on repaint()).
void draw(Graphics g) {

    // Only draw if the game is running / the snake is alive
    if (inGame == 1) {
    	// Draw scores
    	printScore(g);
    	
    	// Set object sizes
        g.setColor(Color.RED);
        g.fillRect(food.getFoodX(), food.getFoodY(), PIXELSIZE, PIXELSIZE); // food
        g.setColor(Color.blue);
        for (int i = 0; i < poison.size(); i++) {
            g.fillRect(poison.get(i).getPoisonX(), poison.get(i).getPoisonY(), PIXELSIZE, PIXELSIZE); // poison
        }
        // Draw our snake.
        for (int i = 0; i < snake.getJoints(); i++) {
            // Snake's head
            if (i == 0) {
                g.setColor(Color.yellow);
                g.fillRect(snake.getSnakeX(i), snake.getSnakeY(i), PIXELSIZE, PIXELSIZE);
                // Body of snake
            } else {
                g.fillRect(snake.getSnakeX(i), snake.getSnakeY(i), PIXELSIZE, PIXELSIZE);
            }
        }
        for (int i = 0; i < snake2.getJoints(); i++) {
            // Snake's head
            if (i == 0) {
                g.setColor(Color.green);

                // Body of snake
                g.fillRect(snake2.getSnakeX(i), snake2.getSnakeY(i), PIXELSIZE, PIXELSIZE);
            } else {

                g.fillRect(snake2.getSnakeX(i), snake2.getSnakeY(i), PIXELSIZE, PIXELSIZE);
            }
        }

        // Sync our graphics together
        Toolkit.getDefaultToolkit().sync();
    }
    else if(inGame == 2) {
    	
    	if(fever) {
    		feverTime.EndFever(g, collision.getFstPlayerWin(), collision.getSndPlayerWin());
    		fever = false;
    	}
    	collision.checkManyFoodCollisions(BOARDWIDTH/PIXELSIZE, BOARDHEIGHT/PIXELSIZE,PIXELSIZE); 	

        for(int i = 0; i < BOARDHEIGHT/PIXELSIZE; i++) {
            for(int j = 0; j < BOARDWIDTH/PIXELSIZE; j++) {
            	if(food.foodPosition[i][j] == 1) {
            		g.setColor(new Color(255, (255 - 3*j), 0));
//            		g.setColor(Color.RED);
            		g.fillRect((i)*PIXELSIZE, (j)*PIXELSIZE, PIXELSIZE, PIXELSIZE); // food
            	}else {
            		g.setColor(Color.BLACK);
            		g.fillRect((i)*PIXELSIZE, (j)*PIXELSIZE, PIXELSIZE, PIXELSIZE); // food

            	}
            }
        }
 
        // Draw scores
        printScore(g);
        
        // Draw our snake.
        for (int i = 0; i < snake.getJoints(); i++) {
            // Snake's head
            if (i == 0) {
                g.setColor(Color.yellow);
                g.fillRect(snake.getSnakeX(i), snake.getSnakeY(i),
                        PIXELSIZE, PIXELSIZE);
                // Body of snake
            } else {
                g.fillRect(snake.getSnakeX(i), snake.getSnakeY(i),
                        PIXELSIZE, PIXELSIZE);
            }
        }
        for (int i = 0; i < snake2.getJoints(); i++) {
            // Snake's head
            if (i == 0) {
                g.setColor(Color.green);

                // Body of snake
                g.fillRect(snake2.getSnakeX(i), snake2.getSnakeY(i),
                        PIXELSIZE, PIXELSIZE);
            } else {

                g.fillRect(snake2.getSnakeX(i), snake2.getSnakeY(i),
                        PIXELSIZE, PIXELSIZE);
            }
        }

        // Sync our graphics together
        Toolkit.getDefaultToolkit().sync();


    }
    else if(inGame == 0) {
    	if (collision.getFstPlayerWin() == true && collision.getSndPlayerWin() == false) {
          	 if (rk.getScores().get(0).getScore() < snake.getScore())
          		newRanker = true;
          	}
          	
          	if (collision.getFstPlayerWin() == false && collision.getSndPlayerWin() == true) {
             	 if (rk.getScores().get(0).getScore() < snake2.getScore())
             		newRanker = true;
             	}
          	
          	feverTime.endGame(g, BOARDWIDTH, BOARDHEIGHT, collision.getFstPlayerWin(), collision.getSndPlayerWin(), newRanker);
          	inGame = 3;	
    		 
        //System.out.println("꺄라ㅏ라라라라락 ");
    }
    
    else if (inGame == 3) {    	
     	  	    	 
    	printRanking(g);
    	initializeGame();
    }
    
}

void initializeGame() {
    snake.setJoints(10); // set our snake's initial size
    snake2.setJoints(10);
    
    collision.setFstPlayerWin(false);
    collision.setSndPlayerWin(false);
    
    // Create our snake's body
    for (int i = 0; i < snake.getJoints(); i++) {
        snake.setSnakeX(BOARDWIDTH / 4);
        snake.setSnakeY(BOARDHEIGHT / 4);
        snake2.setSnakeX(BOARDWIDTH / 4 * 3);
        snake2.setSnakeY(BOARDHEIGHT / 4 * 3);
    }
    
    snake.initialSnake();
    snake2.initialSnake();
   
    // Start off our snake moving right
    snake.setMovingRight(true);
    snake2.setMovingLeft(true);

    // Generate our first 'food'
    food.createFood();
    
//    ArrayList<Poison> _poison = poison;
//    poison = new ArrayList<Poison>();
    poison.clear();
    
    for (int i = 0; i < 10; i++) {
        System.out.println("사과 생성중 " + poison.size());
        poison.add(new Poison());
        poison.get(i).createPoison();
    }
    newRanker = false;

    // set the timer to record our game's speed / make the game move  
    timer.start();
}

// Run constantly as long as we're in game.
@Override
public void actionPerformed(ActionEvent e) {

    if (inGame == 1) {

        collision.checkFoodCollisions();	
        collision.checkPoisonCollisions();	
        
        inGame = collision.checkCollisions(BOARDWIDTH, BOARDHEIGHT);
        
        int checkIndexPosion = collision.checkPoisonCollisions();
        if(checkIndexPosion == -2)
        	inGame = 2;
        else if(checkIndexPosion != -1)
            relocation(poison, checkIndexPosion);

        collision.checkWallCollisions(BOARDWIDTH, BOARDHEIGHT); 

        snake.move();
        snake2.move();

        //System.out.println("뱀1 :" +snake.getSnakeX(0) + " " + snake.getSnakeY(0) + ", 뱀2 :" + snake2.getSnakeX(0)
        //        + " " + snake2.getSnakeY(0) + ", 사과 :" + food.getFoodX() + ", " + food.getFoodY()
        //        + ", 독사과 :" + poison.getPoisonX() +", " + poison.getPoisonY());
        oldTime = (int) System.currentTimeMillis() / 1000;
    }
    else if (inGame == 2) {
    	//inGame = 여기 뭔가 들어가
        collision.checkWallCollisions(BOARDWIDTH, BOARDHEIGHT); 

    	if(collision.getFstPlayerWin() == true && collision.getSndPlayerWin() == false)
    		snake.move();
    	else if(collision.getFstPlayerWin() == false && collision.getSndPlayerWin() == true)
    		snake2.move();
    	collision.checkManyFoodCollisions(BOARDWIDTH/PIXELSIZE, BOARDHEIGHT/PIXELSIZE,PIXELSIZE);

        // System.out.println("뱀1 :" +snake.getSnakeX(0) + " " + snake.getSnakeY(0) + ", 뱀2 :" + snake2.getSnakeX(0)
        // + " " + snake2.getSnakeY(0) + ", 사과 :" + food.getFoodX() + ", " + food.getFoodY()
        // + ", 독사과 :" + poison.getPoisonX() +", " + poison.getPoisonY());

        nowTime = (int) System.currentTimeMillis() / 1000;
        int cc = nowTime - oldTime;
        System.out.println("Timer : " + cc );

        if(nowTime - oldTime == 8)
            inGame = 0;


        if (inGame == 0 || inGame == 3) {
            timer.stop();
        }
        
    }
    
    // Repaint or 'render' our screen
    repaint();
}
private class Keys extends KeyAdapter {

    @Override
    public void keyPressed(KeyEvent e) {

        int startEvent = e.getKeyCode();
        int key = e.getKeyCode();
        int key2 = e.getKeyCode();

        if (startEvent == KeyEvent.VK_ENTER) {
            Board.check = true;
        }

        if ((key == KeyEvent.VK_LEFT) && (!snake.isMovingRight())) {
            snake.setMovingLeft(true);
            snake.setMovingUp(false);
            snake.setMovingDown(false);
        }

        if ((key == KeyEvent.VK_RIGHT) && (!snake.isMovingLeft())) {
            snake.setMovingRight(true);
            snake.setMovingUp(false);
            snake.setMovingDown(false);
        }

        if ((key == KeyEvent.VK_UP) && (!snake.isMovingDown())) {
            snake.setMovingUp(true);
            snake.setMovingRight(false);
            snake.setMovingLeft(false);
        }

        if ((key == KeyEvent.VK_DOWN) && (!snake.isMovingUp())) {
            snake.setMovingDown(true);
            snake.setMovingRight(false);
            snake.setMovingLeft(false);
        }

        if ((key2 == KeyEvent.VK_A) && (!snake2.isMovingRight())) {
            snake2.setMovingLeft(true);
            snake2.setMovingUp(false);
            snake2.setMovingDown(false);
        }

        if ((key2 == KeyEvent.VK_D) && (!snake2.isMovingLeft())) {
            snake2.setMovingRight(true);
            snake2.setMovingUp(false);
            snake2.setMovingDown(false);
        }

        if ((key2 == KeyEvent.VK_W) && (!snake2.isMovingDown())) {
            snake2.setMovingUp(true);
            snake2.setMovingRight(false);
            snake2.setMovingLeft(false);
        }

        if ((key2 == KeyEvent.VK_S) && (!snake2.isMovingUp())) {
            snake2.setMovingDown(true);
            snake2.setMovingRight(false);
            snake2.setMovingLeft(false);
        }

        if ((key == KeyEvent.VK_ENTER) && ((inGame == 0) || (inGame ==3))) {

            inGame = 1;
            initializeGame();

            // stop snakes' movement
            snake.setMovingDown(false);
            snake.setMovingRight(false);
            snake.setMovingLeft(false);
            snake.setMovingUp(false);

            snake2.setMovingDown(false);
            snake2.setMovingRight(false);
            snake2.setMovingLeft(false);
            snake2.setMovingUp(false);

            // initialize the win-value
            fstPlayerWin = false;
            sndPlayerWin = false;
            
            // initialize the score
            snake.setScore(0);
            snake2.setScore(0);
            
            // Start off our snake moving right
            snake.setMovingRight(true);
            snake2.setMovingLeft(true);
            
            fever = true;
            timer.restart();
        }
        
        if ((key == KeyEvent.VK_SPACE) && ((inGame == 0) || (inGame == 3))) {
        	
        	inGame = 3;
        	
        	int intScore_1P = snake.getScore();
       	    int intScore_2P = snake2.getScore();
       	    String name_1P = "";
       	    String name_2P = "";
            
            
         	if(collision.getFstPlayerWin() == true && collision.getSndPlayerWin() == false) {
          	    rk.addScore(name_1P, intScore_1P);
         	 }
         	else if(collision.getFstPlayerWin() == false && collision.getSndPlayerWin() == true) {
          		rk.addScore(name_2P, intScore_2P);
         	}
         	 System.out.print(rk.getHighscoreString());
         	 initializeGame();
         	
        }
              
    }
}


void startGame(Graphics g, int BOARDWIDTH, int BOARDHEIGHT) {
    // Create a message telling the player the game is over
	String updown = "--------------------------------------------------------------------------------";
    String gameTitle = "== 2P Snake Game ==";
    String goodap = "GOOD-apple : ";
    String badap = "BAD-apple : ";
    String instruction = "1. How to win : Eat GOOD-apple as much as possible and Kill other snake.";
    String aware = "2. !!! Becareful !!! for BAD-apple and COLLISION with other snake!";
    String line = "------------------------------------------";
    String startmessage = "|    press the  ENTER  to start    |";
    String explanation1 = "1p : use ◀,▲,▶,▼";
    String explanation2 = "2p : use a,w,d,s";

    // Create a new font instance
    Font font = new Font("Times New Roman", Font.BOLD, 25);
    Font font2 = new Font("Times New Roman", Font.BOLD, 19);
    Font font3 = new Font("Times New Roman", Font.BOLD, 40);
    
    FontMetrics metrics = getFontMetrics(font);

    // updown
    g.setColor(Color.white);
    g.setFont(font);
    g.drawString(updown, (BOARDWIDTH - metrics.stringWidth(updown)) / 2 , BOARDHEIGHT / 2 - 200);
    
    // gametitle
    g.setFont(font3);
    g.drawString(gameTitle, (BOARDWIDTH - metrics.stringWidth(gameTitle)) / 2 - 70, BOARDHEIGHT / 2 - 120);
    
    //good-apple
    g.setColor(Color.red);
    g.setFont(font2);
    g.drawString(goodap, (BOARDWIDTH - metrics.stringWidth(goodap)) / 2 - 70, BOARDHEIGHT / 2 - 80);
    g.fillRect((BOARDWIDTH - metrics.stringWidth(goodap)) / 2 +60, BOARDHEIGHT / 2 - 91 ,PIXELSIZE, PIXELSIZE);
    
    //bad-apple
    g.setColor(Color.blue);
    g.setFont(font2);
    g.drawString(badap, (BOARDWIDTH - metrics.stringWidth(badap)) / 2 + 100, BOARDHEIGHT / 2 - 80);
    g.fillRect((BOARDWIDTH - metrics.stringWidth(goodap)) / 2 +225, BOARDHEIGHT / 2 - 91 ,PIXELSIZE, PIXELSIZE);
    
    //instruction
    g.setColor(Color.white);
    g.setFont(font2);
    g.drawString(instruction, (BOARDWIDTH - metrics.stringWidth(instruction)) / 2 + 90 , BOARDHEIGHT / 2 - 40);
    
    //aware
    g.drawString(aware, (BOARDWIDTH - metrics.stringWidth(aware)) / 2 + 90 , BOARDHEIGHT / 2 - 10);
    
    // enter button
    g.setColor(Color.magenta);
    g.setFont(font);
    g.drawString(line, (BOARDWIDTH - metrics.stringWidth(line)) / 2, BOARDHEIGHT / 2 +30);
    g.drawString(startmessage, (BOARDWIDTH - metrics.stringWidth(startmessage)) / 2, BOARDHEIGHT / 2 +50);
    g.drawString(line, (BOARDWIDTH - metrics.stringWidth(line)) / 2, BOARDHEIGHT / 2 + 70);
    
    //1p manual
    g.setColor(Color.yellow);
    g.drawString(explanation1, (BOARDWIDTH - metrics.stringWidth(explanation1)) /2 - 110, BOARDHEIGHT / 2 + 110);
    
    //2p manual
    g.setColor(Color.green);
    g.drawString(explanation2, (BOARDWIDTH - metrics.stringWidth(explanation1)) /2 + 140 , BOARDHEIGHT / 2 +110);
    
    //1p snake
    for (int i = 0; i < 10; i++) {
        g.setColor(Color.yellow);
        g.fillRect(metrics.stringWidth(explanation1) + 140 - 10 * i, BOARDHEIGHT / 2 + 140, PIXELSIZE, PIXELSIZE);
    }
    
    //2p snake
    for (int i = 0; i < 10; i++) {
        g.setColor(Color.green);
        g.fillRect(metrics.stringWidth(explanation2) + 310 + 10 * i, BOARDHEIGHT / 2 + 140, PIXELSIZE, PIXELSIZE);
    }
    
    //updown
    g.setColor(Color.white);
    g.setFont(font);
    g.drawString(updown, (BOARDWIDTH - metrics.stringWidth(updown)) / 2 , BOARDHEIGHT / 2 + 220);
    
}


private void printScore(Graphics g) {
    int intScore_1P = snake.getScore();
    int intScore_2P = snake2.getScore();
    String title1 = "1P";
    String title2 = "2P";

    Font font = new Font("Times New Roman", Font.BOLD, 25);
    Font font2 = new Font("Times New Roman", Font.BOLD, 15);
    FontMetrics metrics = getFontMetrics(font);

    // Draw the message to the board
    g.setColor(Color.yellow);
    g.setFont(font2);
    g.drawString(title1, (BOARDWIDTH - metrics.stringWidth(String.valueOf(intScore_1P))) / 4, 30);
    g.setFont(font);
    g.drawString(String.valueOf(intScore_1P), 
    		(BOARDWIDTH - metrics.stringWidth(String.valueOf(intScore_1P))) / 4, 50);
   
    g.setColor(Color.green);
    g.setFont(font2);
    g.drawString(title2, (BOARDWIDTH - metrics.stringWidth(String.valueOf(intScore_2P))) * 3 / 4, 30);
    g.setFont(font);
    g.drawString(String.valueOf(intScore_2P),
            (BOARDWIDTH - metrics.stringWidth(String.valueOf(intScore_2P))) * 3 / 4, 50);
}


    private void printRanking(Graphics g) {
    	Font font = new Font("Times New Roman", Font.BOLD, 20);
    	Font font1 = new Font("Times New Roman", Font.BOLD, 23);
    	Font font2 = new Font("Times New Roman", Font.BOLD, 40);
    	Font font3 = new Font("Times New Roman", Font.BOLD, 130);
    	Font font4 = new Font("Times New Roman", Font.BOLD, 27);
    	Font font5 = new Font("Times New Roman", Font.BOLD, 50);
        FontMetrics metrics = getFontMetrics(font);
        int height = BOARDHEIGHT / 30;
        int maxRank = 10;
        String restart = "Press ENTER to restart!";
        String title = "♕ HIGHEST SCORE ♕";
        String line = "-----------------------------------------------";
        
        //titleprint
        g.setColor(Color.white);
        g.setFont(font5);
        g.drawString(title,(BOARDWIDTH - metrics.stringWidth(title)) / 2 - 160, 150);
        
        //graphic squares
        int fsquare_wid = 80;
        int fsquare_hei = 190;
        for(int j = fsquare_hei; j<360; j += 160) {
        	g.setColor(Color.magenta);
            for(int i = fsquare_wid; i< 750; i += 70) {
            	 g.fillRect(i, j, PIXELSIZE, PIXELSIZE);
            }
            g.setColor(Color.yellow);
            for(int i = fsquare_wid + 25; i< 700; i += 70) {
            	g.fillRect(i, j, PIXELSIZE, PIXELSIZE);
            }
            g.setColor(Color.cyan);
            for(int i = fsquare_wid + 50; i< 700; i += 70) {
            	g.fillRect(i, j, PIXELSIZE, PIXELSIZE);
            }
        }
        
        //highest score print
        g.setColor(Color.white);
        g.setFont(font3);
        g.drawString(""+rk.getScores().get(0).getScore(), (BOARDWIDTH - metrics.stringWidth(title)) / 2 - 30, 320);
        
        //line print
        g.setFont(font);
        g.drawString(line, (BOARDWIDTH - metrics.stringWidth(title)) / 2 - 60 , 450);
        
        //other scores print
        int startposition = 465;
        g.setFont(font2);
        for (int i = 2; i < 4; i += 1) {
        	g.drawString( i +".  " + rk.getScores().get(i - 1).getScore(), 320, startposition + height);
        	startposition = startposition + height + 15;
        }
        int startposition2 = 537;
        g.setFont(font1);
        for (int i = 4; i < maxRank; i += 1) {
        	g.drawString( i +".    " + rk.getScores().get(i - 1).getScore(), 339, startposition2 + height);
        	startposition2 = startposition2 + height;
        }
        g.drawString("10.    " + rk.getScores().get(9).getScore(), 327, startposition2 + height);
        
        //line print
        g.setFont(font);
        g.drawString(line, (BOARDWIDTH - metrics.stringWidth(title)) / 2 - 60 , 745);
        
        //restart instruction
        g.setFont(font4);
        g.setColor(Color.magenta);
        g.drawString(restart , (BOARDWIDTH - metrics.stringWidth(String.valueOf(restart))) / 2 - 38 , 415);           
    }
    
    public void relocation(ArrayList<Poison> poison, int collidePoisonNum) {
        Poison addpoison = new Poison();
        poison.add(addpoison);
        poison.get(collidePoisonNum).createPoison();
        for (int i = collidePoisonNum; i < poison.size(); i++) {
            poison.get(i).createPoison();
        }
    }

    
public static int getAllDots() {
    return TOTALPIXELS;
}

public static int getDotSize() {
    return PIXELSIZE;
}
}
