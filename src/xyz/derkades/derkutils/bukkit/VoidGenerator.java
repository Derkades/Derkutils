package xyz.derkades.derkutils.bukkit;

import java.util.Random;

import org.bukkit.World;
import org.bukkit.generator.ChunkGenerator;
import org.jetbrains.annotations.NotNull;

public class VoidGenerator extends ChunkGenerator {

	@Override
	@NotNull
	public ChunkData generateChunkData(@NotNull final World world, @NotNull final Random random, final int x, final int z, @NotNull final BiomeGrid biome) {
		return this.createChunkData(world);
	}
}
