import java.util.ArrayList;
import java.util.List;

public class Image {
	private final int width;
	private final int height;
	private String kind = "";
	private int[][] pix;
	private boolean[][] pixBool;
	private List<Double> featureValues = new ArrayList<Double>();
	public Image(int[][] pixels, String kind) {
		super();
		this.width = pixels.length;
		this.height = pixels[0].length;
		pix = pixels;
		pixBool = new boolean[pixels.length][pix[0].length];
		convPixToBool();
		this.kind = kind;
	}

	private void convPixToBool() {
		for (int i = 0; i < pix.length; i++) {
			for (int j = 0; i < pix[i].length-1; i++) {
				if (pix[i][j] == 1) {
					pixBool[i][j] = true;
				} else {
					pixBool[i][j] = false;
				}
			}
		}
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int checkImageFeature(Feature f) {
		double sum = 0;
		for (int i = 0; i < f.row.length; i++) {
			if (pixelMatch(f.row[i], f.col[i], f.sign[i])) {
				sum++;
			}
			featureValues.add(sum);
		}

		return sum >= 3 ? 1 : 0;
	}

	private boolean pixelMatch(int i, int j, boolean b) {
		if (pixBool[i][j] == b) {
			return true;
		}
		return false;
	}
	public double getFeatureValue(int i){
		return featureValues.get(i);
	}
	public int getType(){
		return kind.equals("Yes") ? 1  :  0;
	}
	public void adjustFeatureWeight(int e,int i){
	featureValues.set(i, featureValues.get(i)+e);	
	}
	@Override
	public String toString(){
		return "Image: "+this.kind+"";
	}
}