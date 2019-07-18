package xyz.derkades.derkutils.bukkit.command;

import org.bukkit.command.CommandSender;

public abstract class HelpMessageHandler {
	
	protected Command command;
	
	public HelpMessageHandler(Command command) {
		this.command = command;
	}
	
	public abstract void sendHelpMessage(CommandSender sender, String label, String[] args);

}
