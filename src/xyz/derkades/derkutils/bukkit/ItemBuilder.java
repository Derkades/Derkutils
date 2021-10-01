package xyz.derkades.derkutils.bukkit;

import java.util.Objects;

import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class ItemBuilder extends AbstractItemBuilder<ItemBuilder> {
	
	public ItemBuilder(@NotNull Material material) {
		super(material);
	}
	
	public ItemBuilder(@NotNull ItemStack item) {
		super(item);
	}
	
	@Deprecated
	public ItemBuilder(@NotNull final OfflinePlayer skullOwner) {
		super(new ItemBuilder(Material.PLAYER_HEAD).skullOwner(Objects.requireNonNull(skullOwner, "Skull owner is null")).create());
	}

	@Override
	@NotNull 
	public ItemBuilder getInstance() {
		return this;
	}

}
