package xyz.derkades.derkutils.bukkit.menu;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

/**
 * Event fired when an {@link IconMenu} is closed.
 */
public class MenuCloseEvent {

	private final CloseReason reason;
	private final OfflinePlayer player;

	MenuCloseEvent(final OfflinePlayer player, final CloseReason reason) {
		this.player = player;
		this.reason = reason;
	}

	public OfflinePlayer getOfflinePlayer() {
		return this.player;
	}

	public Player getPlayer() {
		return (Player) this.player;
	}

	public CloseReason getReason() {
		return this.reason;
	}

}