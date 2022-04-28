package xyz.derkades.derkutils.bukkit;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class HeadTextures {

	private static final Gson GSON = new Gson();
	private static final Map<String, String> HEAD_TEXTURE_CACHE = new HashMap<>();

	public static Optional<String> getHeadTexture(final @NonNull UUID uuid) {
		synchronized (HEAD_TEXTURE_CACHE) {
			if (HEAD_TEXTURE_CACHE.containsKey(uuid.toString())) {
				return Optional.of(HEAD_TEXTURE_CACHE.get(uuid.toString()));
			}
		}

		try {
			final HttpURLConnection connection = (HttpURLConnection) new URL(
					"https://sessionserver.mojang.com/session/minecraft/profile/" + uuid).openConnection();
			try (final Reader reader = new InputStreamReader(connection.getInputStream())) {
				final JsonObject jsonResponse = (JsonObject) JsonParser.parseReader(reader);
				final String texture = jsonResponse.get("properties").getAsJsonArray().get(0).getAsJsonObject()
						.get("value").getAsString();
				synchronized (HEAD_TEXTURE_CACHE) {
					HEAD_TEXTURE_CACHE.put(uuid.toString(), texture);
				}
				return Optional.of(texture);
			}
		} catch (final IOException | IllegalArgumentException | NullPointerException | ClassCastException
				| IllegalStateException | IndexOutOfBoundsException e) {
			return Optional.empty();
		}
	}

	public static void saveCacheToFile(final @NonNull Path path) throws IOException {
		final String json = GSON.toJson(HEAD_TEXTURE_CACHE);
		Files.write(path, json.getBytes(StandardCharsets.UTF_8),
				StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
	}

	public static int readFileToCache(final @NonNull Path path) throws IOException {
		try (Reader reader = Files.newBufferedReader(path)) {
			@SuppressWarnings("unchecked")
			final Map<String, String> map = GSON.fromJson(reader, Map.class);
			synchronized(HEAD_TEXTURE_CACHE) {
				HEAD_TEXTURE_CACHE.putAll(map);
			}
			return map.size();
		} catch (final Exception e) {
			throw new IOException(e);
		}
	}

}
