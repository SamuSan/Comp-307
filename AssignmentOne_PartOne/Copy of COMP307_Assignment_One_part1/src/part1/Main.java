package part1;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

import flowers.FlowerFactory;
import flowers.Iris;

public class Main {

	private static String trainingFilename = "";
	private static String testFilename = "";
	private static ArrayList<Pair> candidates = new ArrayList<Pair>();
	private static ArrayList<Iris> trainingIris = new ArrayList<Iris>();
	private static ArrayList<Iris> testingIris = new ArrayList<Iris>();
	private static int K = 3;

	public static void main(String[] args) {
		 if (args.length == 2) {
		 trainingFilename = args[0];
		 testFilename = args[1];
		 } else {
		 d("Two data files must be specified");
		 }

		createTrainingData(trainingFilename);
		createTestData(testFilename);
		doClassification();
		
//		KCrossFoldValidation kCrossFold = new KCrossFoldValidation(testingIris);
		
		
	}

	private static void createTestData(String testFilename2) {
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					ClassLoader.getSystemResourceAsStream(testFilename2)));
			String val = reader.readLine();
			while (!val.isEmpty()) {
				String[] vals = val.split(" ");
				double sepLength = Double.valueOf(vals[0]);
				double sepWidth = Double.valueOf(vals[2]);
				double petLength = Double.valueOf(vals[4]);
				double petWidth = Double.valueOf(vals[6]);
				String clazz = vals[8];
				Iris iris = FlowerFactory.getFlower(clazz, sepLength, sepWidth,
						petLength, petWidth);
				testingIris.add(iris);
				val = reader.readLine();
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static void createTrainingData(String trainingFilename2) {
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					ClassLoader.getSystemResourceAsStream(trainingFilename2)));
			String val = reader.readLine();
			while (!val.isEmpty()) {
				String[] vals = val.split(" ");
				double sepLength = Double.valueOf(vals[0]);
				double sepWidth = Double.valueOf(vals[2]);
				double petLength = Double.valueOf(vals[4]);
				double petWidth = Double.valueOf(vals[6]);
				String clazz = vals[8];
				Iris iris = FlowerFactory.getFlower(clazz, sepLength, sepWidth,
						petLength, petWidth);
				trainingIris.add(iris);
				val = reader.readLine();
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void doClassification() {
		double count = 0;
		double success = 0;
		for (Iris i : testingIris) {
			for (Iris e : trainingIris) {
				double dist = EucledianCalc.euclidCalc(i, e);
				Pair p = new Pair(dist, e);
				candidates.add(p);
			}
			Collections.sort(candidates, new PairComparator());
			ArrayList<Pair> sublist = new ArrayList<Pair>();
			for (int j = 0; j < K; j++) {
				sublist.add(candidates.get(j));
			}
			String match = doVote(sublist);
			System.out.println(match);
			count++;
			if (i.getClazz().equals(match)) {
				success++;
			}
			candidates.clear();
		}
		double rate = success / count;
		d(rate + "% accuracy");
	}

	private static String doVote(List<Pair> list) {
		HashMap<String, Integer> voters = new HashMap<String, Integer>();
		for (Pair pair : list) {
			if (!voters.keySet().contains(pair.i.getClazz())) {
				voters.put(pair.i.getClazz(), 1);
			} else {
				int x = voters.remove(pair.i.getClazz());
				voters.put(pair.i.getClazz(), x + 1);
			}
		}
		int maj = 0;
		String choice = "";
		for (String s : voters.keySet()) {
			if (voters.get(s) > maj) {
				maj = voters.get(s);
				choice = s;
			}
		}

		return choice;
	}

	private static void d(Object o) {
		 System.out.println(o.toString());
	}
}
