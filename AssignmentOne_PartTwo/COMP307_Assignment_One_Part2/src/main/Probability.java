package main;

public class Probability {
	private final double trueLive;
	private final double trueDie;
	private final double falseLive;
	private final double falseDie;
	private final String cat;
	private final double totalCases;
	private double prob = 0;

	public Probability(double trueLive, double trueDie, double falseLive,
			double falseDie, String cat, double totalCases) {
		super();
		this.trueLive = trueLive;
		this.trueDie = trueDie;
		this.falseLive = falseLive;
		this.falseDie = falseDie;
		this.cat = cat;
		this.totalCases = totalCases;
		calcPaPb();
		d(this);
		
	}

	private void calcPaPb() {
		double allTrue = trueLive + trueDie;
		double allFalse = falseLive + falseDie;
		double total = totalCases;
		double probNodeTrue = allTrue / total;
		double probNodeFalse = allFalse / total;

		prob = probNodeTrue
				* ((trueLive * falseLive) / Math.pow((trueLive + falseLive), 2))
				+probNodeFalse
				* ((trueDie * falseDie) / Math.pow((trueDie + falseDie), 2));

	}

	public double getTotalGain() {
		return prob;
	}

	public String getCat() {
		return cat;
	}

	@Override
	public String toString() {
		return "Probability [trueLive=" + trueLive + ", trueDie=" + trueDie
				+ ", falseLive=" + falseLive + ", falseDie=" + falseDie
				+ ", cat=" + cat + ", totalCases=" + totalCases
				+ ", totalProb=" + prob + "]";
	}
private void d(Object o){
	System.out.println(o);
}
}
