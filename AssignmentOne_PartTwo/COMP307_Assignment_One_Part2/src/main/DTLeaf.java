package main;

public class DTLeaf extends DTNode {
	@Override
	public String toString() {
		return "DTLeaf [category=" + category + ", probability=" + probability
				+ ", prob=" + prob + "]";
	}

	private int category=-1;
	private Probability probability;
	private double prob =0;
	public DTLeaf(String attribute, DTNode trueNode, DTNode falseNode) {
		super(attribute, trueNode, falseNode);
		// TODO Auto-generated constructor stub
	}

	public DTLeaf() {
		// TODO Auto-generated constructor stub
	}

	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
	}

	public Probability getProbability() {
		return probability;
	}

	public void setProbability(Probability probability) {
		this.probability = probability;
	}

	public double getProb() {
		return prob;
	}

	public void setProb(double prob) {
		this.prob = prob;
	}

}
