package xyz.derkades.derkutils.bukkit;

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
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import xyz.derkades.derkutils.bukkit.IconMenu.MenuCloseEvent.CloseReason;

public abstract class IconMenu implements Listener {

	private String name;
	private int size;
	private Plugin plugin;
	private Player player;
	
	public Map<Integer, ItemStack> items; 
	private Inventory inventory;
	
	public IconMenu(Plugin plugin, String name, int size, Player player){
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
	 * @return Whether the menu should be allowed to close
	 */
	public void onClose(MenuCloseEvent event) {
	}

	public void setName(String name){
		this.name = name;
	}
	
	public void setSize(int size){
		this.size = size;
	}
	
	public void close() {
		onClose(new MenuCloseEvent(player, CloseReason.FORCE_CLOSE));
		HandlerList.unregisterAll(this);
		player.closeInventory();
	}
	
	@Deprecated
	public void destroy() {
		close();
	}
	
	/**
	 * 
	 * @param player
	 * @param isSpecific Whether contents vary per player.
	 */
	public void open() {
		inventory = Bukkit.createInventory(player, size, name);
		refreshItems();
		player.openInventory(inventory);
	}
	
	public void refreshItems() {
		inventory.clear();
		for (Map.Entry<Integer, ItemStack> menuItem : this.items.entrySet()){
			inventory.setItem(menuItem.getKey(), menuItem.getValue());
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onInventoryClick(InventoryClickEvent event) {
		if (event.getInventory().getTitle().equals(name) && (event.getWhoClicked().getUniqueId().equals(player.getUniqueId()))) {
			event.setCancelled(true);
			
			if (event.getClick() != ClickType.LEFT)
				return;

			int slot = event.getRawSlot();
			
			final Player clicker = (Player) event.getWhoClicked();
			
			if (slot >= 0 && slot < size && this.items.containsKey(slot)) {				
				boolean close = this.onOptionClick(new OptionClickEvent(clicker, slot, this.items.get(slot)));
				if (close) {
					new BukkitRunnable() {
						public void run() {
							onClose(new MenuCloseEvent(player, CloseReason.ITEM_CLICK));
							close();
						}
					}.runTaskLater(plugin, 1);
				}
			}
		}
	}
	
	@EventHandler
	public void onInventoryClose(InventoryCloseEvent event){
		if (event.getInventory().getTitle().equals(name) && (event.getPlayer().getUniqueId().equals(player.getUniqueId()))) {
			
			onClose(new MenuCloseEvent(player, CloseReason.PLAYER_CLOSED));
			
			new BukkitRunnable() {
				public void run() {
					HandlerList.unregisterAll(IconMenu.this);
				}
			}.runTaskLater(plugin, 1);
		}
	}

	public static class OptionClickEvent extends PlayerEvent {

		private int position;
		private ItemStack item;

		public OptionClickEvent(Player player, int position, ItemStack item) {
			super(player);
			
			this.position = position;
			this.item = item;
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
	
	public static class MenuCloseEvent extends PlayerEvent {

		private CloseReason reason;
		
		public MenuCloseEvent(Player player, CloseReason reason) {
			super(player);
			this.reason = reason;
		}
		
		public CloseReason getReason() {
			return reason;
		}

		@Override
		public HandlerList getHandlers() {
			return null;
		}
		
		public static enum CloseReason {
			
			PLAYER_CLOSED, FORCE_CLOSE, ITEM_CLICK
			
		}
		
	}
	
}