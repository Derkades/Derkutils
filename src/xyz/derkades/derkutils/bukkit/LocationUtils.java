package xyz.derkades.derkutils.bukkit;

import org.bukkit.Location;

public class LocationUtils {

	public static boolean isIn2dBounds(final Location location, final Location cornerOne, final Location cornerTwo) {
		if (!location.getWorld().equals(cornerOne.getWorld())) {
			return false;
		}

		final double maxX = Math.max(cornerOne.getX(), cornerTwo.getX());
		final double minX = Math.min(cornerOne.getX(), cornerTwo.getX());
		final double maxZ = Math.max(cornerOne.getZ(), cornerTwo.getZ());
		final double minZ = Math.min(cornerOne.getZ(), cornerTwo.getZ());

		final double x = location.getX();
		final double z = location.getZ();

		return x >= minX && x <= maxX &&
				z >= minZ && z <= maxZ;
	}
	
	public static boolean isIn3dBounds(final Location location, final Location cornerOne, final Location cornerTwo) {
		if (!isIn2dBounds(location, cornerOne, cornerTwo)) {
			return false;
		}
		
		final double maxY = Math.max(cornerOne.getY(), cornerTwo.getY());
		final double minY = Math.min(cornerOne.getY(), cornerTwo.getY());
		final double y = location.getY();
		
		return y >= minY && y <= maxY;
	}

}
