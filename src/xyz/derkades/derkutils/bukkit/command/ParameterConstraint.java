package xyz.derkades.derkutils.bukkit.command;

@FunctionalInterface
public interface ParameterConstraint {
	
	public boolean validate(String parameter);

}
