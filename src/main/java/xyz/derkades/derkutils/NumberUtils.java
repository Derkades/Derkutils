package xyz.derkades.derkutils;

import java.util.List;

public class NumberUtils {
	
	/**
	 * Calculates the average of all doubles in the list.
	 * @param list
	 * @return
	 */
	public static double calculateAverage(List<Double> list){
		double total = 0;
		for (double d : list){
			total += d;
		}
		
		return total / list.size();
	}

}
