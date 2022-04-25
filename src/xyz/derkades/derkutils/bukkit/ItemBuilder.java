package xyz.derkades.derkutils.bukkit;

import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
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
	
	@Deprecated
	public ItemBuilder(final @NonNull OfflinePlayer skullOwner) {
		super(new ItemBuilder(Material.PLAYER_HEAD).skullOwner(Objects.requireNonNull(skullOwner, "Skull owner is null")).create());
	}

	@Override
	public @NonNull ItemBuilder getInstance() {
		return this;
	}

}
