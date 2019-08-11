package xyz.derkades.derkutils.constraints;

public class DoubleConstraint extends Constraint {
	
	@Override
	public boolean validate(String parameter) {
		try {
			Double.valueOf(parameter);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	@Override
	public String getUserFriendlyString() {
		return "Number";
	}

}
