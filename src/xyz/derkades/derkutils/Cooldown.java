package xyz.derkades.derkutils;

import java.util.HashMap;
import java.util.Map;

public class Cooldown {
	
	private static final Map<String, Long> COOLDOWNS = new HashMap<>();
	
	public static void addCooldown(String identifier, long durationMillis) {
		COOLDOWNS.put(identifier, System.currentTimeMillis() + durationMillis);
	}
	
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
	
	public static void cleanup() {
		COOLDOWNS.keySet().forEach((key) -> {
			getCooldown(key); //The getCooldown method removes the key from the HashMap if it has expired
		});
	}

}
