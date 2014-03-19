package main;

import java.util.Comparator;

public class ProbabilityComparator implements Comparator<Probability> {

	@Override
	public int compare(Probability o1, Probability o2) {
		if(o1.getTotalGain() > o2.getTotalGain()){
			return 1;
		}
		else if(o1.getTotalGain() < o2.getTotalGain()){
			return -1;
		}
		return 0;
	}

}
