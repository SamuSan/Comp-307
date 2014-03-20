import java.util.Random;

public class Feature {
	int[] row;
	int[] col;
	boolean[] sign;

	public Feature(int seed) {
		seed++;
		for (int i = 0; i < 4; i++) {
			row[i] = new Random().nextInt(seed-0) + 0;
			col[i] = new Random().nextInt(seed-0) + 0;
			sign[i] = new Random().nextBoolean();
		}
	}
}
