package xyz.derkades.derkutils;

import org.jetbrains.annotations.NotNull;

import java.security.SecureRandom;

public class EnumUtils {
	
	/**
	 * @param clazz
	 * @return Random element from enum
	 */
	@NotNull
	public static <T extends Enum<?>> T getRandomEnum(@NotNull Class<T> clazz){
		final SecureRandom random = new SecureRandom();
		int size = clazz.getEnumConstants().length;
		if (size == 0) {
			throw new IllegalArgumentException("Cannot get random value from empty enum");
		}
        return clazz.getEnumConstants()[random.nextInt(size)];
    }

}
