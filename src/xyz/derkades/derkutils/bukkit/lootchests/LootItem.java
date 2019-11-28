package xyz.derkades.derkutils.bukkit.lootchests;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import xyz.derkades.derkutils.Random;

public class LootItem {

	private final ItemStack item;
	private final int minAmount;
	private final int maxAmount;
	private final float chance;

	public LootItem(final ItemStack item, final int minAmount, final int maxAmount, final float chance) {
		this.item = item;
		this.minAmount = minAmount;
		this.maxAmount = maxAmount;
		this.chance = chance;
	}

	public LootItem(final Material type, final int minAmount, final int maxAmount, final float chance){
		this(new ItemStack(type), minAmount, maxAmount, chance);
	}

	public LootItem(final ItemStack item, final float chance) {
		this(item, 1, 1, chance);
	}


	public LootItem(final Material type, final float chance) {
		this(new ItemStack(type), chance);
	}

	public ItemStack getItem() {
		return this.item;
	}

	public int calculateAmount() {
		if (Random.getRandomFloat() < this.chance) {
			return Random.getRandomInteger(this.minAmount, this.maxAmount);
		} else {
			return 0;
		}
	}

}
