package xyz.derkades.derkutils.bukkit.menu;

import org.bukkit.entity.Player;

/**
 * Event fired when an {@link IconMenu} is closed.
 */
public class MenuCloseEvent {

	private final CloseReason reason;
	private final Player player;

	MenuCloseEvent(final Player player, final CloseReason reason) {
		this.player = player;
		this.reason = reason;
	}

	public Player getPlayer() {
		return this.player;
	}

	public CloseReason getReason() {
		return this.reason;
	}

}