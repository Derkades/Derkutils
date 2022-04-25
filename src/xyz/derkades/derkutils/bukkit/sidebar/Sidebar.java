package xyz.derkades.derkutils.bukkit.sidebar;

import com.google.common.base.Strings;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Objects;

public class Sidebar {

	private final @NonNull Scoreboard scoreboard;
	private final @NonNull Objective objective;
	private int entries = 0;

	public Sidebar(final @NonNull Component displayName) {
		this(displayName,
				Objects.requireNonNull(Bukkit.getScoreboardManager(),
				"Can only create scoreboard after a world has been loaded").getNewScoreboard());
	}

	public Sidebar(final @NonNull Component displayName,
				   final @NonNull Scoreboard scoreboard) {
		this.scoreboard = scoreboard;
		this.objective = scoreboard.registerNewObjective("sb", "dummy", displayName);
		this.objective.setDisplaySlot(DisplaySlot.SIDEBAR);
	}


	private @NonNull String getInvisibleEntry(final int index) {
		return Strings.repeat(ChatColor.COLOR_CHAR + "r", index);
	}

	public void clearEntries() {
		this.scoreboard.getTeams().forEach(Team::unregister);
		for (int i = 0; i < this.entries; i++) {
			this.scoreboard.resetScores(getInvisibleEntry(i));
		}
		this.entries = 0;
	}

	public void addEntry(final @NonNull Component text) {
		int i = this.entries++;
		Team team = this.scoreboard.registerNewTeam(String.valueOf(i));
		team.prefix(text);
		String entry = getInvisibleEntry(i);
		team.addEntry(entry);
		for (int j = 0; j < this.entries; j++) {
			this.objective.getScore(getInvisibleEntry(j)).setScore(this.entries - j);
		}
	}

	public void setEntry(final int i,
						 final @NonNull Component text) {
		if (i >= this.entries) {
			throw new IllegalArgumentException("There are only " + this.entries + " entries but you are trying to modify index " + i);
		}

		Team team = this.scoreboard.getTeam(String.valueOf(i));
		if (team == null) {
			throw new IllegalStateException();
		}
		team.prefix(text);
	}

	public void showTo(final @NonNull Player player) {
		player.setScoreboard(this.scoreboard);
	}

	public void hideFrom(final @NonNull Player player) {
		player.setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
	}

}
