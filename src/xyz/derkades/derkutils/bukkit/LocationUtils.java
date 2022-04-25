package xyz.derkades.derkutils.bukkit;

import com.google.common.base.Preconditions;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Objects;

public class LocationUtils {

	public static boolean isIn2dBounds(final @NonNull Location location,
									   final @NonNull Location cornerOne,
									   final @NonNull Location cornerTwo) {
		Objects.requireNonNull(location, "Location is null");
		Objects.requireNonNull(cornerOne, "First corner is null");
		Objects.requireNonNull(cornerTwo, "Second corner is null");
		Objects.requireNonNull(location.getWorld(), "Location world is null");
		Objects.requireNonNull(cornerOne.getWorld(), "CornerOne world is null");
		Objects.requireNonNull(cornerTwo.getWorld(), "CornerTwo world is null");

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

	public static boolean isIn3dBounds(final @NonNull Location location,
									   final @NonNull Location cornerOne,
									   final @NonNull Location cornerTwo) {
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


	public static @NonNull Location maxCorner(final @NonNull Location a,
											  final @NonNull Location b) {
		World world = a.getWorld();
		Preconditions.checkArgument(world != null && world.equals(b.getWorld()), "Locations must be in the same world");
		return new Location(world, Math.max(a.getX(), b.getX()), Math.max(a.getY(), b.getY()), Math.max(a.getZ(), b.getZ()));
	}

	public static @NonNull Location minCorner(final @NonNull Location a,
											  final @NonNull Location b) {
		World world = a.getWorld();
		Preconditions.checkArgument(world != null && world.equals(b.getWorld()), "Locations must be in the same world");
		return new Location(a.getWorld(), Math.min(a.getX(), b.getX()), Math.min(a.getY(), b.getY()), Math.min(a.getZ(), b.getZ()));
	}

	/**
	 * @see #yawInBounds(float, float, float)
	 * @param player
	 * @param min
	 * @param max
	 * @return
	 */
	public static boolean yawInBounds(final @NonNull Player player,
									  final float min,
									  final float max) {
		return yawInBounds(player.getLocation(), min, max);
	}

	/**
	 * @see #yawInBounds(float, float, float)
	 * @param location
	 * @param min
	 * @param max
	 * @return
	 */
	public static boolean yawInBounds(final @NonNull Location location,
									  final float min,
									  final float max) {
		return yawInBounds(location.getYaw(), min, max);
	}

	/**
	 * Check if bukkit yaw is in bounds of vanilla yaw
	 * @param bukkitYaw Bukkit yaw (e.g. from {@link Location#getYaw()}
	 * @param clientYawMin Min yaw, as displayed in debug screen
	 * @param clientYawMax Max yaw, as displayed in debug screen
	 * @return whether yaw is in bounds
	 */
	public static boolean yawInBounds(float bukkitYaw,
									  final float clientYawMin,
									  final float clientYawMax) {
		if (bukkitYaw < -180) {
			bukkitYaw += 360;
		} else if (bukkitYaw > 180) {
			bukkitYaw -= 360;
		}
		return bukkitYaw > clientYawMin && bukkitYaw < clientYawMax;
	}

	public static @NonNull BlockFace getYawAsBlockFace(float yaw) {
		if (yaw < 0) {
			yaw += 360;
		}
		if (yaw < 45 || yaw >= 315) { // 0, 360
			return BlockFace.SOUTH; // +Z
		} else if (yaw >= 45 && yaw < 135) { // 90
			return BlockFace.WEST; // -X
		} else if (yaw >= 135 && yaw < 225) { // 180
			return BlockFace.NORTH; // -Z
		} else if (yaw >= 225 && yaw < 315) { // 270
			return BlockFace.EAST; // +X
		} else {
			throw new IllegalStateException("Impossible yaw: " + yaw);
		}
	}

}
