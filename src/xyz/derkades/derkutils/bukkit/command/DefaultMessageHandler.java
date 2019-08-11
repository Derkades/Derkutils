package xyz.derkades.derkutils.bukkit.command;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import xyz.derkades.derkutils.bukkit.command.parameter.Parameter;
import xyz.derkades.derkutils.bukkit.command.parameter.ParameterParseException;

public class DefaultMessageHandler extends MessageHandler {

	public DefaultMessageHandler(final Command parentCommand) {
		super(parentCommand);
	}

	@Override
	public void sendInvalidSubCommandHelpMessage(final Command command, final CommandSender sender, final String label, final String[] args,
			final String invalidSubcommandName) {

		final List<String> usageStrings = new ArrayList<>();

		if (command.subcommands.isEmpty()) { // This shouldn't ever happen.. right?
			usageStrings.add("/" + command.getUsageString());
		} else {
			for (final Command subcommand : command.subcommands) {
				usageStrings.add("/" + subcommand.getUsageString());
			}
		}

		final ComponentBuilder builder = new ComponentBuilder("Invalid subcommand").color(ChatColor.RED);
		for (final String usageString : usageStrings) {
			builder.append("\n -" + usageString).color(ChatColor.GRAY)
					.event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, usageString))
					.event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Click to run " + usageString).color(ChatColor.GRAY).create()));
		}
	}

	@Override
	public void sendMissingParameterMessage(final Command command, final CommandSender sender, final String label, final String[] args,
			final Parameter<?> parameter) {
		final String correctUsage = "/" + command.getUsage();
		if (sender instanceof Player) {
			final Player player = (Player) sender;
			player.spigot().sendMessage(new ComponentBuilder(String.format("Missing parameter %s (%s)!", parameter.toString(), parameter.getConstraintMessage())).color(ChatColor.RED)
					.append(correctUsage).color(ChatColor.GRAY)
					.event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, correctUsage))
					.event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Click to run " + correctUsage).color(ChatColor.GRAY).create()))
					.create());
		} else {
			sender.sendMessage(String.format(ChatColor.RED + "Missing parameter %s (%s)!", parameter.toString(), parameter.getConstraintMessage()));
		}


	}

	@Override
	public void sendTooManyParametersMessage(final Command command, final CommandSender sender, final String label, final String[] args) {
		final String correctUsage = "/" + command.getUsage();

		if (sender instanceof Player) {
			final Player player = (Player) sender;
			player.spigot().sendMessage(new ComponentBuilder("Too many paremeters! ").color(ChatColor.RED)
					.append(correctUsage).color(ChatColor.GRAY)
					.event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, correctUsage))
					.event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Click to run " + correctUsage).color(ChatColor.GRAY).create()))
					.create());
		} else {
			sender.sendMessage(ChatColor.RED + "Too many paremeters!");
		}

	}

	@Override
	public void sendInvalidParameterMessage(final Command command, final CommandSender sender, final String label, final String[] args,
			final Parameter<?> parameter, final ParameterParseException e) {
		final String correctUsage = "/" + command.getUsage();
		if (sender instanceof Player) {
			final Player player = (Player) sender;
			player.spigot().sendMessage(new ComponentBuilder(String.format("Invalid parameter %s!\nThis parameter must be \'%s\'!", parameter.toString(), parameter.getConstraintMessage())).color(ChatColor.RED)
					.append(correctUsage).color(ChatColor.GRAY)
					.event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, correctUsage))
					.event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Click to run " + correctUsage).color(ChatColor.GRAY).create()))
					.create());
		}
		sender.sendMessage(String.format(ChatColor.RED + "Invalid parameter %s!\nThis parameter must be \'%s\'!", parameter.toString(), parameter.getConstraintMessage()));
	}

	@Override
	public void sendSubcommandsMessage(final Command command, final CommandSender sender, final String label, final String[] args) {
		final List<String> usageStrings = new ArrayList<>();

		if (command.subcommands.isEmpty()) { // This shouldn't ever happen.. right?
			usageStrings.add("/" + command.getUsageString());
		} else {
			for (final Command subcommand : command.subcommands) {
				usageStrings.add("/" + subcommand.getUsageString());
			}
		}

		final ComponentBuilder builder = new ComponentBuilder("Subcommands:").color(ChatColor.WHITE);
		for (final String usageString : usageStrings) {
			builder.append("\n -" + usageString).color(ChatColor.GRAY)
					.event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, usageString))
					.event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Click to run " + usageString).color(ChatColor.GRAY).create()));
		}
	}

	@Override
	public void sendNoPermissionMessage(final Command command, final CommandSender sender, final String label, final String[] args,
			final String permission) {
		sender.sendMessage("You don't have permission to execute this command. (" + permission + ")");
	}

	@Override
	public void sendNotPlayerMessage(final Command command, final CommandSender sender, final String label, final String[] args) {
		sender.sendMessage("You must execute this command as a player");
	}

}
