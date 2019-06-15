package xyz.derkades.derkutils.bukkit;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PlaceholderUtil {

	public static String parsePlaceholders(String string, final Player player, final Placeholder... placeholders) {
		for (final Placeholder p : placeholders) string = p.parse(string, player);
		return string;
	}

	public static List<String> parsePlaceholders(List<String> strings, final Player player, final Placeholder... placeholders) {
		for (final Placeholder p : placeholders) {
			strings = p.parse(strings, player);
		}
		return strings;
	}

	public static String parsePapiPlaceholders(String string, final Player player, final Placeholder... placeholders) {
		for (final Placeholder p : placeholders) string = p.parse(string, player);

		string = parsePapiPlaceholders(player, string);

		return string;
	}

	public static List<String> parsePapiPlaceholders(List<String> strings, final Player player, final Placeholder... placeholders) {
		for (final Placeholder p : placeholders) {
			strings = p.parse(strings, player);
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
			throw new RuntimeException(e);
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
					.getMethod("setPlaceholders", Player.class, ((List<String>) new ArrayList<String>()).getClass()).invoke(null, player, string);
		} catch (final ClassNotFoundException | IllegalAccessException | IllegalArgumentException |
				InvocationTargetException | NoSuchMethodException | SecurityException e) {
			throw new RuntimeException(e);
		}
	}

	public static class Placeholder {

		private final String key;
		private final Function<Player, String> parser;

		public Placeholder(final String key, final Function<Player, String> parser) {
			this.key = key;
			this.parser = parser;
		}

		public String parse(final String string, final Player player) {
			return string.replace(this.key, this.parser.apply(player));
		}

		public List<String> parse(final List<String> list, final Player player) {
			final List<String> newList = new ArrayList<>();
			list.stream().map(s -> this.parse(s, player)).forEach(newList::add);
			return newList;
		}

	}

}
