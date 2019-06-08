package Game.src;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;

public class Rank {
		//the array where the scores are deposited
		private ArrayList<Score> scores;
		//the name of the file where the rankings will be saved
		private static final String RANKING_FILE = "rankings.dat";

		ObjectOutputStream outputStream = null;
		ObjectInputStream inputStream = null;

		//Initialize the score
		public Rank() {
			scores = new ArrayList<Score>();
		}

		//Sets the name of Player and his score at one String
		public String getRankedString() {
			String rankedString = "";
			int max = 10;

			ArrayList<Score> scores;
			scores = getScores();

			int i = 0;
			int x = scores.size();
			if (x > max) {
				x = max;
			}
			while (i < x) {
				rankedString += (i + 1) + ".\t" + scores.get(i).getName() +
						"\t\t" + scores.get(i).getScore() + "\n";
				i++;
			}
			return rankedString;
		}

		//gets the score and Player-name, and updates(writes) it to the Ranking file
		public void updateScorefile() {
			try {
				outputStream = new ObjectOutputStream(new FileOutputStream(RANKING_FILE));
			    outputStream.writeObject(scores);
			}
			catch (FileNotFoundException e) {
				System.out.println ("File Not Found!!" + e.getMessage());
			}
			catch (IOException e) {
				System.out.println ("Input or Output Error!!" + e.getMessage());
			}

			try {
				if (outputStream != null) {
					outputStream.flush();
					outputStream.close();
				}
			}
			catch (IOException e) {
				System.out.println ("Input or Output Error!!" + e.getMessage());
			}
		}

		//from the file it gets the String which shows name and score of the five best Player
		public void loadScoreFile() {
			try {
				inputStream = new ObjectInputStream(new FileInputStream(RANKING_FILE));
				scores = (ArrayList<Score>) inputStream.readObject();
			}
			catch (FileNotFoundException e) {
				System.out.println ("File Not Found!!" + e.getMessage());
			}
			catch (IOException e) {
				System.out.println ("Input or Output Error!!" + e.getMessage());
			}
			catch (ClassNotFoundException e) {
				System.out.println ("Class Not Found!!" + e.getMessage());
			}

			try {
				if (outputStream != null) {
					outputStream.flush();
					outputStream.close();
				}
			}
			catch (IOException e) {
				System.out.println ("Input or Output Error!!" + e.getMessage());
			}
		}
		
		public String getHighscoreString() {
	        String highscoreString = "";
		int max = 10;

	        ArrayList<Score> scores;
	        scores = getScores();

	        int i = 0;
	        int x = scores.size();
	        if (x > max) {
	            x = max;
	        }
	        while (i < x) {
	            highscoreString += (i + 1) + ".\t" + scores.get(i).getName() + "\t\t" + scores.get(i).getScore() + "\n";
	            i++;
	        }
	        return highscoreString;
	}
		
		//it takes the score of ranker
		public ArrayList<Score> getScores() {
			loadScoreFile();
			sort();
			return scores;
		}

	    //sorting the rank of Players according to their scores
		private void sort() {
			RankComparator comparator = new RankComparator();
			Collections.sort(scores, comparator);
		}

		public void addScore (String name, int score) {
			loadScoreFile();
			scores.add(new Score(name, score));
			updateScorefile();
		}
	}
