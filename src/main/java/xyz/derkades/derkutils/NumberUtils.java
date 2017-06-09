package xyz.derkades.derkutils;

import java.util.List;

public class NumberUtils {
	
	public static double calculateAverage(List<Double> list){
		double total = 0;
		for (double d : list){
			total += d;
		}
		
		return total / list.size();
	}

}
