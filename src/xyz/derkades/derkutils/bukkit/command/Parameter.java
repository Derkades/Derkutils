package xyz.derkades.derkutils.bukkit.command;

import java.util.ArrayList;
import java.util.List;

public class Parameter<T> {
	
	private String name;
	private List<ParameterConstraint> constraints;
	
	private T value;
	private String stringvalue;
	
	public Parameter(String name) {
		this.name = name;
		this.constraints = new ArrayList<>();
	}
	
	public T getValue() {
		return null;
	}
	
	public void setValue(T value) {
		this.value = value;
	}
	
	public void setValue(String value)
	
	public void setType(ParameterType type) {
		
	}
	
	public void addConstraint() {
		
	}

}
