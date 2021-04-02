package xyz.derkades.derkutils.bukkit.menu;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

/**
 * Event fired when a player clicks on an item in an {@link IconMenu}.
 */
public class OptionClickEvent {

	private final Player player;
	private final int position;
	private final ItemStack item;
	private final ClickType click;

	OptionClickEvent(final Player player, final int position, final ItemStack item, final ClickType click) {
		this.player = player;
		this.position = position;
		this.item = item;
		this.click = click;
	}

	public Player getPlayer() {
		return this.player;
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

}
