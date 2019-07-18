package xyz.derkades.derkutils.bukkit.command.parameter;

public class StringParameter extends Parameter<String> {

	public StringParameter(String name) {
		super(name);
	}
	
	public StringParameter(String name, String def) {
		super(name, def);
	}

	@Override
	protected String parse(String string) throws ParameterParseException {
		return string;
	}

	@Override
	protected String getConstraintMessage() {
		return "String";
	}

}
