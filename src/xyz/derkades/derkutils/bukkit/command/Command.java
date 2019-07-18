package xyz.derkades.derkutils.bukkit.command;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import xyz.derkades.derkutils.bukkit.command.parameter.Parameter;

public class Command {
	
	private final String name;
	private final String description;
	private final String usage;
	private final String[] aliases;
	final List<Command> subcommands;
	final List<Parameter<?>> parameters;
	Consumer<List<Parameter<?>>> callback;
	
	public Command(String name, String description, String usage, String... aliases) {
		this.name = name;
		this.description = description;
		this.usage = usage;
		this.aliases = aliases;
		this.subcommands = new ArrayList<>();
		this.parameters = new ArrayList<>();
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
	
//	public Command[] getSubCommands() {
//		return this.subcommands.toArray(new Command[] {});
//	}
	
	/**
	 * Add a subcommand. If multiple subcommands with the same name are provided, one will be chosen arbitrarily.
	 */
	public void addSubcommand(Command subcommand) {
		subcommands.add(subcommand);
	}
	
	/**
	 * Callback used when no arguments are provided
	 * Will overwrite the previously set callback.
	 */
	public void setCallback(Consumer<List<Parameter<?>>> callback) {
		this.callback = callback;
	}
	
	/**
	 * Add a parameter to this command. Should be called in the constructor.
	 * @param parameter Parameter to add
	 */
	public void addParameter(Parameter<?> parameter) {
		this.parameters.add(parameter);
	}
	

}
