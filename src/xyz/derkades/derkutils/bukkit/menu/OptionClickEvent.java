package xyz.derkades.derkutils.bukkit.menu;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Event fired when a player clicks on an item in an {@link IconMenu}.
 */
public class OptionClickEvent extends SlotClickEvent {

	private final ItemStack item;

	OptionClickEvent(final Player player,
					 final int position,
					 final ClickType click,
					 final @Nullable ItemStack item) {
		super(player, position, click);
		this.item = item;
	}

	/**
	 * @return Display name of the item clicked.
	 */
	public @Nullable String getName() {
		if (this.item == null) {
			return null;
		}
		@SuppressWarnings("null")
		final ItemMeta meta = this.item.getItemMeta();
		return meta == null ? null : meta.getDisplayName();
	}

	/**
	 * @return The clicked item stack
	 */
	public ItemStack getItemStack() {
		return this.item;
	}

}
