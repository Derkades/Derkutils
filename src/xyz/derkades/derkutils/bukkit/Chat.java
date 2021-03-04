package xyz.derkades.derkutils.bukkit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.Validate;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import xyz.derkades.derkutils.bukkit.PlaceholderUtil.Placeholder;

public class Chat {

	/**
	 * TODO example
	 * @param section Location of the message in the config
	 * @return Formatted base component
	 */
	public static BaseComponent[] toComponent(final FileConfiguration config, final String path) {
		Validate.notNull(config, "Provided config is null");
		Validate.notNull(config, "Path is null");
		Validate.notNull(path, "Path is null");
		Validate.notNull(config.getList(path), "Path does not exist in config");

		final List<BaseComponent> message = new ArrayList<>();

		config.getList(path).stream()
			.filter(s -> s instanceof Map<?, ?>)
			.forEach((s) -> {
				@SuppressWarnings("unchecked")
				final Map<String, String> map = (Map<String, String>) s;
				if (!map.containsKey("text")) {
					throw new IllegalArgumentException("Every list entry must contain a message");
				}

				final BaseComponent[] messageParts =
						TextComponent.fromLegacyText(
										Colors.parseColors(map.get("text")));

				for (final Map.Entry<String, String> e : map.entrySet()){
					final String k = e.getKey();
					final String v = e.getValue();

					if (k.equals("text")) {
						continue;
					}

					for (int i = 0; i < messageParts.length; i++) {
						final BaseComponent messagePart = messageParts[i];
						if (k.equals("hover")) {
							messagePart.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
									TextComponent.fromLegacyText(
													Colors.parseColors(v))));
						} else if (k.equals("url")) {
							messagePart.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, v));
						} else if (k.equals("run_command")) {
							messagePart.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, v));
						} else if (k.equals("suggest_command")) {
							messagePart.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, v));
						} else if (k.equals("color")) {
							messagePart.setColor(ChatColor.valueOf(v.toUpperCase()));
						} else if (k.equals("bold")) {
							messagePart.setBold(Boolean.parseBoolean(v));
						} else if (k.equals("underlined")) {
							messagePart.setUnderlined(Boolean.parseBoolean(v));
						} else if (k.equals("italic")) {
							messagePart.setItalic(Boolean.parseBoolean(v));
						} else if (k.equals("obfuscated")) {
							messagePart.setObfuscated(Boolean.parseBoolean(v));
						} else if (k.equals("insert") || k.equals("chat_append")) {
							messagePart.setInsertion(v);
						} else {
							throw new IllegalArgumentException(String.format("Unsupported option for message %s: %s", k, v));
						}
						messageParts[i] = messagePart;
					}
				}
				message.addAll(Arrays.asList(messageParts));
			});
		return message.toArray(new BaseComponent[] {});
	}

	/**
	 * TODO example
	 * @param section Location of the message in the config
	 * @return Formatted base component
	 */
	public static BaseComponent[] toComponentWithPapiPlaceholders(final FileConfiguration config, final String path, final Player player, final Placeholder... placeholders) {
		Validate.notNull(config, "Provided config is null");
		Validate.notNull(path, "Path is null");
		Validate.notNull(player, "Player is null");
		Validate.noNullElements(placeholders, "Provided null placeholder");
		Validate.notNull(config.getList(path), "Path does not exist in config");

		final List<BaseComponent> message = new ArrayList<>();

		config.getList(path).stream()
			.filter(s -> s instanceof Map<?, ?>)
			.forEach((s) -> {
				@SuppressWarnings("unchecked")
				final Map<String, String> map = (Map<String, String>) s;
				if (!map.containsKey("text")) {
					throw new IllegalArgumentException("Every list entry must contain a message");
				}

				final BaseComponent[] messageParts =
						TextComponent.fromLegacyText(
								PlaceholderUtil.parsePapiPlaceholders(
									player, Colors.parseColors(map.get("text")), placeholders));

				for (final Map.Entry<String, String> e : map.entrySet()){
					final String k = e.getKey();
					final String v = e.getValue();

					if (k.equals("text")) {
						continue;
					}

					for (int i = 0; i < messageParts.length; i++) {
						final BaseComponent messagePart = messageParts[i];
						if (k.equals("hover")) {
							messagePart.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
									TextComponent.fromLegacyText(
											PlaceholderUtil.parsePapiPlaceholders(
													player, Colors.parseColors(v), placeholders))));
						} else if (k.equals("url")) {
							messagePart.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL,
									PlaceholderUtil.parsePapiPlaceholders(player, v, placeholders)));
						} else if (k.equals("run_command")) {
							messagePart.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, v));
						} else if (k.equals("suggest_command")) {
							messagePart.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, v));
						} else if (k.equals("color")) {
							messagePart.setColor(ChatColor.valueOf(v.toUpperCase()));
						} else if (k.equals("bold")) {
							messagePart.setBold(Boolean.parseBoolean(v));
						} else if (k.equals("underlined")) {
							messagePart.setUnderlined(Boolean.parseBoolean(v));
						} else if (k.equals("italic")) {
							messagePart.setItalic(Boolean.parseBoolean(v));
						} else if (k.equals("obfuscated")) {
							messagePart.setObfuscated(Boolean.parseBoolean(v));
						} else if (k.equals("insert") || k.equals("chat_append")) {
							messagePart.setInsertion(v);
						} else {
							throw new IllegalArgumentException(String.format("Unsupported option for message %s: %s", k, v));
						}
						messageParts[i] = messagePart;
					}
				}

				message.addAll(Arrays.asList(messageParts));
			});
		return message.toArray(new BaseComponent[] {});
	}

	/**
	 * TODO example
	 * @param section Location of the message in the config
	 * @return Formatted base component
	 */
	public static BaseComponent[] toComponentWithPlaceholders(final FileConfiguration config, final String path, final Placeholder... placeholders) {
		Validate.notNull(config, "Provided config is null");
		Validate.notNull(path, "Path is null");
		Validate.noNullElements(placeholders, "Provided null placeholder");
		Validate.notNull(config.getList(path), "Path does not exist in config");

		final List<BaseComponent> message = new ArrayList<>();

		config.getList(path).stream()
			.filter(s -> s instanceof Map<?, ?>)
			.forEach((s) -> {
				@SuppressWarnings("unchecked")
				final Map<String, String> map = (Map<String, String>) s;
				if (!map.containsKey("text")) {
					throw new IllegalArgumentException("Every list entry must contain a message");
				}

				final BaseComponent[] messageParts =
						TextComponent.fromLegacyText(
								PlaceholderUtil.parsePlaceholders(
										Colors.parseColors(map.get("text")), placeholders));

				for (final Map.Entry<String, String> e : map.entrySet()){
					final String k = e.getKey();
					final String v = e.getValue();

					if (k.equals("text")) {
						continue;
					}

					for (int i = 0; i < messageParts.length; i++) {
						final BaseComponent messagePart = messageParts[i];
						if (k.equals("hover")) {
							messagePart.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
									TextComponent.fromLegacyText(
											PlaceholderUtil.parsePlaceholders(
													Colors.parseColors(v), placeholders))));
						} else if (k.equals("url")) {
							messagePart.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL,
									PlaceholderUtil.parsePlaceholders(v, placeholders)));
						} else if (k.equals("run_command")) {
							messagePart.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, v));
						} else if (k.equals("suggest_command")) {
							messagePart.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, v));
						} else if (k.equals("color")) {
							messagePart.setColor(ChatColor.valueOf(v.toUpperCase()));
						} else if (k.equals("bold")) {
							messagePart.setBold(Boolean.parseBoolean(v));
						} else if (k.equals("underlined")) {
							messagePart.setUnderlined(Boolean.parseBoolean(v));
						} else if (k.equals("italic")) {
							messagePart.setItalic(Boolean.parseBoolean(v));
						} else if (k.equals("obfuscated")) {
							messagePart.setObfuscated(Boolean.parseBoolean(v));
						} else if (k.equals("insert") || k.equals("chat_append")) {
							messagePart.setInsertion(v);
						} else {
							throw new IllegalArgumentException(String.format("Unsupported option for message %s: %s", k, v));
						}
						messageParts[i] = messagePart;
					}
				}
				message.addAll(Arrays.asList(messageParts));
			});
		return message.toArray(new BaseComponent[] {});
	}

}
