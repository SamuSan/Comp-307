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
import java.util.Random;
import java.util.Scanner;

public class Main {

	private static int numCategories;
	private static int numAtts;
	private static List<String> categoryNames;
	private static ArrayList<String> attNames;
	private static ArrayList<Instance> trainingInstances;
	private static List<Instance> testInstances;
	private static HashMap<String, Double> percentages = new HashMap<String, Double>();
	private static ArrayList<Probability> gains = new ArrayList<Probability>();

	private static String hepatitisTesting = "hepatitis-test.data";
	private static String hepatitisTraining = "hepatitis-training.data";

	private static DTNode decisionTree = null;

	private static int k = 1;

	public static void main(String[] args) throws IOException {

		trainingInstances = readDataFile(hepatitisTraining);
//		gains = doInformationGainCalc(trainingInstances);
		buildTree(trainingInstances, attNames);
		testInstances = readDataFile(hepatitisTesting);

	}

	private static void doClassify(Instance i) {
		DTNode curr = decisionTree;
		while (!isLeaf(curr)) {
			if (i.getAtt(attNames.indexOf(curr.getAttribute()))) {
				curr = curr.getTrueNode();
			} else if (!i.getAtt(attNames.indexOf(curr.getAttribute()))) {
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

	private static DTNode buildTree(ArrayList<Instance> instances,
			ArrayList<String> attributes) {
		// if instances is empty
		if (instances.size() == 0) {
			DTNode leaf = getMajority(trainingInstances);
			// return a leaf node containing the name and probability of the
			// overall
			// most probable class (ie, the baseline predictor)
			return leaf;
		}

		// if instances are pure
		if(isPure(instances)){
			DTNode leaf = new DTNode();
			leaf.setCategory(instances.get(0).getCategory());
			leaf.setProb(1);
			// return a leaf node containing the name of the class of the instances
			// in the node and probability 1
			return leaf;
		}
		// if attributes is empty
		if(attributes.size() == 0){
			// return a leaf node containing the name and probability of the
			// majority class of the instances in the node (choose randomly if
			// classes are equal)	
			DTNode leaf = getMajority(instances);
			return leaf;
		}
		// else find best attribute:
		else{
		String	bestAtt = doInformationGainCalc(attributes, instances).get(0).getAttribute();
			// for each attribute
			// separate instances instances for
			// into two sets:
			// instances for which the attribute is true, and
			// instances for which the attribute is false
			// compute purity of each set.
			// if weighted average purity of these sets is best so far
			// bestAtt = this attribute
		
		ArrayList<Instance> trueInst = new ArrayList<Instance>();
		ArrayList<Instance> falseInst = new ArrayList<Instance>();
		for (Instance instance : instances) {
			if(instance.getAtt(attNames.indexOf(bestAtt))){
				trueInst.add(instance);
				}
			else{
				falseInst.add(instance);
			}
		}
			// bestInstsTrue = set of true instances
			// bestInstsFalse = set of false instances
			// build subtrees using the remaining attributes:
		attributes.remove(bestAtt);

			// left = BuildNode(bestInstsTrue, attributes - bestAtt)
			// right = BuildNode(bestInstsFalse, attributes - bestAttr)
			// return Node containing (bestAtt, left, right)
		}

	


		return null;
	}

//	private static DTNode getBaseLine() {
//		Random r = new Random();
//		int classOne = 0;
//		int classTwo = 0;
//		for (Instance i : trainingInstances) {
//			if (i.getCategory() == 1) {
//				classOne++;
//			} else {
//				classTwo++;
//			}
//		}
//		int cat=0;
//		int prob =0;
//		if(classOne > classTwo){
//			cat = classOne;
//			prob = (classOne / (classOne + classTwo));
//		}
//		else if(classOne < classTwo){
//			cat =  classTwo;
//			prob = (classTwo / (classOne + classTwo));
//		}
//		else{
//			cat = r.nextInt(2);
//			prob = 1;
//		}
//		DTNode leaf = new DTNode();
//		leaf.setCategory(cat);
//		leaf.setProb(prob);
//		return leaf;
//	}
	private static DTNode getMajority(ArrayList<Instance> inst) {
		Random r = new Random();
		int classOne = 0;
		int classTwo = 0;
		for (Instance i : inst) {
			if (i.getCategory() == 1) {
				classOne++;
			} else {
				classTwo++;
			}
		}
		int cat=0;
		int prob =0;
		if(classOne > classTwo){
			cat =  classOne;
			prob = (classOne / (classOne + classTwo));
		}
		else if(classOne < classTwo){
			cat =  classTwo;
			prob = (classTwo / (classOne + classTwo));
		}
		else{
			cat =  r.nextInt(2);
			prob = 1;
		}
		DTNode leaf = new DTNode();
		leaf.setCategory(cat);
		leaf.setProb(prob);
		return leaf;
	}
	private static boolean isPure(ArrayList<Instance> inst) {
		int lastCat=-1;
		for (Instance instance : inst) {
			if(lastCat == -1){
				lastCat = instance.getCategory();
			}
			else if(instance.getCategory() != lastCat){
				return false;
			}
		}
		return true;
	}

	private static ArrayList<Probability> doInformationGainCalc(ArrayList<String> atts,
			ArrayList<Instance> instances) {
		ArrayList<Probability> probs = new ArrayList<Probability>();
		int idx = 0;

		while (idx < attNames.size()) {
			double trueLiveCount = 0;
			double trueDieCount = 0;
			double falseDieCount = 0;
			double falseLiveCount = 0;
			for (Instance inst : instances) {
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
			probs.add(new Probability(trueLiveCount, trueDieCount,
					falseLiveCount, falseDieCount, attNames.get(idx),
					totalCases));
			idx++;
			// Collections.sort(gains, new ProbabilityComparator());
		}
		Collections.sort(probs, new ProbabilityComparator());
		return probs;
	}

	private static ArrayList<Instance> readDataFile(String fname)
			throws IOException {
		/*
		 * format of names file: names of categories, separated by spaces names
		 * of attributes category followed by true's and false's for each
		 * instance
		 */
		ArrayList<Instance> inst = new ArrayList<Instance>();
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

	private static ArrayList<Instance> readInstances(Scanner din) {
		/* instance = classname and space separated attribute values */
		ArrayList<Instance> instances = new ArrayList<Instance>();
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
			for (int i = 0; i < vals.size(); i++) {
				ans.append(attNames.get(i) + "  ");
				ans.append(vals.get(i) ? "true  " : "false " + "\n");
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
