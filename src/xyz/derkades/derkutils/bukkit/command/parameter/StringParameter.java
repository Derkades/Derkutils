package xyz.derkades.derkutils.bukkit.command.parameter;

public class StringParameter extends Parameter<String> {

	public StringParameter(final String name) {
		super(name);
	}
	
	public StringParameter(final String name, final String def) {
		super(name, def);
	}

	@Override
	protected String parse(final String string) throws ParameterParseException {
		return string;
	}

	@Override
	public String getConstraintMessage() {
		return "String";
	}

}
