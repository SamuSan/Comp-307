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

	private static String trainingFilename = "iris-training.txt";
	private static String testFilename = "iris-test.txt";
	private static ArrayList<Pair> candidates = new ArrayList<Pair>();
	private static ArrayList<Iris> trainingIris = new ArrayList<Iris>();
	private static ArrayList<Iris> testingIris = new ArrayList<Iris>();
	private static int K = 15;

	public static void main(String[] args) {
		// if (args.length == 2) {
		// trainingFilename = args[0];
		// testFilename = args[1];
		// } else {
		// d("Two data files must be specified");
		// }

		createTrainingData(trainingFilename);
		createTestData(testFilename);
		// for (Iris iris : trainingIris) {
		// d(iris.toString());
		// }
		// d("///////////////////////");
		// for (Iris iris : testingIris) {
		// d(iris.toString());
		// }
		doClassification();
		
		KCrossFoldValidation kCrossFold = new KCrossFoldValidation(testingIris);
		
		
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
			// TODO Auto-generated catch block
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void doClassification() {
		double count = 0;
		double success = 0;
		for (Iris i : testingIris) {
			for (Iris e : trainingIris) {
				double dist = EucledianCalc.euclidCalc(i, e);
				// d("Dist = " + dist);
				Pair p = new Pair(dist, e);
				candidates.add(p);
			}
			Collections.sort(candidates, new PairComparator());
		//	d(candidates.size());
			d(i.toString());
			ArrayList<Pair> sublist = new ArrayList<Pair>();
			for (int j = 0; j < K; j++) {
				sublist.add(candidates.get(j));
			}
			String match = doVote(sublist);
			System.out.println(match);
			//d(match);
			count++;
			//d("Chosen Dist = " + candidates.get(0).dist);
			if (i.getClazz().equals(match)) {
				success++;
			}
//			if (i.getClass() == candidates.get(0).i.getClass()) {
//				success++;
//			}
			// d("///////////////////////////S");
			candidates.clear();
		}
		double rate = success / count;
		d(rate + "% success");
	}

	private static String doVote(List<Pair> list) {
		HashMap<String, Integer> voters = new HashMap<String, Integer>();
		// Make a sublist of candidates
		// List<Pair> subList = candidates.subList(0, K);
		// candidates.subList(0, K);
		// from that sublist count how many times a particular class appears
		// The majority wins
		// Return
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
