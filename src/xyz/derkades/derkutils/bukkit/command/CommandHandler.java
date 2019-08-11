package xyz.derkades.derkutils.bukkit.command;

import java.util.Arrays;
import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

import xyz.derkades.derkutils.ListUtils;
import xyz.derkades.derkutils.bukkit.command.parameter.Parameter;
import xyz.derkades.derkutils.bukkit.command.parameter.ParameterParseException;
import xyz.derkades.derkutils.bukkit.reflection.ReflectionUtil;

public class CommandHandler extends org.bukkit.command.Command {

	private final Command command;

	protected CommandHandler(final Command command) {
		super(command.getName(), command.getDescription(), command.getUsage(), Arrays.asList(command.getAliases()));

		this.command = command;
	}

	public static CommandHandler registerCommand(final Plugin plugin, final Command command) {
		final CommandHandler handler = new CommandHandler(command);
		ReflectionUtil.registerCommand(plugin.getName(), handler);
		return handler;
	}

	@Override
	public boolean execute(final CommandSender sender, final String label, final String[] args) {
		if (!this.command.hasPermission(sender)) {
			this.command.messageHandler.sendNoPermissionMessage(this.command, sender, label, args, this.command.permission);
		}

		Command subcommand = this.command; // This variable is called "subcommand", but if 'this.command' does not have any subcommands 'subcommand' will be same as 'this.command'
		int layer = 0;
		while (!subcommand.subcommands.isEmpty()) {
			if (args.length <= layer) {
				// Stop checking deeper for subcommands if the user hasn't provided more arguments
				break;
			}

			for (final Command subcommand2 : subcommand.subcommands) {
				if (subcommand2.getName().equalsIgnoreCase(args[layer])) {
					subcommand = subcommand2;
					layer++;
					continue;
				}
			}
		}

		if (subcommand.callback == null) {
			// No callback provided. This is only allowed if the command has subcommands.
			if (subcommand.subcommands.isEmpty()) {
				throw new IllegalArgumentException("A command must have a default callback and/or subcommands");
			}

			// It is not clear which subcommand the user would want to use, so display all subcommands.
			// Display only the first level of subcommands to avoid chat spam
			subcommand.messageHandler.sendSubcommandsMessage(subcommand, sender, label, args);
			return true;
		}

		if (subcommand == this.command) {
			// No valid subcommand found
			subcommand.messageHandler.sendSubcommandsMessage(subcommand, sender, label, args);
			return true;
		} else {
			if (!subcommand.hasPermission(sender)) {
				subcommand.messageHandler.sendNoPermissionMessage(this.command, sender, label, args, this.command.permission);
			}

			final String[] parameterStrings = ListUtils.subarray(args, 0, layer);
			final List<Parameter<?>> parameters = subcommand.parameters;
			if (args.length - layer > parameters.size()) {
				// The number of arguments provided, exluding subcommands, is larger than the number of parameters
				// TODO Send too many parameters message
				return true;
			}

			for (int i = 0; i < parameters.size(); i++) {
				final Parameter<?> parameter = parameters.get(i);
				if (parameter.isOptional() || (parameterStrings.length > (i + 1))) { // If this parameter was specified, or if the parameter is optional
					// Try parsing the parameter
					final String specifiedParameter = parameterStrings[i];
					try {
						parameter.setStringValue(specifiedParameter);
					} catch (final ParameterParseException e) {
						subcommand.messageHandler.sendInvalidParameterMessage(subcommand, sender, label, args, parameter, e);
						return true;
					}
				} else {
					subcommand.messageHandler.sendMissingParameterMessage(subcommand, sender, label, args, parameter);
					return true;
				}
			}

			subcommand.callback.accept(parameters);
			return true;
		}
	}

}
