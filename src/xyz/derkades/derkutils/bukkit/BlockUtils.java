package xyz.derkades.derkutils.bukkit;

import java.util.Objects;
import java.util.function.Function;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

public class BlockUtils {

	public static boolean atSameLocation(final Block a, final Block b) {
		Objects.requireNonNull(a, "Block A is null");
		Objects.requireNonNull(b, "Block B is null");
		return (a.getX() == b.getX() &&
				a.getY() == b.getY() &&
				a.getZ() == b.getZ() &&
				a.getWorld().getName().equals(b.getWorld().getName()));	}


	public static void fillArea(final World world,
			final int x1, final int y1, final int z1,
			final int x2, final int y2, final int z2,
			final Material material, final Function<Block, Boolean> blockFilter, final boolean applyPhysics) {

		Objects.requireNonNull(world, "World is null");

		final int minX = Math.min(x1, x2);
		final int minY = Math.min(y1, y2);
		final int minZ = Math.min(z1, z2);
		final int maxX = Math.max(x1, x2);
		final int maxY = Math.max(y1, y2);
		final int maxZ = Math.max(z1, z2);

		for (int x = minX; x <= maxX; x++) {
			for (int y = minY; y <= maxY; y++) {
				for (int z = minZ; z <= maxZ; z++) {
					final Block block = new Location(world, x, y, z).getBlock();
					if (blockFilter != null) {
						if (!blockFilter.apply(block)) {
							continue;
						}
					}
					block.setType(material, applyPhysics);
				}
			}
		}
	}

	public static void fillArea(final World world,
			final int x1, final int y1, final int z1,
			final int x2, final int y2, final int z2,
			final Material material, final boolean applyPhysics) {

		fillArea(world, x1, y1, z1, x2, y2, z2, material, null, applyPhysics);
	}

	public static void fillArea(final World world,
			final int x1, final int y1, final int z1,
			final int x2, final int y2, final int z2,
			final Material material) {

		fillArea(world, x1, y1, z1, x2, y2, z2, material, null, true);
	}

	public static void fillArea(final World world,
			final int x1, final int y1, final int z1,
			final int x2, final int y2, final int z2,
			final Material material, final Function<Block, Boolean> blockFilter) {

		fillArea(world, x1, y1, z1, x2, y2, z2, material, blockFilter, true);
	}

	public static void fillArea(final Location a, final Location b, final Material material,
			final Function<Block, Boolean> blockFilter, final boolean applyPhysics) {
		fillArea(a.getWorld(),
				a.getBlockX(), a.getBlockY(), a.getBlockZ(),
				b.getBlockX(), b.getBlockY(), b.getBlockZ(),
				material, blockFilter, applyPhysics);
	}

	public static void fillArea(final Location a, final Location b, final Material material,
			final Function<Block, Boolean> blockFilter) {
		fillArea(a, b, material, blockFilter, true);
	}

	public static void fillArea(final Location a, final Location b, final Material material, final boolean applyPhysics) {
		fillArea(a, b, material, null, applyPhysics);
	}

	public static void fillArea(final Location a, final Location b, final Material material) {
		fillArea(a, b, material, null, true);
	}

	public static void replaceBlocks(final Material original, final Material newMaterial, final Block... blocks) {
		Objects.requireNonNull(blocks, "Blocks array is null");

		for (final Block block : blocks) {
			if (block.getType().equals(original)) {
				block.setType(newMaterial);
			}
		}
	}

}
