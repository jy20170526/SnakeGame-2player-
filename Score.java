package Game.src;

import java.io.Serializable;

public class Score implements Serializable {
		private int score;               //the score of player
		private String name;             //the name or initial of player
		
		public Score (String name, int score) {
			this.score = score;
			this.name = name;
		}
		
		public int getScore() {
			return score;
		}
		public String getName() {
			return name;
		}				
		public void setScore (int score) {
			this.score = score;
		}
		public void setName (String name) {
			this.name = name;
		}
	}