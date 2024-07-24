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
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

public abstract class AbstractItemBuilder<T extends AbstractItemBuilder<T>> {

	protected ItemStack item;

	public AbstractItemBuilder(final Material material) {
		this.item = new ItemStack(Objects.requireNonNull(material, "Material is null"));
	}

	public AbstractItemBuilder(final ItemStack item) {
		this.item = Objects.requireNonNull(item, "item is null");
	}

	public abstract T getInstance();

	@SuppressWarnings("unchecked")
	public @NonNull <M extends ItemMeta> T editMeta(Class<M> metaClasss, Consumer<M> metaEditor) {
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

	public @NonNull T name(final @Nullable String name) {
		final ItemMeta meta = this.item.getItemMeta();
		meta.setDisplayName(name);
		this.item.setItemMeta(meta);
		return this.getInstance();
	}

	public @NonNull T coloredName(final @Nullable String name){
		final ItemMeta meta = this.item.getItemMeta();
		if (name == null) {
			meta.setDisplayName(null);
		} else {
			meta.setDisplayName(Colors.parseColors(name));
		}
		this.item.setItemMeta(meta);
		return this.getInstance();
	}

	public @NonNull T lore(@Nullable final String... lore){
		final ItemMeta meta = this.item.getItemMeta();
		if (lore == null) {
			meta.setLore(null);
		} else {
			meta.setLore(Arrays.asList(lore));
		}
		this.item.setItemMeta(meta);
		return this.getInstance();
	}

	public @NonNull T coloredLore(final @Nullable String... lore){
		final ItemMeta meta = this.item.getItemMeta();
		if (lore == null) {
			meta.setLore(null);
		} else {
			meta.setLore(Colors.parseColors(Arrays.asList(lore)));
		}
		this.item.setItemMeta(meta);
		return this.getInstance();
	}

	public @NonNull T lore(final @Nullable List<String> lore){
		final ItemMeta meta = this.item.getItemMeta();
		meta.setLore(lore);
		this.item.setItemMeta(meta);
		return this.getInstance();
	}

	public @NonNull T coloredLore(final @Nullable List<String> lore){
		final ItemMeta meta = this.item.getItemMeta();
		if (lore == null) {
			meta.setLore(null);
		} else {
			meta.setLore(Colors.parseColors(lore));
		}
		this.item.setItemMeta(meta);
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
		ItemMeta meta = this.item.getItemMeta();
		if (meta != null) {
			((Damageable) meta).setDamage(damage);
			item.setItemMeta(meta);
		}
		return this.getInstance();
	}

	public @NonNull T namePlaceholder(final @NonNull String key, final @NonNull String value) {
		Objects.requireNonNull(key, "Placeholder key is null");
		Objects.requireNonNull(value, "Placeholder value is null");

		if (this.item.getItemMeta() == null) {
			return this.getInstance();
		}

		return this.name(this.item.getItemMeta().getDisplayName().replace(key, value));
	}

	public @NonNull T namePlaceholders(final @NonNull Map<String, String> placeholders) {
		Objects.requireNonNull(placeholders, "Placeholder map is null");

		if (this.item.getItemMeta() == null) {
			return this.getInstance();
		}

		placeholders.forEach(this::namePlaceholder);
		return this.getInstance();
	}

	public @NonNull T namePlaceholderOptional(final @NonNull String key,
											  final @NonNull Supplier<String> value) {
		Objects.requireNonNull(key, "Placeholder key is null");
		Objects.requireNonNull(value, "Placeholder value is null");

		if (this.item.getItemMeta() == null) {
			return this.getInstance();
		}

		final String oldName = this.item.getItemMeta().getDisplayName();
		if (oldName.contains(key)) {
			return this.name(oldName.replace(key, value.get()));
		} else {
			return this.getInstance();
		}
	}

	public @NonNull T namePlaceholdersOptional(final @NonNull Map<String, Supplier<String>> placeholders) {
		Objects.requireNonNull(placeholders, "Placeholder map is null");

		if (this.item.getItemMeta() == null) {
			return this.getInstance();
		}

		placeholders.forEach(this::namePlaceholderOptional);
		return this.getInstance();
	}

	public @NonNull T lorePlaceholder(final @NonNull String key, final @NonNull String value) {
		Objects.requireNonNull(key, "Placeholder key is null");
		Objects.requireNonNull(value, "Placeholder value is null");

		if (this.item.getItemMeta() == null || this.item.getItemMeta().getLore() == null) {
			return this.getInstance();
		}

		return this.lore(this.item.getItemMeta().getLore().stream().map((s) -> s.replace(key, value)).collect(Collectors.toList()));
	}

	public @NonNull T lorePlaceholders(final @NonNull Map<String, String> placeholders) {
		Objects.requireNonNull(placeholders, "Placeholder map is null");

		if (this.item.getItemMeta() == null || this.item.getItemMeta().getLore() == null) {
			return this.getInstance();
		}

		placeholders.forEach(this::lorePlaceholder);
		return this.getInstance();
	}

	public @NonNull T lorePlaceholderOptional(final @NonNull String key,
											  final @NonNull Supplier<String> value) {
		Objects.requireNonNull(key, "Placeholder key is null");
		Objects.requireNonNull(value, "Placeholder value is null");

		if (this.item.getItemMeta() == null || this.item.getItemMeta().getLore() == null) {
			return this.getInstance();
		}

		return this.lore(this.item.getItemMeta().getLore().stream().map((s) -> {
			if (s.contains(key)) {
				return s.replace(key, value.get());
			} else {
				return s;
			}
		}).collect(Collectors.toList()));
	}

	public @NonNull T lorePlaceholdersOptional(final @NonNull Map<String, Supplier<String>> placeholders) {
		Objects.requireNonNull(placeholders, "Placeholder map is null");

		if (this.item.getItemMeta() == null || this.item.getItemMeta().getLore() == null) {
			return this.getInstance();
		}

		placeholders.forEach(this::lorePlaceholderOptional);
		return this.getInstance();
	}

	public @NonNull T skullTexture(final @NonNull String skinTextureJsonBase64) {
		final String skinTextureJson;
		try {
			skinTextureJson = new String(Base64.getDecoder().decode(skinTextureJsonBase64), StandardCharsets.UTF_8.toString());
		} catch (UnsupportedEncodingException e) {
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
		
		// Uses reflection so it compiles for older server versions
		
		try {
			Class<?> iPlayerProfile = Class.forName("org.bukkit.profile.PlayerProfile");
			Class<?> iPlayerTextures = Class.forName("org.bukkit.profile.PlayerTextures");
			Method createPlayerProfile = Bukkit.getServer().getClass().getMethod("createPlayerProfile", UUID.class);
			Object playerProfile = createPlayerProfile.invoke(Bukkit.getServer(), UUID.randomUUID());
			Object playerTextures = iPlayerProfile.getMethod("getTextures").invoke(playerProfile);
			iPlayerTextures.getMethod("setSkin", URL.class).invoke(playerTextures, skinTextureUrl);
			iPlayerProfile.getMethod("setTextures", iPlayerTextures).invoke(playerProfile, playerTextures);
			SkullMeta meta = (SkullMeta) item.getItemMeta();
			SkullMeta.class.getMethod("setOwnerProfile", iPlayerProfile).invoke(meta, playerProfile);
			item.setItemMeta(meta);
		} catch (ClassNotFoundException | NoSuchMethodException e) {
			throw new UnsupportedOperationException("Method not found, only available on 1.20+", e);
		} catch (IllegalAccessException | InvocationTargetException e2) {
			e2.printStackTrace();
		}
		
		return this.getInstance();
	}

	@Deprecated
	public T hideFlags(final @NonNull ItemFlag @NonNull... itemFlags) {
		return addHideFlags(itemFlags);
	}

	public @NonNull T addHideFlags(final @NonNull ItemFlag @NonNull... itemFlags) {
		ItemMeta meta = this.item.getItemMeta();
		if (meta == null) {
			throw new IllegalStateException("Item meta is null");
		}
		meta.addItemFlags(itemFlags);
		this.item.setItemMeta(meta);
		return this.getInstance();
	}

	public @NonNull T replaceHideFlags(final @NonNull ItemFlag @NonNull... itemFlags) {
		ItemMeta meta = this.item.getItemMeta();
		if (meta == null) {
			throw new IllegalStateException("Item meta is null");
		}
		meta.removeItemFlags(ItemFlag.values());
		meta.addItemFlags(itemFlags);
		this.item.setItemMeta(meta);
		return this.getInstance();
	}

	public T removeHideFlags() {
		ItemMeta meta = this.item.getItemMeta();
		if (meta == null) {
			throw new IllegalStateException("Item meta is null");
		}
		meta.removeItemFlags(ItemFlag.values());
		item.setItemMeta(meta);
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

	public @NonNull T placeholder(final @NonNull String key,
								  final @NonNull String value) {
		Objects.requireNonNull(key, "Placeholder key is null");
		Objects.requireNonNull(value, "Placeholder value is null");

		return this.namePlaceholder(key, value).lorePlaceholder(key, value);
	}

	public @NonNull T placeholders(final @NonNull Map<String, String> placeholders) {
		Objects.requireNonNull(placeholders, "Placeholder map is null");

		return this.namePlaceholders(placeholders).lorePlaceholders(placeholders);
	}

	public @NonNull T placeholderOptional(final @NonNull String key,
								 final Supplier<String> value) {
		Objects.requireNonNull(key, "Placeholder key is null");
		Objects.requireNonNull(value, "Placeholder value is null");

		return this.namePlaceholderOptional(key, value).lorePlaceholderOptional(key, value);
	}

	public @NonNull T placeholdersOptional(final @NonNull Map<String, Supplier<String>> placeholders) {
		Objects.requireNonNull(placeholders, "Placeholder map is null");

		return this.namePlaceholdersOptional(placeholders).lorePlaceholdersOptional(placeholders);
	}

	public @NonNull T lorePapi(final @NonNull Player player) {
		if (this.item.getItemMeta() == null || this.item.getItemMeta().getLore() == null) {
			return this.getInstance();
		}

		return this.lore(this.item.getItemMeta().getLore().stream().map((s) -> PlaceholderUtil.parsePapiPlaceholders(player, s)).collect(Collectors.toList()));
	}

	public @NonNull T namePapi(final @NonNull Player player) {
		if (this.item.getItemMeta() == null) {
			return this.getInstance();
		}

		return this.name(PlaceholderUtil.parsePapiPlaceholders(player, this.item.getItemMeta().getDisplayName()));
	}

	public @NonNull T papi(final @NonNull Player player) {
		Objects.requireNonNull(player, "Player is null");
		this.namePapi(player);
		return this.lorePapi(player);
	}

	public @NonNull ItemStack create(){
		return this.item;
	}

}
