package xyz.derkades.derkutils;

import java.util.concurrent.ThreadLocalRandom;

public class Random {
	
	/**
	 * @see #getRandomDouble()
	 * @see #getRandomFloat()
	 * @param min
	 * @param max
	 * @return A random integer between min (inclusive) and max (inclusive)
	 */
	public static int getRandomInteger(final int min, final int max){
		if (min == max) {
			return min;
		}
		
		if (max < min) {
			throw new IllegalArgumentException("Maximum cannot be less than minumum");
		}
		
		return ThreadLocalRandom.current().nextInt(min, max + 1);
	}
	
	/**
	 * @return True or false, both with 50% chance.
	 */
	public static boolean getRandomBoolean(){
		return ThreadLocalRandom.current().nextBoolean();
	}
	
	/**
	 * @see #getRandomFloat()
	 * @see #getRandomInteger(int, int)
	 * @return A value between 0.0 (inclusive) and 1.0 (exclusive)
	 */
	public static double getRandomDouble(){
		return ThreadLocalRandom.current().nextDouble();
	}
	
	/**
	 * @param min
	 * @param max
	 * @return A random number between min (inclusive) and max (exclusive)
	 */
	public static double getRandomDouble(final double min, final double max) {
		if (min == max) {
			return min;
		}
		
		if (max < min) {
			throw new IllegalArgumentException("Maximum cannot be less than minumum");
		}
		
		return ThreadLocalRandom.current().nextDouble(min, max);
	}
	
	/**
	 * @see #getRandomDouble()
	 * @see #getRandomInteger(int, int)
	 * @return A random value between 0.0 (inclusive) and 1.0 (exclusive)
	 */
	public static float getRandomFloat(){
		return ThreadLocalRandom.current().nextFloat();
	}
	
}
