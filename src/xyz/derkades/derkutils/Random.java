package xyz.derkades.derkutils;

import java.util.concurrent.ThreadLocalRandom;

public class Random {
	
	private static java.util.Random random = new java.util.Random();
	
	/**
	 * @see #getRandomDouble()
	 * @see #getRandomFloat()
	 * @param min
	 * @param max
	 * @return A random integer between min (inclusive) and max (inclusive)
	 */
	public static int getRandomInteger(int min, int max){
		return ThreadLocalRandom.current().nextInt(min, max + 1);
	}
	
	/**
	 * @return True or false, both with 50% chance.
	 */
	public static boolean getRandomBoolean(){
		int i = getRandomInteger(0, 1);
		return i == 1;
	}
	
	/**
	 * @see #getRandomFloat()
	 * @see #getRandomInteger(int, int)
	 * @return A value between 0.0 (inclusive) and 1.0 (exclusive)
	 */
	public static double getRandomDouble(){
		return random.nextDouble();
	}
	
	/**
	 * @see #getRandomDouble()
	 * @see #getRandomInteger(int, int)
	 * @return A random value between 0.0 (inclusive) and 1.0 (exclusive)
	 */
	public static float getRandomFloat(){
		return random.nextFloat();
	}
	
}
