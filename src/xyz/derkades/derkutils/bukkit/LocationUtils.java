package xyz.derkades.derkutils.bukkit;

import java.util.Objects;

import org.apache.commons.lang3.Validate;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class LocationUtils {

	public static boolean isIn2dBounds(final Location location, final Location cornerOne, final Location cornerTwo) {
		Objects.requireNonNull(location, "Location is null");
		Objects.requireNonNull(cornerOne, "First corner is null");
		Objects.requireNonNull(cornerTwo, "Second corner is null");

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
		Objects.requireNonNull(location, "Location is null");
		Objects.requireNonNull(cornerOne, "First corner is null");
		Objects.requireNonNull(cornerTwo, "Second corner is null");

		if (!isIn2dBounds(location, cornerOne, cornerTwo)) {
			return false;
		}

		final double maxY = Math.max(cornerOne.getY(), cornerTwo.getY());
		final double minY = Math.min(cornerOne.getY(), cornerTwo.getY());
		final double y = location.getY();

		return y >= minY && y <= maxY;
	}

	public static Location maxCorner(final Location a, final Location b) {
		Validate.isTrue(a.getWorld().equals(b.getWorld()), "Locations must be in the same world");
		return new Location(a.getWorld(), Math.max(a.getX(), b.getX()), Math.max(a.getY(), b.getY()), Math.max(a.getZ(), b.getZ()));
	}

	public static Location minCorner(final Location a, final Location b) {
		Validate.isTrue(a.getWorld().equals(b.getWorld()), "Locations must be in the same world");
		return new Location(a.getWorld(), Math.min(a.getX(), b.getX()), Math.min(a.getY(), b.getY()), Math.min(a.getZ(), b.getZ()));
	}

	/**
	 * @see #yawInBounds(float, float, float)
	 * @param player
	 * @param min
	 * @param max
	 * @return
	 */
	public boolean yawInBounds(final Player player, final float min, final float max) {
		return yawInBounds(player.getLocation(), min, max);
	}

	/**
	 * @see #yawInBounds(float, float, float)
	 * @param player
	 * @param min
	 * @param max
	 * @return
	 */
	public boolean yawInBounds(final Location location, final float min, final float max) {
		return yawInBounds(location.getYaw(), min, max);
	}

	/**
	 * Check if bukkit yaw is in bounds of vanilla yaw
	 * @param yaw Bukkit yaw (e.g. from {@link Location#getYaw()}
	 * @param min Min yaw, as displayed in debug screen
	 * @param max Max yaw, as displayed in debug screen
	 * @return whether yaw is in bounds
	 */
	public boolean yawInBounds(float yaw, final float min, final float max) {
		if (yaw < -180) {
			yaw += 360;
		} else if (yaw > 180) {
			yaw -= 360;
		}
		return yaw > min && yaw < max;
	}

}
