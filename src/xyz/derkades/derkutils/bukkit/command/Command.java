package xyz.derkades.derkutils.bukkit.command;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

import xyz.derkades.derkutils.bukkit.command.parameter.Parameter;

public class Command {
	
	private final Command parent;
	private final String name;
	private final String description;
	private final String usage;
	private final String[] aliases;
	final List<Command> subcommands;
	final List<Parameter<?>> parameters;
	Consumer<List<Parameter<?>>> callback;
	final HelpMessageHandler helpHandler;
	
	public Command(Command parent, String name, String description, String usage, String... aliases) {
		this.parent = parent;
		this.name = name;
		this.description = description;
		this.usage = usage;
		this.aliases = aliases;
		this.subcommands = new ArrayList<>();
		this.parameters = new ArrayList<>();
		this.helpHandler = new DefaultHelpMessageHandler();
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
	
	public boolean isSubcommand() {
		return parent != null;
	}
	
	public Command getParentCommand() {
		if (!this.isSubcommand()) {
			throw new UnsupportedOperationException("Command must be a subcommand to use Command::getParent");
		}
		
		return parent;
	}
	
	/**
	 * Add a subcommand. If multiple subcommands with the same name are provided, one will be chosen arbitrarily.
	 */
	public void addSubcommand(Command subcommand) {
		subcommands.add(subcommand);
	}
	
	/**
	 * Callback used when no arguments are provided. This method
	 * will overwrite a previously set callback. If a callback 
	 * is not set, the command <strong>must</strong> have subcommands.
	 * If this command does not have subcommands, it must have a
	 * callback. If the command does have subcommands, providing a 
	 * callback is optional but can be useful, for instance if you 
	 * want to display a custom help message.
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

	public String getParametersString() {
		StringBuilder builder = new StringBuilder();
		this.parameters.forEach((p) -> builder.append(p.toString()));
		return builder.toString();
	}
	
	/**
	 * 
	 * @return Usage string, without slash prefix, including any parent commands or parameters. Example (where {@code this::getName() == "subcommandtwo"}): 
	 * <pre>{@code parent subcommandone subcommandtwo <param> [optparam]}</pre>
	 * Parameter syntax is defined by {@link Parameter#toString()}
	 */
	public String getUsageString() {
		List<String> parentCommandNames = new ArrayList<>();
		Command command = this;
		while (command.isSubcommand()) {
			command = command.getParentCommand();
			parentCommandNames.add(command.getName() + " ");
		}
		
		StringBuilder builder = new StringBuilder();
		parentCommandNames.stream().sorted(Comparator.reverseOrder()).forEach(builder::append);
		builder.append(this.getParametersString());
		return builder.toString();
	}
	
}
