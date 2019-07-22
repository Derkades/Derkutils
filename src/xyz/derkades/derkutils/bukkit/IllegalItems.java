package xyz.derkades.derkutils.bukkit;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import de.tr7zw.nbtapi.NBTItem;

public class IllegalItems implements Listener {
	
	private static final String NBT_KEY = "DerkutilsIllegalItem";
	
	private final Plugin plugin;
	private final boolean debug;
	
	public IllegalItems(final Plugin plugin) {
		this(plugin, false);
	}
	
	public IllegalItems(final Plugin plugin, final boolean debug) {
		this.plugin = plugin;
		this.debug = debug;
		
		Bukkit.getPluginManager().registerEvents(this, plugin);
		
		if (debug) {
			plugin.getLogger().info("Illegal items registered");
		}
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
	
	@EventHandler
	public void onInventoryClick(final InventoryClickEvent event) {
		if (event.getClickedInventory().getType() == InventoryType.PLAYER) {
			final ItemStack item = event.getCurrentItem();
			if (this.isIllegal(item)) {
				if (this.debug) {
					this.plugin.getLogger().warning(String.format("Removed illegal item (%sx%s) from %s", item.getAmount(), item.getType(), event.getView().getPlayer().getName()));
				}
				item.setType(Material.AIR);
			}
		}
	}

}
