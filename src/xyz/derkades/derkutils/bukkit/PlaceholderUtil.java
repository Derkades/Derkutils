package xyz.derkades.derkutils.bukkit;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class PlaceholderUtil {

	public static @NonNull String parsePlaceholders(@NonNull String string,
													final @NonNull Placeholder... placeholders) {
		Objects.requireNonNull(string, "input string must not be null");
		Objects.requireNonNull(placeholders, "placeholders varargs is null");
		for (final Placeholder p : placeholders) {
			string = Objects.requireNonNull(p, "a placeholder is null").parse(string);
		}
		return string;
	}

	public static @NonNull String parsePlaceholders(@NonNull String string,
													final @NonNull Collection<Placeholder> placeholders) {
		Objects.requireNonNull(string, "input string must not be null");
		Objects.requireNonNull(placeholders, "placeholders collection is null");
		for (final Placeholder p : placeholders) {
			string = Objects.requireNonNull(p, "a placeholder is null").parse(string);
		}
		return string;
	}

	public static @NonNull List<String> parsePlaceholders(@NonNull List<String> strings,
														  final @NonNull Placeholder... placeholders) {
		Objects.requireNonNull(strings, "strings list is null");
		Objects.requireNonNull(placeholders, "placeholders array is null");
		for (final Placeholder p : placeholders) {
			strings = Objects.requireNonNull(p, "a placeholder is null").parse(strings);
		}
		return strings;
	}

	public static @NonNull List<String> parsePlaceholders(@NonNull List<String> strings,
														  final @NonNull Collection<Placeholder> placeholders) {
		Objects.requireNonNull(strings, "strings list is null");
		Objects.requireNonNull(placeholders, "placeholders collection is null");
		for (final Placeholder p : placeholders) {
			strings = Objects.requireNonNull(p, "a placeholder is null").parse(strings);
		}
		return strings;
	}


	public static @NonNull String parsePapiPlaceholders(final @NonNull Player player,
														@NonNull String string,
														final @NonNull Placeholder... placeholders) {
		Objects.requireNonNull(player, "Player must not be null");
		Objects.requireNonNull(string, "Input string must not be null");
		Objects.requireNonNull(placeholders, "Placeholders varargs is null");

		for (final Placeholder p : placeholders) {
			string = Objects.requireNonNull(p, "a placeholder is null").parse(string);
		}

		string = parsePapiPlaceholders(player, string);

		return string;
	}


	public static @NonNull String parsePapiPlaceholders(final @NonNull Player player,
														@NonNull String string,
														final @NonNull Collection<Placeholder> placeholders) {
		Objects.requireNonNull(player, "Player must not be null");
		Objects.requireNonNull(string, "Input string must not be null");
		Objects.requireNonNull(placeholders, "Placeholders collection is null");

		for (final Placeholder p : placeholders) {
			string = Objects.requireNonNull(p, "a placeholder is null").parse(string);
		}

		string = parsePapiPlaceholders(player, string);

		return string;
	}


	public static @NonNull List<@NonNull String> parsePapiPlaceholders(final @NonNull Player player,
																	   @NonNull List<@NonNull String> strings,
																	   final @NonNull Placeholder... placeholders) {
		Objects.requireNonNull(player, "Player must not be null");
		Objects.requireNonNull(placeholders, "Placeholders array is null");

		for (final Placeholder p : placeholders) {
			strings = Objects.requireNonNull(p, "A placeholder is null").parse(strings);
		}

		strings = parsePapiPlaceholders(player, strings);

		return strings;
	}


	public static @NonNull List<@NonNull String> parsePapiPlaceholders(final @NonNull Player player,
																	   @NonNull List<@NonNull String> strings,
																	   final @NonNull Collection<Placeholder> placeholders) {
		Objects.requireNonNull(player, "Player must not be null");
		Objects.requireNonNull(placeholders, "Placeholders collection is null");

		for (final Placeholder p : placeholders) {
			strings = Objects.requireNonNull(p, "A placeholder is null").parse(strings);
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
	public static @NonNull String parsePapiPlaceholders(final @NonNull Player player,
														final @NonNull String string) {
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
	public static @NonNull List<String> parsePapiPlaceholders(
			final @NonNull Player player,
			final @NonNull List<String> strings) {
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

		private final @NonNull String key;
		private final @NonNull String value;

		public Placeholder(final @NonNull String key, final @NonNull String value) {
			this.key = Objects.requireNonNull(key, "Placeholder key is null");
			this.value = Objects.requireNonNull(value, "Placeholder value is null");
		}

		public @NonNull String parse(final @NonNull String string) {
			return string.replace(this.key, this.value);
		}

		public @NonNull List<String> parse(final @NonNull List<String> list) {
			return list.stream().map(this::parse).collect(Collectors.toList());
		}

	}

}
