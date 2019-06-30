package xyz.derkades.derkutils.bukkit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

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
		final List<BaseComponent> message = new ArrayList<>();

		config.getList(path).stream()
			.filter(s -> s instanceof Map<?, ?>)
			.forEach((s) -> {
				@SuppressWarnings("unchecked")
				final Map<String, String> map = ((Map<String, String>) s);
				if (!map.containsKey("text")) {
					throw new IllegalArgumentException("Every list entry must contain a message");
				}

				final List<BaseComponent> messagePartComponents = Arrays.asList(Colors.toComponent(map.get("text")));

				map.entrySet().stream().filter(e -> !e.getKey().equals("text")).forEach(e -> {
					final String k = e.getKey();
					final String v = e.getValue();

					final List<BaseComponent> newMessagePartComponents = new ArrayList<>();

					for (final BaseComponent messagePartComponent : messagePartComponents) {
						if (k.equals("hover")) {
							messagePartComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, Colors.toComponent(v)));
						} else if (k.equals("url")) {
							messagePartComponent.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, v));
						} else if (k.equals("run_command")) {
							messagePartComponent.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, v));
						} else if (k.equals("suggest_command")) {
							messagePartComponent.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, v));
						} else if (k.equals("color")) {
							messagePartComponent.setColor(ChatColor.valueOf(v.toUpperCase()));
						} else if (k.equals("bold")) {
							messagePartComponent.setBold(Boolean.parseBoolean(v));
						} else if (k.equals("underlined")) {
							messagePartComponent.setUnderlined(Boolean.parseBoolean(v));
						} else if (k.equals("italic")) {
							messagePartComponent.setItalic(Boolean.parseBoolean(v));
						} else if (k.equals("obfuscated")) {
							messagePartComponent.setObfuscated(Boolean.parseBoolean(v));
						} else if (k.equals("insert") || k.equals("chat_append")) {
							messagePartComponent.setInsertion(v);
						} else {
							throw new IllegalArgumentException(String.format("Unsupported option for message %s: %s", k, v));
						}
					}

					messagePartComponents.clear();
					messagePartComponents.addAll(newMessagePartComponents);
				});
				message.addAll(messagePartComponents);
			});
		return message.toArray(new BaseComponent[] {});
	}

	/**
	 * TODO example
	 * @param section Location of the message in the config
	 * @return Formatted base component
	 */
	public static BaseComponent[] toComponentWithPapiPlaceholders(final FileConfiguration config, final String path, final Player player, final Placeholder... placeholders) {
		final List<BaseComponent> message = new ArrayList<>();

		config.getList(path).stream()
			.filter(s -> s instanceof Map<?, ?>)
			.forEach((s) -> {
				@SuppressWarnings("unchecked")
				final Map<String, String> map = ((Map<String, String>) s);
				if (!map.containsKey("text")) {
					throw new IllegalArgumentException("Every list entry must contain a message");
				}

				final List<BaseComponent> messagePartComponents = Arrays.asList(
						TextComponent.fromLegacyText(
								PlaceholderUtil.parsePapiPlaceholders(
										Colors.parseColors(map.get("text")), player, placeholders)));

				map.entrySet().stream().filter(e -> !e.getKey().equals("text")).forEach(e -> {
					final String k = e.getKey();
					final String v = e.getValue();

					final List<BaseComponent> newMessagePartComponents = new ArrayList<>();

					for (final BaseComponent messagePartComponent : messagePartComponents) {
						if (k.equals("hover")) {
							messagePartComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
									TextComponent.fromLegacyText(
											PlaceholderUtil.parsePapiPlaceholders(
													Colors.parseColors(v), player, placeholders))));
						} else if (k.equals("url")) {
							messagePartComponent.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL,
									PlaceholderUtil.parsePapiPlaceholders(v, player, placeholders)));
						} else if (k.equals("run_command")) {
							messagePartComponent.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, v));
						} else if (k.equals("suggest_command")) {
							messagePartComponent.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, v));
						} else if (k.equals("color")) {
							messagePartComponent.setColor(ChatColor.valueOf(v.toUpperCase()));
						} else if (k.equals("bold")) {
							messagePartComponent.setBold(Boolean.parseBoolean(v));
						} else if (k.equals("underlined")) {
							messagePartComponent.setUnderlined(Boolean.parseBoolean(v));
						} else if (k.equals("italic")) {
							messagePartComponent.setItalic(Boolean.parseBoolean(v));
						} else if (k.equals("obfuscated")) {
							messagePartComponent.setObfuscated(Boolean.parseBoolean(v));
						} else if (k.equals("insert") || k.equals("chat_append")) {
							messagePartComponent.setInsertion(v);
						} else {
							throw new IllegalArgumentException(String.format("Unsupported option for message %s: %s", k, v));
						}
					}

					messagePartComponents.clear();
					messagePartComponents.addAll(newMessagePartComponents);
				});
				message.addAll(messagePartComponents);
			});
		return message.toArray(new BaseComponent[] {});
	}

	/**
	 * TODO example
	 * @param section Location of the message in the config
	 * @return Formatted base component
	 */
	public static BaseComponent[] toComponentWithPlaceholders(final FileConfiguration config, final String path, final Placeholder... placeholders) {
		final List<BaseComponent> message = new ArrayList<>();

		config.getList(path).stream()
			.filter(s -> s instanceof Map<?, ?>)
			.forEach((s) -> {
				@SuppressWarnings("unchecked")
				final Map<String, String> map = ((Map<String, String>) s);
				if (!map.containsKey("text")) {
					throw new IllegalArgumentException("Every list entry must contain a message");
				}

				final List<BaseComponent> messagePartComponents = Arrays.asList(
						TextComponent.fromLegacyText(
								PlaceholderUtil.parsePlaceholders(
										Colors.parseColors(map.get("text")), placeholders)));

				map.entrySet().stream().filter(e -> !e.getKey().equals("text")).forEach(e -> {
					final String k = e.getKey();
					final String v = e.getValue();

					final List<BaseComponent> newMessagePartComponents = new ArrayList<>();

					for (final BaseComponent messagePartComponent : messagePartComponents) {
						if (k.equals("hover")) {
							messagePartComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
									TextComponent.fromLegacyText(
											PlaceholderUtil.parsePlaceholders(
													Colors.parseColors(v), placeholders))));
						} else if (k.equals("url")) {
							messagePartComponent.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL,
									PlaceholderUtil.parsePlaceholders(v, placeholders)));
						} else if (k.equals("run_command")) {
							messagePartComponent.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, v));
						} else if (k.equals("suggest_command")) {
							messagePartComponent.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, v));
						} else if (k.equals("color")) {
							messagePartComponent.setColor(ChatColor.valueOf(v.toUpperCase()));
						} else if (k.equals("bold")) {
							messagePartComponent.setBold(Boolean.parseBoolean(v));
						} else if (k.equals("underlined")) {
							messagePartComponent.setUnderlined(Boolean.parseBoolean(v));
						} else if (k.equals("italic")) {
							messagePartComponent.setItalic(Boolean.parseBoolean(v));
						} else if (k.equals("obfuscated")) {
							messagePartComponent.setObfuscated(Boolean.parseBoolean(v));
						} else if (k.equals("insert") || k.equals("chat_append")) {
							messagePartComponent.setInsertion(v);
						} else {
							throw new IllegalArgumentException(String.format("Unsupported option for message %s: %s", k, v));
						}
					}

					messagePartComponents.clear();
					messagePartComponents.addAll(newMessagePartComponents);
				});
				message.addAll(messagePartComponents);
			});
		return message.toArray(new BaseComponent[] {});
	}

}