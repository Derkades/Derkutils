package xyz.derkades.derkutils.bukkit.sidebar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

public class Sidebar implements ConfigurationSerializable {

	private static transient ScoreboardManager bukkitManager = Bukkit.getScoreboardManager();

	static {
		ConfigurationSerialization.registerClass(Sidebar.class);
	}

	private List<SidebarString> entries;
	private transient Scoreboard bukkitScoreboard;
	private transient Objective bukkitObjective;
	private transient BukkitTask updateTask;
	private String title;
	private Player setPlaceholdersOnUpdate = null;

	/**
	 * Constructs a new Sidebar.
	 * 
	 * @param title              (String) - the title of the sidebar
	 * @param plugin             (Plugin) - your plugin
	 * @param updateDelayInTicks (int) - how many server ticks to wait in between
	 *                           each update. 20 = 1 second
	 * @param entries            (SidebarString...) - all the entries
	 */
	public Sidebar(final String title, final Plugin plugin, final int updateDelayInTicks, final SidebarString... entries) {

		this.bukkitScoreboard = bukkitManager.getNewScoreboard();

		this.bukkitObjective = this.bukkitScoreboard.registerNewObjective("obj", "dummy", "mgsb");

		this.entries = new ArrayList<>();
		this.entries.addAll(Arrays.asList(entries));

		this.title = title;

		update();

		setUpdateDelay(plugin, updateDelayInTicks);

		SidebarAPI.registerSidebar(this);

	}

	@SuppressWarnings("unchecked")
	public Sidebar(final Map<String, Object> map) {

		this.entries = (List<SidebarString>) map.get("entries");
		this.title = (String) map.get("title");

		if (map.containsKey("placeholders")) {
			this.setPlaceholdersOnUpdate = Bukkit.getPlayer(UUID.fromString((String) map.get("placeholders")));
		}

	}

	@Override
	public Map<String, Object> serialize() {

		final Map<String, Object> map = new HashMap<>();

		map.put("entries", this.entries);
		map.put("title", this.title);

		if (this.setPlaceholdersOnUpdate != null) {
			map.put("placeholders", this.setPlaceholdersOnUpdate.getUniqueId().toString());
		}

		return map;

	}

	/**
	 * Gets the player that will be used for setting the placeholders in the update
	 * function.
	 * 
	 * @return (Player) - The player or null.
	 * @see #update()
	 */
	public Player getPlaceholderPlayerForUpdate() {
		return this.setPlaceholdersOnUpdate;
	}

	/**
	 * Sets the player that will be used for setting the placeholders in the update
	 * function. If set to null, the placeholders will not be set.
	 * 
	 * @param player (Player) - the player or null
	 * @return (Sidebar) - this Sidebar Object, for chaining.
	 * @see #update()
	 */
	public Sidebar setPlaceholderPlayerForUpdate(final Player player) {
		this.setPlaceholdersOnUpdate = player;
		return this;
	}

	/**
	 * Sets how many server ticks to wait in between each update.
	 * 
	 * @param plugin       (Plugin) - your plugin
	 * @param delayInTicks (int) - the ticks
	 * @return (Sidebar) - this Sidebar Object, for chaining.
	 */
	public Sidebar setUpdateDelay(final Plugin plugin, final int delayInTicks) {
		if (delayInTicks < 1) {
			throw new IllegalArgumentException("delayInTicks cannot be less than 1!");
		}

		if (this.updateTask != null) {
			this.updateTask.cancel();
		}

		this.updateTask = (new BukkitRunnable() {

			@Override
			public void run() {
				update();
			}

		}).runTaskTimer(plugin, delayInTicks, delayInTicks);

		return this;
	}

	/**
	 * Gets the title of this Sidebar.
	 * 
	 * @return (String) - the title.
	 */
	public String getTitle() {
		return this.title;
	}

	/**
	 * Sets the title of this Sidebar.
	 * 
	 * @param title (String) - the new title
	 * @return (Sidebar) - this Sidebar Object, for chaining.
	 */
	public Sidebar setTitle(final String title) {
		this.title = title;
		return this;
	}

	/**
	 * Gets a list of all entries.
	 * 
	 * @return (List: SidebarString) - all entries.
	 */
	public List<SidebarString> getEntries() {
		return this.entries;
	}

	/**
	 * Overrides all current entries.
	 * 
	 * @param entries (List: SidebarString) - the new entries
	 * @return (Sidebar) - this Sidebar Object, for chaining.
	 */
	public Sidebar setEntries(final List<SidebarString> entries) {
		this.entries = entries;
		return this;
	}

	/**
	 * Adds an entry.
	 * 
	 * @param entries (SidebarString) - the entry
	 * @return (Sidebar) - this Sidebar Object, for chaining.
	 */
	public Sidebar addEntry(final SidebarString... entries) {
		this.entries.addAll(Arrays.asList(entries));
		return this;
	}

	/**
	 * Removes an entry.
	 * 
	 * @param entry (SidebarString) - the entry
	 * @return (Sidebar) - this Sidebar Object, for chaining.
	 */
	public Sidebar removeEntry(final SidebarString entry) {
		this.entries.remove(entry);
		return this;
	}

	/**
	 * Removes the entry referring to a specific line.
	 * 
	 * @param num (int) - the line
	 * @return (Sidebar) - this Sidebar Object, for chaining.
	 */
	public Sidebar removeEntry(final int num) {
		this.entries.remove(num);
		return this;
	}

	/**
	 * Shows this Sidebar to a player.
	 * 
	 * @param player (Player) - the player
	 * @return (Sidebar) - this Sidebar Object, for chaining.
	 */
	public Sidebar showTo(final Player player) {
		player.setScoreboard(this.bukkitScoreboard);
		return this;
	}

	/**
	 * Hides this Sidebar from a player.
	 * 
	 * @param player (Player) - the player
	 * @return (Sidebar) - this Sidebar Object, for chaining.
	 */
	public Sidebar hideFrom(final Player player) {
		player.setScoreboard(bukkitManager.getMainScoreboard());
		return this;
	}

	/**
	 * Updates the sidebar (it's entries and title). If
	 * {@link #getPlaceholderPlayerForUpdate()} is not null, this will also run
	 * {@link #setAllPlaceholders(Player)} with
	 * {@link #getPlaceholderPlayerForUpdate()} as the argument.
	 * 
	 * @return (Sidebar) - this Sidebar Object, for chaining.
	 */
	public Sidebar update() {
		redoBukkitObjective();

		for (int i = this.entries.size(); i > 0; i--) {
			this.bukkitObjective.getScore(this.entries.get(this.entries.size() - i).getNext()).setScore(i);
		}

		return this;
	}

	/**
	 * Adds an empty entry. The entry won't conflict with any other empty entries
	 * made this way.
	 * 
	 * @return (Sidebar) - this Sidebar Object, for chaining.
	 */
	public Sidebar addEmpty() {
		this.entries.add(new SidebarString(new String(new char[this.entries.size()]).replace("\0", " ")));
		return this;
	}

	private void redoBukkitObjective() {
		this.bukkitObjective.unregister();
		this.bukkitObjective = this.bukkitScoreboard.registerNewObjective("obj", "dummy", "mgsb");

		this.bukkitObjective.setDisplayName(this.title);
		this.bukkitObjective.setDisplaySlot(DisplaySlot.SIDEBAR);
	}

}
