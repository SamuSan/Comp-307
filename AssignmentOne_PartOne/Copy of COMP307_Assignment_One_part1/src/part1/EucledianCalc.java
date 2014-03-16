package part1;

import flowers.Iris;

public class EucledianCalc {
	public static double euclidCalc(Iris i, Iris e) {
		double dist = 0;
		double sw = (e.getSepalWidth() - i.getSepalWidth());
		double sl = (e.getSepalLength() - i.getSepalLength());
		double pw = (e.getPetalWidth() - i.getPetalWidth());
		double pl = (e.getPetalLength() - i.getPetalLength());
		dist = Math.sqrt((Math.pow(sw, 2.0)) + (Math.pow(sl, 2.0))
				+ (Math.pow(pw, 2.0)) + (Math.pow(pl, 2.0)));
		return dist;

	}
}
