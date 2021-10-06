package xyz.derkades.derkutils.bukkit.sidebar;

import net.kyori.adventure.text.Component;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class Siderbar {

	@NotNull
	private final Scoreboard scoreboard;
	@NotNull
	private final Objective objective;
	private int entries = 0;

	public Siderbar(@NotNull Component displayName) {
		this(displayName,
				Objects.requireNonNull(Bukkit.getScoreboardManager(),
				"Can only create scoreboard after a world has been loaded").getNewScoreboard());
	}

	public Siderbar(@NotNull Component displayName, @NotNull Scoreboard scoreboard) {
		this.scoreboard = scoreboard;
		this.objective = scoreboard.registerNewObjective("sb", "dummy", displayName);
		this.objective.setDisplaySlot(DisplaySlot.SIDEBAR);
	}

	@NotNull
	private String getInvisibleEntry(int index) {
		return StringUtils.repeat(ChatColor.COLOR_CHAR + "r", index);
	}

	public void clearEntries() {
		this.scoreboard.getTeams().forEach(Team::unregister);
		for (int i = 0; i < this.entries; i++) {
			this.scoreboard.resetScores(getInvisibleEntry(i));
		}
		this.entries = 0;
	}

	public void addEntry(@NotNull Component text) {
		int i = this.entries++;
		Team team = this.scoreboard.registerNewTeam(String.valueOf(i));
		team.prefix(text);
		String entry = getInvisibleEntry(i);
		team.addEntry(entry);
		for (int j = 0; j < this.entries; j++) {
			this.objective.getScore(getInvisibleEntry(j)).setScore(this.entries - j);
		}
	}

	public void setEntry(int i, @NotNull Component text) {
		if (i >= this.entries) {
			throw new IllegalArgumentException("There are only " + this.entries + " entries but you are trying to modify index " + i);
		}

		Team team = this.scoreboard.getTeam(String.valueOf(i));
		if (team == null) {
			throw new IllegalStateException();
		}
		team.prefix(text);
	}

	public void showTo(@NotNull Player player) {
		player.setScoreboard(this.scoreboard);
	}

	public void hideFrom(@NotNull Player player) {
		player.setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
	}

}
