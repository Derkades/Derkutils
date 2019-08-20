package xyz.derkades.derkutils.bukkit;

import java.util.Random;

import org.bukkit.World;
import org.bukkit.generator.ChunkGenerator;

public class VoidGenerator extends ChunkGenerator {

	@Override
	public ChunkData generateChunkData(final World world, final Random random, final int x, final int z, final BiomeGrid biome) {
		return this.createChunkData(world);
	}
}
