package xyz.derkades.derkutils.bukkit.command;

import java.util.Arrays;
import java.util.List;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

public class CommandHandler extends org.bukkit.command.Command {
	
	private Command command;
	
	protected CommandHandler(Command command) {
		super(command.getName(), command.getDescription(), command.getUsage(), Arrays.asList(command.getAliases()));
		
		this.command = command;
	}

	
	public static void registerCommand(Plugin plugin, Command command) {
		// TODO Register command
	}

	@Override
	public boolean execute(CommandSender sender, String label, String[] args) {
		// TODO Auto-generated method stub
		return false;
	}

}
