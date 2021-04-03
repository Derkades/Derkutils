package xyz.derkades.derkutils.bukkit;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.bukkit.ChatColor;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;

public class Colors {

	/**
	 * Converts all & characters belonging to a color code in a string list to ChatColor.COLOR_CHAR
	 * @see #parseColors(String)
	 * @param list
	 * @return Converted list
	 */
	public static List<String> parseColors(final List<String> list) {
		Objects.requireNonNull(list, "List is null");
		return list.stream().map(Colors::parseColors).collect(Collectors.toList());
	}

	/**
	 * Converts all & characters belonging to a color code in a string to ChatColor.COLOR_CHAR
	 * @param string String to convert
	 * @see #parseColors(List)
	 */
	public static String parseColors(final String string) {
		Objects.requireNonNull(string, "String is null");
		return ChatColor.translateAlternateColorCodes('&', string);
	}

	/**
	 * Parse colors and convert to BaseComponent[]
	 * @param string
	 * @return
	 */
	public static BaseComponent[] toComponent(final String string) {
		Objects.requireNonNull(string, "String is null");
		return TextComponent.fromLegacyText(Colors.parseColors(string));
	}

	/**
	 * Converts all & characters belonging to a color code in a string to ChatColor.COLOR_CHAR, and
	 * then uses {@link ChatColor#stripColor} to remove all color codes.
	 * @param string String to convert
	 * @deprecated Use {@link #stripColorCodes(String)}
	 */
	@Deprecated
	public static String stripColors(final String string) {
		Objects.requireNonNull(string, "String is null");
		return ChatColor.stripColor(Colors.parseColors(string));
	}

	/**
	 * Converts all & characters belonging to a color code in a string to ChatColor.COLOR_CHAR, and
	 * then uses {@link ChatColor#stripColor} to remove all color codes.
	 * @param string String to convert
	 */
	public static String stripColorCodes(final String string) {
		Objects.requireNonNull(string, "String is null");
		return ChatColor.stripColor(Colors.parseColors(string));
	}

}
