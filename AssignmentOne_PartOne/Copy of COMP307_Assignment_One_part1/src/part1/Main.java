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
	private static ArrayList <Pair>  candidates = new ArrayList<Pair>();
	private static ArrayList<Iris> trainingIris = new ArrayList<Iris>();
	private static ArrayList<Iris> testingIris = new ArrayList<Iris>();
	private static int K = 3;


	public static void main(String[] args) {
//		if (args.length == 2) {
//			trainingFilename = args[0];
//			testFilename = args[1];
//		} else {
//			System.out.println("Two data files must be specified");
//		}

		createTrainingData(trainingFilename);
		createTestData(testFilename);
//		for (Iris iris : trainingIris) {
//			System.out.println(iris.toString());
//		}
//		System.out.println("///////////////////////");
//		for (Iris iris : testingIris) {
//			System.out.println(iris.toString());
//		}
		doClassification();
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
	
	private static void doClassification(){
		double count =0;
		double success =0;
		for (Iris i : testingIris) {
			for (Iris e : trainingIris) {
			double dist = 	EucledianCalc.euclidCalc(i, e);
//			System.out.println("Dist = " + dist);
			Pair p = new Pair(dist,e);
			candidates.add(p);
			}
			Collections.sort(candidates, new PairComparator());
			System.out.println(candidates.size());
			System.out.println(i.toString());
			ArrayList<Pair> sublist = new ArrayList<Pair>();
			for (int j = 0; j < K; j++) {
				sublist.add(candidates.get(j));
			}
			Iris match = doVote(sublist);
			System.out.println(candidates.get(0).i.toString());
			count ++;
			System.out.println("Count"+count);
			System.out.println("Sucess "+success);
			System.out.println("Chosen Dist = "+ candidates.get(0).dist);
			if(i.getClass() == candidates.get(0).i.getClass()){
				success++;
			}
//			System.out.println("///////////////////////////S");
			candidates.clear();
		}
		double rate = success / count;
		System.out.println(rate + "% success");
	}

	private static Iris doVote(List<Pair> list) {
		HashMap<Iris, Integer> voters = new HashMap<Iris, Integer> ();
		//Make a sublist of candidates
//		List<Pair> subList = candidates.subList(0, K);
//		candidates.subList(0, K);
		//from that sublist count how many times a particular class appears
		//The majority wins
		//Return
		for (Pair pair : list) {
			if(! voters.keySet().contains(pair.i)){
				voters.put(pair.i, 1);
			}
			else{
			int x =	voters.remove(pair.i);
			voters.put(pair.i, x+1);
			}
		}
		
		Pair maj = null;
		
		return null;
	}
}
