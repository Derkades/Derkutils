package xyz.derkades.derkutils.bukkit.command;

import org.bukkit.command.CommandSender;

import net.md_5.bungee.api.chat.ComponentBuilder;

public class DefaultHelpMessageHandler extends HelpMessageHandler {

	public DefaultHelpMessageHandler(Command command) {
		super(command);
	}

	@Override
	public void sendHelpMessage(CommandSender sender, String label, String[] args) {
		for (Command subcommand : command.getSubCommands()) {
			for (int i = 0; i < args.length; i++) {
				
			}
			
			sender.spigot().sendMessage(new ComponentBuilder("")
					.append("/" + label + " ")
					.append(subcommand.getName())
					.create());
		}
	}

}
