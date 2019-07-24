package de.tr7zw.nbtapi.utils;

import static de.tr7zw.nbtapi.utils.MinecraftVersion.logger;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import java.util.logging.Level;
import java.util.zip.GZIPOutputStream;

import javax.net.ssl.HttpsURLConnection;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.ServicePriority;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * bStats collects some data for plugin authors.
 * <p>
 * Check out https://bStats.org/ to learn more about bStats!
 * 
 * This class is modified by tr7zw to work when the api is shaded into other peoples plugins.
 */
public class ApiMetricsLite {

	private static final String PLUGINNAME = "ItemNBTAPI"; // DO NOT CHANGE THE NAME! else it won't link the data on bStats
	private static final String PLUGINVERSION = "2.0.0-Derkutils"; // In case you fork the nbt-api for internal use in your network, plugins and so on, you *may* add that to the version here. (2.x.x-Timolia or something like that?)
	// Not sure how good of an idea that is, so maybe just leave it as is ¯\_(ツ)_/¯

	// The version of this bStats class
	public static final int B_STATS_VERSION = 1;

	// The version of the NBT-Api bStats
	public static final int NBT_BSTATS_VERSION = 1;

	// The url to which the data is sent
	private static final String URL = "https://bStats.org/submitData/bukkit";

	// Is bStats enabled on this server?
	private boolean enabled;

	// Should failed requests be logged?
	private static boolean logFailedRequests;

	// Should the sent data be logged?
	private static boolean logSentData;

	// Should the response text be logged?
	private static boolean logResponseStatusText;

	// The uuid of the server
	private static String serverUUID;

	// The plugin
	private Plugin plugin;

	public ApiMetricsLite() {
		// The register method just uses any enabled plugin it can find to register. This *shouldn't* cause any problems, since the plugin isn't used any other way.
		// Register our service
		for(final Plugin plug : Bukkit.getPluginManager().getPlugins()) {
				this.plugin = plug;
				if(this.plugin != null) {
					break;
				}
		}
		if(this.plugin == null)
			return;// Didn't find any plugin that could work

		// Get the config file
		final File bStatsFolder = new File(this.plugin.getDataFolder().getParentFile(), "bStats");
		final File configFile = new File(bStatsFolder, "config.yml");
		final YamlConfiguration config = YamlConfiguration.loadConfiguration(configFile);

		// Check if the config file exists
		if (!config.isSet("serverUuid")) {

			// Add default values
			config.addDefault("enabled", true);
			// Every server gets it's unique random id.
			config.addDefault("serverUuid", UUID.randomUUID().toString());
			// Should failed request be logged?
			config.addDefault("logFailedRequests", false);
			// Should the sent data be logged?
			config.addDefault("logSentData", false);
			// Should the response text be logged?
			config.addDefault("logResponseStatusText", false);

			// Inform the server owners about bStats
			config.options().header(
					"bStats collects some data for plugin authors like how many servers are using their plugins.\n" +
							"To honor their work, you should not disable it.\n" +
							"This has nearly no effect on the server performance!\n" +
							"Check out https://bStats.org/ to learn more :)"
					).copyDefaults(true);
			try {
				config.save(configFile);
			} catch (final IOException ignored) { }
		}

		// Load the data
		serverUUID = config.getString("serverUuid");
		logFailedRequests = config.getBoolean("logFailedRequests", false);
		this.enabled = config.getBoolean("enabled", true);
		logSentData = config.getBoolean("logSentData", false);
		logResponseStatusText = config.getBoolean("logResponseStatusText", false);
		if (this.enabled) {
			boolean found = false;
			// Search for all other bStats Metrics classes to see if we are the first one
			for (final Class<?> service : Bukkit.getServicesManager().getKnownServices()) {
				try {
					service.getField("NBT_BSTATS_VERSION"); // Create only one instance of the nbt-api bstats.
					return;
				} catch (final NoSuchFieldException ignored) { }
				try {
					service.getField("B_STATS_VERSION"); // Our identifier :)
					found = true; // We aren't the first
					break;
				} catch (final NoSuchFieldException ignored) { }
			}
			// Register our service
			Bukkit.getServicesManager().register(ApiMetricsLite.class, this, this.plugin, ServicePriority.Normal);
			if (!found) {
				logger.info("[NBTAPI] Using the plugin '" + this.plugin.getName() + "' to create a bStats instance!");
				// We are the first!
				this.startSubmitting();
			}
		}
	}

