package xyz.derkades.derkutils.bukkit;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import de.tr7zw.nbtapi.NBTItem;
import net.md_5.bungee.api.ChatColor;

public class IllegalItems implements Listener {

	private static final String NBT_KEY = "DerkutilsIllegalItem";

	public IllegalItems(final Plugin plugin) {
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}

	public ItemStack setIllegal(final ItemStack item, final boolean illegal) {
		final NBTItem nbt = new NBTItem(item);
		nbt.setBoolean(NBT_KEY, illegal);
		return nbt.getItem();
	}

	public boolean isIllegal(final ItemStack item) {
		final NBTItem nbt = new NBTItem(item);
		if (!nbt.hasNBTData())
			return false;
		else if (!nbt.hasKey(NBT_KEY))
			return false;
		else
			return nbt.getBoolean(NBT_KEY);
	}

	public void unregister() {
		HandlerList.unregisterAll(this);
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onInventoryClick(final InventoryClickEvent event) {
		if (event.getClickedInventory() == null) { // when clicking outside of the inventory while it is open
			return;
		}

		if (event.getClickedInventory().getType() == InventoryType.PLAYER) {
			final ItemStack item = event.getCurrentItem();
			if (this.isIllegal(item)) {
				event.getView().getPlayer().sendMessage(ChatColor.RED + "You are not supposed to have this item");
				item.setType(Material.AIR);
				event.setCurrentItem(item);
			}

		}
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onItemDrop(final PlayerDropItemEvent event) {
		final ItemStack item = event.getItemDrop().getItemStack();
		if (this.isIllegal(item)) {
			event.getPlayer().sendMessage(ChatColor.RED + "You are not supposed to have this item");
			item.setType(Material.AIR);
			event.getItemDrop().remove();
		}
	}

}
