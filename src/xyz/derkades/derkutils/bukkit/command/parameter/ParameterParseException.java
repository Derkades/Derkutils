package xyz.derkades.derkutils.bukkit.command.parameter;

public class ParameterParseException extends Exception {

	private static final long serialVersionUID = -8676498160593559180L;
	
	private final Parameter<?> parameter;
	
	public ParameterParseException(Parameter<?> parameter) {
		this.parameter = parameter;
	}
	
	public Parameter<?> getParameter() {
		return parameter;
	}
	
	@Override
	public String getMessage() {
		return parameter.getConstraintMessage();
	}
	
	

}
