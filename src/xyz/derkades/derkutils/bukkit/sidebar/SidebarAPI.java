package xyz.derkades.derkutils.bukkit.sidebar;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;

public class SidebarAPI {

	private static final List<Sidebar> sidebars = new ArrayList<>();

	protected static void registerSidebar(final Sidebar sidebar) {
		sidebars.add(sidebar);
	}

	protected static void unregisterSidebar(final Sidebar sidebar) {
		sidebars.remove(sidebar);
	}

	/**
	 * Gets the Sidebar Object associated with the specified player. Note that this
	 * will only return a Sidebar Object if it has been shown to the player and then
	 * not been hidden again. Note also that this will only return a Sidebar Object
	 * if the specified player's scoreboard sidebar had been created with this API.
	 * 
	 * @param forPlayer (Player) - the player
	 * @return
	 */
	public static Sidebar getSidebar(final Player forPlayer) {
		if (forPlayer == null) {
			throw new NullPointerException("forPlayer cannot be null!");
		}

		final Objective obj = forPlayer.getScoreboard().getObjective(DisplaySlot.SIDEBAR);

		if (obj == null) {
			return null;
		}

		if (!sidebars.isEmpty()) {
			for (final Sidebar sidebar : sidebars) {
				if (sidebar.getTitle().equals(obj.getDisplayName())) {
					return sidebar;
				}
			}
		}

		return null;
	}

}
