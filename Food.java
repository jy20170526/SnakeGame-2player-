package Game.src;
public class Food {

private Snake snake = new Snake();
private Snake snake2 = new Snake();
private int foodX; // Stores X pos of our food
private int foodY; // Stores Y pos of our food
public int [][] foodPosition;


// Used to determine random position of food
private final int RANDOMPOSITION = 80;

public void createFood() {

    // Set our food's x & y position to a random position

    int location = (int) (Math.random() * RANDOMPOSITION);
    foodX = ((location * Board.getDotSize()));

    location = (int) (Math.random() * RANDOMPOSITION);
    foodY = ((location * Board.getDotSize()));

    if ((foodX == snake.getSnakeX(0)) && (foodY == snake.getSnakeY(0)) &&
    		(foodX == snake2.getSnakeX(0)) && (foodY == snake2.getSnakeY(0))) {
       	createFood();
    }
}

public void createManyFoods(int widthNum, int heightNum) {
	foodPosition = new int [widthNum][heightNum];

    for(int i = 0; i < heightNum; i++) {
        for(int j = 0; j < widthNum; j++) {
        	int a = (int) (Math.random() * 6 + 1);
        	int b = (int) (Math.random() * 6 + 1);
        	int c = (int) (Math.random() * 6 + 1);
        	int d = (int) (Math.random() * 6 + 1);

        	if((i % a == 3 || (i % a == 1 )) && (j % b == 5 || j % a == 0 ))
//        	if((i + j) % 4 == 1 && i % 2 == 0)
        		foodPosition[i][j] = 1;
        	else
        		foodPosition[i][j] = 0;

        }
    }
}

public int getFoodX() {

    return foodX;
}

public int getFoodY() {
    return foodY;
}

public int[][] getFoods() {

    return foodPosition;
}

}
