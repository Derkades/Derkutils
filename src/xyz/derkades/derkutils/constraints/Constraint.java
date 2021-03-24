package xyz.derkades.derkutils.constraints;

@Deprecated
public abstract class Constraint {
	
	public abstract boolean validate(String parameter);
	
	public abstract String getUserFriendlyString();

}
