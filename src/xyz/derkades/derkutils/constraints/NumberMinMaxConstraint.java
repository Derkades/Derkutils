package xyz.derkades.derkutils.constraints;

@Deprecated
public class NumberMinMaxConstraint extends Constraint {

	private final int min;
	private final int max;
	
	public NumberMinMaxConstraint(int min, int max) {
		this.min = min;
		this.max = max;
	}
	
	@Override
	public boolean validate(String parameter) {
		try {
			double i = Double.parseDouble(parameter);
			return i > min && i < max;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	@Override
	public String getUserFriendlyString() {
		return String.format("Number more than %s and less than %s", min, max);
	}

}
