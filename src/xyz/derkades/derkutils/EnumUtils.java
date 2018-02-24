package xyz.derkades.derkutils;

import java.security.SecureRandom;

public class EnumUtils {
	
	/**
	 * @param clazz
	 * @return Random element from enum
	 */
	public static <T extends Enum<?>> T getRandomEnum(Class<T> clazz){
		final SecureRandom random = new SecureRandom();
        final int x = random.nextInt(clazz.getEnumConstants().length);
        return clazz.getEnumConstants()[x];
    }

}
