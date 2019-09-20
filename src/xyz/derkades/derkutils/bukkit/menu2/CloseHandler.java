package xyz.derkades.derkutils.bukkit.menu2;

import org.bukkit.entity.Player;

@FunctionalInterface
public interface CloseHandler {

	public void onClose(Player player, CloseReason reason);

}
