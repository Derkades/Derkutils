package xyz.derkades.derkutils.constraints;

@Deprecated
public class ConstraintValidationException extends Exception {
	
	private static final long serialVersionUID = -960916237626495088L;
	
	private Constraint constraint;
	
	public ConstraintValidationException(Constraint constraint) {
		this.constraint = constraint;
	}
	
	public Constraint getConstraint() {
		return constraint;
	}
	
	@Override
	public String getMessage() {
		return constraint.getUserFriendlyString();
	}

}
