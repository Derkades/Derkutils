package xyz.derkades.derkutils.bukkit.menu;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Event fired when a player clicks on an item in an {@link IconMenu}.
 * This event extends PlayerEvent for convenience, but it is not a bukkit event.
 */
public class OptionClickEvent extends PlayerEvent {

	private final int position;
	private final ItemStack item;
	private final ClickType click;

	OptionClickEvent(final Player player, final int position, final ItemStack item, final ClickType click) {
		super(player);

		this.position = position;
		this.item = item;
		this.click = click;
	}

	/**
	 * @return Slot number of the item clicked
	 */
	public int getPosition() {
		return this.position;
	}

	/**
	 * @return Display name of the item clicked.
	 * @throws NullPointerException When item does not have a display name
	 */
	public String getName() {
		return this.item.getItemMeta().getDisplayName();
	}

	/**
	 * @return The clicked item stack
	 */
	public ItemStack getItemStack() {
		return this.item;
	}
	
	public ClickType getClickType() {
		return this.click;
	}

	@Override
	public HandlerList getHandlers() {
		return null;
	}
}
