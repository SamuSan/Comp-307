package main;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main {

	private static int numCategories;
	private static int numAtts;
	private static List<String> categoryNames;
	private static ArrayList<String> attNames;
	private static ArrayList<Instance> trainingInstances;
	private static ArrayList<Instance> testInstances;

	private static String hepatitisTesting = "";
	private static String hepatitisTraining = "";

	private static DTNode decTree = null;

	private static int k = 1;

	public static void main(String[] args) throws IOException {
		hepatitisTraining = args[0];
		hepatitisTesting = args[1];
		trainingInstances = readDataFile(hepatitisTraining);
		testInstances = readDataFile(hepatitisTesting);
		ArrayList<String> attributes = new ArrayList<String>(attNames);
		decTree = buildTree(trainingInstances, attributes);
		
		
		double success = 0;
		double baseLineSuccess = 0;
		double count = 0;
		for (Instance i : trainingInstances) {
			count++;
			DTLeaf d = findClass(decTree, i);
			if (categoryNames.get(d.getCategory()) == categoryNames.get(i
					.getCategory())) {
				success++;
			}
			if(categoryNames.get(getBaseLine(trainingInstances)) == categoryNames.get(i
					.getCategory())){
				baseLineSuccess++;
			}
		}
System.out.printf("%4.2f Success\n",(success / count)*100 );
System.out.printf("%4.2f Baseline Success\n",(baseLineSuccess / count)*100 );
System.out.println();
doMultipleRuns();
//		decTree.report("");
		

	}

	private static DTLeaf findClass(DTNode node, Instance i) {

		if (node instanceof DTLeaf) {
			return (DTLeaf) node;
		}

		if (i.getAtt(attNames.indexOf(node.getAttribute()))) {
			node = findClass(node.getTrueNode(), i);
		} else if (!i.getAtt(attNames.indexOf(node.getAttribute()))) {
			node = findClass(node.getFalseNode(), i);
		}

		return (DTLeaf) node;
	}

	private static DTNode buildTree(ArrayList<Instance> instances,
			ArrayList<String> attributes) {
		if (instances.size() == 0) {
			DTLeaf leaf = getMajority(trainingInstances);
			return leaf;
		}

		if (isPure(instances)) {
			DTLeaf leaf = new DTLeaf();
			leaf.setCategory(instances.get(0).getCategory());
			leaf.setProb(1.0);
			return leaf;
		}
		if (attributes.size() == 0) {
			DTLeaf leaf = getMajority(instances);
			return leaf;
		} else {
			String bestAtt = doInformationGainCalc(attributes, instances)
					.get(0).getAttribute();
			ArrayList<Instance> trueInst = new ArrayList<Instance>();
			ArrayList<Instance> falseInst = new ArrayList<Instance>();
			for (Instance instance : instances) {
				if (instance.getAtt(attNames.indexOf(bestAtt))) {
					trueInst.add(instance);
				} else {
					falseInst.add(instance);
				}
			}
			ArrayList<String> attNext = new ArrayList<String>();
			for (String string : attributes) {
				if (!string.equals(bestAtt)) {
					attNext.add(string);

				}
			}
			DTNode trueNode = buildTree(trueInst, attNext);
			DTNode falseNode = buildTree(falseInst, attNext);
			return new DTNode(bestAtt, trueNode, falseNode);
		}
	}

	@SuppressWarnings("unchecked")
	private static <T extends DTNode> T getMajority(ArrayList<Instance> inst) {
		Random r = new Random();
		double classOne = 0;
		double classTwo = 0;
		double prob = 0;
		for (Instance i : inst) {
			if (i.getCategory() == 0) {
				classOne++;
			} else {
				classTwo++;
			}
		}
		int cat = 0;

		if (classOne > classTwo) {
			cat = 0;

			prob = classOne / (classOne + classTwo);
		} else if (classOne < classTwo) {
			cat = 1;
			prob = classTwo / (classOne + classTwo);
		}

		else {
			d("even");
			cat = r.nextInt(2);
			prob = 1;
		}
		d(prob);
		DTLeaf leaf = new DTLeaf();
		leaf.setCategory(cat);
		leaf.setProb(prob);
		return (T) leaf;
	}
	@SuppressWarnings("unchecked")
	private static int getBaseLine(ArrayList<Instance> inst) {
		Random r = new Random();
		double classOne = 0;
		double classTwo = 0;
		double prob = 0;
		for (Instance i : inst) {
			if (i.getCategory() == 0) {
				classOne++;
			} else {
				classTwo++;
			}
		}
		int cat = 0;

		if (classOne > classTwo) {
			cat = 0;

			prob = classOne / (classOne + classTwo);
		} else if (classOne < classTwo) {
			cat = 1;
			prob = classTwo / (classOne + classTwo);
		}

		else {
			cat = r.nextInt(2);
			prob = 1;
		}
		return cat;
	}

	private static boolean isPure(ArrayList<Instance> inst) {
		int lastCat = -1;
		for (Instance instance : inst) {
			if (lastCat == -1) {
				lastCat = instance.getCategory();
			} else if (instance.getCategory() != lastCat) {
				return false;
			}
		}
		return true;
	}

	private static ArrayList<Probability> doInformationGainCalc(
			ArrayList<String> atts, ArrayList<Instance> instances) {
		ArrayList<Probability> probs = new ArrayList<Probability>();
		int idx = 0;

		while (idx < atts.size()) {
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
					falseLiveCount, falseDieCount, atts.get(idx), totalCases));
			idx++;
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
private static void doMultipleRuns() throws IOException{
	ArrayList<Double> successRates = new ArrayList<Double> ();
	for(int j=10; j>0;j--){
		if (j == 10) {
			hepatitisTraining = "hepatitis-training-run"+j+".dat";
			hepatitisTesting = "hepatitis-test-run"+j+".dat";
		}
		else{
			hepatitisTraining = "hepatitis-training-run0"+j+".dat";
			hepatitisTesting = "hepatitis-test-run0"+j+".dat";
		}
		trainingInstances = readDataFile(hepatitisTraining);
		testInstances = readDataFile(hepatitisTesting);
		ArrayList<String> attributes = new ArrayList<String>(attNames);
		decTree = buildTree(trainingInstances, attributes);
		
		
		double success = 0;
		double count = 0;
		for (Instance i : testInstances) {
			count++;
			DTLeaf d = findClass(decTree, i);
			if (categoryNames.get(d.getCategory()) == categoryNames.get(i
					.getCategory())) {
				success++;
			}
		}
		successRates.add((success / count));
		
		
		
	}
	double averagedSuccess =0;
	for (Double double1 : successRates) {
		averagedSuccess+=double1;
	}
	System.out.printf("%4.2f Averaged Success Across 10 Files\n",(averagedSuccess / successRates.size())*100 );
}
	private static void d(Object o) {
//		 System.out.println(o.toString());
	}

}
