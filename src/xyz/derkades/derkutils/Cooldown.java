package xyz.derkades.derkutils;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Cooldown {

	private static final Map<String, Long> COOLDOWNS = new HashMap<>();

	/**
	 * Adds a new cooldown with the specified duration. This will override any other cooldowns with the same identifier.
	 * @param identifier
	 * @param durationMillis
	 */
	public static void addCooldown(final String identifier, final long durationMillis) {
		Objects.requireNonNull(identifier, "Identifier is null");
		COOLDOWNS.put(identifier, System.currentTimeMillis() + durationMillis);
	}

	/**
	 * Gets the time left for this cooldown. This method will never return a value below 0.
	 * @param identifier
	 * @return Time in milliseconds
	 */
	public static long getCooldown(final String identifier) {
		Objects.requireNonNull(identifier, "Identifier is null");

		if (!COOLDOWNS.containsKey(identifier)) {
			return 0;
		}

		final long timeLeft = COOLDOWNS.get(identifier) - System.currentTimeMillis();

		if (timeLeft <= 0) {
			COOLDOWNS.remove(identifier);
		}

		return timeLeft < 0 ? 0 : timeLeft;
	}

	/**
	 * Remove all expired cooldowns from memory.
	 */
	public static void cleanup() {
		// The getCooldown method removes the key from the HashMap if it has expired
		COOLDOWNS.keySet().forEach(Cooldown::getCooldown);
	}

}
