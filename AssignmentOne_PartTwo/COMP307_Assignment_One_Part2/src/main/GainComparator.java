package main;

import java.util.Comparator;

public class GainComparator implements Comparator<InformationGain> {

	@Override
	public int compare(InformationGain o1, InformationGain o2) {
		if(o1.getTotalGain() > o2.getTotalGain()){
			return 1;
		}
		else if(o1.getTotalGain() < o2.getTotalGain()){
			return -1;
		}
		return 0;
	}

}
