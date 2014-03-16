package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Main {

	private static int numCategories;
	private static int numAtts;
	private static List<String> categoryNames;
	private static List<String> attNames;
	private static List<Instance> allInstances;
	private static HashMap<String,Double> percentages = new HashMap<String,Double>();

	private static String hepatitisTesting = "hepatitis-test.data";
	private static String hepatitisTraining = "hepatitis-training.data";
	
	private static int k =1;

	public static void main(String[] args) throws IOException {
		// if (args.length == 2) {
		// trainingFilename = args[0];
		// testFilename = args[1];
		// } else {
		// System.out.println("Two data files must be specified");
		// }

//		createTrainingData(hepatitisTraining);
	readDataFile(hepatitisTraining);
	for (Instance i : allInstances) {
		System.out.println(i.toString());
	} 
	for (String string : attNames) {
		System.out.println(string);
	}
	for (String string : categoryNames) {
		System.out.println(string);
	}
	
	}

	private static void doInformationGainCalc(){
		int idx=0;
		double trueLiveCount = 0;
		double trueDieCount = 0;
		double falseDieCount=0;
		double falseLiveCount=0;
		while (idx < attNames.size()) {
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
				idx++;
			}
			
			
		}
		
		
	}
	
	private static void createTrainingData(String trainingFilename2) {
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					ClassLoader.getSystemResourceAsStream(trainingFilename2)));
			String val = reader.readLine();
			while (!val.isEmpty()) {
				String[] vals = val.split("\\t");
				// double sepLength = Double.valueOf(vals[0]);
				// double sepWidth = Double.valueOf(vals[2]);
				// double petLength = Double.valueOf(vals[4]);
				// double petWidth = Double.valueOf(vals[6]);
				// String clazz = vals[8];
				// val = reader.readLine();

				for (String string : vals) {
					System.out.println(string);
				}

			}
			reader.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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

}
