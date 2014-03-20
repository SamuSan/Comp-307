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
	private static int seed =0;
	private final static String fileName = "image.data";

	public static void main(String[] args) throws IOException  {
		setUp();
	for (Feature f : features) {
		d(f.toString());
	}
peceptron();
	}

	private static void peceptron() {
		for (Image i : images) {
			for (Feature f : features) {
				if(f.weight(i) == 0){
					d("Awesome cuntsticks");
				}
				else{
					d("Fucksticks");
				}
			}
		}
		
	}

	private static void setUp() throws IOException {
		readData(fileName);
		d(images.size());
		generateFeatures();
	}

	private static void generateFeatures() {
	int max = new Random().nextInt(80-50) + 50;
		for (int i = 0; i < max; i++) {
			features.add(new Feature(seed));
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
d(category);
			int rows = Integer.valueOf(tokens.nextToken(" "));
			d(rows);
			
			int cols = Integer.valueOf(tokens.nextToken());
d(cols);
			int [][] newImage = new int [rows][cols];
			String pixels = scan.nextLine();
			pixels+=scan.nextLine();
			char[] pix = pixels.toCharArray();
			d(pix.length);
			int pos =0;
			for (int r = 0; r < rows; r++) {
				for (int c = 0; c < cols; c++) {
					newImage[r][c] =pix[pos];
					pos++;
				}
			}
			seed = Math.max(cols, rows);
			Image imge = new Image(newImage,category );
			images.add(imge);
		}
	}

	private static void d(Object o) {
		System.out.println(o);
	}
}