package xyz.derkades.derkutils.bukkit;

import java.util.ArrayList;
import java.util.List;

import net.md_5.bungee.api.ChatColor;

public class Colors {
	
	/**
	 * Converts all & characters belonging to a color code in a string list to ChatColor.COLOR_CHAR
	 * @see #parseColors(String)
	 * @param list
	 * @return Converted list
	 */
	public static List<String> parseColors(List<String> list){
		List<String> parsedStrings = new ArrayList<>();
		for (String string : list){
			parsedStrings.add(parseColors(string));
		}
		return parsedStrings;
	}

	/**
	 * Converts all & characters belonging to a color code in a string to ChatColor.COLOR_CHAR
	 * @param string String to convert
	 * @see #parseColors(List)
	 */
	public static String parseColors(String string){
		return ChatColor.translateAlternateColorCodes('&', string);
	}

}
