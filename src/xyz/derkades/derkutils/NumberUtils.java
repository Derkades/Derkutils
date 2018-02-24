package xyz.derkades.derkutils;

import java.util.List;

public class NumberUtils {
	
	/**
	 * Calculates the average of all doubles in a list.
	 * @param list
	 * @return
	 */
	public static double calculateAverage(final List<Double> list){
		double total = 0;
		for (final double d : list){
			total += d;
		}
		
		return total / list.size();
	}

}
