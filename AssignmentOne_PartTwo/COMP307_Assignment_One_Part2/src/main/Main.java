package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream.GetField;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

public class Main {

	private static int numCategories;
	private static int numAtts;
	private static List<String> categoryNames;
	private static List<String> attNames;
	private static List<Instance> traingingInstances;
	private static List<Instance> testInstances;
	private static HashMap<String, Double> percentages = new HashMap<String, Double>();
	private static ArrayList<Probability> gains = new ArrayList<Probability>();

	private static String hepatitisTesting = "hepatitis-test.data";
	private static String hepatitisTraining = "hepatitis-training.data";

	private static DTNode decisionTree = null;

	private static int k = 1;

	public static void main(String[] args) throws IOException {
		traingingInstances = readDataFile(hepatitisTraining);
		doInformationGainCalc();
		//createTree();

		testInstances = readDataFile(hepatitisTesting);
		d(traingingInstances.size());
		for (Instance i : traingingInstances) {
			//System.out.println(i.getAtt(attNames.indexOf("BIGLIVER")));
		}
		//doTest();
		for (Probability p : gains) {
			System.out.println(p.toString());
		}
	}

	private static void doTest() {
		for (Instance i : testInstances) {
			doClassify(i);
		}
	}

	private static void doClassify(Instance i) {
		DTNode curr = decisionTree;
		while (!isLeaf(curr)) {
			if (i.getAtt(attNames.indexOf(curr.getAttribute()))) {
				curr = curr.getTrueNode();
			}
			else if (!i.getAtt(attNames.indexOf(curr.getAttribute()))) {
				curr = curr.getFalseNode();
			}
		}
		if (curr.getCategory() == i.getCategory()) {
			d("Correct");
		} else {
			d("Wrong");
		}

	}

	private static boolean isLeaf(DTNode d) {
		if (d.getCategory() == -1) {
			return false;
		}
		return true;
	}

//	private static void createTree() {
//		DTNode curr = new DTNode();
//		root = new DTNode(gains.get(0).getAttribute());
//		curr = root;
//
//		for (Instance i : allInstances) {
//			for (int att = 0; att < gains.size(); att++) {
//				String currAtt = gains.get(att).getAttribute();
//				if (i.getAtt(att) && curr.getTrueNode() == null) {
//					if (!isLastAtt(att, gains.size() - 1)) {
//						curr.setTrueNode(new DTNode(gains.get(att + 1)
//								.getAttribute()));
//						curr = curr.getTrueNode();
//					} else {
//						DTNode leaf = new DTNode();
//						leaf.setCategory(i.getCategory());
//						curr.setTrueNode(leaf);
//						curr = root;
//					}
//				} else if (i.getAtt(att) && curr.getTrueNode() != null) {
//					curr = curr.getTrueNode();
//				} else if (!i.getAtt(att) && curr.getFalseNode() == null) {
//					if (!isLastAtt(att, gains.size() - 1)) {
//						curr.setFalseNode(new DTNode(gains.get(att + 1)
//								.getAttribute()));
//						curr = curr.getFalseNode();
//					} else {
//						DTNode leaf = new DTNode();
//						leaf.setCategory(i.getCategory());
//						curr.setFalseNode(leaf);
//						curr = root;
//					}
//				} else if (!i.getAtt(att) && curr.getFalseNode() != null) {
//					curr = curr.getFalseNode();
//				}
//			}
//		}
//		d("Done");
//	}
	private static DTNode buildTree(ArrayList<Instance> instances, ArrayList<String> attributes  ) {
		



		//if instances is empty
		if(instances.size()==0){
			 
		}
			//return a leaf node containing the name and probability of the overall most probable class (ie, the baseline predictor)
		//if instances are pure
			//return a leaf node containing the name of the class of the instances
				//in the node and probability 1 
		//if attributes is empty
			//return a leaf node containing the name and probability of the majority class of the instances in the node (choose randomly if classes are equal)
//else find best attribute: for each attribute
		//separate instances  instances for
			//into two sets:
				//instances for which the attribute is true, and 
				//instances for which the attribute is false
					//compute purity of each set.
					//if weighted average purity of these sets is best so far
							//bestAtt = this attribute
							//bestInstsTrue = set of true instances 
							//bestInstsFalse = set of false instances
			//build subtrees using the remaining attributes:
			//left = BuildNode(bestInstsTrue, attributes - bestAtt) 
			//right = BuildNode(bestInstsFalse, attributes - bestAttr)
//return Node containing (bestAtt, left, right)
		
		
		
		
		
		
		
		

		return null;
	}

