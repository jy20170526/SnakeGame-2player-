package Game.src;


import java.util.ArrayList;



public class Collisions {

    private Snake snake1;
    private Snake snake2;
    private Food food;
    private ArrayList<Poison> poison = new ArrayList<Poison>();
    private boolean fstPlayerWin = false;
    private boolean sndPlayerWin = false;
    private boolean check = false;

    private int distance = 10;
    private int nmScore = 100;
    private int fvScore = 10;


    public Collisions(Snake snake1, Snake snake2, Food food, ArrayList<Poison> poison) {
        this.snake1 = snake1;
        this.snake2 = snake2;
        this.food = food;
        this.poison = poison;
    }


    void checkManyFoodCollisions(int widthNum, int heightNum, int PIXELSIZE) {
    	if(fstPlayerWin && !sndPlayerWin) {
    
	    	for(int i = 0; i < heightNum; i++) {
	            for(int j = 0; j < widthNum; j++) {

	            	if ((proximity(snake1.getSnakeX(0), j * PIXELSIZE, distance+2))
	                        && (proximity(snake1.getSnakeY(0), i * PIXELSIZE, distance +2))) {
	            		// Add score
	            		if(food.foodPosition[j][i] == 1) {
		            		snake1.setScore(snake1.getScore() + fvScore);
		            		snake1.setJoints(snake1.getJoints() + 1);
		                    food.foodPosition[j][i] = 0;
	            		}		                    

	            	}
	            }
	        }
	    //System.out.println(count);
    	}

    	else if(!fstPlayerWin && sndPlayerWin) {
    		for(int i = 0; i < heightNum; i++) {
	            for(int j = 0; j < widthNum; j++) {

	            	if ((proximity(snake2.getSnakeX(0), j * PIXELSIZE, distance +2 ))
	                        && (proximity(snake2.getSnakeY(0), i * PIXELSIZE, distance +2))) {
	            		
	            		 // Add score
	            		if(food.foodPosition[j][i] == 1) {
		                    snake2.setScore(snake2.getScore() + fvScore);
		                    snake2.setJoints(snake2.getJoints() + 1);
	            			food.foodPosition[j][i] = 0;
	            		}
	            	}
	            }
	        }
    	}

    }
    // if our snake is in the close proximity of the food..
    void checkFoodCollisions() {

        if ((proximity(snake1.getSnakeX(0), food.getFoodX(), distance))
                && (proximity(snake1.getSnakeY(0), food.getFoodY(), distance))) {

            System.out.println("intersection");
            // Add a 'joint' to our snake
            for(int i = 0; i < 5; i++)
            	snake1.setSnakeX(snake1.getJoints()-1 + i, snake1.getSnakeX(snake1.getJoints() - 3));
            snake1.setJoints(snake1.getJoints() + 5);
            // Add score
            snake1.setScore(snake1.getScore() + nmScore);
            // Create new food
            food.createFood();
        }
        if ((proximity(snake2.getSnakeX(0), food.getFoodX(), distance))
                && (proximity(snake2.getSnakeY(0), food.getFoodY(), distance))) {

            System.out.println("intersection");
            // Add a 'joint' to our snake
            for(int i = 0; i < 5; i++)
            	snake1.setSnakeX(snake1.getJoints()-1 + i, snake1.getSnakeX(snake1.getJoints() - 3));
            snake2.setJoints(snake2.getJoints() + 5);
            // Add score
            snake2.setScore(snake2.getScore() + nmScore);
            // Create new food
            food.createFood();
        }
    }

    // if our snake is in the close proximity of the poison..
    int checkPoisonCollisions() {
        for (int i = 0; i < poison.size(); i++) {
            if ((proximity(snake1.getSnakeX(0), poison.get(i).getPoisonX(), distance))
                    && (proximity(snake1.getSnakeY(0), poison.get(i).getPoisonY(), distance))) {

                System.out.println("intersection");
                // Add a 'joint' to our snake
                snake1.setJoints(snake1.getJoints() - 1);
                if (snake1.getJoints() < 1) {
                    sndPlayerWin = true;
                    return -2;
                }
                return i;
                
            } else if ((proximity(snake2.getSnakeX(0), poison.get(i).getPoisonX(), distance))
                    && (proximity(snake2.getSnakeY(0), poison.get(i).getPoisonY(), distance))) {

                System.out.println("intersection");
                // Add a 'joint' to our snake
                snake2.setJoints(snake2.getJoints() - 1);
                if (snake2.getJoints() < 1) {
                    fstPlayerWin = true;
                    return -2;
                }
                return i;


            }
        }
        return -1;
    }

    // Used to check collisions with snake's self and board edges
    int checkCollisions(int width, int height) { 

// If the snake hits other snake's joints..
	for (int i = snake2.getJoints(); i > 0; i--) {
	
		if (proximity(snake1.getSnakeX(0), snake2.getSnakeX(i), 5)
		&& proximity(snake1.getSnakeY(0), snake2.getSnakeY(i), 5)) {
			System.out.println("asfdjnaovjefakjfsanvkfj");
			sndPlayerWin = true;
			return 2;
		}
	}

	for (int i = snake1.getJoints(); i > 0; i--) {
	
		if (proximity(snake2.getSnakeX(0), snake1.getSnakeX(i), 5)
		&& proximity(snake2.getSnakeY(0), snake1.getSnakeY(i), 5)) {
			fstPlayerWin = true;
			return 2; // then the game ends
		}
	}

	return 1;
}

    
    void checkWallCollisions(int width, int height) {
// If the snake intersects with the board edges..
		if (snake1.getSnakeY(0) >= height) {
			snake1.setSnakeY(0);
			snake1.setSnakeX((int) (Math.random() * width));
		}
			
		if (snake1.getSnakeY(0) < 0) {
			snake1.setSnakeY(height);
			snake1.setSnakeX((int) (Math.random() * width));
		}
		
		if (snake1.getSnakeX(0) >= width) {
			snake1.setSnakeX(0);
			snake1.setSnakeY((int) (Math.random() * height));
		}
		
		if (snake1.getSnakeX(0) < 0) {
			snake1.setSnakeX(width);
			snake1.setSnakeY((int) (Math.random() * height));
		}
		
		if (snake2.getSnakeY(0) >= height) {
			snake2.setSnakeY(0);
			snake2.setSnakeX((int) (Math.random() * width));
		}
		
		if (snake2.getSnakeY(0) < 0) {
			snake2.setSnakeY(height);
			snake2.setSnakeX((int) (Math.random() * width));
		}
		
		if (snake2.getSnakeX(0) >= width) {
			snake2.setSnakeX(0);
			snake2.setSnakeY((int) (Math.random() * height));
		}
		
		if (snake2.getSnakeX(0) < 0) {
			snake2.setSnakeX(width);
			snake2.setSnakeY((int) (Math.random() * height));
		}

	}

    private boolean proximity(int a, int b, int closeness) {
        return Math.abs((long) a - b) <= closeness;
    }

    public boolean getFstPlayerWin() {
        return fstPlayerWin;
    }

    public boolean getSndPlayerWin() {
        return sndPlayerWin;
    }

    public void setFstPlayerWin(boolean fstPlayerWin) {
    	this.fstPlayerWin = fstPlayerWin;
    }

    public void setSndPlayerWin(boolean sndPlayerWin) {
    	this.sndPlayerWin = sndPlayerWin;
    }

}
