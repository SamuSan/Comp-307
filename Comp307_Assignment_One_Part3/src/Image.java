public class Image {
	private final int width;
	private final int height;
	private String kind = "";
	private int[][] pix;

	public Image(int[][] pixels, String kind) {
		super();
		this.width = pixels.length;
		this.height = pixels[0].length;
		pix = pixels;
		this.kind = kind;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public boolean getImageFeature(int i, int j) {
		return (pix[i][j] == 1) ? true : false;
	}
}
