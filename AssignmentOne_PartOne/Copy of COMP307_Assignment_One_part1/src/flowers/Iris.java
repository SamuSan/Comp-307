package flowers;

public abstract class Iris {

	private double sepalLength;
	private double sepalWidth;
	private double petalLength;
	private double petalWidth;
	private double petalRatio;
	private double sepalRatio;
	private String clazz;

	public Iris(double sepalLength, double sepalWidth, double petalLength,
			double petalWidth) {
		super();
		setSepalLength(sepalLength);
		setSepalWidth(sepalWidth);
		setPetalLength(petalLength);
		setPetalWidth(petalWidth);

	}
	
	public Iris(double sepalLength, double sepalWidth, double petalLength,
			double petalWidth, String clazz) {
		super();
		setSepalLength(sepalLength);
		setSepalWidth(sepalWidth);
		setPetalLength(petalLength);
		setPetalWidth(petalWidth);
		this.petalRatio = petalLength / petalWidth;
		this.sepalRatio = sepalLength / sepalWidth;
		setClazz(clazz);
	}

	public double getSepalLength() {
		return sepalLength;
	}

	public void setSepalLength(double sepalLength) {
		this.sepalLength = sepalLength;
	}

	public double getSepalWidth() {
		return sepalWidth;
	}

	public void setSepalWidth(double sepalWidth) {
		this.sepalWidth = sepalWidth;
	}

	public double getPetalLength() {
		return petalLength;
	}

	public void setPetalLength(double petalLength) {
		this.petalLength = petalLength;
	}

	public double getPetalWidth() {
		return petalWidth;
	}

	public void setPetalWidth(double petalWidth) {
		this.petalWidth = petalWidth;
	}

	public String getClazz() {
		return clazz;
	}

	public void setClazz(String clazz) {
		this.clazz = clazz;
	}

	public double getPetalRatio() {
		return petalRatio;
	}

	public double getSepalRatio() {
		return sepalRatio;
	}

	@Override
	public String toString() {
		return "Iris [sepalLength=" + sepalLength + ", sepalWidth="
				+ sepalWidth + ", petalLength=" + petalLength + ", petalWidth="
				+ petalWidth + ", petalRatio=" + petalRatio + ", sepalRatio="
				+ sepalRatio + ", clazz=" + this.getClass() + "]";
	}



}
