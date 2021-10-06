package xyz.derkades.derkutils.bukkit;

import org.apache.commons.lang3.Validate;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class PlaceholderUtil {

	@NotNull
	public static String parsePlaceholders(@NotNull String string, @NotNull final Placeholder... placeholders) {
		Objects.requireNonNull(string, "input string must not be null");
		Validate.noNullElements(placeholders, "no placeholder must be null");
		for (final Placeholder p : placeholders) {
			string = p.parse(string);
		}
		return string;
	}

	@NotNull
	public static List<String> parsePlaceholders(@NotNull List<String> strings,
												 @NotNull final Placeholder... placeholders) {
		Validate.noNullElements(strings, "placeholder input strings must not be null");
		Validate.noNullElements(placeholders, "no placeholder must be null");
		for (final Placeholder p : placeholders) {
			strings = p.parse(strings);
		}
		return strings;
	}

	@NotNull
	public static String parsePapiPlaceholders(@NotNull final Player player, @NotNull String string,
											   @NotNull final Placeholder... placeholders) {
		Objects.requireNonNull(player, "Player must not be null");
		Objects.requireNonNull(string, "Input string must not be null");
		Validate.noNullElements(placeholders, "Provided strings must not be null");

		for (final Placeholder p : placeholders) {
			string = p.parse(string);
		}

		string = parsePapiPlaceholders(player, string);

		return string;
	}

	@NotNull
	public static List<String> parsePapiPlaceholders(@NotNull final Player player, @NotNull List<String> strings,
													 @NotNull final Placeholder... placeholders) {
		Objects.requireNonNull(player, "Player must not be null");
		Validate.noNullElements(strings, "Input strings must not be null");
		Validate.noNullElements(placeholders, "Provided strings must not be null");

		for (final Placeholder p : placeholders) {
			strings = p.parse(strings);
		}

		strings = parsePapiPlaceholders(player, strings);

		return strings;
	}

	@SuppressWarnings("CanBeFinal")
	private static Method papiParseString = null;
	@SuppressWarnings("CanBeFinal")
	private static Method papiParseList = null;

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
	@NotNull
	public static String parsePapiPlaceholders(@NotNull final Player player, @NotNull final String string) {
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
	@NotNull
	public static List<String> parsePapiPlaceholders(@NotNull final Player player,
													 @NotNull final List<String> strings) {
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

		private final @NotNull String key;
		private final @NotNull String value;

		public Placeholder(@NotNull final String key, @NotNull final String value) {
			this.key = Objects.requireNonNull(key, "Placeholder key is null");
			this.value = Objects.requireNonNull(value, "Placeholder value is null");
		}

		public @NotNull String parse(@NotNull final String string) {
			return string.replace(this.key, this.value);
		}

		public @NotNull List<String> parse(@NotNull final List<String> list) {
			return list.stream().map(this::parse).collect(Collectors.toList());
		}

	}

}
