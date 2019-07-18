package xyz.derkades.derkutils.bukkit.menu;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public abstract class IconMenu implements Listener {

	private String name;
	private int size;
	private final Plugin plugin;
	private final Player player;

	public Map<Integer, ItemStack> items;
	private Inventory inventory;

	/**
	 * Creates a new menu instance. To add items to this menu, add them to the {@link #items} hashmap.
	 * @param plugin Bukkit plugin instance
	 * @param name Name of the menu
	 * @param size Number of slots. Must be a multiple of 9 that is greater than 0 and less than 10.
	 * @param player Player that this menu will be opened for when {@link #open()} is called
	 */
	public IconMenu(final Plugin plugin, final String name, final int size, final Player player){
		this.plugin = plugin;
		this.size = size;
		this.name = name;
		this.player = player;
		this.items = new HashMap<>();

		Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
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

	/**
	 * Sets the size of the menu, a multiple of 9. Has no effect after the menu has been opened
	 * @param size
	 */
	public void setSize(final int size){
		this.size = size;
	}

	/**
	 * Calls {@link #onClose(MenuCloseEvent)} with {@link CloseReason#FORCE_CLOSE} and closes the inventory.
	 */
	public void close() {
		this.onClose(new MenuCloseEvent(this.player, CloseReason.FORCE_CLOSE));
		HandlerList.unregisterAll(this);
		this.player.closeInventory();
	}

	/**
	 * Opens the menu
	 */
	public void open() {
		this.inventory = Bukkit.createInventory(this.player, this.size, this.name);
		this.refreshItems();
		this.player.openInventory(this.inventory);
	}

	/**
	 * Update the menu with the {@link #items} map
	 */
	public void refreshItems() {
		this.inventory.clear();
		for (final Map.Entry<Integer, ItemStack> menuItem : this.items.entrySet()){
			this.inventory.setItem(menuItem.getKey(), menuItem.getValue());
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onInventoryClick(final InventoryClickEvent event) {
		if (event.getView().getTitle().equals(this.name) && (event.getWhoClicked().getUniqueId().equals(this.player.getUniqueId()))) {
			event.setCancelled(true);

			if (event.getClick() != ClickType.LEFT)
				return;

			final int slot = event.getRawSlot();

			final Player clicker = (Player) event.getWhoClicked();

			if (slot >= 0 && slot < this.size && this.items.containsKey(slot)) {
				final boolean close = this.onOptionClick(new OptionClickEvent(clicker, slot, this.items.get(slot)));
				if (close) {
					new BukkitRunnable() {
						@Override
						public void run() {
							IconMenu.this.onClose(new MenuCloseEvent(IconMenu.this.player, CloseReason.ITEM_CLICK));
							IconMenu.this.close();
						}
					}.runTaskLater(this.plugin, 1);
				}
			}
		}
	}

	@EventHandler
	public void onInventoryClose(final InventoryCloseEvent event){
		if (event.getView().getTitle().equals(this.name) && (event.getPlayer().getUniqueId().equals(this.player.getUniqueId()))) {

			this.onClose(new MenuCloseEvent(this.player, CloseReason.PLAYER_CLOSED));

			new BukkitRunnable() {
				@Override
				public void run() {
					HandlerList.unregisterAll(IconMenu.this);
				}
			}.runTaskLater(this.plugin, 1);
		}
	}

}