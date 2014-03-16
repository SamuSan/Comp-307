package flowers;

public class FlowerFactory {
	private static final String IRIS_SETOSA = "Iris-setosa";
	private static final String IRIS_VERSICOLOR = "Iris-versicolor";
	private static final String IRIS_VIRGINICA = "Iris-virginica";

	public FlowerFactory() {

	}

	public static Iris getFlower(String s, double a, double b, double c, double d) {
		if (s.equals(IRIS_SETOSA)) {
			IrisSetosa i =new IrisSetosa(a, b, c, d);
			
			return  i;
		} else if (s.equals(IRIS_VERSICOLOR)) {
			return new IrisVersicolor(a, b, c, d);
		}
		return new IrisVirginica(a, b, c, d);
	}

}
