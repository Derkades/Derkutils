package xyz.derkades.derkutils.bukkit;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class HeadTextures {

	private static final Map<UUID, String> HEAD_TEXTURE_CACHE = new HashMap<>();

	public static Optional<String> getHeadTexture(final UUID uuid) {
		synchronized (HEAD_TEXTURE_CACHE) {
			if (HEAD_TEXTURE_CACHE.containsKey(uuid)) {
				return Optional.of(HEAD_TEXTURE_CACHE.get(uuid));
			}
		}

		try {
			final HttpURLConnection connection = (HttpURLConnection) new URL(
					"https://sessionserver.mojang.com/session/minecraft/profile/" + uuid.toString()).openConnection();
			try (final Reader reader = new InputStreamReader(connection.getInputStream())) {
				final JsonObject jsonResponse = (JsonObject) JsonParser.parseReader(reader);
				final String texture = jsonResponse.get("properties").getAsJsonArray().get(0).getAsJsonObject()
						.get("value").getAsString();
				synchronized (HEAD_TEXTURE_CACHE) {
					HEAD_TEXTURE_CACHE.put(uuid, texture);
				}
				return Optional.of(texture);
			}
		} catch (final IOException | IllegalArgumentException | NullPointerException | ClassCastException
				| IllegalStateException | IndexOutOfBoundsException e) {
			return Optional.empty();
		}
	}

}
