package xyz.derkades.derkutils.bukkit.menu;

public enum CloseReason {
	
	/**
	 * When the player closes the menu, for example by pressing escape.
	 */
	PLAYER_CLOSED, 
	
	/**
	 * When the menu has been closed using the {@link IconMenu#close()} method.
	 */
	FORCE_CLOSE,
	
	/**
	 * When the menu has been closed because {@link IconMenu#onOptionClick(OptionClickEvent)} has returned true
	 */
	ITEM_CLICK,
	
}
