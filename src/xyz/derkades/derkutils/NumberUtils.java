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

	/**
	 * Round a double. Does not always return the correct values, but it's close.
	 * @param valueToRound
	 * @param numberOfDecimalPlaces
	 * @return
	 */
	public static double roundApprox(final double valueToRound, final int numberOfDecimalPlaces) {
	    final double multipicationFactor = Math.pow(10, numberOfDecimalPlaces);
	    final double interestedInZeroDPs = valueToRound * multipicationFactor;
	    return Math.round(interestedInZeroDPs) / multipicationFactor;
	}

}
