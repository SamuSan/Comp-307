package main;

public class Probability {
	private double trueLive=0.0;
	private double trueDie=0.0;
	private double falseLive=0.0;
	private double falseDie=0.0;
	private String attribute="";
	private double totalCases=0.0;

	private double prob = 0;

	public Probability(double trueLive, double trueDie, double falseLive,
			double falseDie, String cat, double totalCases) {
		super();
		this.trueLive = trueLive;
		this.trueDie = trueDie;
		this.falseLive = falseLive;
		
		this.falseDie = falseDie;
		this.attribute = cat;
		this.totalCases = totalCases;
		calcPaPb();
		d(this);
		
	}
	public Probability(){
		
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

	public String getAttribute() {
		return attribute;
	}
	
	public double getProb() {
		return prob;
	}
	public void setProb(double prob) {
		this.prob = prob;
	}

	@Override
	public String toString() {
		return "Probability "
				+ ", cat=" + attribute +  " prob = "+ prob + "]";
	}
private void d(Object o){
//	System.out.println(o);
}
}
