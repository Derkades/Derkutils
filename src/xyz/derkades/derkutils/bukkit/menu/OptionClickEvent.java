package xyz.derkades.derkutils.bukkit.menu;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Event fired when a player clicks on an item in an {@link IconMenu}.
 */
public class OptionClickEvent {

	@NotNull
	private final Player player;
	private final int position;
	@Nullable
	private final ItemStack item;
	@NotNull
	private final ClickType click;

	OptionClickEvent(@NotNull final Player player, final int position, @Nullable final ItemStack item, @NotNull final ClickType click) {
		this.player = player;
		this.position = position;
		this.item = item;
		this.click = click;
	}

	@NotNull
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
	 */
	@Nullable
	public String getName() {
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
	@Nullable
	public ItemStack getItemStack() {
		return this.item;
	}

	@NotNull
	public ClickType getClickType() {
		return this.click;
	}

}
