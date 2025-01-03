package xyz.derkades.derkutils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

import javax.net.ssl.HttpsURLConnection;

import org.checkerframework.checker.nullness.qual.NonNull;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Hastebin {

	private static final Charset UTF_8 = StandardCharsets.UTF_8;

	/**
	 * Creates paste on a hastebin paste site
	 * @param content
	 * @param baseUrl e.g. hastebin.com or paste.rkslot.nl
	 * @return key (full url will be https://hastebin.com/key or https://hastebin.com/raw/key)
	 * @throws IOException
	 */
	public static @NonNull String createPaste(final @NonNull String content,
											  final @NonNull String baseUrl) throws IOException {
		Objects.requireNonNull(content, "Content is null");
		Objects.requireNonNull(baseUrl, "Base url is null");

		return createPaste(content.getBytes(UTF_8), baseUrl);
	}

	/**
	 * Creates paste on a hastebin paste site
	 * @param content
	 * @param baseUrl e.g. hastebin.com or paste.rkslot.nl
	 * @return key (full url will be https://hastebin.com/key or https://hastebin.com/raw/key)
	 * @throws IOException
	 */
	public static @NonNull String createPaste(final byte @NonNull[] content,
											  final @NonNull String baseUrl) throws IOException {
		Objects.requireNonNull(content, "Content is null");
		Objects.requireNonNull(baseUrl, "Base url is null");

		HttpsURLConnection connection;
		try {
			connection = (HttpsURLConnection) new URI("https://" + baseUrl + "/documents").toURL().openConnection();
		} catch (MalformedURLException | URISyntaxException e) {
			throw new IOException(e);
		}
		connection.setRequestMethod("POST");
		connection.setDoOutput(true);
		connection.getOutputStream().write(content);
		try (Reader reader = new InputStreamReader(connection.getInputStream())) {
			final JsonObject json = (JsonObject) JsonParser.parseReader(reader);
			return json.get("key").getAsString();
		}
	}

}
