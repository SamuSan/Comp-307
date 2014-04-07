import java.util.Arrays;
import java.util.Random;

public class Feature {
	int[] row;
	int[] col;
	boolean[] sign;

	public Feature(int seed) {
		row = new int[4];
		col = new int[4];
		sign = new boolean[4];
		for (int i = 0; i < 4; i++) {
			row[i] = new Random().nextInt(seed - 0) + 0;
			col[i] = new Random().nextInt(seed - 0) + 0;
			sign[i] = new Random().nextBoolean();
		}
	}


	@Override
	public String toString() {
		String s = "";
		
		for (int i = 0; i < row.length; i++) {
			s+=row[i]+":"+col[i]+": "+sign[i]+"\n";
		}
		return s;
	}
}
