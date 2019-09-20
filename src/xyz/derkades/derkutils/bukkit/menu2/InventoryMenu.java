package xyz.derkades.derkutils.bukkit.menu2;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public abstract class InventoryMenu implements Listener {

	protected final Plugin plugin;
	private final int size;
	private final String title;

	private final Map<Integer, ItemStack> items = new HashMap<>();
	private final Map<Integer, ClickHandler> clickHandlers = new HashMap<>();

	private Inventory inventory;
	private InventoryView inventoryView;

	private CloseHandler closeHandler;

	/**
	 * Constructs a new InventoryMenu. The menu is not opened until {@link #open(Player)} is called.
	 * @param plugin
	 * @param rows
	 * @param title
	 */
	public InventoryMenu(final Plugin plugin, final int rows, final String title) {
		this.plugin = plugin;
		this.size = rows * 9;
		this.title = title;
	}

	/**
	 * Set a close handler, overwriting a previously set close handler.
	 * {@link CloseHandler#onClose(Player, CloseReason)} is executed when the
	 * menu is closed.
	 * @param closeHandler
	 */
	protected void onClose(final CloseHandler closeHandler) {
		this.closeHandler = closeHandler;
	}

	/**
	 * Set the item in a slot. Will overwrite a previously set item. When the
	 * item is clicked, nothing will happen and the menu will stay open.
	 * {@see #setItem(int, ItemStack, ClickHandler)}
	 * @param slot
	 * @param item
	 */
	protected void setItem(final int slot, final ItemStack item) {
		this.items.put(slot, item);
	}

	/**
	 * Set the item in a slot. Will overwrite a previously set item. When the
	 * item is clicked, {@link ClickHandler#accept(ClickEvent)} is executed.
	 * {@see #setItem(int, ItemStack)}
	 * @param slot
	 * @param item
	 * @param handler
	 */
	protected void setItem(final int slot, final ItemStack item, final ClickHandler handler) {
		this.clickHandlers.put(slot, handler);
	}

	/**
	 * Update the menu with changes by {@link #setItem(int, ItemStack, ClickHandler)}
	 */
	protected void refresh() {
		this.items.forEach(this.inventory::setItem);
	}

	/**
	 * Opens the menu for a player, if the player has no open inventories. {@see #open(Player)}
	 * @param player
	 * @return true if the menu opened, false if it didn't because a menu was already open.
	 */
	public boolean open(final Player player) {
		return this.open(player, false);
	}

	/**
	 * Opens the menu for a player, if the player has no open inventories. {@see #open(Player)}
	 * @param player
	 * @param force If set to true, the menu will open even if the player has an open inventory.
	 * @return true if the menu opened, false if it didn't because a menu was already open.
	 */
	public boolean open(final Player player, final boolean force) {
		if (!force && player.getOpenInventory() == null)
			return false;

		this.inventory = Bukkit.createInventory(player, this.size, this.title);
		this.refresh(); // Add items to inventory

		Bukkit.getServer().getPluginManager().registerEvents(this, this.plugin);
		player.openInventory(this.inventory);

		return true;
	}

	/**
	 * Close the menu with reason {@link CloseReason#FORCE_CLOSE}
	 * @param player
	 */
	public void close(final Player player) {
		this.close(player, CloseReason.FORCE_CLOSE);
	}

	/**
	 * Closes the menu with the given reason.
	 * @param player
	 * @param reason
	 */
	public void close(final Player player, final CloseReason reason) {
		if (this.closeHandler != null) {
			this.closeHandler.onClose(player, reason);
		}

		HandlerList.unregisterAll(this);
		player.closeInventory();
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onInventoryClick(final InventoryClickEvent event) {
		if (!event.getView().equals(this.inventoryView))
			return;

		event.setCancelled(true);

		final int slot = event.getRawSlot();

		if (!this.clickHandlers.containsKey(slot))
			return;

		final Player player = (Player) event.getWhoClicked();

		final ClickEvent clickEvent = new ClickEvent(slot, this.inventory.getItem(slot), event.getClick(), player);

		this.clickHandlers.get(slot).accept(clickEvent);

		final ClickResult result = clickEvent.getResult();

		if (result == ClickResult.CLOSE) {
			this.close(player, CloseReason.ITEM_CLICK);
		}
	}

	@EventHandler
	public void onInventoryClose(final InventoryCloseEvent event){
		if (!event.getView().equals(this.inventoryView))
			return;

		if (this.closeHandler != null) {
			this.closeHandler.onClose((Player) event.getPlayer(), CloseReason.PLAYER_CLOSED);
		}

		HandlerList.unregisterAll(this);
	}

}
