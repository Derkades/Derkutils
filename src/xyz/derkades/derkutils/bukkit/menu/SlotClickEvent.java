package xyz.derkades.derkutils.bukkit.menu;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.checkerframework.checker.nullness.qual.NonNull;

public class SlotClickEvent {

	private final Player player;
	private final int position;
	private final ClickType click;

	SlotClickEvent(final Player player,
					 final int position,
					 final ClickType click) {
		this.player = player;
		this.position = position;
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

	public @NonNull ClickType getClickType() {
		return this.click;
	}

}
