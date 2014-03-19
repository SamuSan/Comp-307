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
	private static List<Instance> allInstances;
	private static HashMap<String,Double> percentages = new HashMap<String,Double>();
	private static ArrayList<Probability> gains = new ArrayList<Probability>();

	private static String hepatitisTesting = "hepatitis-test.data";
	private static String hepatitisTraining = "hepatitis-training.data";
	
	private static DTNode root=null;
	
	private static int k =1;

	public static void main(String[] args) throws IOException {
	readDataFile(hepatitisTraining);
	doInformationGainCalc();
	d("/////////////////////////////");
	for (Probability p : gains) {
		d(p.toString());
	}
	//createTree();
	}
	private static void createTree(){
		root = new DTNode();
		DTNode currNode = root;
		for (Instance i : allInstances) {
			for (int att = 0; att < gains.size(); att++) {
				String query = 	gains.get(att).getCat();
				if(i.vals.get(attNames.indexOf(query)) && currNode.getTrueNode()==null){
					currNode.setTrueNode(new DTNode(query));
					currNode = currNode.getTrueNode();
				}
				else if (!i.vals.get(attNames.indexOf(query)) && currNode.getFalseNode()==null){
					currNode.setFalseNode(new DTNode(query));
					currNode = currNode.getFalseNode();
				}
			}
			currNode.setCategory(categoryNames.get(i.getCategory()));
			d(currNode.getCategory()+ currNode.toString());
			currNode = root;
		}
		d("Done");
	}


	private static void doInformationGainCalc(){
		int idx=0;

		while (idx < attNames.size()) {
			double trueLiveCount = 0;
			double trueDieCount = 0;
			double falseDieCount=0;
			double falseLiveCount=0;
			for (Instance inst : allInstances) {
				if(inst.vals.get(idx)==true && inst.category ==0){
					trueLiveCount++;
				}
				else if(inst.vals.get(idx)==true && inst.category ==1){
					trueDieCount++;
				}
				if(inst.vals.get(idx)==false && inst.category ==0){
					falseLiveCount++;
				}
				else if(inst.vals.get(idx)==false && inst.category ==1){
					falseDieCount++;
				}
			}
			double totalCases = trueDieCount+trueLiveCount+falseDieCount+falseLiveCount;
			gains.add(new Probability(trueLiveCount, trueDieCount, falseLiveCount, falseDieCount, attNames.get(idx), totalCases));
			idx++;
			Collections.sort(gains,new ProbabilityComparator());
		}
	}

	private static void readDataFile(String fname) throws IOException {
		/*
		 * format of names file: names of categories, separated by spaces names
		 * of attributes category followed by true's and false's for each
		 * instance
		 */
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

		allInstances = readInstances(din);
		din.close();
	}

	private static List<Instance> readInstances(Scanner din) {
		/* instance = classname and space separated attribute values */
		List<Instance> instances = new ArrayList<Instance>();
		String ln;
		while (din.hasNext()) {
			Scanner line = new Scanner(din.nextLine());
			instances.add(new Instance(categoryNames.indexOf(line.next()), line));
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
			for (Boolean val : vals)
				ans.append(val ? "true  " : "false ");
			return ans.toString();
		}

	}
	
private static void d(Object o){
	System.out.println(o.toString());
}

}
