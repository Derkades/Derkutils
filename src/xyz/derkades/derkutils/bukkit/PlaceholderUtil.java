package xyz.derkades.derkutils.bukkit;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.commons.lang3.Validate;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PlaceholderUtil {

	public static String parsePlaceholders(String string, final Placeholder... placeholders) {
		Objects.requireNonNull(string, "input string must not be null");
		Validate.noNullElements(placeholders, "no placeholder must be null");
		for (final Placeholder p : placeholders) {
			string = p.parse(string);
		}
		return string;
	}

	public static List<String> parsePlaceholders(List<String> strings, final Placeholder... placeholders) {
		Validate.noNullElements(strings, "placeholder input strings must not be null");
		Validate.noNullElements(placeholders, "no placeholder must be null");
		for (final Placeholder p : placeholders) {
			strings = p.parse(strings);
		}
		return strings;
	}

	public static String parsePapiPlaceholders(final Player player, String string, final Placeholder... placeholders) {
		Objects.requireNonNull(player, "Player must not be null");
		Objects.requireNonNull(string, "Input string must not be null");
		Validate.noNullElements(placeholders, "Provided strings must not be null");

		for (final Placeholder p : placeholders) {
			string = p.parse(string);
		}

		string = parsePapiPlaceholders(player, string);

		return string;
	}

	public static List<String> parsePapiPlaceholders(final Player player, List<String> strings, final Placeholder... placeholders) {
		Objects.requireNonNull(player, "Player must not be null");
		Validate.noNullElements(strings, "Input strings must not be null");
		Validate.noNullElements(placeholders, "Provided strings must not be null");

		for (final Placeholder p : placeholders) {
			strings = p.parse(strings);
		}

		strings = parsePapiPlaceholders(player, strings);

		return strings;
	}

	private static final Method papiParseString = null;
	private static final Method papiParseList = null;

	static {
		if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
			try {
				final Class<?>c = Class.forName("me.clip.placeholderapi.PlaceholderAPI");
				papiParseString = c.getMethod("setPlaceholders", Player.class, String.class);
				papiParseList = c.getMethod("setPlaceholders", Player.class, List.class);
			} catch (final ClassNotFoundException | IllegalArgumentException |
					SecurityException | NoSuchMethodException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Parses PlaceholderAPI placeholders in a string if the plugin is installed
	 * @return
	 */
	public static String parsePapiPlaceholders(final Player player, final String string) {
		Objects.requireNonNull(player, "Player must not be null");
		Objects.requireNonNull(string, "Provided string must not be null");

		if (papiParseString == null) {
			return string;
		} else {
			try {
				return (String) papiParseString.invoke(null, player, string);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				e.printStackTrace();
				return string;
			}
		}
	}

	/**
	 * Parses PlaceholderAPI placeholders in a string if the plugin is installed
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<String> parsePapiPlaceholders(final Player player, final List<String> strings) {
		Objects.requireNonNull(player, "Player must not be null");
		Objects.requireNonNull(strings, "Provided string list must not be null");

		if (papiParseList == null) {
			return strings;
		} else {
			try {
				return (List<String>) papiParseList.invoke(null, player, strings);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				e.printStackTrace();
				return strings;
			}
		}
	}

	public static class Placeholder {

		private final String key;
		private final String value;

		public Placeholder(final String key, final String value) {
			this.key = Objects.requireNonNull(key, "Placeholder key is null");
			this.value = Objects.requireNonNull(value, "Placeholder value is null");
		}

		public String parse(final String string) {
			return string.replace(this.key, this.value);
		}

		public List<String> parse(final List<String> list) {
			return list.stream().map(this::parse).collect(Collectors.toList());
		}

	}

}
