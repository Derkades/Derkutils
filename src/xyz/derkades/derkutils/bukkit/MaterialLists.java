package xyz.derkades.derkutils.bukkit;

import java.util.Set;

import org.bukkit.Material;

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
			Material.CRIMSON_TRAPDOOR,
			Material.DARK_OAK_TRAPDOOR,
			Material.IRON_TRAPDOOR,
			Material.JUNGLE_TRAPDOOR,
			Material.OAK_TRAPDOOR,
			Material.SPRUCE_TRAPDOOR,
			Material.WARPED_TRAPDOOR
	);
	
	public static final Set<Material> FENCES = Set.of(
			Material.ACACIA_FENCE,
			Material.BIRCH_FENCE,
			Material.CRIMSON_FENCE,
			Material.DARK_OAK_FENCE,
			Material.JUNGLE_FENCE,
			Material.NETHER_BRICK_FENCE,
			Material.OAK_FENCE,
			Material.SPRUCE_FENCE,
			Material.WARPED_FENCE
	);
	
	public static final Set<Material> FENCE_GATES = Set.of(
			Material.ACACIA_FENCE_GATE,
			Material.BIRCH_FENCE_GATE,
			Material.CRIMSON_FENCE_GATE,
			Material.DARK_OAK_FENCE_GATE,
			Material.JUNGLE_FENCE_GATE,
			Material.OAK_FENCE_GATE,
			Material.SPRUCE_FENCE_GATE,
			Material.WARPED_FENCE_GATE
	);
	
	public static final Set<Material> WALLS = Set.of(
			Material.COBBLESTONE_WALL,
			Material.MOSSY_COBBLESTONE_WALL,
			Material.ANDESITE_WALL,
			Material.DIORITE_WALL,
			Material.GRANITE_WALL,
			Material.BLACKSTONE_WALL,
			Material.STONE_BRICK_WALL
	);
	
	public static final Set<Material> DOORS = Set.of(
			Material.ACACIA_DOOR,
			Material.BIRCH_DOOR,
			Material.CRIMSON_DOOR,
			Material.DARK_OAK_DOOR,
			Material.JUNGLE_DOOR,
			Material.OAK_DOOR,
			Material.SPRUCE_DOOR,
			Material.WARPED_DOOR
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
			Material.BLACK_DYE,
			Material.BLUE_DYE,
			Material.BROWN_DYE,
			Material.CYAN_DYE,
			Material.GRAY_DYE,
			Material.GREEN_DYE,
			Material.LIGHT_BLUE_DYE,
			Material.LIGHT_GRAY_DYE,
			Material.LIME_DYE,
			Material.MAGENTA_DYE,
			Material.ORANGE_DYE,
			Material.PINK_DYE,
			Material.PURPLE_DYE,
			Material.RED_DYE,
			Material.WHITE_DYE,
			Material.YELLOW_DYE
	);
	
	public static final Set<Material> SIGNS = Set.of(
			Material.ACACIA_SIGN,
			Material.ACACIA_WALL_SIGN,
			Material.BIRCH_SIGN,
			Material.BIRCH_WALL_SIGN,
			Material.CRIMSON_SIGN,
			Material.CRIMSON_WALL_SIGN,
			Material.DARK_OAK_SIGN,
			Material.DARK_OAK_WALL_SIGN,
			Material.JUNGLE_SIGN,
			Material.JUNGLE_WALL_SIGN,
			Material.OAK_SIGN,
			Material.OAK_WALL_SIGN,
			Material.SPRUCE_SIGN,
			Material.SPRUCE_WALL_SIGN,
			Material.WARPED_SIGN,
			Material.WARPED_WALL_SIGN
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
