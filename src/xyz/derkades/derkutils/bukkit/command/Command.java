package xyz.derkades.derkutils.bukkit.command;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

public class Command {
	
	private final List<Subcommand> subcommands;
	private CommandCallback noSubcommandCallback;
	
	
	public Command(Plugin plugin, String name, String description, String usage, CommandConfig config, String... aliases) {
		this.subcommands = new ArrayList<>();
		
		// TODO Register command
	}
	
	public Command(Plugin plugin, String name, String description, String usage, String... aliases) {
		this(plugin, name, description, usage, CommandConfig.DEFAULT, aliases);
	}
	
	public Command(Plugin plugin, String name, String description, String... aliases) {
		this(plugin, name, description, null, CommandConfig.DEFAULT, aliases);
	}
	
	/**
	 * Add a subcommand. If multiple subcommands with the same name are provided, one will be chosen arbitrarily.
	 */
	public void addCallback(Subcommand subcommand) {
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
			
			for (Subcommand subcommand : subcommands) {
				
			}
			
			if (!success) {
				// TODO Display hover help message
			}
			
			return true;
		}
		
	}
	

}
