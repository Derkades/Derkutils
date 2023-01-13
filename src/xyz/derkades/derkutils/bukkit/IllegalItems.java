package xyz.derkades.derkutils.bukkit;

import de.tr7zw.changeme.nbtapi.NBTItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Objects;

public class IllegalItems implements Listener {

	private static final String NBT_KEY = "DerkutilsIllegalItem";

	public IllegalItems(final @NonNull Plugin plugin) {
		Bukkit.getPluginManager().registerEvents(this, Objects.requireNonNull(plugin, "Plugin is null"));
	}

	public ItemStack setIllegal(final @NonNull ItemStack item, final boolean illegal) {
		Objects.requireNonNull(item, "Item is null");
		final NBTItem nbt = new NBTItem(item);
		nbt.setBoolean(NBT_KEY, illegal);
		return nbt.getItem();
	}

	public boolean isIllegal(final @NonNull ItemStack item) {
		Objects.requireNonNull(item, "Item is null");
		final NBTItem nbt = new NBTItem(item);
		if (!nbt.hasNBTData()) {
			return false;
		} else if (!nbt.hasKey(NBT_KEY)) {
			return false;
		} else {
			return nbt.getBoolean(NBT_KEY);
		}
	}

	public void unregister() {
		HandlerList.unregisterAll(this);
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onInventoryClick(final @NonNull InventoryClickEvent event) {
		if (event.getClickedInventory() == null) { // when clicking outside of the inventory while it is open
			return;
		}

		final Inventory inventory = event.getClickedInventory();
		if (inventory != null &&
				inventory.getType() == InventoryType.PLAYER) {
			final ItemStack item = event.getCurrentItem();
			if (item != null && this.isIllegal(item)) {
				item.setType(Material.AIR);
				event.setCurrentItem(item);
			}
		}
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onItemDrop(final @NonNull PlayerDropItemEvent event) {
		final ItemStack item = event.getItemDrop().getItemStack();
		if (this.isIllegal(item)) {
			item.setType(Material.AIR);
			event.getItemDrop().remove();
		}
	}

}
