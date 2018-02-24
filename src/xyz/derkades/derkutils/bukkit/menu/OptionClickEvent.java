package xyz.derkades.derkutils.bukkit.menu;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.inventory.ItemStack;

public class OptionClickEvent extends PlayerEvent {

	private int position;
	private ItemStack item;

	OptionClickEvent(Player player, int position, ItemStack item) {
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
