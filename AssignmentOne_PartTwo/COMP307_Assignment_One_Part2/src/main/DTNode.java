package main;

public class DTNode {
	private DTNode trueNode;
	private DTNode falseNode;
	private String attribute;
	private String category;

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

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	@Override
	public String toString() {
		return "DTNode "+ attribute + "]";
	}

}
