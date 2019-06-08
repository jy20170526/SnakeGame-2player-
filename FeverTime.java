package Game.src;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JPanel;

public class FeverTime extends JPanel{
	//Graphics g;
	Food food;
	int BOARDWIDTH, BOARDHEIGHT;
	//boolean fstPlayerWin, sndPlayerWin;
	int PIXELSIZE;
	Snake snake1, snake2;
	Timer timer;

	public FeverTime(Food food, int BOARDWIDTH, int BOARDHEIGHT, int PIXELSIZE, Snake sn_1, Snake sn_2){
	    System.out.println("This is Fever");

	    //this.g = g;
	    this.food = food;
	    this.BOARDWIDTH = BOARDWIDTH;
	    this.BOARDHEIGHT = BOARDHEIGHT;
	    this.PIXELSIZE = PIXELSIZE;
	    this.snake1 = sn_1;
	    this.snake2 = sn_2;
	}


void EndFever(Graphics g, boolean fstPlayerWin, boolean sndPlayerWin) {	// 留덉�留� �뵾踰꾪��엫�슜 �븿�닔

	int widthNum = BOARDWIDTH/PIXELSIZE;
	int heightNum = BOARDHEIGHT/PIXELSIZE;
	food.createManyFoods(widthNum, heightNum);
	
    }

void endGame(Graphics g, int BOARDWIDTH, int BOARDHEIGHT, boolean fstPlayerWin, boolean sndPlayerWin, boolean Ranker) {

	// �씠遺�遺꾩� 蹂대뱶�겢�옒�뒪�뿉�꽌 媛��졇�삩 endGame�엫
    // Create a message telling the player the game is over
    String message = "Game over";
    String newRecord = "!! NEW RECORD !!";
    
    String winsMessage = "♕Wins♕";
    String drawMessage = "Draws";
    
    String fstPlayer = "♕First Player♕ ";
    String sndPlayer = "♕Second Player♕";
    String player = "";
    
    String score = "Score: ";
    
    String line = "------------------------------------------";
    String nextIntro = "|    press the  ENTER  to restart    |";
    String rankIntro = "|    press the  Space  to view rankings    |";
    
    int finalScore = 0;
    int score_1P = snake1.getScore();
    int score_2P = snake2.getScore();

    // Create a new font instance
    Font normalFont = new Font("Times New Roman", Font.BOLD, 80);
    Font recordFont = new Font("Times New Roman", Font.BOLD, 50);
    Font playerFont = new Font("Times New Roman", Font.BOLD, 100);
    Font nextToFont = new Font("Times New Roman", Font.BOLD, 40);
    Font rankToFont = new Font("Times New Roman", Font.BOLD, 25);
    
    FontMetrics metrics = getFontMetrics(normalFont);

    // Draw the message to the board
    //If first player wins the game
    if (fstPlayerWin == true && sndPlayerWin == false) {    
    	 g.setColor(Color.yellow);
    	 g.setFont(playerFont);
    	 
    	 player = fstPlayer;
    	 finalScore = score_1P; 
    }

    //if second player wins
    else if (sndPlayerWin == true && fstPlayerWin == false) {      
    	 g.setColor(Color.GREEN);
    	 g.setFont(playerFont);
    	 
    	 player = sndPlayer;    	    
    	 finalScore = score_2P;       
        }

    
    else if (fstPlayerWin ==true && sndPlayerWin == true) {    	
        g.drawString(drawMessage, (BOARDWIDTH - metrics.stringWidth(drawMessage) / 2),
                BOARDHEIGHT / 2 + 20);
    }
    g.drawString(player, (BOARDWIDTH / 2 - metrics.stringWidth(player) / 2) - 50, 190);
    g.drawString(winsMessage, (BOARDWIDTH / 2 - metrics.stringWidth(winsMessage) /2) - 20, 290);
    
    
    //print Score
    g.setColor(Color.magenta);
    g.setFont(normalFont);
    g.drawString(score, (BOARDWIDTH / 2 - metrics.stringWidth(score) / 2) - 90, BOARDHEIGHT / 2 + 100);
    g.drawString(String.valueOf(finalScore), BOARDWIDTH / 2 - metrics.stringWidth(String.valueOf(finalScore)) / 2 + 90,
   		 BOARDHEIGHT / 2 + 100);
    
   
    g.setColor(Color.red);
    g.setFont(recordFont);
    
    // if new ranker arrived
    if (Ranker)
    	message = newRecord;
    
    g.drawString(message, (BOARDWIDTH / 2 - metrics.stringWidth(message) / 2) + 50,
        	            BOARDHEIGHT / 2);
    
    // input instruction
    g.setColor(Color.magenta);
    g.setFont(rankToFont);
    g.drawString(line, (BOARDWIDTH - metrics.stringWidth(line) / 2), BOARDHEIGHT / 2 +230);
    g.drawString(rankIntro, (BOARDWIDTH - metrics.stringWidth(rankIntro) / 2) + 60, BOARDHEIGHT / 2 +250);
    g.drawString(line, (BOARDWIDTH - metrics.stringWidth(line) / 2) , BOARDHEIGHT / 2 + 270);
    
    g.setColor(Color.white);
    g.setFont(nextToFont);
    g.drawString(line, (BOARDWIDTH - metrics.stringWidth(line) / 2) - 80, BOARDHEIGHT / 2 +300);
    g.drawString(nextIntro, (BOARDWIDTH - metrics.stringWidth(nextIntro) / 2) - 80, BOARDHEIGHT / 2 +330);
    g.drawString(line, (BOARDWIDTH - metrics.stringWidth(line) / 2) - 80, BOARDHEIGHT / 2 + 360);

    //System.out.println("Game Ended");

}


}
