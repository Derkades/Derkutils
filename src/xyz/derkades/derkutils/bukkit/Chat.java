package xyz.derkades.derkutils.bukkit;

import java.util.List;
import java.util.Map;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.ComponentBuilder.FormatRetention;
import net.md_5.bungee.api.chat.HoverEvent;

public class Chat {

	/**
	 * TODO example
	 * @param section Location of the message in the config
	 * @return Formatted base component
	 */
	public static BaseComponent[] toComponent(final List<?> configMessageComponentsList) {
		final ComponentBuilder builder = new ComponentBuilder("");

		configMessageComponentsList.stream()
			.filter(s -> s instanceof Map<?, ?>)
			.forEach((s) -> {
				@SuppressWarnings("unchecked")
				final Map<String, String> map = ((Map<String, String>) s);
				if (!map.containsKey("text")) {
					throw new IllegalArgumentException("Every list entry must contain a message");
				}

				builder.append(Colors.toComponent(map.get("text")), FormatRetention.NONE);

				map.entrySet().stream().filter(e -> !e.getKey().equals("text")).forEach(e -> {
					final String k = e.getKey();
					final String v = e.getValue();

					if (k.equals("hover")) {
						builder.event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, Colors.toComponent(v)));
					} else if (k.equals("url")) {
						builder.event(new ClickEvent(ClickEvent.Action.OPEN_URL, v));
					} else if (k.equals("run_command")) {
						builder.event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, v));
					} else if (k.equals("suggest_command")) {
						builder.event(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, v));
					} else if (k.equals("color")) {
						builder.color(ChatColor.valueOf(v.toUpperCase()));
					} else if (k.equals("bold")) {
						builder.bold(Boolean.parseBoolean(v));
					} else if (k.equals("underlined")) {
						builder.underlined(Boolean.parseBoolean(v));
					} else if (k.equals("italic")) {
						builder.italic(Boolean.parseBoolean(v));
					} else if (k.equals("obfuscated")) {
						builder.obfuscated(Boolean.parseBoolean(v));
					} else if (k.equals("insert") || k.equals("chat_append")) {
						builder.insertion(v);
					} else {
						throw new IllegalArgumentException(String.format("Unsupported option for message %s: %s", k, v));
					}
				});
			});
		return builder.create();
	}



}
