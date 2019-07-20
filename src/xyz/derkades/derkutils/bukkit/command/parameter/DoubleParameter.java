package xyz.derkades.derkutils.bukkit.command.parameter;

import xyz.derkades.derkutils.AssertionException;
import xyz.derkades.derkutils.constraints.DoubleConstraint;

public class DoubleParameter extends Parameter<Double> {

	private Integer min;
	private Integer max;
	
	public DoubleParameter(final String name) {
		super(name);
		
		this.addConstraint(new DoubleConstraint());
	}
	
	public void setMinimum(final int min) {
		this.min = min;
	}
	
	public void setMaximum(final int max) {
		this.max = max;
	}

	@Override
	protected Double parse(final String string) {
		return Double.valueOf(string);
	}

	@Override
	public String getConstraintMessage() {
		if (this.min == null || this.max == null)
			return "Number";
		else if (this.min != null && this.max == null)
			return "Number greater than " + this.min;
		else if (this.min != null && this.max != null)
			return "Number greater " + this.min +" and less than " + this.max;
		else if (this.min == null && this.max != null)
			return "Number less than " + this.max;
		else
			throw new AssertionException();
	}

}
