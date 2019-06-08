package Game.src;

import java.util.ArrayList;

public class Poison {
	
private Snake snake = new Snake();
private Snake snake2 = new Snake();
private int poisonX; // Stores X pos of our food
private int poisonY; // Stores Y pos of our food

// Used to determine random position of food
private final int RANDOMPOSITION = 80;

public void createPoison() {

    // Set our food's x & y position to a random position

    int location = (int) (Math.random() * RANDOMPOSITION);
    poisonX = ((location * Board.getDotSize()));

    location = (int) (Math.random() * RANDOMPOSITION);
    poisonY = ((location * Board.getDotSize()));

    if ((poisonX == snake.getSnakeX(0)) && (poisonY == snake.getSnakeY(0)) && (poisonX
    		== snake2.getSnakeX(0)) && (poisonY == snake2.getSnakeY(0))) 
    {
        createPoison();
    }
}



public int getPoisonX() {

    return poisonX;
}

public int getPoisonY() {
    return poisonY;
}
}