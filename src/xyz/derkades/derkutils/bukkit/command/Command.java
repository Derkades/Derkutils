package xyz.derkades.derkutils.bukkit.command;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.bukkit.command.CommandSender;

import xyz.derkades.derkutils.bukkit.command.parameter.Parameter;

public class Command {

	private final Command parent;
	private final String name;
	private final String description;
	private final String usage;
	private final String[] aliases;
	final List<Command> subcommands;
	final List<Parameter<?>> parameters;
	CommandCallback callback;
	final MessageHandler messageHandler;
	String permission;
	boolean requirePlayer = false;

	public Command(final Command parent, final MessageHandler helpHandler, final String name, final String description, final String usage, final String... aliases) {
		this.parent = parent;
		this.name = name;
		this.description = description;
		this.usage = usage;
		this.aliases = aliases;
		this.subcommands = new ArrayList<>();
		this.parameters = new ArrayList<>();
		this.messageHandler = helpHandler;
	}

	public String getName() {
		return this.name;
	}

	public String getDescription() {
		return this.description;
	}

	public String getUsage() {
		return this.usage;
	}

	public String[] getAliases() {
		return this.aliases;
	}

	public boolean isSubcommand() {
		return this.parent != null;
	}

	public Command getParentCommand() {
		if (!this.isSubcommand())
			throw new UnsupportedOperationException("Command must be a subcommand to use Command::getParent");

		return this.parent;
	}

	/**
	 * Add a subcommand. If multiple subcommands with the same name are provided, one will be chosen arbitrarily.
	 */
	public void addSubcommand(final Command subcommand) {
		this.subcommands.add(subcommand);
	}

	/**
	 * Callback used when no subcommand is provided. This method
	 * will overwrite a previously set callback. If a callback
	 * is not set, the command <strong>must</strong> have subcommands.
	 * If this command does not have subcommands, it must have a
	 * callback. If the command does have subcommands, providing a
	 * callback is optional but can be useful, for instance if you
	 * want to display a custom help message.
	 */
	public void setCallback(final CommandCallback callback) {
		this.callback = callback;
	}

	/**
	 * Add a parameter to this command. Should be called in the constructor.
	 * @param parameter Parameter to add
	 */
	public void addParameter(final Parameter<?> parameter) {
		this.parameters.add(parameter);
	}

	public String getParametersString() {
		final StringBuilder builder = new StringBuilder();
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
		final List<String> parentCommandNames = new ArrayList<>();
		Command command = this;
		while (command.isSubcommand()) {
			command = command.getParentCommand();
			parentCommandNames.add(command.getName() + " ");
		}

		final StringBuilder builder = new StringBuilder();
		parentCommandNames.stream().sorted(Comparator.reverseOrder()).forEach(builder::append);
		builder.append(this.getParametersString());
		return builder.toString();
	}

	public boolean hasPermission(final CommandSender sender) {
		return this.permission == null ? true : sender.hasPermission(this.permission);
	}

	public void setPermission(final String permission) {
		this.permission = permission;
	}

	public void setRequirePlayer(final boolean requirePlayer) {
		this.requirePlayer = requirePlayer;
	}

}
