package xyz.derkades.derkutils.bukkit;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
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

import de.tr7zw.changeme.nbtapi.NBTCompound;
import de.tr7zw.changeme.nbtapi.NBTItem;
import xyz.derkades.derkutils.bukkit.reflection.ReflectionUtil;

public class ItemBuilder {

	private ItemStack item;

	public ItemBuilder(final Material material) {
		this.item = new ItemStack(Objects.requireNonNull(material, "Material is null"));
	}

	public ItemBuilder(final ItemStack item) {
		this.item = Objects.requireNonNull(item, "item is null");
	}

	public ItemBuilder(final String ownerName) {
		Objects.requireNonNull(ownerName, "Skull owner name is null");
		this.item = new ItemBuilder(Material.SKULL_ITEM).damage(3).skullOwner(ownerName).create();
	}

	public ItemBuilder amount(final int amount) {
		this.item.setAmount(amount);
		return this;
	}

	public ItemBuilder name(final String name) {
		final ItemMeta meta = this.item.getItemMeta();
		meta.setDisplayName(name);
		this.item.setItemMeta(meta);
		return this;
	}

	public ItemBuilder coloredName(final String name){
		final ItemMeta meta = this.item.getItemMeta();
		if (name == null) {
			meta.setDisplayName(null);
		} else {
			meta.setDisplayName(Colors.parseColors(name));
		}
		this.item.setItemMeta(meta);
		return this;
	}

	public ItemBuilder lore(final String... lore){
		final ItemMeta meta = this.item.getItemMeta();
		meta.setLore(Arrays.asList(lore));
		this.item.setItemMeta(meta);
		return this;
	}

	public ItemBuilder coloredLore(final String... lore){
		final ItemMeta meta = this.item.getItemMeta();
		if (lore == null) {
			meta.setLore(null);
		} else {
			meta.setLore(Colors.parseColors(Arrays.asList(lore)));
		}
		this.item.setItemMeta(meta);
		return this;
	}

	public ItemBuilder lore(final List<String> lore){
		final ItemMeta meta = this.item.getItemMeta();
		meta.setLore(lore);
		this.item.setItemMeta(meta);
		return this;
	}

	public ItemBuilder coloredLore(final List<String> lore){
		final ItemMeta meta = this.item.getItemMeta();
		if (lore == null) {
			meta.setLore(null);
		} else {
			meta.setLore(Colors.parseColors(lore));
		}
		this.item.setItemMeta(meta);
		return this;
	}

	public ItemBuilder skullOwner(final String ownerName){
		Objects.requireNonNull(ownerName, "Skull owner name is null");
		final SkullMeta meta = (SkullMeta) this.item.getItemMeta();
		meta.setOwner(ownerName);
		this.item.setItemMeta(meta);
		return this;
	}

	public ItemBuilder skullTexture(final String texture) {
		Objects.requireNonNull(texture, "Texture string is null");
		final NBTItem nbt = new NBTItem(this.item);
		final NBTCompound skullOwner = nbt.addCompound("SkullOwner");
		skullOwner.setString("Id", UUID.randomUUID().toString());
		skullOwner.addCompound("Properties").getCompoundList("textures").addCompound().setString("Value", texture);
		this.item = nbt.getItem();
		return this;
	}

	public ItemBuilder leatherArmorColor(final Color color) {
		Objects.requireNonNull(color, "Color is null");
		final LeatherArmorMeta meta = (LeatherArmorMeta) this.item.getItemMeta();
		meta.setColor(color);
		this.item.setItemMeta(meta);
		return this;
	}

	public ItemBuilder enchant(final Enchantment type, final int level) {
		Objects.requireNonNull(type, "Enchantment type is null");
		this.item.addEnchantment(type, level);
		return this;
	}

	public ItemBuilder unsafeEnchant(final Enchantment type, final int level) {
		Objects.requireNonNull(type, "Enchantment type is null");
		this.item.addUnsafeEnchantment(type, level);
		return this;
	}

	public ItemBuilder material(final Material material) {
		this.item.setType(material);
		return this;
	}

	public ItemBuilder type(final Material type) {
		this.item.setType(type);
		return this;
	}

	public ItemBuilder canDestroy(final Material... materials) {
		Objects.requireNonNull(materials, "Materials varargs is null");
		final List<String> minecraftItemNames = ReflectionUtil.materialToMinecraftName(materials);
		final NBTItem nbt = new NBTItem(this.item);
		nbt.getStringList("CanDestroy").addAll(minecraftItemNames);
		this.item = nbt.getItem();
		return this;
	}

	public ItemBuilder canPlaceOn(final Material... materials) {
		Objects.requireNonNull(materials, "materials varargs is null");
		final List<String> minecraftItemNames = ReflectionUtil.materialToMinecraftName(materials);
		final NBTItem nbt = new NBTItem(this.item);
		nbt.getStringList("CanPlaceOn").addAll(minecraftItemNames);
		this.item = nbt.getItem();
		return this;
	}

	public ItemBuilder damage(final int damage) {
		this.item.setDurability((short) damage);
		return this;
	}

	public ItemBuilder hideFlags(final int hideFlags) {
		final NBTItem nbt = new NBTItem(this.item);
		nbt.setInteger("HideFlags", hideFlags);
		this.item = nbt.getItem();
		return this;
	}

