package Game.src;

import java.util.Comparator;

//the class actually compares two scores and return true or false
public class RankComparator implements Comparator<Score> {
	public int compare (Score score1, Score score2) {
		int s1 = score1.getScore();
		int s2 = score2.getScore();

		if (s1 > s2) {
			return -1;
		}
		else if (s1 < s2) {
			return 1;
		}
		else {
			return 0;
		}
	}
}
