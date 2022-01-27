package xyz.derkades.derkutils.bukkit;

import java.util.Set;

import org.bukkit.Material;

/**
 * Use {@link com.destroystokyo.paper.MaterialTags} and {@link org.bukkit.Tag}
 */
@Deprecated
public class MaterialLists {

	@SafeVarargs
	public static boolean isInList(final Material materialToSearch, final Set<Material>... materialLists) {
		if (materialToSearch == null) {
			return false;
		}

		if (materialLists.length == 0) {
			return false;
		}

		for (final Set<Material> materialList : materialLists) {
			if (materialList.contains(materialToSearch)) {
				return true;
			}
		}

		return false;
	}

	public static final Set<Material> TRAPDOORS = Set.of(
			Material.ACACIA_TRAPDOOR,
			Material.BIRCH_TRAPDOOR,
			Material.DARK_OAK_TRAPDOOR,
			Material.IRON_TRAPDOOR,
			Material.JUNGLE_TRAPDOOR,
			Material.OAK_TRAPDOOR,
			Material.SPRUCE_TRAPDOOR
	);

	public static final Set<Material> FENCES = Set.of(
			Material.ACACIA_FENCE,
			Material.BIRCH_FENCE,
			Material.DARK_OAK_FENCE,
			Material.JUNGLE_FENCE,
			Material.NETHER_BRICK_FENCE,
			Material.OAK_FENCE,
			Material.SPRUCE_FENCE
	);

	public static final Set<Material> FENCE_GATES = Set.of(
			Material.ACACIA_FENCE_GATE,
			Material.BIRCH_FENCE_GATE,
			Material.DARK_OAK_FENCE_GATE,
			Material.JUNGLE_FENCE_GATE,
			Material.OAK_FENCE_GATE,
			Material.SPRUCE_FENCE_GATE
	);

	public static final Set<Material> WALLS = Set.of(
			Material.COBBLESTONE_WALL,
			Material.MOSSY_COBBLESTONE_WALL
	);

	public static final Set<Material> DOORS = Set.of(
			Material.ACACIA_DOOR,
			Material.BIRCH_DOOR,
			Material.DARK_OAK_DOOR,
			Material.JUNGLE_DOOR,
			Material.OAK_DOOR,
			Material.SPRUCE_DOOR
	);

	public static final Set<Material> STAINED_GLASS_BLOCKS = Set.of(
			Material.BLACK_STAINED_GLASS,
			Material.BLUE_STAINED_GLASS,
			Material.BROWN_STAINED_GLASS,
			Material.CYAN_STAINED_GLASS,
			Material.GRAY_STAINED_GLASS,
			Material.GREEN_STAINED_GLASS,
			Material.LIGHT_BLUE_STAINED_GLASS,
			Material.LIGHT_GRAY_STAINED_GLASS,
			Material.LIME_STAINED_GLASS,
			Material.MAGENTA_STAINED_GLASS,
			Material.ORANGE_STAINED_GLASS,
			Material.PINK_STAINED_GLASS,
			Material.PURPLE_STAINED_GLASS,
			Material.RED_STAINED_GLASS,
			Material.WHITE_STAINED_GLASS,
			Material.YELLOW_STAINED_GLASS
	);

	public static final Set<Material> STAINED_GLASS_PANES = Set.of(
			Material.BLACK_STAINED_GLASS_PANE,
			Material.BLUE_STAINED_GLASS_PANE,
			Material.BROWN_STAINED_GLASS_PANE,
			Material.CYAN_STAINED_GLASS_PANE,
			Material.GRAY_STAINED_GLASS_PANE,
			Material.GREEN_STAINED_GLASS_PANE,
			Material.LIGHT_BLUE_STAINED_GLASS_PANE,
			Material.LIGHT_GRAY_STAINED_GLASS_PANE,
			Material.LIME_STAINED_GLASS_PANE,
			Material.MAGENTA_STAINED_GLASS_PANE,
			Material.ORANGE_STAINED_GLASS_PANE,
			Material.PINK_STAINED_GLASS_PANE,
			Material.PURPLE_STAINED_GLASS_PANE,
			Material.RED_STAINED_GLASS_PANE,
			Material.WHITE_STAINED_GLASS_PANE,
			Material.YELLOW_STAINED_GLASS_PANE
	);

	public static final Set<Material> TORCHES = Set.of(
			Material.TORCH,
			Material.REDSTONE_TORCH,
			Material.REDSTONE_WALL_TORCH,
			Material.WALL_TORCH
	);

	public static final Set<Material> LEAVES = Set.of(
			Material.ACACIA_LEAVES,
			Material.BIRCH_LEAVES,
			Material.DARK_OAK_LEAVES,
			Material.JUNGLE_LEAVES,
			Material.OAK_LEAVES,
			Material.SPRUCE_LEAVES
	);

	public static final Set<Material> FLUIDS = Set.of(
			Material.WATER,
			Material.LAVA
	);

