package xyz.derkades.derkutils.bukkit;

import com.google.common.base.Preconditions;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.function.Function;

public class BlockUtils {

	public static boolean atSameLocation(final Block a, final Block b) {
		Objects.requireNonNull(a, "Block A is null");
		Objects.requireNonNull(b, "Block B is null");
		return (a.getX() == b.getX() &&
				a.getY() == b.getY() &&
				a.getZ() == b.getZ() &&
				a.getWorld().getName().equals(b.getWorld().getName()));	}


	public static void fillArea(@NotNull final World world,
			final int x1, final int y1, final int z1,
			final int x2, final int y2, final int z2,
			@NotNull final Material material, @Nullable final Function<Block, Boolean> blockFilter, final boolean applyPhysics) {

		Objects.requireNonNull(world, "World is null");
		Objects.requireNonNull(material, "Material is null");

		final int minX = Math.min(x1, x2);
		final int minY = Math.min(y1, y2);
		final int minZ = Math.min(z1, z2);
		final int maxX = Math.max(x1, x2);
		final int maxY = Math.max(y1, y2);
		final int maxZ = Math.max(z1, z2);

		for (int x = minX; x <= maxX; x++) {
			for (int y = minY; y <= maxY; y++) {
				for (int z = minZ; z <= maxZ; z++) {
					final Block block = world.getBlockAt(x, y, z);
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

	public static void fillArea(@NotNull final World world,
			final int x1, final int y1, final int z1,
			final int x2, final int y2, final int z2,
			@NotNull final Material material, final boolean applyPhysics) {

		fillArea(world, x1, y1, z1, x2, y2, z2, material, null, applyPhysics);
	}

	public static void fillArea(@NotNull final World world,
			final int x1, final int y1, final int z1,
			final int x2, final int y2, final int z2,
			@NotNull final Material material) {

		fillArea(world, x1, y1, z1, x2, y2, z2, material, null, true);
	}

	public static void fillArea(@NotNull final World world,
			final int x1, final int y1, final int z1,
			final int x2, final int y2, final int z2,
			@NotNull final Material material, @Nullable final Function<Block, Boolean> blockFilter) {

		fillArea(world, x1, y1, z1, x2, y2, z2, material, blockFilter, true);
	}

	@SuppressWarnings("null")
	public static void fillArea(@NotNull final Location a, @NotNull final Location b, @NotNull final Material material,
			@Nullable final Function<Block, Boolean> blockFilter, final boolean applyPhysics) {
		final World world = a.getWorld();
		Objects.requireNonNull(a, "Location a is null");
		Objects.requireNonNull(world, "Location a is not in any world");
		Objects.requireNonNull(b, "Location b is null");
		Preconditions.checkArgument(a.getWorld() == b.getWorld(), "Locations must be in the same world");
		fillArea(a.getWorld(),
				a.getBlockX(), a.getBlockY(), a.getBlockZ(),
				b.getBlockX(), b.getBlockY(), b.getBlockZ(),
				material, blockFilter, applyPhysics);
	}

	public static void fillArea(@NotNull final Location a, @NotNull final Location b, @NotNull final Material material,
			final Function<Block, Boolean> blockFilter) {
		fillArea(a, b, material, blockFilter, true);
	}

	public static void fillArea(@NotNull final Location a, @NotNull final Location b, @NotNull final Material material, final boolean applyPhysics) {
		fillArea(a, b, material, null, applyPhysics);
	}

	public static void fillArea(@NotNull final Location a, @NotNull final Location b, @NotNull final Material material) {
		fillArea(a, b, material, null, true);
	}

	public static void replaceBlocks(@NotNull final Material original, @NotNull final Material newMaterial, final Block... blocks) {
		Objects.requireNonNull(original, "Original material is null");
		Objects.requireNonNull(newMaterial, "New material is null");
		Objects.requireNonNull(blocks, "Blocks array is null");

		for (final Block block : blocks) {
			if (block.getType().equals(original)) {
				block.setType(newMaterial);
			}
		}
	}

}
