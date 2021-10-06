package xyz.derkades.derkutils.bukkit;

import com.destroystokyo.paper.Namespaced;
import com.destroystokyo.paper.profile.PlayerProfile;
import com.destroystokyo.paper.profile.ProfileProperty;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Extension of ItemBuilder with paper-specific functionality
 */
public class PaperItemBuilder extends AbstractItemBuilder<PaperItemBuilder> {

	public PaperItemBuilder(@NotNull Material material) {
		super(material);
	}

	public PaperItemBuilder(@NotNull ItemStack item) {
		super(item);
	}

	@Override
	public @NotNull PaperItemBuilder getInstance() {
		return this;
	}

	public @NotNull PaperItemBuilder canPlaceOn(@NotNull Collection<Namespaced> placeableKeys) {
		item.editMeta(meta -> meta.setPlaceableKeys(placeableKeys));
		return this;
	}

	public @NotNull PaperItemBuilder canPlaceOnMinecraft(@Nullable String... canPlaceOn) {
		if (canPlaceOn == null) {
			return this.canPlaceOn(Collections.emptySet());
		} else {
			return this.canPlaceOn(Arrays.stream(canPlaceOn).map(NamespacedKey::minecraft).collect(Collectors.toSet()));
		}
	}

	public @NotNull PaperItemBuilder canDestroy(@NotNull Collection<Namespaced> destroyableKeys) {
		item.editMeta(meta -> meta.setDestroyableKeys(destroyableKeys));
		return this;
	}

	public @NotNull PaperItemBuilder canDestroyMinecraft(@Nullable String... canDestroy) {
		if (canDestroy == null) {
			return this.canDestroy(Collections.emptySet());
		} else {
			return this.canDestroy(Arrays.stream(canDestroy).map(NamespacedKey::minecraft).collect(Collectors.toSet()));
		}
	}

	public @NotNull PaperItemBuilder itemFlags(@NotNull ItemFlag... flags) {
		item.editMeta(meta -> meta.addItemFlags(flags));
		return this;
	}

	public @NotNull PaperItemBuilder skullTexture(@NotNull String skullTexture) {
		PlayerProfile profile = Bukkit.getServer().createProfile(UUID.randomUUID());
		profile.setProperty(new ProfileProperty("textures", skullTexture));
		return this.skullProfile(profile);
	}

	public @NotNull PaperItemBuilder skullProfile(@NotNull PlayerProfile profile) {
		item.editMeta(SkullMeta.class, meta -> meta.setPlayerProfile(profile));
		return this;
	}

	public @NotNull PaperItemBuilder nameAdventure(@Nullable Component name) {
		item.editMeta(meta -> meta.displayName(name));
		return this;
	}

	public @NotNull PaperItemBuilder loreAdventure(@Nullable List<Component> lore) {
		item.editMeta(meta -> meta.lore(lore));
		return this;
	}

}
