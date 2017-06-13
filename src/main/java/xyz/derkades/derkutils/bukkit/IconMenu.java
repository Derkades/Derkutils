package xyz.derkades.derkutils.bukkit;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public abstract class IconMenu implements Listener {

	private String name;
	private int size;
	private Plugin plugin;
	private Player player;
	
	public IconMenu(Plugin plugin, String name, int size) {
		this.name = name;
		this.size = size;

		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	public IconMenu(Plugin plugin, String name, int size, Player player){
		this(plugin, name, size);
		this.player = player;
	}
	
	public abstract List<MenuItem> getMenuItems(Player player);
	
	/**
	 * Called when a player clicks on an item in the menu
	 * @param event
	 * @return Whether the menu should be closed
	 */
	public abstract boolean onOptionClick(OptionClickEvent event);

	/**
	 * 
	 * @param player
	 * @param isSpecific Whether contents vary per player.
	 */
	public void open(Player player, boolean isSpecific) {	
		Inventory inventory = Bukkit.createInventory(player, size, name);
		for (MenuItem menuItem : this.getMenuItems(player)){
			inventory.setItem(menuItem.getPosition(), menuItem.getItemStack());
		}
		player.openInventory(inventory);
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onInventoryClick(InventoryClickEvent event) {
		if (event.getInventory().getTitle().equals(name) && (player == null || event.getWhoClicked().getUniqueId().equals(player.getUniqueId()))) {
			event.setCancelled(true);
			
			if (event.getClick() != ClickType.LEFT)
				return;
			
			int slot = event.getRawSlot();
			
			final Player clicker = (Player) event.getWhoClicked();
			
			if (slot >= 0 && slot < size && this.getMenuItems(clicker).get(slot) != null) {				
				boolean close = this.onOptionClick(new OptionClickEvent(clicker, slot, this.getMenuItems(clicker).get(slot)));
				if (close) {
					new BukkitRunnable() {
						public void run() {
							clicker.closeInventory();
						}
					}.runTaskLater(plugin, 1);
				}
			}
		}
	}

	public static class OptionClickEvent extends PlayerEvent {

		private int position;
		private ItemStack item;

		public OptionClickEvent(Player player, int position, MenuItem item) {
			super(player);
			
			this.player = player;
			this.position = item.getPosition();
			this.item = item.getItemStack();
		}

		public int getPosition() {
			return position;
		}

		public String getName() {
			return item.getItemMeta().getDisplayName();
		}

		public ItemStack getItemStack() {
			return item;
		}

		@Override
		public HandlerList getHandlers() {
			return null;
		}
	}
	
	public static class MenuItem {

		private ItemStack item;
		private int position;

		public MenuItem(int position, ItemStack item) {
			this.item = item;
			this.position = position;
		}

		public MenuItem(int position, ItemStack item, String name) {
			this.item = new ItemBuilder(item).setName(name).create();
			this.position = position;
		}

		public MenuItem(int position, Material type, String name) {
			this.item = new ItemBuilder(type).setName(name).create();
			this.position = position;
		}

		public MenuItem(int position, ItemStack item, String name, String... lore) {
			this.item = new ItemBuilder(item).setName(name).setLore(lore).create();
			this.position = position;
		}

		public MenuItem(int position, Material type, String name, String... lore) {
			this.item = new ItemBuilder(type).setName(name).setLore(lore).create();
			this.position = position;
		}

		public int getPosition() {
			return position;
		}

		public ItemStack getItemStack() {
			return item;
		}
		
	}
	
}