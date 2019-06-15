package xyz.derkades.derkutils.bukkit;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

import xyz.derkades.derkutils.bukkit.reflection.ReflectionUtil;

public class ItemBuilder {

	private ItemStack item;

	public ItemBuilder(final Material material){
		this.item = new ItemStack(material);
	}

	public ItemBuilder(final ItemStack item){
		this.item = item;
	}

	public ItemBuilder(final OfflinePlayer skullOwner){
		this.item = new ItemBuilder(Material.SKULL_ITEM).damage(3).skullOwner(skullOwner).create();
	}

	public ItemBuilder(final String ownerName) {
		this.item = new ItemBuilder(Material.SKULL_ITEM).damage(3).skullOwner(ownerName).create();
	}

	public ItemBuilder amount(final int amount){
		this.item.setAmount(amount);
		return this;
	}

	public ItemBuilder name(final String name){
		final ItemMeta meta = this.item.getItemMeta();
		meta.setDisplayName(name);
		this.item.setItemMeta(meta);
		return this;
	}

	public ItemBuilder coloredName(final String name){
		final ItemMeta meta = this.item.getItemMeta();
		meta.setDisplayName(Colors.parseColors(name));
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
		meta.setLore(Colors.parseColors(Arrays.asList(lore)));
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
		meta.setLore(Colors.parseColors(lore));
		this.item.setItemMeta(meta);
		return this;
	}

	public ItemBuilder skullOwner(final OfflinePlayer player){
		return this.skullOwner(player.getName());
	}

	public ItemBuilder skullOwner(final String ownerName) {
		final SkullMeta meta = (SkullMeta) this.item.getItemMeta();
		meta.setOwner(ownerName);
		this.item.setItemMeta(meta);
		return this;
	}

	public ItemBuilder leatherArmorColor(final Color color){
		final LeatherArmorMeta meta = (LeatherArmorMeta) this.item.getItemMeta();
		meta.setColor(color);
		this.item.setItemMeta(meta);
		return this;
	}

	public ItemBuilder enchant(final Enchantment type, final int level){
		this.item.addEnchantment(type, level);
		return this;
	}

	public ItemBuilder unsafeEnchant(final Enchantment type, final int level){
		final ItemMeta meta = this.item.getItemMeta();
		meta.addEnchant(type, level, true);
		return this;
	}

	public ItemBuilder material(final Material material){
		this.item.setType(material);
		return this;
	}

	public ItemBuilder type(final Material type){
		this.item.setType(type);
		return this;
	}

	public ItemBuilder canDestroy(final String... minecraftItemNames) {
		this.item = ReflectionUtil.addCanDestroy(this.item, minecraftItemNames);
		return this;
	}

	public ItemBuilder canPlaceOn(final String... minecraftItemNames) {
		this.item = ReflectionUtil.addCanPlaceOn(this.item, minecraftItemNames);
		return this;
	}

	public ItemBuilder damage(final int damage) {
		this.item.setDurability((short) damage);
		return this;
	}

	public ItemStack create(){
		return this.item;
	}

}
