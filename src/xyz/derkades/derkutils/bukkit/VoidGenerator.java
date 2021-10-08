package xyz.derkades.derkutils.bukkit;

import org.bukkit.World;
import org.bukkit.generator.ChunkGenerator;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class VoidGenerator extends ChunkGenerator {

	@Override
	@SuppressWarnings("deprecation") // to remain compatible with pre 1.17
	@NotNull
	public ChunkData generateChunkData(@NotNull final World world, @NotNull final Random random, final int x, final int z, @NotNull final BiomeGrid biome) {
		return this.createChunkData(world);
	}
}
