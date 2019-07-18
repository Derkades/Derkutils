package xyz.derkades.derkutils.bukkit.command.parameter;

import xyz.derkades.derkutils.AssertionException;
import xyz.derkades.derkutils.constraints.DoubleConstraint;

public class DoubleParameter extends Parameter<Double> {

	private Integer min;
	private Integer max;
	
	public DoubleParameter(String name) {
		super(name);
		
		this.addConstraint(new DoubleConstraint());
	}
	
	public void setMinimum(int min) {
		this.min = min;
	}
	
	public void setMaximum(int max) {
		this.max = max;
	}

	@Override
	protected Double parse(String string) {
		return Double.valueOf(string);
	}

	@Override
	protected String getConstraintMessage() {
		if (min == null || max == null) {
			return "Number";
		} else if (min != null && max == null) {
			return "Number greater than " + min;
		} else if (min != null && max != null) {
			return "Number greater " + min +" and less than " + max;
		} else if (min == null && max != null) {
			return "Number less than " + max;
		} else {
			throw new AssertionException();
		}
	}

}
