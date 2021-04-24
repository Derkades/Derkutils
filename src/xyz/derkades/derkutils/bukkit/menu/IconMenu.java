package xyz.derkades.derkutils.bukkit.menu;

import java.util.Objects;
import java.util.UUID;
import java.util.function.Consumer;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public abstract class IconMenu implements Listener {

	private String name;
	private final int size;
	private final UUID uuid;

	private final Inventory inventory;
	private final InventoryView view;
	private boolean closeEventCalled = false;
//	private boolean cancelTask = false;

	/**
	 * Creates a new menu instance.
	 * <ul>
	 * <li>To add items, use the {@link IconMenu#addItem(int, ItemStack)} method</li>
	 * <li>The menu should be opened in the same tick as it's created. Do this using {@link #open()}.
	 * @param plugin Bukkit plugin instance
	 * @param name Name of the menu
	 * @param size Number of slots. Must be a multiple of 9 that is greater than 0 and less than 10.
	 * @param player Player that this menu will be opened for when {@link #open()} is called
	 */
	public IconMenu(final Plugin plugin, final String name, final int rows, final Player player) {
		this(name, rows, player,
				t -> t.runTaskTimer(plugin, 1, 1),
				l -> Bukkit.getServer().getPluginManager().registerEvents(l, plugin));
	}

	public IconMenu(final String name, final int rows, final Player player,
			final Consumer<BukkitRunnable> timerRegistrar, final Consumer<Listener> listenerRegistrar) {
		Objects.requireNonNull(name, "Name is null");
		Objects.requireNonNull(player, "Player is null");
		Objects.requireNonNull(listenerRegistrar, "Listener registrar is null");

		this.size = rows * 9;
		this.name = name;
		this.uuid = player.getUniqueId();

		listenerRegistrar.accept(this);
		this.inventory = Bukkit.createInventory(player, this.size, this.name);
		this.view = player.openInventory(this.inventory);
		Objects.requireNonNull(this.view, "Opened inventory view is null");

		timerRegistrar.accept(new BukkitRunnable() {

			@Override
			public void run() {
				// Unregister listeners for the menu if the player has opened a different
				// menu, which means that this menu must be closed.
				final Player player = Bukkit.getPlayer(IconMenu.this.uuid);
				if (player == null) {
					// player went offline
					if (!IconMenu.this.closeEventCalled) {
						IconMenu.this.onClose(new MenuCloseEvent(Bukkit.getOfflinePlayer(IconMenu.this.uuid), CloseReason.PLAYER_QUIT));
					}
				} else if (!player.getOpenInventory().getTopInventory().equals(IconMenu.this.inventory)) {
					// player closed inventory
					if (!IconMenu.this.closeEventCalled) {
						IconMenu.this.onClose(new MenuCloseEvent(player, CloseReason.PLAYER_CLOSED));
					}
				} else {
					// menu is still open
					return;
				}

				HandlerList.unregisterAll(IconMenu.this);
				this.cancel();
				return;
			}

		});
	}

	/**
	 * Called when a player clicks on an item in the menu
	 * @param event
	 * @return Whether the menu should be closed
	 */
	public abstract boolean onOptionClick(OptionClickEvent event);

	/**
	 * Called when the menu closes
	 * @param event
	 */
	public void onClose(final MenuCloseEvent event) {}

	/**
	 * Sets the name of the menu. Has no effect after the menu has been opened.
	 * @param name
	 */
	public void setName(final String name){
		this.name = name;
	}

	public int getSize() {
		return this.size;
	}

	public OfflinePlayer getPlayer() {
		final OfflinePlayer player = Bukkit.getPlayer(this.uuid);
		return player != null ? player : Bukkit.getOfflinePlayer(this.uuid);
	}

	/**
	 * Calls {@link #onClose(MenuCloseEvent)} with {@link CloseReason#FORCE_CLOSE} and closes the inventory.
	 */
	public void close() {
		this.closeEventCalled = true;
		this.onClose(new MenuCloseEvent(this.getPlayer(), CloseReason.FORCE_CLOSE));
		this.view.close();
//		this.cancelTask = true;
	}

	public void addItem(final int slot, final ItemStack item) {
		this.inventory.setItem(slot, item);
	}

	public boolean hasItem(final int slot) {
		return this.inventory.getItem(slot) != null &&
				this.inventory.getItem(slot).getType() != Material.AIR;
	}

	public void clear() {
		this.inventory.clear();
	}

	public Inventory getInventory() {
		return this.inventory;
	}

	public InventoryView getInventoryView() {
		return this.view;
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onInventoryClick(final InventoryClickEvent event) {
		if (!event.getView().getTopInventory().equals(this.inventory)) {
			return;
		}

		event.setCancelled(true);

		final int slot = event.getRawSlot();

		final Player clicker = (Player) event.getWhoClicked();

		if (slot >= 0 && slot < this.size && this.hasItem(slot)) {
			final ItemStack item = this.inventory.getItem(slot);

			final boolean close = this.onOptionClick(new OptionClickEvent(clicker, slot, item, event.getClick()));
			if (close) {
				this.closeEventCalled = true;
				IconMenu.this.onClose(new MenuCloseEvent(this.getPlayer(), CloseReason.ITEM_CLICK));
				this.view.close();
			}
		}
	}

//	@EventHandler(priority = EventPriority.MONITOR)
//	public void onQuit(final PlayerQuitEvent event) {
//		final Player player = event.getPlayer();
//		this.closeEventCalled = true;
//		this.cancelTask = true;
//		this.onClose(new MenuCloseEvent(player, CloseReason.PLAYER_QUIT));
//	}

}