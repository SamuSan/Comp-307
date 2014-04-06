package main;

public class DTNode {
	private DTNode trueNode = null;
	private DTNode falseNode = null;
	private String attribute;


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

	@Override
	public String toString() {

		return "DTNode " + attribute ;
	}

}
