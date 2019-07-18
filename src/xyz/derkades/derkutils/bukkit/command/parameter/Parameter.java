package xyz.derkades.derkutils.bukkit.command.parameter;

import java.util.ArrayList;
import java.util.List;

import xyz.derkades.derkutils.constraints.Constraint;

public abstract class Parameter<T> {
	
	private String name;
	private List<Constraint> constraints;
	
	private T value;
	
	private final boolean optional;
	
	public Parameter(String name) {
		this.name = name;
		this.constraints = new ArrayList<>();
		this.optional = false;
	}
	
	public Parameter(String name, T def) {
		this.name = name;
		this.constraints = new ArrayList<>();
		this.value = def;
		this.optional = true;
	}
	
	public T getValue() {
		return value;
	}
	
	public void setStringValue(String value) throws ParameterParseException {
		this.value = this.parse(value);
	}
	
	public void addConstraint(Constraint constraint) {
		constraints.add(constraint);
	}
	
	public String getName() {
		return name;
	}
	
	public boolean isOptional() {
		return optional;
	}
	
	protected abstract T parse(String string) throws ParameterParseException;
	
	protected abstract String getConstraintMessage();

}
