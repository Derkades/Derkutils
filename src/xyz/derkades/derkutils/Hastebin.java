package xyz.derkades.derkutils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

import javax.net.ssl.HttpsURLConnection;

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
	public static String createPaste(final String content, final String baseUrl) throws IOException {
		Objects.requireNonNull(content, "Content is null");
		Objects.requireNonNull(baseUrl, "Base url is null");

		return createPaste(content.toString().getBytes(UTF_8), baseUrl);
	}

	/**
	 * Creates paste on a hastebin paste site
	 * @param content
	 * @param baseUrl e.g. hastebin.com or paste.rkslot.nl
	 * @return key (full url will be https://hastebin.com/key or https://hastebin.com/raw/key)
	 * @throws IOException
	 */
	public static String createPaste(final byte[] content, final String baseUrl) throws IOException {
		Objects.requireNonNull(content, "Content is null");
		Objects.requireNonNull(baseUrl, "Base url is null");

		final HttpsURLConnection connection = (HttpsURLConnection) new URL("https://" + baseUrl + "/documents").openConnection();
		connection.setRequestMethod("POST");
		connection.setDoOutput(true);
		connection.getOutputStream().write(content);
		try (Reader reader = new InputStreamReader(connection.getInputStream())) {
			final JsonObject json = (JsonObject) JsonParser.parseReader(reader);
			return json.get("key").getAsString();
		}
	}

}
