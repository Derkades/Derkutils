package xyz.derkades.derkutils.bukkit;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
	public T name(@Nullable final String name) {
		final ItemMeta meta = this.item.getItemMeta();
		meta.setDisplayName(name);
		this.item.setItemMeta(meta);
		return this.getInstance();
	}

	public T coloredName(final String name){
		final ItemMeta meta = this.item.getItemMeta();
		if (name == null) {
			meta.setDisplayName(null);
		} else {
			meta.setDisplayName(Colors.parseColors(name));
		}
		this.item.setItemMeta(meta);
		return this.getInstance();
	}

	public T lore(final String... lore){
		final ItemMeta meta = this.item.getItemMeta();
		meta.setLore(Arrays.asList(lore));
		this.item.setItemMeta(meta);
		return this.getInstance();
	}

	public T coloredLore(final String... lore){
		final ItemMeta meta = this.item.getItemMeta();
		if (lore == null) {
			meta.setLore(null);
		} else {
			meta.setLore(Colors.parseColors(Arrays.asList(lore)));
		}
		this.item.setItemMeta(meta);
		return this.getInstance();
	}

	public T lore(final List<String> lore){
		final ItemMeta meta = this.item.getItemMeta();
		meta.setLore(lore);
		this.item.setItemMeta(meta);
		return this.getInstance();
	}

	public T coloredLore(final List<String> lore){
		final ItemMeta meta = this.item.getItemMeta();
		if (lore == null) {
			meta.setLore(null);
		} else {
			meta.setLore(Colors.parseColors(lore));
		}
		this.item.setItemMeta(meta);
		return this.getInstance();
	}

	public T skullOwner(String owner) {
		final SkullMeta meta = (SkullMeta) this.item.getItemMeta();
		meta.setOwner(owner);
		this.item.setItemMeta(meta);
		return this.getInstance();
	}

	public T leatherArmorColor(final Color color) {
		Objects.requireNonNull(color, "Color is null");
		final LeatherArmorMeta meta = (LeatherArmorMeta) this.item.getItemMeta();
		meta.setColor(color);
		this.item.setItemMeta(meta);
		return this.getInstance();
	}

	public T enchant(final Enchantment type) {
		return enchant(type, 1);
	}

	public T enchant(final Enchantment type, final int level) {
		Objects.requireNonNull(type, "Enchantment type is null");
		this.item.addEnchantment(type, level);
		return this.getInstance();
	}

	public T unsafeEnchant(final Enchantment type, final int level) {
		Objects.requireNonNull(type, "Enchantment type is null");
		this.item.addUnsafeEnchantment(type, level);
		return this.getInstance();
	}

	public T material(final Material material) {
		this.item.setType(material);
		return this.getInstance();
	}

	public T type(final Material type) {
		this.item.setType(type);
		return this.getInstance();
	}

	public T damage(final int durability) {
		item.setDurability((short) durability);
		return this.getInstance();
	}

	public T namePlaceholder(final String key, final String value) {
		Objects.requireNonNull(key, "Placeholder key is null");
		Objects.requireNonNull(value, "Placeholder value is null");

		if (this.item.getItemMeta() == null || this.item.getItemMeta().getDisplayName() == null) {
			return this.getInstance();
		}

		return this.name(this.item.getItemMeta().getDisplayName().replace(key, value));
	}

	public T namePlaceholders(final Map<String, String> placeholders) {
		Objects.requireNonNull(placeholders, "Placeholder map is null");

		if (this.item.getItemMeta() == null || this.item.getItemMeta().getDisplayName() == null) {
			return this.getInstance();
		}

		placeholders.forEach(this::namePlaceholder);
		return this.getInstance();
	}

	public T namePlaceholderOptional(final String key, final Supplier<String> value) {
		Objects.requireNonNull(key, "Placeholder key is null");
		Objects.requireNonNull(value, "Placeholder value is null");

		if (this.item.getItemMeta() == null || this.item.getItemMeta().getDisplayName() == null) {
			return this.getInstance();
		}

		final String oldName = this.item.getItemMeta().getDisplayName();
		if (oldName.contains(key)) {
			return this.name(oldName.replace(key, value.get()));
		} else {
			return this.getInstance();
		}
	}

	public T namePlaceholdersOptional(final Map<String, Supplier<String>> placeholders) {
		Objects.requireNonNull(placeholders, "Placeholder map is null");

		if (this.item.getItemMeta() == null || this.item.getItemMeta().getDisplayName() == null) {
			return this.getInstance();
		}

		placeholders.forEach(this::namePlaceholderOptional);
		return this.getInstance();
	}

	public T lorePlaceholder(final String key, final String value) {
		Objects.requireNonNull(key, "Placeholder key is null");
		Objects.requireNonNull(value, "Placeholder value is null");

		if (this.item.getItemMeta() == null || this.item.getItemMeta().getLore() == null) {
			return this.getInstance();
		}

		return this.lore(this.item.getItemMeta().getLore().stream().map((s) -> s.replace(key, value)).collect(Collectors.toList()));
	}

	public T lorePlaceholders(final Map<String, String> placeholders) {
		Objects.requireNonNull(placeholders, "Placeholder map is null");

		if (this.item.getItemMeta() == null || this.item.getItemMeta().getLore() == null) {
			return this.getInstance();
		}

		placeholders.forEach(this::lorePlaceholder);
		return this.getInstance();
	}

	public T lorePlaceholderOptional(final String key, final Supplier<String> value) {
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

	public T lorePlaceholdersOptional(final Map<String, Supplier<String>> placeholders) {
		Objects.requireNonNull(placeholders, "Placeholder map is null");

		if (this.item.getItemMeta() == null || this.item.getItemMeta().getLore() == null) {
			return this.getInstance();
		}

		placeholders.forEach(this::lorePlaceholderOptional);
		return this.getInstance();
	}

	public T placeholder(final String key, final String value) {
		Objects.requireNonNull(key, "Placeholder key is null");
		Objects.requireNonNull(value, "Placeholder value is null");

		return this.namePlaceholder(key, value).lorePlaceholder(key, value);
	}

	public T placeholders(final Map<String, String> placeholders) {
		Objects.requireNonNull(placeholders, "Placeholder map is null");

		return this.namePlaceholders(placeholders).lorePlaceholders(placeholders);
	}

	public T placeholderOptional(final String key, final Supplier<String> value) {
		Objects.requireNonNull(key, "Placeholder key is null");
		Objects.requireNonNull(value, "Placeholder value is null");

		return this.namePlaceholderOptional(key, value).lorePlaceholderOptional(key, value);
	}

	public T placeholdersOptional(final Map<String, Supplier<String>> placeholders) {
		Objects.requireNonNull(placeholders, "Placeholder map is null");

		return this.namePlaceholdersOptional(placeholders).lorePlaceholdersOptional(placeholders);
	}

	public T lorePapi(final Player player) {
		if (this.item.getItemMeta() == null || this.item.getItemMeta().getLore() == null) {
			return this.getInstance();
		}

		return this.lore(this.item.getItemMeta().getLore().stream().map((s) -> PlaceholderUtil.parsePapiPlaceholders(player, s)).collect(Collectors.toList()));
	}

	public T namePapi(final Player player) {
		if (this.item.getItemMeta() == null || this.item.getItemMeta().getDisplayName() == null) {
			return this.getInstance();
		}

		return this.name(PlaceholderUtil.parsePapiPlaceholders(player, this.item.getItemMeta().getDisplayName()));
	}

	public T papi(@NotNull final Player player) {
		Objects.requireNonNull(player, "Player is null");
		this.namePapi(player);
		return this.lorePapi(player);
	}

	@NotNull
	public ItemStack create(){
		return this.item;
	}

}
