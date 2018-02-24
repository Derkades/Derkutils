package xyz.derkades.derkutils.bukkit.menu;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

// Note: This is not a bukkit event, even though it extends PlayerEvent.
public class MenuCloseEvent extends PlayerEvent {

	private CloseReason reason;
	
	MenuCloseEvent(Player player, CloseReason reason) {
		super(player);
		this.reason = reason;
	}
	
	public CloseReason getReason() {
		return reason;
	}

	@Override
	public HandlerList getHandlers() {
		return null;
	}
	
}