	public static final Set<Material> WOOLS = Set.of(
			Material.BLACK_WOOL,
			Material.BLUE_WOOL,
			Material.BROWN_WOOL,
			Material.CYAN_WOOL,
			Material.GRAY_WOOL,
			Material.GREEN_WOOL,
			Material.LIGHT_BLUE_WOOL,
			Material.LIGHT_GRAY_WOOL,
			Material.LIME_WOOL,
			Material.MAGENTA_WOOL,
			Material.ORANGE_WOOL,
			Material.PINK_WOOL,
			Material.PURPLE_WOOL,
			Material.RED_WOOL,
			Material.WHITE_WOOL,
			Material.YELLOW_WOOL
	);

	public static final Set<Material> DYES = Set.of(
			Material.INK_SAC,
			Material.LAPIS_LAZULI,
			Material.COCOA_BEANS,
			Material.CYAN_DYE,
			Material.GRAY_DYE,
			Material.CACTUS_GREEN,
			Material.LIGHT_BLUE_DYE,
			Material.LIGHT_GRAY_DYE,
			Material.LIME_DYE,
			Material.MAGENTA_DYE,
			Material.ORANGE_DYE,
			Material.PINK_DYE,
			Material.PURPLE_DYE,
			Material.ROSE_RED,
			Material.BONE_MEAL,
			Material.DANDELION_YELLOW
	);

	public static final Set<Material> TERRACOTTA_BLOCKS = Set.of(
			Material.BLACK_TERRACOTTA,
			Material.BLUE_TERRACOTTA,
			Material.BROWN_TERRACOTTA,
			Material.CYAN_TERRACOTTA,
			Material.GRAY_TERRACOTTA,
			Material.GREEN_TERRACOTTA,
			Material.LIGHT_BLUE_TERRACOTTA,
			Material.LIGHT_GRAY_TERRACOTTA,
			Material.LIME_TERRACOTTA,
			Material.MAGENTA_TERRACOTTA,
			Material.ORANGE_TERRACOTTA,
			Material.PINK_TERRACOTTA,
			Material.PURPLE_TERRACOTTA,
			Material.RED_TERRACOTTA,
			Material.WHITE_TERRACOTTA,
			Material.YELLOW_TERRACOTTA
	);

	@Deprecated
	public static final Set<Material> POTTED_PLANT = Set.of(
			Material.POTTED_ACACIA_SAPLING,
			Material.POTTED_ALLIUM,
			Material.POTTED_AZURE_BLUET,
			Material.POTTED_BIRCH_SAPLING,
			Material.POTTED_BLUE_ORCHID,
			Material.POTTED_BROWN_MUSHROOM,
			Material.POTTED_CACTUS,
			Material.POTTED_DANDELION,
			Material.POTTED_DARK_OAK_SAPLING,
			Material.POTTED_DEAD_BUSH,
			Material.POTTED_FERN,
			Material.POTTED_JUNGLE_SAPLING,
			Material.POTTED_OAK_SAPLING,
			Material.POTTED_ORANGE_TULIP,
			Material.POTTED_OXEYE_DAISY,
			Material.POTTED_PINK_TULIP,
			Material.POTTED_POPPY,
			Material.POTTED_RED_MUSHROOM,
			Material.POTTED_RED_TULIP,
			Material.POTTED_SPRUCE_SAPLING,
			Material.POTTED_WHITE_TULIP
	);

	public static final Set<Material> FLOWER_POTS = Set.of(
			Material.FLOWER_POT,
			Material.POTTED_ACACIA_SAPLING,
			Material.POTTED_ALLIUM,
			Material.POTTED_AZURE_BLUET,
			Material.POTTED_BIRCH_SAPLING,
			Material.POTTED_BLUE_ORCHID,
			Material.POTTED_BROWN_MUSHROOM,
			Material.POTTED_CACTUS,
			Material.POTTED_DANDELION,
			Material.POTTED_DARK_OAK_SAPLING,
			Material.POTTED_DEAD_BUSH,
			Material.POTTED_FERN,
			Material.POTTED_JUNGLE_SAPLING,
			Material.POTTED_OAK_SAPLING,
			Material.POTTED_ORANGE_TULIP,
			Material.POTTED_OXEYE_DAISY,
			Material.POTTED_PINK_TULIP,
			Material.POTTED_POPPY,
			Material.POTTED_RED_MUSHROOM,
			Material.POTTED_RED_TULIP,
			Material.POTTED_SPRUCE_SAPLING,
			Material.POTTED_WHITE_TULIP
	);

	/*public static final Material[] PARTIAL_BLOCKS = ListUtils.mergeArrays(
			TRAPDOORS,
			FENCES,
			FENCE_GATES,
			WALLS,
			LEAVES,
			TORCHES,
			STAINED_GLASS_BLOCKS,
			STAINED_GLASS_PANES,
			SIGNS,
			new Material[] {
				Material.ANVIL,
				Material.GLASS,
				Material.END_ROD}
	);

	public static final Material[] INTERACTABLE_BLOCKS = ListUtils.mergeArrays(
			FENCE_GATES,
			TRAPDOORS,
			DOORS
	);*/

}
