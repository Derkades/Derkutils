package xyz.derkades.derkutils.bukkit.menu;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Event fired when a player clicks on an item in an {@link IconMenu}.
 */
public class OptionClickEvent {

	private final @NonNull Player player;
	private final int position;
	private final @Nullable ItemStack item;
	private final @NonNull ClickType click;

	OptionClickEvent(final @NonNull Player player,
					 final int position,
					 final @Nullable ItemStack item,
					 final @NonNull ClickType click) {
		this.player = player;
		this.position = position;
		this.item = item;
		this.click = click;
	}

	public @NonNull Player getPlayer() {
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
	public @Nullable ItemStack getItemStack() {
		return this.item;
	}

	public @NonNull ClickType getClickType() {
		return this.click;
	}

}
