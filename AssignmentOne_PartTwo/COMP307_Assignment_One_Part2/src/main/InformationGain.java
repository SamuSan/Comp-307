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
		this.totalCases = trueLive + falseLive+trueDie + falseDie;
		calcGain();
	}

	private void calcGain() {
		double allLive = trueLive + falseLive;
		double allDie = trueDie + falseDie;
		double total = totalCases;
		
		
		
		double totalEnt = (allLive/totalCases) * entropy(trueLive,falseLive) + (allDie/totalCases)* entropy(trueDie,falseDie);
		totalGain = 1- totalEnt;

	}

	public double entropy(double a, double b) {
		if(a ==0 || b == 0){
			return 0;
		}
		double ent = -a / (a+b) * Math.log(a / (a+b)) -b / (a+b) * Math.log(b / (a+b)) ;
		return ent;
	}

	public double getTotalGain() {
		return totalGain;
	}

	public String getCat() {
		return cat;
	}

	@Override
	public String toString() {
		return "InformationGain [trueLive=" + trueLive + ", trueDie=" + trueDie
				+ ", falseLive=" + falseLive + ", falseDie=" + falseDie
				+ ", cat=" + cat + ", totalCases=" + totalCases
				+ ", totalGain=" + totalGain + "]";
	}

}
