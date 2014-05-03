package comp307_a2_part3_jgapvrs;

import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class TestTrainingSplit {
	public static void main(String[] args) {
		ArrayList<String[]> training = new ArrayList<String[]>();
		ArrayList<String[]> test = new ArrayList<String[]>();
		ArrayList<String[]> benign = new ArrayList<String[]>();
		ArrayList<String[]> malig = new ArrayList<String[]>();
		String file = args[0];
		Scanner scan = new Scanner(new InputStreamReader(
				ClassLoader.getSystemResourceAsStream(file)));

		while (scan.hasNextLine()) {
			String[] s = scan.nextLine().split("\t");

			if (s[9].equals("2")) {
				benign.add(s);
			} else if (s[9].equals("4")) {
				malig.add(s);
			}
			// System.out.println(benign.size()+malig.size());

		}
		double total = benign.size() + malig.size();
		System.out.println("Proportion benign"+(benign.size()/total));
		System.out.println("Proportion malig"+(malig.size()/total));
		System.out.println("Benign size " + benign.size() + " : "
				+ Math.round(benign.size() * .7) + " :: "
				+ Math.round(benign.size() * .3));
		System.out.println("Malig size " + malig.size() + " : "
				+ Math.round(malig.size() * .7) + " :: "
				+ Math.round(malig.size() * .3));
	
		for (int i = 0; i < Math.round(benign.size() * .7); i++) {
			training.add(benign.get(i));
		}
		for (int i = 0; i < Math.round(malig.size() * .7); i++) {
			training.add(malig.get(i));
		}
		double x = Math.round(benign.size() * .3);
		int end = (int)x;
		for (int i = end; i > 0;i--){
			test.add(benign.get(i));
		}
		 x = Math.round(malig.size() * .3);
		 end = (int)x;
		for (int i = end; i > 0;i--){
			test.add(malig.get(i));
		}
		
		
		
try {
	FileWriter trainFile = new FileWriter("BCTraining.txt");
	FileWriter testFile = new FileWriter("BCTest.txt");
	
	for (String[] strings : training) {
		String s = "";
		
		for (String string : strings) {
			s+=string+"\t";
		}
		trainFile.write(s+"\n");
	}
	for (String[] strings : test) {
		String s = "";
		
		for (String string : strings) {
			s+=string+"\t";
		}
		testFile.write(s+"\n");
	}
	
	
	testFile.close();
	trainFile.close();
} catch (Exception e) {
System.out.println(e.toString());
}
		
	}
}
