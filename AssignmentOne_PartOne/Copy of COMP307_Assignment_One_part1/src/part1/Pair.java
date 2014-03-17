package part1;

import flowers.Iris;

public class Pair {
double dist;
Iris i;
String clazz;
double count;
public Pair(double e, Iris x){
	this.dist = e;
	this.i=x;
}
public Pair(String s, double d){
	
}
@Override
public String toString() {
	return "Pair [dist=" + dist + ", i=" + i + "]";
}


}
