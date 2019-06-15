package xyz.derkades.derkutils.bukkit;

import org.bukkit.configuration.ConfigurationSection;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;

public class Chat {

	/**
	 * TODO example
	 * @param section Location of the message in the config
	 * @return Formatted base component
	 */
	public static BaseComponent[] toComponent(final ConfigurationSection section) {
		final ComponentBuilder builder = new ComponentBuilder("");
		for (final String key : section.getKeys(false)){
			builder.append(Colors.toComponent(key));

			if (!section.isConfigurationSection(key)) {
				continue;
			}

			for (final String option : section.getConfigurationSection(key).getKeys(false)) {
				final String value = section.getString(key + "." + option);

				if (option.equals("hover")) {
					builder.event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, Colors.toComponent(value)));
				} else if (option.equals("url")) {
					builder.event(new ClickEvent(ClickEvent.Action.OPEN_URL, value));
				} else if (option.equals("command")) {
					builder.event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, value));
				} else if (option.equals("color")) {
					builder.color(ChatColor.valueOf(value.toUpperCase()));
				} else if (option.equals("bold")) {
					builder.bold(Boolean.parseBoolean(value));
				} else if (option.equals("underlined")) {
					builder.underlined(Boolean.parseBoolean(value));
				} else if (option.equals("italic")) {
					builder.italic(Boolean.parseBoolean(value));
				} else if (option.equals("obfuscated")) {
					builder.obfuscated(Boolean.parseBoolean(value));
				} else {
					throw new IllegalArgumentException(String.format("Unsupported option for message %s: %s", key, option));
				}
			}
		}
		return builder.create();
	}



}
