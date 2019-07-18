package xyz.derkades.derkutils.bukkit.constraints;

import org.bukkit.Bukkit;

import xyz.derkades.derkutils.constraints.Constraint;

public class OnlinePlayerNameConstraint extends Constraint {

	@Override
	public boolean validate(String parameter) {
		return Bukkit.getPlayerExact(parameter) != null;
	}

	@Override
	public String getUserFriendlyString() {
		return "Username of an online player";
	}

}
