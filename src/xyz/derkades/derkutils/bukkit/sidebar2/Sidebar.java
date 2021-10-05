package xyz.derkades.derkutils.bukkit.sidebar2;

import net.kyori.adventure.text.Component;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Sidebar {

    private final Scoreboard scoreboard = Objects.requireNonNull(Bukkit.getScoreboardManager(),
            "Can only create scoreboard after a world has been loaded").getNewScoreboard();
//    private final EnumMap<DisplaySlot, Objective> objectives = new EnumMap<>(DisplaySlot.class);
//    private final Objective[] objectives;
    private final int previousLength = 0;
    private final List<Component> entries = new ArrayList<>();

    public Sidebar(DisplaySlot[] displaySlots) {
//        this.objectives = new Objective[displaySlots.length];
//        for (int i = 0; i < displaySlots.length; i++) {
//            this.scoreboard.obj
//        }
//        for (DisplaySlot slot : displaySlots) {
//           Objective obj = this.scoreboard.registerNewObjective( + ,)
//        }
        for (DisplaySlot slot : displaySlots) {
            Objective obj = this.scoreboard.registerNewObjective(slot.name(), "dummy", "derkutils sidebar");
            obj.setDisplaySlot(slot);
        }
    }

    private String generateInvisibleEntry(int index) {
        return StringUtils.repeat(ChatColor.COLOR_CHAR + "r", index + 1); // TODO maybe +1 isn't necessary
    }

    public void update() {
        for (int i = 0; i < this.previousLength; i++) {
            this.scoreboard.resetScores(generateInvisibleEntry(i));
            this.scoreboard.getTeams().forEach(Team::unregister);
        }

        for (int i = 0; i < this.entries.size(); i++) {
            Component entryDisplayText = this.entries.get(i);
            Team team = this.scoreboard.registerNewTeam(String.valueOf(i));
            team.prefix(entryDisplayText);
            String entry = generateInvisibleEntry(i);
            team.addEntry(entry);

            for (Objective objective : this.scoreboard.getObjectives()) {
                objective.getScore(entry).setScore(i);
            }
        }
    }

    public List<Component> getEntries() {
        return this.entries;
    }

//    public void addEntry(Component text) {
//        this.entries.add(text);
//    }

}
