package xyz.derkades.derkutils.bukkit.command;

import java.util.Arrays;
import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

import xyz.derkades.derkutils.bukkit.command.parameter.Parameter;
import xyz.derkades.derkutils.bukkit.command.parameter.ParameterParseException;
import xyz.derkades.derkutils.bukkit.reflection.ReflectionUtil;

public class CommandHandler extends org.bukkit.command.Command {
	
	private Command command;
	
	protected CommandHandler(Command command) {
		super(command.getName(), command.getDescription(), command.getUsage(), Arrays.asList(command.getAliases()));
		
		this.command = command;
	}
	
	public static CommandHandler registerCommand(Plugin plugin, Command command) {
		CommandHandler handler = new CommandHandler(command);
		ReflectionUtil.registerCommand(plugin.getName(), handler);
		return handler;
	}

	@Override
	public boolean execute(CommandSender sender, String label, String[] args) {
		if (args.length == 0) {
			// TODO Send generic help message
		}

		Command command = this.command;
		int layer = 0;
		while (!command.subcommands.isEmpty()) {
			for (Command subcommand : command.subcommands) {
				if (subcommand.getName().equalsIgnoreCase(args[layer])) {
					command = subcommand;
					layer++;
					continue;
				}
			}
		}
		
		if (command == this.command) {
			// No valid subcommand found
			// TODO Send generic help message
			return true;
		} else {
			List<Parameter<?>> parameters = command.parameters;
			if (args.length - layer > parameters.size()) {
				// The number of arguments provided, exluding subcommands, is larger than the number of parameters
				// TODO Send too many parameters message
				return true;
			}
			
			for (int i = 0; i < parameters.size(); i++) {
				Parameter<?> parameter = parameters.get(i);
				if (parameter.isOptional() || (args.length > (layer + i + 1))) { // If this parameter was specified, or if the parameter is optional
					// Try parsing the parameter
					String specifiedParameter = args[layer + i];
					try {
						parameter.setStringValue(specifiedParameter);
					} catch (ParameterParseException e) {
						// TODO Handle invalid parameter
					}
				} else {
					// TODO Handle missing parameter
				}
			}
			
			command.callback.accept(parameters);
		}
		
		return true;
	}

}
