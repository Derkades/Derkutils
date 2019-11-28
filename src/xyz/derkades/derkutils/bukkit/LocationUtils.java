package xyz.derkades.derkutils.bukkit;

import org.bukkit.Location;

public class LocationUtils {

	public static boolean isIn2dBounds(final Location location, final Location cornerOne, final Location cornerTwo) {
		if (!location.getWorld().equals(cornerOne.getWorld())) {
			return false;
		}

		final int maxX = Math.max(cornerOne.getBlockX(), cornerTwo.getBlockX());
		final int minX = Math.min(cornerOne.getBlockX(), cornerTwo.getBlockX());
		final int maxZ = Math.max(cornerOne.getBlockZ(), cornerTwo.getBlockZ());
		final int minZ = Math.min(cornerOne.getBlockZ(), cornerTwo.getBlockZ());

		final int x = location.getBlockX();
		final int z = location.getBlockZ();

		return x > minX && x < maxX &&
				z > minZ && z < maxZ;
	}

}
