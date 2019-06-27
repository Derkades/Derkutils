package xyz.derkades.derkutils.bukkit.menu;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

/**
 * Event fired when an {@link IconMenu} is closed. This is not a bukkit event, even though it extends PlayerEvent.
 */
public class MenuCloseEvent extends PlayerEvent {

	private final CloseReason reason;

	MenuCloseEvent(final Player player, final CloseReason reason) {
		super(player);
		this.reason = reason;
	}

	public CloseReason getReason() {
		return this.reason;
	}

	@Override
	public HandlerList getHandlers() {
		return null;
	}

}