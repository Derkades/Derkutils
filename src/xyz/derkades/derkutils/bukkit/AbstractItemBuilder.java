package xyz.derkades.derkutils.bukkit;

import com.destroystokyo.paper.Namespaced;
import com.destroystokyo.paper.profile.PlayerProfile;
import com.destroystokyo.paper.profile.ProfileProperty;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.OfflinePlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

public abstract class AbstractItemBuilder<T extends AbstractItemBuilder<T>> {
	
	@NotNull
	protected ItemStack item;
	
	public AbstractItemBuilder(@NotNull final Material material) {
		this.item = new ItemStack(Objects.requireNonNull(material, "Material is null"));
	}

	public AbstractItemBuilder(@NotNull final ItemStack item) {
		this.item = Objects.requireNonNull(item, "item is null");
	}
	
	@NotNull
	public abstract T getInstance();

	@NotNull
	public T amount(final int amount) {
		this.item.setAmount(amount);
		return this.getInstance();
	}

	@NotNull
	public T name(@Nullable final Component name) {
		item.editMeta(meta -> meta.displayName(name));
		return this.getInstance();
	}

	@NotNull
	public T lore(final @NotNull Component@Nullable... lore) {
		item.editMeta(meta -> meta.lore(lore == null ? Collections.emptyList() : Arrays.asList(lore)));
		return this.getInstance();
	}

	@NotNull
	public T lore(@Nullable final List<@NotNull Component> lore) {
		item.editMeta(meta -> meta.lore(lore));
		return this.getInstance();
	}

	@NotNull
	public T skullOwner(@Nullable final OfflinePlayer player) {
		final SkullMeta meta = (SkullMeta) this.item.getItemMeta();
		meta.setOwningPlayer(player);
		this.item.setItemMeta(meta);
		return this.getInstance();
	}

	@NotNull
	public T leatherArmorColor(@NotNull final Color color) {
		Objects.requireNonNull(color, "Color is null");
		final LeatherArmorMeta meta = (LeatherArmorMeta) this.item.getItemMeta();
		meta.setColor(color);
		this.item.setItemMeta(meta);
		return this.getInstance();
	}

	@NotNull
	public T enchant(@NotNull final Enchantment type) {
		return enchant(type, 1);
	}

	@NotNull
	public T enchant(@NotNull final Enchantment type, final int level) {
		Objects.requireNonNull(type, "Enchantment type is null");
		this.item.addEnchantment(type, level);
		return this.getInstance();
	}

	@NotNull
	public T unsafeEnchant(@NotNull final Enchantment type, final int level) {
		Objects.requireNonNull(type, "Enchantment type is null");
		this.item.addUnsafeEnchantment(type, level);
		return this.getInstance();
	}

	@NotNull
	public T material(final Material material) {
		this.item.setType(material == null ? Material.AIR : material);
		return this.getInstance();
	}

	@NotNull
	public T type(@Nullable final Material type) {
		this.item.setType(type == null ? Material.AIR : type);
		return this.getInstance();
	}

	@NotNull
	public T unbreakable() {
		final ItemMeta meta = this.item.getItemMeta();
		meta.setUnbreakable(true);
		this.item.setItemMeta(meta);
		return this.getInstance();
	}

	@NotNull
	public T damage(final int damage) {
		final Damageable damageable = (Damageable) this.item.getItemMeta();
		damageable.setDamage(damage);
		this.item.setItemMeta(damageable);
		return this.getInstance();
	}

	public @NotNull T canPlaceOn(@NotNull Collection<Namespaced> placeableKeys) {
		item.editMeta(meta -> meta.setPlaceableKeys(placeableKeys));
		return this.getInstance();
	}

	public @NotNull T canPlaceOnMinecraft(@Nullable String... canPlaceOn) {
		if (canPlaceOn == null) {
			return this.canPlaceOn(Collections.emptySet());
		} else {
			return this.canPlaceOn(Arrays.stream(canPlaceOn).map(NamespacedKey::minecraft).collect(Collectors.toSet()));
		}
	}

	public @NotNull T canDestroy(@NotNull Collection<Namespaced> destroyableKeys) {
		item.editMeta(meta -> meta.setDestroyableKeys(destroyableKeys));
		return this.getInstance();
	}

	public @NotNull T canDestroyMinecraft(@Nullable String... canDestroy) {
		if (canDestroy == null) {
			return this.canDestroy(Collections.emptySet());
		} else {
			return this.canDestroy(Arrays.stream(canDestroy).map(NamespacedKey::minecraft).collect(Collectors.toSet()));
		}
	}

	public @NotNull T itemFlags(@NotNull ItemFlag... flags) {
		item.editMeta(meta -> meta.addItemFlags(flags));
		return this.getInstance();
	}

	public @NotNull T skullTexture(@NotNull String skullTexture) {
		PlayerProfile profile = Bukkit.getServer().createProfile(UUID.randomUUID());
		profile.setProperty(new ProfileProperty("textures", skullTexture));
		return this.skullProfile(profile);
	}

	public @NotNull T skullProfile(@NotNull PlayerProfile profile) {
		item.editMeta(SkullMeta.class, meta -> meta.setPlayerProfile(profile));
		return this.getInstance();
	}

	public T hideFlags(@NotNull ItemFlag @NotNull... itemFlags) {
		ItemMeta meta = this.item.getItemMeta();
		if (meta == null) {
			throw new IllegalStateException("Item meta is null");
		}
		meta.addItemFlags(itemFlags);
		return this.getInstance();
	}

	public T hideFlags() {
		ItemMeta meta = this.item.getItemMeta();
		if (meta == null) {
			throw new IllegalStateException("Item meta is null");
		}
		meta.addItemFlags(ItemFlag.values());
		return this.getInstance();
	}

	@NotNull
	public ItemStack create(){
		return this.item;
	}

}
