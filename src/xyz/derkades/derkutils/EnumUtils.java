package xyz.derkades.derkutils;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.security.SecureRandom;

public class EnumUtils {
	
	/**
	 * @param clazz
	 * @return Random element from enum
	 */
	public static <T extends Enum<?>> @NonNull T getRandomEnum(final @NonNull Class<T> clazz){
		final SecureRandom random = new SecureRandom();
		int size = clazz.getEnumConstants().length;
		if (size == 0) {
			throw new IllegalArgumentException("Cannot get random value from empty enum");
		}
        return clazz.getEnumConstants()[random.nextInt(size)];
    }

}
