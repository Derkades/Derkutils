package xyz.derkades.derkutils.bukkit;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * Helper-class for getting UUIDs of players
 */
public class UUIDFetcher {

	/**
	 * @param player The player
	 * @return The UUID of the given player
	 */
	public static UUID getUUID(Player player) {
		return getUUID(player.getName());
	}

	/**
	 * @param playerName The name of the player
	 * @return The UUID of the given player
	 */
	public static UUID getUUID(final String playerName) {
		// First check if the player is online
		for (final Player player : Bukkit.getOnlinePlayers()) {
			if (player.getName().equals(playerName)) {
				return player.getUniqueId();
			}
		}

		final String output = callURL("https://api.mojang.com/users/profiles/minecraft/" + playerName);

		final StringBuilder result = new StringBuilder();

		readData(output, result);

		final String u = result.toString();

		String uuid = "";

		for(int i = 0; i <= 31; i++) {
			if(i >= u.length())
				break;

			uuid = uuid + u.charAt(i);
			if(i == 7 || i == 11 || i == 15 || i == 19) {
				uuid = uuid + "-";
			}
		}

		return UUID.fromString(uuid);
	}

	private static void readData(final String toRead, final StringBuilder result) {
		int i = 7;

		while(i < 200) {
			if(i >= toRead.length())
				break;

			if(!String.valueOf(toRead.charAt(i)).equalsIgnoreCase("\"")) {

				result.append(String.valueOf(toRead.charAt(i)));

			} else {
				break;
			}

			i++;
		}
	}

	private static String callURL(final String URL) {
		final StringBuilder sb = new StringBuilder();
		URLConnection urlConn = null;
		InputStreamReader in = null;
		try {
			final URL url = new URL(URL);
			urlConn = url.openConnection();

			if (urlConn != null) urlConn.setReadTimeout(60 * 1000);

			if (urlConn != null && urlConn.getInputStream() != null) {
				in = new InputStreamReader(urlConn.getInputStream(), Charset.defaultCharset());
				final BufferedReader bufferedReader = new BufferedReader(in);

				if (bufferedReader != null) {
					int cp;

					while ((cp = bufferedReader.read()) != -1) {
						sb.append((char) cp);
					}

					bufferedReader.close();
				}
			}

			in.close();
		} catch (final Exception e) {
			e.printStackTrace();
		}

		return sb.toString();
	}
}
