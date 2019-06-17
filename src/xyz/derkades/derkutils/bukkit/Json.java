/* Copyright © 2016 Acquized <Acquized@users.noreply.github.com>
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See the LICENSE.txt file for more details.
 */
package xyz.derkades.derkutils.bukkit;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.json.simple.JSONValue;

/**
 * Simple JSON message creation in minecraft
 * @author Acquized
 * @version 1.0
 */
public class Json {

    private final ArrayList<JsonMessage> messages = new ArrayList<>();

    /**
     * Appends a JsonMessage to the current Json array
     * @param msg JsonMessage
     * @return this class
     */
    public Json append(final JsonMessage msg) {
        this.messages.add(msg);
        return this;
    }

    /**
     * Sets a JsonMessage at the specified index to the Json array
     * @param msg JsonMessage
     * @param index index in the Json array
     * @return this class
     */
    public Json set(final JsonMessage msg, final int index) {
        this.messages.add(index, msg);
        return this;
    }

    /**
     * Sends the Json message to specified players
     * @param players Array or object of a player
     * @return this class
     */
    public Json send(final Player... players) {
        if(this.messages.size() > 0) {
            try {
                for(final Player p : players) {
                    final Object serializer = Class.forName("net.minecraft.server." + Bukkit.getServer().getClass().getPackage().getName().substring(23) + ".ChatSerializer").getMethod("a", String.class).invoke(null, this.toString());
                    final Object packet = Class.forName("net.minecraft.server." + Bukkit.getServer().getClass().getPackage().getName().substring(23) + "PacketPlayOutChat").getConstructor(Class.forName("net.minecraft.server." + Bukkit.getServer().getClass().getPackage().getName().substring(23) + "IChatBaseComponent")).newInstance(serializer);
                    final Object handle = p.getClass().getMethod("getHandle").invoke(p);
                    final Object connection = handle.getClass().getField("playerConnection").get(handle);
                    connection.getClass().getMethod("sendPacket", Class.forName("net.minecraft.server." + Bukkit.getServer().getClass().getPackage().getName().substring(23) + "Packet")).invoke(connection, packet);
                }
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | NoSuchFieldException | ClassNotFoundException | InstantiationException ex) {
                ex.printStackTrace();
            }
        }
        return this;
    }

    /**
     * Sends the Json message to all players
     * @return this class
     */
    public Json send() {
        this.send(Bukkit.getOnlinePlayers().toArray(new Player[Bukkit.getOnlinePlayers().size()]));
        return this;
    }

    /**
     * Converts the current Json array to a string
     * @return JSON string
     */
    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder("[\"\"");
        if(this.messages.size() > 0) {
            for(final JsonMessage jm : this.messages) {
                builder.append(jm.toString());
            }
        }
        return builder.toString() + "]";
    }

    /**
     * Escapes the string to fit into Json values. Used internally.
     * @param text text to escape
     * @return escaped String
     */
    public static String escape(final String text) {
        return JSONValue.escape(text);
    }

    /**
     * Creates a new Json creator
     * @return new Json array creator
     */
    public static Json create() {
        return new Json();
    }

    public static class JsonMessage {

        private String text = "Argument 'text' not found.";

        private ChatColor color = ChatColor.WHITE;
        private ChatFormatting formatting = ChatFormatting.RESET;

        private final Object[] hoverAction = {};
        private final Object[] clickAction = {};

        /**
         * Sets the text in the Json message
         * @param text Text
         * @return this class
         */
        public JsonMessage text(final String text) {
            this.text = text;
            return this;
        }

        /**
         * Sets the text color in the Json message
         * @param color Color
         * @return this class
         */
        public JsonMessage color(final ChatColor color) {
            this.color = color;
            return this;
        }

        /**
         * Sets the formatting of the Json message
         * @param formatting Formatting
         * @return this class
         */
        public JsonMessage formatting(final ChatFormatting formatting) {
            this.formatting = formatting;
            return this;
        }

        /**
         * Sets the hover action of the Json message
         * @param action hover action
         * @param value hover value
         * @return this class
         */
        public JsonMessage onHover(final HoverAction action, final String value) {
            this.hoverAction[0] = action;
            this.hoverAction[1] = value;
            return this;
        }

        /**
         * Sets the click action of the Json message
         * @param action click action
         * @param value click value
         * @return this class
         */
        public JsonMessage onClick(final ClickAction action, final String value) {
            this.clickAction[0] = action;
            this.clickAction[1] = value;
            return this;
        }

        /**
         * Converts the message in a string which can be appended in the json array
         * @return Json string
         */
        @Override
        public String toString() {
            final StringBuilder builder = new StringBuilder(",{\"text\":\"" + Json.escape(this.text) + "\",\"color\":\"" + this.color.name().toLowerCase() + "\",");
            if(this.formatting != ChatFormatting.RESET) {
                builder.append("\"").append(this.formatting.color.name().toLowerCase()).append("\":true,");
            }
            if(this.clickAction.length > 0) {
                builder.append("\"clickEvent\":{\"action\":\"").append(((ClickAction) this.clickAction[0]).name().toLowerCase()).append("\",\"value\":\"").append(Json.escape((String) this.clickAction[1])).append("\"},");
            }
            if(this.hoverAction.length > 0) {
                if(this.hoverAction[0] != HoverAction.SHOW_TEXT) {
                    builder.append("\"hoverEvent\":{\"action\":\"").append(((HoverAction) this.hoverAction[0]).name().toLowerCase()).append("\",\"value\":\"").append(this.hoverAction[1]).append("\"},");
                } else {
                    builder.append("\"hoverEvent\":{\"action\":\"").append(((HoverAction) this.hoverAction[0]).name().toLowerCase()).append("\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"").append(Json.escape((String) this.hoverAction[1])).append("\"}]}},");
                }
            }
            String json = builder.toString();
            if(json.endsWith(",")) {
                json = json.substring(0, json.length() - 1);
            }
            return json + "}";
        }

        /**
         * Creates new Json message
         * @return new Json message
         */
        public static JsonMessage newMessage() {
            return new JsonMessage();
        }

    }

    public static class ChatFormatting {

        public static final ChatFormatting ITALIC = new ChatFormatting(ChatColor.ITALIC);
        public static final ChatFormatting OBFUSCATED = new ChatFormatting(ChatColor.MAGIC);
        public static final ChatFormatting BOLD = new ChatFormatting(ChatColor.BOLD);
        public static final ChatFormatting STRIKETHROUGH = new ChatFormatting(ChatColor.STRIKETHROUGH);
        public static final ChatFormatting UNDERLINE = new ChatFormatting(ChatColor.UNDERLINE);
        public static final ChatFormatting RESET = new ChatFormatting(ChatColor.RESET);

        private final ChatColor color;

        public ChatFormatting(final ChatColor color) {
            this.color = color;
        }

        public ChatColor getColor() {
            return this.color;
        }

    }

    public enum HoverAction {
        SHOW_TEXT, SHOW_ACHIEVEMENT, SHOW_ITEM, SHOW_ENTITY
    }

    public enum ClickAction {
        OPEN_URL, OPEN_FILE, RUN_COMMAND, SUGGEST_COMMAND, CHANGE_PAGE
    }

}