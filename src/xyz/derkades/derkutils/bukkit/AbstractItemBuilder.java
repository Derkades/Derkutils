package xyz.derkades.derkutils.bukkit;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.annotation.Nonnull;

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
import org.bukkit.profile.PlayerProfile;
import org.bukkit.profile.PlayerTextures;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import com.destroystokyo.paper.Namespaced;
import com.destroystokyo.paper.profile.ProfileProperty;
import com.google.common.base.FinalizablePhantomReference;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import io.netty.util.internal.shaded.org.jctools.queues.MessagePassingQueue.Consumer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;

public abstract class AbstractItemBuilder<T extends AbstractItemBuilder<T>> {

	private static final Component NO_ITALICS = Component.empty().decoration(TextDecoration.ITALIC, false);

	protected ItemStack item;
	
	public AbstractItemBuilder(final Material material) {
		this.item = new ItemStack(Objects.requireNonNull(material, "Material is null"));
	}

	public AbstractItemBuilder(final ItemStack item) {
		this.item = Objects.requireNonNull(item, "item is null");
	}

	public abstract T getInstance();

	@SuppressWarnings("unchecked")
	public @Nonnull <M extends ItemMeta> T editMeta(Class<M> metaClasss, Consumer<M> metaEditor) {
		// Convenient helper function inspired by paper
	   final @Nullable ItemMeta meta = this.item.getItemMeta();
	   metaEditor.accept((M) meta);
		this.item.setItemMeta(meta);
		return this.getInstance();
	}

	public @NonNull T amount(final int amount) {
		this.item.setAmount(amount);
		return this.getInstance();
	}


	public @NonNull T name(final @Nullable Component name) {
		if (name == null) {
			item.editMeta(meta -> meta.displayName(null));
		} else {
			item.editMeta(meta -> meta.displayName(NO_ITALICS.append(name)));
		}
		return this.getInstance();
	}

	public @NonNull T lore(final @NonNull Component@Nullable... lore) {
		if (lore == null) {
			item.editMeta(meta -> meta.lore(null));
		} else {
			item.editMeta(meta -> meta.lore(Arrays.stream(lore).map(NO_ITALICS::append).collect(Collectors.toList())));
		}
		return this.getInstance();
	}

	public @NonNull T lore(final @Nullable List<@NonNull Component> lore) {
		if (lore == null) {
			item.editMeta(meta -> meta.lore(null));
		} else {
			item.editMeta(meta -> meta.lore(lore.stream().map(NO_ITALICS::append).collect(Collectors.toList())));
		}
		return this.getInstance();
	}

	public @NonNull T skullOwner(final @Nullable OfflinePlayer player) {
		final SkullMeta meta = (SkullMeta) this.item.getItemMeta();
		meta.setOwningPlayer(player);
		this.item.setItemMeta(meta);
		return this.getInstance();
	}

	public @NonNull T leatherArmorColor(final @NonNull Color color) {
		Objects.requireNonNull(color, "Color is null");
		final LeatherArmorMeta meta = (LeatherArmorMeta) this.item.getItemMeta();
		meta.setColor(color);
		this.item.setItemMeta(meta);
		return this.getInstance();
	}

	public @NonNull T enchant(final @NonNull Enchantment type) {
		return enchant(type, 1);
	}

	public @NonNull T enchant(final @NonNull Enchantment type, final int level) {
		Objects.requireNonNull(type, "Enchantment type is null");
		this.item.addEnchantment(type, level);
		return this.getInstance();
	}

	public @NonNull T unsafeEnchant(final @NonNull Enchantment type, final int level) {
		Objects.requireNonNull(type, "Enchantment type is null");
		this.item.addUnsafeEnchantment(type, level);
		return this.getInstance();
	}

	public @NonNull T material(final Material material) {
		this.item.setType(material == null ? Material.AIR : material);
		return this.getInstance();
	}


	public @NonNull T type(final @Nullable Material type) {
		this.item.setType(type == null ? Material.AIR : type);
		return this.getInstance();
	}

	public @NonNull T unbreakable() {
		final ItemMeta meta = this.item.getItemMeta();
		meta.setUnbreakable(true);
		this.item.setItemMeta(meta);
		return this.getInstance();
	}

	public @NonNull T damage(final int damage) {
		final Damageable damageable = (Damageable) this.item.getItemMeta();
		damageable.setDamage(damage);
		this.item.setItemMeta(damageable);
		return this.getInstance();
	}

	public @NonNull T canPlaceOn(final @NonNull Collection<Namespaced> placeableKeys) {
		item.editMeta(meta -> meta.setPlaceableKeys(placeableKeys));
		return this.getInstance();
	}

