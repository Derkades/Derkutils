package xyz.derkades.derkutils.bukkit.command.parameter;

import java.util.ArrayList;
import java.util.List;

import xyz.derkades.derkutils.constraints.Constraint;

public abstract class Parameter<T> {
	
	private final String name;
	private final List<Constraint> constraints;
	
	private T value;
	
	private final boolean optional;
	
	public Parameter(final String name) {
		this.name = name;
		this.constraints = new ArrayList<>();
		this.optional = false;
	}
	
	public Parameter(final String name, final T def) {
		this.name = name;
		this.constraints = new ArrayList<>();
		this.value = def;
		this.optional = true;
	}
	
	public T getValue() {
		return this.value;
	}
	
	public void setStringValue(final String value) throws ParameterParseException {
		this.value = this.parse(value);
	}
	
	public void addConstraint(final Constraint constraint) {
		this.constraints.add(constraint);
	}
	
	public String getName() {
		return this.name;
	}
	
	public boolean isOptional() {
		return this.optional;
	}
	
	@Override
	public String toString() {
		if (this.optional)
			return "[" + this.name + "]";
		else
			return "<" + this.name + ">";
	}
	
	protected abstract T parse(String string) throws ParameterParseException;
	
	public abstract String getConstraintMessage();

}
