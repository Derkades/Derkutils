package xyz.derkades.derkutils.bukkit;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PlaceholderUtil {

	public static String parsePlaceholders(String string, final Placeholder... placeholders) {
		for (final Placeholder p : placeholders) {
			string = p.parse(string);
		}
		return string;
	}

	public static List<String> parsePlaceholders(List<String> strings, final Placeholder... placeholders) {
		for (final Placeholder p : placeholders) {
			strings = p.parse(strings);
		}
		return strings;
	}

	public static String parsePapiPlaceholders(final Player player, String string, final Placeholder... placeholders) {
		for (final Placeholder p : placeholders) {
			string = p.parse(string);
		}

		string = parsePapiPlaceholders(player, string);

		return string;
	}

	public static List<String> parsePapiPlaceholders(final Player player, List<String> strings, final Placeholder... placeholders) {
		for (final Placeholder p : placeholders) {
			strings = p.parse(strings);
		}

		strings = parsePapiPlaceholders(player, strings);

		return strings;
	}

	/**
	 * Parses PlaceholderAPI placeholders in a string if the plugin is installed
	 * @return
	 */
	public static String parsePapiPlaceholders(final Player player, final String string) {
		if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") == null) {
			return string;
		}

		try {
			return (String) Class.forName("me.clip.placeholderapi.PlaceholderAPI")
					.getMethod("setPlaceholders", Player.class, String.class).invoke(null, player, string);
		} catch (final ClassNotFoundException | IllegalAccessException | IllegalArgumentException |
				InvocationTargetException | NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
			return string;
		}
	}

	/**
	 * Parses PlaceholderAPI placeholders in a string if the plugin is installed
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<String> parsePapiPlaceholders(final Player player, final List<String> string) {
		if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") == null) {
			return string;
		}

		try {
			return (List<String>) Class.forName("me.clip.placeholderapi.PlaceholderAPI")
					.getMethod("setPlaceholders", Player.class, List.class).invoke(null, player, string);
		} catch (final ClassNotFoundException | IllegalAccessException | IllegalArgumentException |
				InvocationTargetException | NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
			return string;
		}
	}

	public static class Placeholder {

		private final String key;
		private final String value;

		public Placeholder(final String key, final String value) {
			this.key = key;
			this.value = value;
		}

		public String parse(final String string) {
			return string.replace(this.key, this.value);
		}

		public List<String> parse(final List<String> list) {
			return list.stream().map(this::parse).collect(Collectors.toList());
		}

	}

}
