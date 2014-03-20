package main;

public class DTNode {
	private DTNode trueNode = null;
	private DTNode falseNode = null;
	private String attribute;
	private int category;

	public DTNode(String attribute) {
		super();
		this.attribute = attribute;
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

	@Override
	public String toString() {

		return "DTNode " + attribute ;
	}

}