	public @NonNull T canPlaceOnMinecraft(final @Nullable String... canPlaceOn) {
		if (canPlaceOn == null) {
			return this.canPlaceOn(Collections.emptySet());
		} else {
			return this.canPlaceOn(Arrays.stream(canPlaceOn).map(NamespacedKey::minecraft).collect(Collectors.toSet()));
		}
	}

	public @NonNull T canDestroy(final @NonNull Collection<Namespaced> destroyableKeys) {
		item.editMeta(meta -> meta.setDestroyableKeys(destroyableKeys));
		return this.getInstance();
	}

	public @NonNull T canDestroyMinecraft(final @Nullable String... canDestroy) {
		if (canDestroy == null) {
			return this.canDestroy(Collections.emptySet());
		} else {
			return this.canDestroy(Arrays.stream(canDestroy).map(NamespacedKey::minecraft).collect(Collectors.toSet()));
		}
	}

	public @NonNull T itemFlags(final @NonNull ItemFlag... flags) {
		item.editMeta(meta -> meta.addItemFlags(flags));
		return this.getInstance();
	}

	@SuppressWarnings("deprecation") // we intentionally use methods deprecated in paper, to support spigot servers
	public @NonNull T skullTexture(final @NonNull String skinTextureJson) {
		final URL skinTextureUrl;
		try {
			skinTextureUrl = new URI(
					JsonParser.parseString(skinTextureJson)
							.getAsJsonObject()
							.get("textures")
							.getAsJsonObject()
							.get("SKIN")
							.getAsJsonObject()
							.get("url")
							.getAsString()
					).toURL();
		} catch (IllegalStateException | JsonSyntaxException | MalformedURLException | URISyntaxException e) {
			throw new RuntimeException("Failed to parse skin texture json", e);
		}
		
		Throwable spigotError = null;
		
		// Try spigot native method first, exists since 1.20
		try {
			PlayerProfile profile = Bukkit.getServer().createPlayerProfile(UUID.randomUUID());;
			PlayerTextures textures = profile.getTextures();
			textures.setSkin(skinTextureUrl);
			profile.setTextures(textures); // is this necessary?
			this.editMeta(SkullMeta.class, skullMeta -> skullMeta.setOwnerProfile(profile));
		} catch (NoSuchMethodError err) {
			spigotError = err;
		}
		
		// Try paper method next, exists since at least 1.18
		try {
			com.destroystokyo.paper.profile.PlayerProfile profile = Bukkit.getServer().createProfile(UUID.randomUUID());
			profile.setProperty(new ProfileProperty("textures", skinTextureJson));
			this.editMeta(SkullMeta.class, skullMeta -> skullMeta.setPlayerProfile(profile));
		} catch (NoSuchMethodError paperError) {
			throw new UnsupportedOperationException("Failed to set head texture, API not available.", 
					spigotError != null ? spigotError : paperError);
		}
		
		return this.getInstance();
	}

	public @NonNull T skullProfile(com.destroystokyo.paper.profile.@NonNull PlayerProfile profile) {
		item.editMeta(SkullMeta.class, meta -> meta.setPlayerProfile(profile));
		return this.getInstance();
	}

	@Deprecated
	public T hideFlags(final @NonNull ItemFlag @NonNull... itemFlags) {
		return addHideFlags(itemFlags);
	}

	public @NonNull T addHideFlags(final @NonNull ItemFlag @NonNull... itemFlags) {
		this.item.editMeta(meta -> {
			if (meta == null) {
				throw new IllegalStateException("Item meta is null");
			}
			meta.addItemFlags(itemFlags);
		});
		return this.getInstance();
	}

	public @NonNull T replaceHideFlags(final @NonNull ItemFlag @NonNull... itemFlags) {
		this.item.editMeta(meta -> {
			if (meta == null) {
				throw new IllegalStateException("Item meta is null");
			}
			meta.removeItemFlags(ItemFlag.values());
			meta.addItemFlags(itemFlags);
		});
		return this.getInstance();
	}

	public @NonNull T removeHideFlags() {
		this.item.editMeta(meta -> {
			if (meta == null) {
				throw new IllegalStateException("Item meta is null");
			}
			meta.removeItemFlags(ItemFlag.values());
		});
		return this.getInstance();
	}

	public @NonNull T hideFlags() {
		return this.hideFlags(ItemFlag.values());
	}

	public @NonNull T hideFlags(boolean hideAllFlags) {
		if (hideAllFlags) {
			return this.hideFlags(ItemFlag.values());
		} else {
			return removeHideFlags();
		}
	}

	public @NonNull ItemStack create(){
		return this.item;
	}

}
