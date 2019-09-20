package xyz.derkades.derkutils.bukkit.menu2;

import xyz.derkades.derkutils.bukkit.menu.IconMenu;

public enum CloseReason {

	/**
	 * When the player closes the menu, for example by pressing the escape button.
	 */
	PLAYER_CLOSED,

	/**
	 * When the menu has been closed using the {@link IconMenu#close()} method.
	 */
	FORCE_CLOSE,

	/**
	 * When the menu has been closed because the click result has been set to {@link ClickResult#CLOSE} returned true.
	 */
	ITEM_CLICK,

}