	/**
	 * Checks if bStats is enabled.
	 *
	 * @return Whether bStats is enabled or not.
	 */
	public boolean isEnabled() {
		return this.enabled;
	}

	/**
	 * Starts the Scheduler which submits our data every 30 minutes.
	 */
	private void startSubmitting() {
		final Timer timer = new Timer(true); // We use a timer cause the Bukkit scheduler is affected by server lags
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				if (!ApiMetricsLite.this.plugin.isEnabled()) { // Plugin was disabled
					timer.cancel();
					return;
				}
				// Nevertheless we want our code to run in the Bukkit main thread, so we have to use the Bukkit scheduler
				// Don't be afraid! The connection to the bStats server is still async, only the stats collection is sync ;)
				Bukkit.getScheduler().runTask(ApiMetricsLite.this.plugin, () -> ApiMetricsLite.this.submitData());
			}
		}, 1000l * 60l * 5l, 1000l * 60l * 30l);
		// Submit the data every 30 minutes, first time after 5 minutes to give other plugins enough time to start
		// WARNING: Changing the frequency has no effect but your plugin WILL be blocked/deleted!
		// WARNING: Just don't do it!
	}

	/**
	 * Gets the plugin specific data.
	 * This method is called using Reflection.
	 *
	 * @return The plugin specific data.
	 */
	public JsonObject getPluginData() {
		final JsonObject data = new JsonObject();

		data.addProperty("pluginName", PLUGINNAME); // Append the name of the plugin
		data.addProperty("pluginVersion", PLUGINVERSION); // Append the version of the plugin
		data.add("customCharts", new JsonArray());

		return data;
	}

	/**
	 * Gets the server specific data.
	 *
	 * @return The server specific data.
	 */
	private JsonObject getServerData() {
		// Minecraft specific data
		int playerAmount;
		try {
			// Around MC 1.8 the return type was changed to a collection from an array,
			// This fixes java.lang.NoSuchMethodError: org.bukkit.Bukkit.getOnlinePlayers()Ljava/util/Collection;
			final Method onlinePlayersMethod = Class.forName("org.bukkit.Server").getMethod("getOnlinePlayers");
			playerAmount = onlinePlayersMethod.getReturnType().equals(Collection.class)
					? ((Collection<?>) onlinePlayersMethod.invoke(Bukkit.getServer())).size()
							: ((Player[]) onlinePlayersMethod.invoke(Bukkit.getServer())).length;
		} catch (final Exception e) {
			playerAmount = Bukkit.getOnlinePlayers().size(); // Just use the new method if the Reflection failed
		}
		final int onlineMode = Bukkit.getOnlineMode() ? 1 : 0;
		final String bukkitVersion = Bukkit.getVersion();
		final String bukkitName = Bukkit.getName();

		// OS/Java specific data
		final String javaVersion = System.getProperty("java.version");
		final String osName = System.getProperty("os.name");
		final String osArch = System.getProperty("os.arch");
		final String osVersion = System.getProperty("os.version");
		final int coreCount = Runtime.getRuntime().availableProcessors();

		final JsonObject data = new JsonObject();

		data.addProperty("serverUUID", serverUUID);

		data.addProperty("playerAmount", playerAmount);
		data.addProperty("onlineMode", onlineMode);
		data.addProperty("bukkitVersion", bukkitVersion);
		data.addProperty("bukkitName", bukkitName);

		data.addProperty("javaVersion", javaVersion);
		data.addProperty("osName", osName);
		data.addProperty("osArch", osArch);
		data.addProperty("osVersion", osVersion);
		data.addProperty("coreCount", coreCount);

		return data;
	}

	/**
	 * Collects the data and sends it afterwards.
	 */
	private void submitData() {
		final JsonObject data = this.getServerData();

		final JsonArray pluginData = new JsonArray();
		// Search for all other bStats Metrics classes to get their plugin data
		for (final Class<?> service : Bukkit.getServicesManager().getKnownServices()) {
			try {
				service.getField("B_STATS_VERSION"); // Our identifier :)

				for (final RegisteredServiceProvider<?> provider : Bukkit.getServicesManager().getRegistrations(service)) {
					try {
						final Object plugin = provider.getService().getMethod("getPluginData").invoke(provider.getProvider());
						if (plugin instanceof JsonObject) {
							pluginData.add((JsonObject) plugin);
						} else { // old bstats version compatibility
							try {
								final Class<?> jsonObjectJsonSimple = Class.forName("org.json.simple.JSONObject");
								if (plugin.getClass().isAssignableFrom(jsonObjectJsonSimple)) {
									final Method jsonStringGetter = jsonObjectJsonSimple.getDeclaredMethod("toJSONString");
									jsonStringGetter.setAccessible(true);
									final String jsonString = (String) jsonStringGetter.invoke(plugin);
									final JsonObject object = new JsonParser().parse(jsonString).getAsJsonObject();
									pluginData.add(object);
								}
							} catch (final ClassNotFoundException e) {
								// minecraft version 1.14+
								if (logFailedRequests) {
									logger.log(Level.WARNING, "[NBTAPI][BSTATS] Encountered exception while posting request!", e);
									// Not using the plugins logger since the plugin isn't the plugin containing the NBT-Api most of the time
									//this.plugin.getLogger().log(Level.SEVERE, "Encountered unexpected exception ", e); 
								}
								continue; // continue looping since we cannot do any other thing.
							}
						}
					} catch (NullPointerException | NoSuchMethodException | IllegalAccessException | InvocationTargetException ignored) {
					}
				}
			} catch (final NoSuchFieldException ignored) { }
		}

		data.add("plugins", pluginData);

		// Create a new thread for the connection to the bStats server
		new Thread(() -> {
			try {
				// Send the data
				sendData(ApiMetricsLite.this.plugin, data);
			} catch (final Exception e) {
				// Something went wrong! :(
				if (logFailedRequests) {
					logger.log(Level.WARNING, "[NBTAPI][BSTATS] Could not submit plugin stats of " + ApiMetricsLite.this.plugin.getName(), e);
					// Not using the plugins logger since the plugin isn't the plugin containing the NBT-Api most of the time
					//plugin.getLogger().log(Level.WARNING, "Could not submit plugin stats of " + plugin.getName(), e);
				}
			}
		}).start();
	}

	/**
	 * Sends the data to the bStats server.
	 *
	 * @param plugin Any plugin. It's just used to get a logger instance.
	 * @param data The data to send.
	 * @throws Exception If the request failed.
	 */
	private static void sendData(final Plugin plugin, final JsonObject data) throws Exception {
		if (data == null)
			throw new IllegalArgumentException("Data cannot be null!");
		if (Bukkit.isPrimaryThread())
			throw new IllegalAccessException("This method must not be called from the main thread!");
		if (logSentData) {
			System.out.println("[NBTAPI][BSTATS] Sending data to bStats: " + data.toString());
			// Not using the plugins logger since the plugin isn't the plugin containing the NBT-Api most of the time
			//plugin.getLogger().info("Sending data to bStats: " + data.toString());
		}
		final HttpsURLConnection connection = (HttpsURLConnection) new URL(URL).openConnection();

		// Compress the data to save bandwidth
		final byte[] compressedData = compress(data.toString());

		// Add headers
		connection.setRequestMethod("POST");
		connection.addRequestProperty("Accept", "application/json");
		connection.addRequestProperty("Connection", "close");
		connection.addRequestProperty("Content-Encoding", "gzip"); // We gzip our request
		connection.addRequestProperty("Content-Length", String.valueOf(compressedData.length));
		connection.setRequestProperty("Content-Type", "application/json"); // We send our data in JSON format
		connection.setRequestProperty("User-Agent", "MC-Server/" + B_STATS_VERSION);

		// Send data
		connection.setDoOutput(true);
		final DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
		outputStream.write(compressedData);
		outputStream.flush();
		outputStream.close();

		final InputStream inputStream = connection.getInputStream();
		final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

		final StringBuilder builder = new StringBuilder();
		String line;
		while ((line = bufferedReader.readLine()) != null) {
			builder.append(line);
		}
		bufferedReader.close();
		if (logResponseStatusText) {
			logger.info("[NBTAPI][BSTATS] Sent data to bStats and received response: " + builder.toString());
			// Not using the plugins logger since the plugin isn't the plugin containing the NBT-Api most of the time
			//plugin.getLogger().info("Sent data to bStats and received response: " + builder.toString());
		}
	}

	/**
	 * Gzips the given String.
	 *
	 * @param str The string to gzip.
	 * @return The gzipped String.
	 * @throws IOException If the compression failed.
	 */
	private static byte[] compress(final String str) throws IOException {
		if (str == null)
			return new byte[0];
		final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		final GZIPOutputStream gzip = new GZIPOutputStream(outputStream);
		gzip.write(str.getBytes(StandardCharsets.UTF_8));
		gzip.close();
		return outputStream.toByteArray();
	}

}
