package xyz.derkades.derkutils.constraints;

public class IntegerConstraint extends Constraint {

	@Override
	public boolean validate(String parameter) {
		try {
			Integer.valueOf(parameter);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	@Override
	public String getUserFriendlyString() {
		return "Whole number";
	}

}
