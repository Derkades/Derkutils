package xyz.derkades.derkutils.bukkit;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class ItemBuilder extends AbstractItemBuilder<ItemBuilder> {

	public ItemBuilder(@NotNull Material material) {
		super(material);
	}

	public ItemBuilder(@NotNull ItemStack item) {
		super(item);
	}

	@Override
	@NotNull
	public ItemBuilder getInstance() {
		return this;
	}

}
