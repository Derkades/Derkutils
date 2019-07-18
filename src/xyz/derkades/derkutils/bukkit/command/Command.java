package xyz.derkades.derkutils.bukkit.command;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

public class Command {
	
	private final String name;
	private final String description;
	private final String usage;
	private final String[] aliases;
	private final List<Command> subcommands;
	private CommandCallback noSubcommandCallback;
	
	public Command(String name, String description, String usage, String... aliases) {
		this.name = name;
		this.description = description;
		this.usage = usage;
		this.aliases = aliases;
		
		this.subcommands = new ArrayList<>();
	}
	
	public Command(String name, String description, String... aliases) {
		this(name, description, null, aliases);
	}
	
	public Command(String name, String... aliases) {
		this(name, null, aliases);
	}
	
	public String getName() {
		return name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public String getUsage() {
		return usage;
	}
	
	public String[] getAliases() {
		return aliases;
	}
	
	public Command[] getSubCommands() {
		return this.subcommands.toArray(new Command[] {});
	}
	
	/**
	 * Add a subcommand. If multiple subcommands with the same name are provided, one will be chosen arbitrarily.
	 */
	public void addCallback(Command subcommand) {
		subcommands.add(subcommand);
	}
	
	/**
	 * Callback used when no arguments are provided
	 * Will overwrite the previously set callback.
	 */
	public void addCallback(CommandCallback callback) {
		noSubcommandCallback = callback;
	}

	private class Executor implements CommandExecutor {

		@Override
		public boolean onCommand(CommandSender arg0, org.bukkit.command.Command arg1, String arg2, String[] args) {
			boolean success = false;
			
			if (args.length == 0) {
				if (noSubcommandCallback != null) {
					success = noSubcommandCallback.callback();
				}
			}
			
			for (Command subcommand : subcommands) {
				
			}
			
			if (!success) {
				// TODO Display hover help message
			}
			
			return true;
		}
		
	}
	

}
