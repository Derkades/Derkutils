package xyz.derkades.derkutils.bukkit;

import org.bukkit.World;
import org.bukkit.generator.ChunkGenerator;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Random;

public class VoidGenerator extends ChunkGenerator {

	@Override
	@SuppressWarnings("deprecation") // to remain compatible with pre 1.17
	public @NonNull ChunkData generateChunkData(final @NonNull World world,
												final @NonNull Random random,
												final int x,
												final int z,
												final @NonNull BiomeGrid biome) {
		return this.createChunkData(world);
	}
}
