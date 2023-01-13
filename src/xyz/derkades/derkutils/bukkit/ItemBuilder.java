package xyz.derkades.derkutils.bukkit;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.checkerframework.checker.nullness.qual.NonNull;

public class ItemBuilder extends AbstractItemBuilder<ItemBuilder> {
	
	public ItemBuilder(final Material material) {
		super(material);
	}
	
	public ItemBuilder(final ItemStack item) {
		super(item);
	}

	@Override
	public @NonNull ItemBuilder getInstance() {
		return this;
	}

}
