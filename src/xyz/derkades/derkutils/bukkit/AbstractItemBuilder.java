package xyz.derkades.derkutils.bukkit;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;
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
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.attribute.AttributeModifier.Operation;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import com.destroystokyo.paper.Namespaced;
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
			this.item.editMeta(meta -> meta.displayName(null));
		} else {
			this.item.editMeta(meta -> meta.displayName(NO_ITALICS.append(name)));
		}
		return this.getInstance();
	}

	public @NonNull T lore(final @NonNull Component@Nullable... lore) {
		if (lore == null) {
			this.item.editMeta(meta -> meta.lore(null));
		} else {
			this.item.editMeta(meta -> meta.lore(Arrays.stream(lore).map(NO_ITALICS::append).collect(Collectors.toList())));
		}
		return this.getInstance();
	}

	public @NonNull T lore(final @Nullable List<@NonNull Component> lore) {
		if (lore == null) {
			this.item.editMeta(meta -> meta.lore(null));
		} else {
			this.item.editMeta(meta -> meta.lore(lore.stream().map(NO_ITALICS::append).collect(Collectors.toList())));
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
		return this.enchant(type, 1);
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
		this.item.editMeta(meta -> meta.setPlaceableKeys(placeableKeys));
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
		this.item.editMeta(meta -> meta.setDestroyableKeys(destroyableKeys));
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
		this.item.editMeta(meta -> meta.addItemFlags(flags));
		return this.getInstance();
	}

	public @NonNull T skullTexture(final @NonNull String skinTextureJsonBase64) {
		final String skinTextureJson;
		try {
			skinTextureJson = new String(Base64.getDecoder().decode(skinTextureJsonBase64), StandardCharsets.UTF_8.toString());
		} catch (final UnsupportedEncodingException e) {
			throw new RuntimeException("Failed to decode skin texture base64: " + skinTextureJsonBase64);
		}
		
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
			throw new RuntimeException("Failed to parse skin texture json: " + skinTextureJson);
		}
		return this.skullTextureURL(skinTextureUrl);	
	}
	
	public T skullTextureURL(final URL skinTextureUrl) {
		// Uses reflection so it compiles for older server versions
		try {
			final Class<?> iPlayerProfile = Class.forName("org.bukkit.profile.PlayerProfile");
			final Class<?> iPlayerTextures = Class.forName("org.bukkit.profile.PlayerTextures");
			final Method createPlayerProfile = Bukkit.getServer().getClass().getMethod("createPlayerProfile", UUID.class);
			final Object playerProfile = createPlayerProfile.invoke(Bukkit.getServer(), UUID.randomUUID());
			final Object playerTextures = iPlayerProfile.getMethod("getTextures").invoke(playerProfile);
			iPlayerTextures.getMethod("setSkin", URL.class).invoke(playerTextures, skinTextureUrl);
			iPlayerProfile.getMethod("setTextures", iPlayerTextures).invoke(playerProfile, playerTextures);
			final SkullMeta meta = (SkullMeta) this.item.getItemMeta();
			SkullMeta.class.getMethod("setOwnerProfile", iPlayerProfile).invoke(meta, playerProfile);
			this.item.setItemMeta(meta);
		} catch (ClassNotFoundException | NoSuchMethodException e) {
			throw new UnsupportedOperationException("Method not found, only available on 1.20+", e);
		} catch (IllegalAccessException | InvocationTargetException e2) {
			e2.printStackTrace();
		}
		
		return this.getInstance();
	}

	public @NonNull T skullProfile(com.destroystokyo.paper.profile.@NonNull PlayerProfile profile) {
		this.item.editMeta(SkullMeta.class, meta -> meta.setPlayerProfile(profile));
		return this.getInstance();
	}

	public @NonNull T addHideFlags(final @NonNull ItemFlag @NonNull... itemFlags) {
		this.item.editMeta(meta -> {
			if (meta == null) {
				throw new IllegalStateException("Item meta is null");
			}
			// Paper requires an attribute to be present to hide anything
			// https://github.com/PaperMC/Paper/issues/10655
			meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, new AttributeModifier(UUID.randomUUID(), "", 1, Operation.ADD_NUMBER));
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
		return this.addHideFlags(ItemFlag.values());
	}

	public @NonNull T hideFlags(boolean hideAllFlags) {
		if (hideAllFlags) {
			return this.hideFlags();
		} else {
			return this.removeHideFlags();
		}
	}
	
	public T modelData(@Nullable Integer data) {
		this.item.editMeta(meta -> {
			meta.setCustomModelData(data);
		});
		return this.getInstance();
	}

	public @NonNull ItemStack create(){
		return this.item;
	}

}
