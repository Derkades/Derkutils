package xyz.derkades.derkutils.bukkit;

import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Material;

import com.google.common.collect.ImmutableMap;

public class ItemNames {
	
	/*
	 *  If there is a block and item variant
	 *      block: "cauldron block"
	 *      item: "cauldron item", "cauldron"
	 *      
	 *  Include American and British spelling/words
	 *  
	 *  Capital letters are ignored, please use lower case in the map
	 *  
	 *  Item names with underscores instead of spaces are tried automatically
	 */
	
	private static final Map<String, String[]> NAMES_MAP = ImmutableMap.<String, String[]>builder()
		    .put("ACACIA_DOOR", new String[] {"acacia door", "acacia wood door"})
		    .put("ACACIA_DOOR_ITEM", new String[] {"acacia door item", "acacia wood door item"})
		    .put("ACACIA_FENCE", new String[] {"acacia fence", "acacia wood fence", "acacia wooden fence"})
		    .put("ACACIA_FENCE_GATE", new String[] {"acacia fence door", "acacia fence gate", "acacia wooden fence gate"})
		    .put("ACACIA_STAIRS", new String[] {"acacia stairs", "acacia wooden stairs"})
		    .put("ACTIVATOR_RAIL", new String[] {"activator rail", "pressure rail"})
		    .put("AIR", new String[] {"air", "none", "blank", "empty"})
		    .put("ANVIL", new String[] {"anvil"})
		    .put("APPLE", new String[] {"apple"})
		    .put("ARMOR_STAND", new String[] {"armor stand", "armour stand"})
		    .put("ARROW", new String[] {"arrow"})
		    .put("BAKED_POTATO", new String[] {"baked potato", "cooked potato"})
		    .put("BANNER", new String[] {"banner"})
		    .put("BARRIER", new String[] {"barrier", "barrier block"})
		    .put("BEACON", new String[] {"beacon"})
		    .put("BED", new String[] {"bed", "bed item"})
		    .put("BED_BLOCK", new String[] {"bed block"})
		    .put("BEDROCK", new String[] {"bedrock"})
		    .put("BEETROOT", new String[] {"beetroot", "beetroot item"})
		    .put("BEETROOT_BLOCK", new String[] {"beetroot block", "beetroot crops"})
		    .put("BEETROOT_SEEDS", new String[] {"beetroot seeds"})
		    .put("BEETROOT_SOUP", new String[] {"beetroot soup", "beetroot stew", "beetroot bowl"})
		    .put("BIRCH_DOOR", new String[] {"birch door", "birch wood door"})
		    .put("BIRCH_DOOR_ITEM", new String[] {"birch door item", "birch wooden door item", "birch wood door item"})
		    .put("BIRCH_FENCE", new String[] {"birch fence", "birch wooden fence", "birch wood fence"})
		    .put("BIRCH_FENCE_GATE", new String[] {"birch fence door", "birch wood fence door", "birch wooden fence door", "birch fence gate", "birch wooden fence gate", "birch wood fence gate"})
		    .put("BIRCH_WOOD_STAIRS", new String[] {"birch stairs", "birch wood stairs", "birch wooden stairs"})
		    .put("BLACK_GLAZED_TERRACOTTA", new String[] {"black glazed terracotta"})
		    .put("BLACK_SHULKER_BOX", new String[] {"black shulker box"})
		    .put("BLAZE_POWDER", new String[] {"blaze powder"})
		    .put("BLAZE_ROD", new String[] {"blaze rod", "blaze stick"})
		    .put("BLUE_GLAZED_TERRACOTTA", new String[] {"blue glazed terracotta"})
		    .put("BLUE_SHULKER_BOX", new String[] {"blue shulker box"})
		    .put("BOAT", new String[] {"boat"})
		    .put("BOAT_ACACIA", new String[] {"acacia boat", "acacia wood boat", "acacia wooden boat"})
		    .put("BOAT_BIRCH", new String[] {"birch boat", "birch wood boat", "birch wooden boat"})
		    .put("BOAT_DARK_OAK", new String[] {"dark oak boat", "big oak boat", "dark oak wood boat", "dark oak wooden boat"})
		    .put("BOAT_JUNGLE", new String[] {"jungle boat", "jungle wood boat", "jungle wooden boat"})
		    .put("BOAT_SPRUCE", new String[] {"spruce boat", "spruce wood boat", "spruce wooden boat"})
		    .put("BONE", new String[] {"bone", "skeleton bone"})
		    .put("BONE_BLOCK", new String[] {"bone block"})
		    .put("BOOK", new String[] {"book"})
		    .put("BOOK_AND_QUILL", new String[] {"book and quill", "book & quill", "writable book"})
		    .put("BOOKSHELF", new String[] {"bookshelf"})
		    .put("BOW", new String[] {"bow"})
		    .put("BOWL", new String[] {"bowl", "wooden bowl", "empty bowl", "empty wooden bowl", "wood bowl", "empty wood bowl"})
		    .put("BREAD", new String[] {"bread"})
		    .put("BREWING_STAND", new String[] {"brewing stand"})
		    .put("BREWING_STAND_ITEM", new String[] {"brewing stand item"})
		    .put("BRICK", new String[] {"bricks", "clay bricks"})
		    .put("BRICK_STAIRS", new String[] {"brick stairs", "bricks stairs", "clay brick stairs"})
		    .put("BROWN_GLAZED_TERRACOTTA", new String[] {"brown glazed terracotta"})
		    .put("BROWN_MUSHROOM", new String[] {"brown mushroom"})
		    .put("BROWN_SHULKER_BOX", new String[] {"brown shulker box"})
		    .put("BUCKET", new String[] {"bucket", "bukkit", "empty bucket"})
		    .put("BURNING_FURNACE", new String[] {"burning furnace", "lit furnace"})
		    .put("CACTUS", new String[] {"cactus"})
		    .put("CAKE", new String[] {"cake"})
		    .put("CAKE_BLOCK", new String[] {"cake block"})
		    .put("CARPET", new String[] {"carpet", "wool slab"})
		    .put("CARROT", new String[] {"carrot crop", "carrot crops"})
		    .put("CARROT_ITEM", new String[] {"carrot", "raw carrot", "carrot item"})
		    .put("CARROT_STICK", new String[] {"carrot stick", "carrot on a stick", "stick with carrot", "carrot fishing rod"})
		    .put("CAULDRON", new String[] {"cauldron block"})
		    .put("CAULDRON_ITEM", new String[] {"cauldron", "cauldron item"})
		    .put("CHAINMAIL_BOOTS", new String[] {"chainmail boots", "chainmail shoes"})
		    .put("CHAINMAIL_CHESTPLATE", new String[] {"chainmail chestplate"})
		    .put("CHAINMAIL_HELMET", new String[] {"chainmail helmet"})
		    .put("CHAINMAIL_LEGGINGS", new String[] {"chainmail leggings", "chainmail pants"})
		    .put("CHEST", new String[] {"chest"})
		    .put("CHORUS_FLOWER", new String[] {"chorus flower"})
		    .put("CHORUS_FRUIT", new String[] {"chorus fruit"})
		    .put("CHORUS_FRUIT_POPPED", new String[] {"popped chorus fruit", "chorus fruit popped", "popped chorus"})
		    .put("CHORUS_PLANT", new String[] {"chorus plant"})
		    .put("CLAY", new String[] {"clay block"})
		    .put("CLAY_BALL", new String[] {"clay", "clay ball"})
		    .put("CLAY_BRICK", new String[] {"brick", "clay brick"})
		    .put("COAL", new String[] {"coal", "coal item"})
		    .put("COAL_BLOCK", new String[] {"coal block"})
		    .put("COAL_ORE", new String[] {"coal ore"})
		    .put("COBBLE_WALL", new String[] {"cobble wall", "cobble fence", "cobblestone wall", "cobblestone fence"})
		    .put("COBBLESTONE", new String[] {"cobble", "cobblestone"})
		    .put("COBBLESTONE_STAIRS", new String[] {"cobble stairs", "cobblestone stairs"})
		    .put("COCOA", new String[] {"cocoa"})
		    .put("COMMAND", new String[] {"command", "command block"})
		    .put("COMMAND_CHAIN", new String[] {"chain command block", "command chain", "chain command", "chained command block"})
		    .put("COMMAND_MINECART", new String[] {"command minecart", "command block minecart", "minecart command block", "minecart with command block", "minecart command"})
		    .put("COMMAND_REPEATING", new String[] {"repeating command block", "repeat command", "command repeating", "repeating command"})
		    .put("", new String[] {})
		    .put("", new String[] {})
		    .put("", new String[] {})
		    .put("", new String[] {})
		    .put("", new String[] {})
		    .put("", new String[] {})
		    .put("", new String[] {})
		    .put("", new String[] {})
		    .put("", new String[] {})
		    .put("", new String[] {})
		    .put("", new String[] {})
		    .put("", new String[] {})
		    .put("", new String[] {})
		    .put("", new String[] {})
		    .put("", new String[] {})
		    .put("", new String[] {})
		    .put("", new String[] {})
		    .put("", new String[] {})
		    .put("", new String[] {})
		    .put("", new String[] {})
		    .put("", new String[] {})
		    .put("", new String[] {})
		    .put("", new String[] {})
		    .put("", new String[] {})
		    .put("", new String[] {})
		    .put("", new String[] {})
		    .put("", new String[] {})
		    .put("", new String[] {})
		    .put("", new String[] {})
		    .put("", new String[] {})
		    .put("", new String[] {})
		    .put("", new String[] {})
		    .put("", new String[] {})
		    .put("", new String[] {})
		    .put("", new String[] {})
		    .put("", new String[] {})
		    .put("", new String[] {})
		    .put("", new String[] {})
		    .put("", new String[] {})
		    .put("", new String[] {})
		    .put("", new String[] {})
		    .put("", new String[] {})
		    .put("", new String[] {})
		    .put("", new String[] {})
			.put("", new String[] {})
			.build();
	
	/**
	 * @param itemName
	 * @return The material corresponding to the provided item name, or null if the provided item name is not recognised.
	 */
	public static Material getMaterial(String itemName) {
		for (Entry<String, String[]> entry : NAMES_MAP.entrySet()) {
			for (String possibleItemName : entry.getValue()) {
				if (possibleItemName.equalsIgnoreCase(itemName) ||
						possibleItemName.replace(" ", "_").equalsIgnoreCase(itemName)) {
					return Material.valueOf(entry.getKey());
				}
			}
		}
		
		return null;
	}

}
