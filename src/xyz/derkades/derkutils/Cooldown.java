package xyz.derkades.derkutils;

import java.util.HashMap;
import java.util.Map;

public class Cooldown {
	
	private static final Map<String, Long> COOLDOWNS = new HashMap<>();
	
	/**
	 * Adds a new cooldown with the specified duration. This will override any other cooldowns with the same identifier.
	 * @param identifier
	 * @param durationMillis
	 */
	public static void addCooldown(String identifier, long durationMillis) {
		COOLDOWNS.put(identifier, System.currentTimeMillis() + durationMillis);
	}
	
	/**
	 * Gets the time left for this cooldown. This method will never return a value below 0.
	 * @param identifier
	 * @return Time in milliseconds
	 */
	public static long getCooldown(String identifier) {
		if (!COOLDOWNS.containsKey(identifier)) {
			return 0;
		}
		
		long timeLeft = COOLDOWNS.get(identifier) - System.currentTimeMillis();
		
		if (timeLeft < 0) {
			timeLeft = 0;
		}
		
		if (timeLeft == 0) {
			COOLDOWNS.remove(identifier);
		}
		
		return timeLeft;
	}
	
	/**
	 * Remove all expired cooldowns from memory.
	 */
	public static void cleanup() {
		COOLDOWNS.keySet().forEach((key) -> {
			getCooldown(key); //The getCooldown method removes the key from the HashMap if it has expired
		});
	}

}
