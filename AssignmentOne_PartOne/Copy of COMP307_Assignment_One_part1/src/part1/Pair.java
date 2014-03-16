package part1;

import flowers.Iris;

public class Pair {
double dist;
Iris i;
public Pair(double e, Iris x){
	this.dist = e;
	this.i=x;
}
@Override
public String toString() {
	return "Pair [dist=" + dist + ", i=" + i + "]";
}


}
