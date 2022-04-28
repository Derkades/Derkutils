package xyz.derkades.derkutils.bukkit;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Objects;

public class ItemBuilder extends AbstractItemBuilder<ItemBuilder> {

	public ItemBuilder(final @NonNull Material material) {
		super(material);
	}

	public ItemBuilder(final @NonNull ItemStack item) {
		super(item);
	}

	@Override
	public @NonNull ItemBuilder getInstance() {
		return this;
	}

}