	public ItemBuilder namePlaceholder(final String key, final String value) {
		Objects.requireNonNull(key, "Placeholder key is null");
		Objects.requireNonNull(value, "Placeholder value is null");

		if (this.item.getItemMeta() == null || this.item.getItemMeta().getDisplayName() == null) {
			return this;
		}

		return this.name(this.item.getItemMeta().getDisplayName().replace(key, value));
	}

	public ItemBuilder namePlaceholders(final Map<String, String> placeholders) {
		Objects.requireNonNull(placeholders, "Placeholder map is null");

		if (this.item.getItemMeta() == null || this.item.getItemMeta().getDisplayName() == null) {
			return this;
		}

		placeholders.forEach(this::namePlaceholder);
		return this;
	}

	public ItemBuilder namePlaceholderOptional(final String key, final Supplier<String> value) {
		Objects.requireNonNull(key, "Placeholder key is null");
		Objects.requireNonNull(value, "Placeholder value is null");

		if (this.item.getItemMeta() == null || this.item.getItemMeta().getDisplayName() == null) {
			return this;
		}

		final String oldName = this.item.getItemMeta().getDisplayName();
		if (oldName.contains(key)) {
			return this.name(oldName.replace(key, value.get()));
		} else {
			return this;
		}
	}

	public ItemBuilder namePlaceholdersOptional(final Map<String, Supplier<String>> placeholders) {
		Objects.requireNonNull(placeholders, "Placeholder map is null");

		if (this.item.getItemMeta() == null || this.item.getItemMeta().getDisplayName() == null) {
			return this;
		}

		placeholders.forEach(this::namePlaceholderOptional);
		return this;
	}

	public ItemBuilder lorePlaceholder(final String key, final String value) {
		Objects.requireNonNull(key, "Placeholder key is null");
		Objects.requireNonNull(value, "Placeholder value is null");

		if (this.item.getItemMeta() == null || this.item.getItemMeta().getLore() == null) {
			return this;
		}

		return this.lore(this.item.getItemMeta().getLore().stream().map((s) -> s.replace(key, value)).collect(Collectors.toList()));
	}

	public ItemBuilder lorePlaceholders(final Map<String, String> placeholders) {
		Objects.requireNonNull(placeholders, "Placeholder map is null");

		if (this.item.getItemMeta() == null || this.item.getItemMeta().getLore() == null) {
			return this;
		}

		placeholders.forEach(this::lorePlaceholder);
		return this;
	}

	public ItemBuilder lorePlaceholderOptional(final String key, final Supplier<String> value) {
		Objects.requireNonNull(key, "Placeholder key is null");
		Objects.requireNonNull(value, "Placeholder value is null");

		if (this.item.getItemMeta() == null || this.item.getItemMeta().getLore() == null) {
			return this;
		}

		return this.lore(this.item.getItemMeta().getLore().stream().map((s) -> {
			if (s.contains(key)) {
				return s.replace(key, value.get());
			} else {
				return s;
			}
		}).collect(Collectors.toList()));
	}

	public ItemBuilder lorePlaceholdersOptional(final Map<String, Supplier<String>> placeholders) {
		Objects.requireNonNull(placeholders, "Placeholder map is null");

		if (this.item.getItemMeta() == null || this.item.getItemMeta().getLore() == null) {
			return this;
		}

		placeholders.forEach(this::lorePlaceholderOptional);
		return this;
	}

	public ItemBuilder placeholder(final String key, final String value) {
		Objects.requireNonNull(key, "Placeholder key is null");
		Objects.requireNonNull(value, "Placeholder value is null");

		return this.namePlaceholder(key, value).lorePlaceholder(key, value);
	}

	public ItemBuilder placeholders(final Map<String, String> placeholders) {
		Objects.requireNonNull(placeholders, "Placeholder map is null");

		return this.namePlaceholders(placeholders).lorePlaceholders(placeholders);
	}

	public ItemBuilder placeholderOptional(final String key, final Supplier<String> value) {
		Objects.requireNonNull(key, "Placeholder key is null");
		Objects.requireNonNull(value, "Placeholder value is null");

		return this.namePlaceholderOptional(key, value).lorePlaceholderOptional(key, value);
	}

	public ItemBuilder placeholdersOptional(final Map<String, Supplier<String>> placeholders) {
		Objects.requireNonNull(placeholders, "Placeholder map is null");

		return this.namePlaceholdersOptional(placeholders).lorePlaceholdersOptional(placeholders);
	}

	public ItemBuilder lorePapi(final Player player) {
		if (this.item.getItemMeta() == null || this.item.getItemMeta().getLore() == null) {
			return this;
		}

		return this.lore(this.item.getItemMeta().getLore().stream().map((s) -> PlaceholderUtil.parsePapiPlaceholders(player, s)).collect(Collectors.toList()));
	}

	public ItemBuilder namePapi(final Player player) {
		if (this.item.getItemMeta() == null || this.item.getItemMeta().getDisplayName() == null) {
			return this;
		}

		return this.name(PlaceholderUtil.parsePapiPlaceholders(player, this.item.getItemMeta().getDisplayName()));
	}

	public ItemBuilder papi(final Player player) {
		this.namePapi(player);
		return this.lorePapi(player);
	}

	public ItemStack create(){
		return this.item;
	}

}
