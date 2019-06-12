package xyz.derkades.derkutils.bukkit.command;

public class Subcommand {
	
	private String name;
	private String description;
	private String usage;
	private CommandCallback callback;
	private String[] aliases;
	
	/**
	 * 
	 * @param name
	 * @param description
	 * @param usage Usage string, without the main command, and %s for the sub command name. For example: '%s <player>'
	 * @param callback
	 * @param aliases
	 */
	public Subcommand(String name, String description, String usage, CommandCallback callback, String... aliases) {
		this.name = name;
		this.description = description;
		this.usage = usage;
		this.callback = callback;
		this.aliases = aliases;
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
	
	public CommandCallback getCallback() {
		return callback;
	}
	
	public String[] getAliases() {
		return aliases;
	}
	
}
