package xyz.derkades.derkutils.constraints;

public class BooleanConstraint extends Constraint {

	@Override
	public boolean validate(String parameter) {
		return parameter.equalsIgnoreCase("true") || parameter.equalsIgnoreCase("false");
	}

	@Override
	public String getUserFriendlyString() {
		return "Boolean ('true' or 'false')";
	}

}
