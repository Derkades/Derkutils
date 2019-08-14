package xyz.derkades.derkutils.bukkit.command;

import org.bukkit.command.CommandSender;

import xyz.derkades.derkutils.bukkit.command.parameter.Parameter;
import xyz.derkades.derkutils.bukkit.command.parameter.ParameterParseException;

public abstract class MessageHandler {

	protected final Command parentCommand;

	public MessageHandler(final Command parentCommand) {
		this.parentCommand = parentCommand;
	}

	public abstract void sendInvalidSubCommandHelpMessage(Command command,
			CommandSender sender, String label, String[] args,
			String invalidSubcommandName);

	public abstract void sendMissingParameterMessage(Command command,
			CommandSender sender, String label, String[] args,
			Parameter<?> parameter);

	public abstract void sendTooManyParametersMessage(Command command,
			CommandSender sender, String label, String[] args);

	public abstract void sendInvalidParameterMessage(Command command,
			CommandSender sender, String label, String[] args,
			Parameter<?> parameter, ParameterParseException e);

	public abstract void sendSubcommandsMessage(Command command,
			CommandSender sender, String label, String[] args);

	public abstract void sendNoPermissionMessage(Command command,
			CommandSender sender, String label, String[] args,
			String permission);

	public abstract void sendNotPlayerMessage(Command command,
			CommandSender sender, String label, String[] args);

}