	private static void doInformationGainCalc() {
		int idx = 0;

		while (idx < attNames.size()) {
			double trueLiveCount = 0;
			double trueDieCount = 0;
			double falseDieCount = 0;
			double falseLiveCount = 0;
			for (Instance inst : traingingInstances) {
				if (inst.vals.get(idx) == true && inst.category == 0) {
					trueLiveCount++;
				} else if (inst.vals.get(idx) == true && inst.category == 1) {
					trueDieCount++;
				}
				if (inst.vals.get(idx) == false && inst.category == 0) {
					falseLiveCount++;
				} else if (inst.vals.get(idx) == false && inst.category == 1) {
					falseDieCount++;
				}
			}
			double totalCases = trueDieCount + trueLiveCount + falseDieCount
					+ falseLiveCount;
			gains.add(new Probability(trueLiveCount, trueDieCount,
					falseLiveCount, falseDieCount, attNames.get(idx),
					totalCases));
			idx++;
		//	Collections.sort(gains, new ProbabilityComparator());
		}
		Collections.sort(gains, new ProbabilityComparator());
	}

	private static List<Instance> readDataFile(String fname) throws IOException {
		/*
		 * format of names file: names of categories, separated by spaces names
		 * of attributes category followed by true's and false's for each
		 * instance
		 */
		List<Instance> inst = new ArrayList<Instance>();
		System.out.println("Reading data from file " + fname);
		Scanner din = new Scanner(new InputStreamReader(
				ClassLoader.getSystemResourceAsStream(fname)));

		categoryNames = new ArrayList<String>();
		for (Scanner s = new Scanner(din.nextLine()); s.hasNext();)
			categoryNames.add(s.next());
		numCategories = categoryNames.size();
		System.out.println(numCategories + " categories");

		attNames = new ArrayList<String>();
		for (Scanner s = new Scanner(din.nextLine()); s.hasNext();)
			attNames.add(s.next());
		numAtts = attNames.size();
		System.out.println(numAtts + " attributes");

		inst = readInstances(din);
		din.close();
		return inst;

	}

	private static List<Instance> readInstances(Scanner din) {
		/* instance = classname and space separated attribute values */
		List<Instance> instances = new ArrayList<Instance>();
		String ln;
		while (din.hasNext()) {
			Scanner line = new Scanner(din.nextLine());
			instances
					.add(new Instance(categoryNames.indexOf(line.next()), line));
		}
		System.out.println("Read " + instances.size() + " instances");
		return instances;
	}

	private static class Instance {

		private int category;
		private List<Boolean> vals;

		public Instance(int cat, Scanner s) {
			category = cat;
			vals = new ArrayList<Boolean>();
			while (s.hasNextBoolean())
				vals.add(s.nextBoolean());
		}

		public boolean getAtt(int index) {
			return vals.get(index);
		}

		public int getCategory() {
			return category;
		}

		public String toString() {
			StringBuilder ans = new StringBuilder(categoryNames.get(category));
			ans.append(" ");
			for (int i=0;i<vals.size();i++){
				ans.append(attNames.get(i)+"  ");
				ans.append(vals.get(i) ? "true  " : "false "+"\n");
			}
			return ans.toString();
		}

	}

	private static boolean isLastAtt(int i, int j) {
		if (i != j) {
			return false;
		}
		return true;
	}

	private static void d(Object o) {
		System.out.println(o.toString());
	}

}
