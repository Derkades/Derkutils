package xyz.derkades.derkutils;

import java.util.concurrent.ThreadLocalRandom;

public class Random {
	
	private static java.util.Random random = new java.util.Random();
	
	public static int getRandomInteger(int min, int max){
		return ThreadLocalRandom.current().nextInt(min, max + 1);
	}
	
	public static boolean getRandomBoolean(){
		int i = getRandomInteger(0, 1);
		return i == 1;
	}
	
	public static double getRandomDouble(){
		return random.nextDouble();
	}
	
	public static float getRandomFloat(){
		return random.nextFloat();
	}
	
}
