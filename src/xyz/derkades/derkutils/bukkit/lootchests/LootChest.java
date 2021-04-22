package xyz.derkades.derkutils.bukkit.lootchests;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class LootChest {

	private final LootItem[] lootItems;
	private final Location location;

	public LootChest(final Location location, final LootItem... lootItems) {
		this.location = location;
		this.lootItems = lootItems;
	}

	public void fill() {
		final Block block = this.location.getBlock();

		if (block.getType() != Material.CHEST && block.getType() != Material.TRAPPED_CHEST) {
			throw new UnsupportedOperationException(String.format("Target block is not a chest (%s, %s, %s)",
					block.getX(), block.getY(), block.getZ()));
		}

		final Chest chest = (Chest) block.getState();

		final Inventory inventory = chest.getBlockInventory();

		inventory.clear();

		lootItems: for (final LootItem lootItem : this.lootItems) {
			final int amount = lootItem.calculateAmount();

			if (amount == 0) {
				continue;
			}

			final ItemStack item = lootItem.getItem();
			item.setAmount(amount);

			final List<Integer> slots = new ArrayList<>(27);
			for (int i = 0; i < 26; i++) {
				slots.add(i);
			}

			Collections.shuffle(slots);

			for (final int slot : slots) {
				if (inventory.getItem(slot) == null || inventory.getItem(slot).getType().equals(Material.AIR)) {
					inventory.setItem(slot, item);
					continue lootItems;
				}
			}
		}
	}

}
