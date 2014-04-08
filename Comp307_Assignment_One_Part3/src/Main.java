import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Main {
	private static List<Image> images = new ArrayList<Image>();
	private static List<Feature> features = new ArrayList<Feature>();
	private static List<Double> weights = new ArrayList<Double>();
	private static int seed = 0;
	private static String fileName="";

	public static void main(String[] args) throws IOException {
		 if (args.length == 1) {
			 fileName = args[0];
		 } else {
		 d("At least one data file must be specified");
		 }
		setUp();

		peceptron();
		for (int i = 0; i < features.size(); i++) {
			d("Feature "+i+": Weight: "+weights.get(i)+"\n"+features.get(i));
		}
		d("Weights"+weights);
	}

	private static void peceptron() {
		for (Image i : images) {
			for (Feature f : features) {
				i.checkImageFeature(f);
			}
		}
		doWeightCalc();
	}

	private static void doWeightCalc() {
		int wrong = 0;
		double count = 0;

		do {
			d(weights);
			wrong = 0;
			for (int i = 0; i < images.size(); i++) {
				Image image = images.get(i);
				double sum = 0;

				for (int j = 0; j < features.size(); j++) {
					sum += (image.getFeatureValue(j) * weights.get(j));
				}
				if (image.getType() == 0 && (sum < 0)) {
					for (int j = 1; j < features.size(); j++) {
						double val =weights.get(j)+image.getFeatureValue(j);
						weights.set(j,val);
					}
					wrong++;
				}
				else if (image.getType() == 1 && (sum >= 0)) {
					for (int j = 1; j < features.size(); j++) {
						double val =weights.get(j)-image.getFeatureValue(j);
						weights.set(j,val);
					}
					wrong++;
				}
			}
			count++;

		} while (wrong != 0 );
		System.out.printf("Converged in %f steps\n", count);

	}

	private static void setUp() throws IOException {
		readData(fileName);
		generateFeatures();
	}

	private static void generateFeatures() {
		weights.add(1.0);
		int max = new Random().nextInt(80 - 50) + 50;
		for (int i = 0; i < max; i++) {
			features.add(new Feature(seed));

			double d = new Random().nextDouble()*10;
			weights.add(d);
		}
	}

	public static void readData(String fname) throws IOException {
		String category;
		Scanner scan = new Scanner(new InputStreamReader(
				ClassLoader.getSystemResourceAsStream(fname)));
		String val;
		while (scan.hasNextLine()) {
			val = scan.nextLine();
			if (!val.equals("P1")) {
				System.out.println("Not a P1 PBM file");
			}
			category = scan.nextLine().substring(1);
			String size = scan.nextLine();
			StringTokenizer tokens = new StringTokenizer(size);
			int rows = Integer.valueOf(tokens.nextToken(" "));

			int cols = Integer.valueOf(tokens.nextToken());
			int[][] newImage = new int[rows][cols];
			String pixels = scan.nextLine();
			pixels += scan.nextLine();
			char[] pix = pixels.toCharArray();
			int pos = 0;
			for (int r = 0; r < rows; r++) {
				for (int c = 0; c < cols; c++) {
					newImage[r][c] = pix[pos];
					pos++;
				}
			}
			seed = Math.max(cols, rows);
			Image imge = new Image(newImage, category);
			images.add(imge);
		}
	}

	private static void d(Object o) {
		System.out.println(o);
	}
}