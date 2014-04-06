package main;

public class DTNode {
	private DTNode trueNode = null;
	private DTNode falseNode = null;
	private String attribute;
	private int category=-1;
	private Probability probability;
	private int prob =0;

	public DTNode(String attribute, DTNode trueNode, DTNode falseNode) {
		super();
		this.attribute = attribute;
		this.trueNode = trueNode;
		this.falseNode = falseNode;
	}

	public DTNode() {
	}

	public void setTrueNode(DTNode trueNode) {
		this.trueNode = trueNode;
	}

	public void setFalseNode(DTNode falseNode) {
		this.falseNode = falseNode;
	}

	public String getAttribute() {
		return attribute;
	}

	public DTNode getTrueNode() {
		return trueNode;
	}

	public DTNode getFalseNode() {
		return falseNode;
	}

	public int getCategory() {
		return category;
	}

	public void setCategory(int i) {
		this.category = i;
	}
public void setProb(int e){
	this.prob=e;
}
	@Override
	public String toString() {

		return "DTNode " + attribute ;
	}

}
