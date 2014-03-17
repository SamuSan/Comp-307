package main;

public class InformationGain {
	private final double trueLive;
	private final double trueDie;
	private final double falseLive;
	private final double falseDie;
	private final String cat;
	private final double totalCases;
	private double totalGain = 0;

	public InformationGain(double trueLive, double trueDie, double falseLive,
			double falseDie, String cat, double totalCases) {
		super();
		this.trueLive = trueLive;
		this.trueDie = trueDie;
		this.falseLive = falseLive;
		this.falseDie = falseDie;
		this.cat = cat;
		this.totalCases = totalCases;
		calcGain();
	}

	private void calcGain() {
		double allTrue = trueLive + trueDie;
		double allFalse = falseLive + falseDie;
		double entT = entropy(trueLive, falseLive);

	}

	public double entropy(double a, double b) {
		double ent = -a / (a+b) * Math.log(a / (a+b)) -b / (a+b) * Math.log(b / (a+b)) ;
		return ent;
	}

	@Override
	public String toString() {
		return "InformationGain [trueLive=" + trueLive + ", trueDie=" + trueDie
				+ ", falseLive=" + falseLive + ", falseDie=" + falseDie
				+ ", cat=" + cat + ", totalCases=" + totalCases
				+ ", totalGain=" + totalGain + "]";
	}

}
