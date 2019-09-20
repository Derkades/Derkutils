package xyz.derkades.derkutils.bukkit.menu2;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

public class ClickEvent {

	private ClickResult result = ClickResult.STAY_OPEN;

	private final int slot;
	private final ItemStack item;
	private final ClickType clickType;
	private final Player player;

	ClickEvent(final int slot, final ItemStack item, final ClickType clickType, final Player player){
		this.slot = slot;
		this.item = item;
		this.clickType = clickType;
		this.player = player;
	}

	public ClickResult getResult() {
		return this.result;
	}

	public void setResult(final ClickResult result) {
		this.result = result;
	}

	public int getSlot() {
		return this.slot;
	}

	public ItemStack getItem() {
		return this.item;
	}

	public ClickType getClickType() {
		return this.clickType;
	}

	public Player getPlayer() {
		return this.player;
	}

}
