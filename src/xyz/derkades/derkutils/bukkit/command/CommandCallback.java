package xyz.derkades.derkutils.bukkit.command;

import java.util.List;
import java.util.function.BiConsumer;

import org.bukkit.command.CommandSender;

import xyz.derkades.derkutils.bukkit.command.parameter.Parameter;

@FunctionalInterface
public interface CommandCallback extends BiConsumer<CommandSender, List<Parameter<?>>> {

}
