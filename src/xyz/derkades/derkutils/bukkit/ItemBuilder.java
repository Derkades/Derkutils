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

public class ItemBuilder {
	
	private ItemStack item;
	
	public ItemBuilder(Material material){
		item = new ItemStack(material);
	}
	
	public ItemBuilder(ItemStack item){
		this.item = item;
	}
	
	public ItemBuilder(String skullOwner){
		item = new ItemBuilder(Material.PLAYER_HEAD).setSkullOwner(skullOwner).create();
	}
	
	@Deprecated
	public ItemBuilder setAmount(int amount){
		item.setAmount(amount);
		return this;
	}
	
	public ItemBuilder amount(int amount){
		item.setAmount(amount);
		return this;
	}
	
	@Deprecated
	public ItemBuilder setName(String name){
		final ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(name);
		item.setItemMeta(meta);
		return this;
	}
	
	public ItemBuilder name(String name){
		final ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(name);
		item.setItemMeta(meta);
		return this;
	}
	
	public ItemBuilder coloredName(String name){
		final ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(Colors.parseColors(name));
		item.setItemMeta(meta);
		return this;
	}
	
	@Deprecated
	public ItemBuilder setLore(String... lore){
		final ItemMeta meta = item.getItemMeta();
		meta.setLore(Arrays.asList(lore));
		item.setItemMeta(meta);
		return this;
	}
	
	public ItemBuilder lore(String... lore){
		final ItemMeta meta = item.getItemMeta();
		meta.setLore(Arrays.asList(lore));
		item.setItemMeta(meta);
		return this;
	}
	
	public ItemBuilder coloredLore(String... lore){
		final ItemMeta meta = item.getItemMeta();
		meta.setLore(Colors.parseColors(Arrays.asList(lore)));
		item.setItemMeta(meta);
		return this;
	}
	
	@Deprecated
	public ItemBuilder setLore(List<String> lore){
		final ItemMeta meta = item.getItemMeta();
		meta.setLore(lore);
		item.setItemMeta(meta);
		return this;
	}
	
	public ItemBuilder lore(List<String> lore){
		final ItemMeta meta = item.getItemMeta();
		meta.setLore(lore);
		item.setItemMeta(meta);
		return this;
	}
	
	public ItemBuilder coloredLore(List<String> lore){
		final ItemMeta meta = item.getItemMeta();
		meta.setLore(Colors.parseColors(lore));
		item.setItemMeta(meta);
		return this;
	}
	
	@Deprecated
	public ItemBuilder setSkullOwner(String playerName){
		final SkullMeta meta = (SkullMeta) item.getItemMeta();
		meta.setOwner(playerName);
		item.setItemMeta(meta);
		return this;
	}
	
	public ItemBuilder skullOwner(OfflinePlayer player){
		final SkullMeta meta = (SkullMeta) item.getItemMeta();
		meta.setOwningPlayer(player);
		item.setItemMeta(meta);
		return this;
	}
	
	@Deprecated	
	public ItemBuilder setLeatherArmorColor(Color color){
		final LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
		meta.setColor(color);
		return this;
	}

	public ItemBuilder leatherArmorColor(Color color){
		final LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
		meta.setColor(color);
		return this;
	}
	
	@Deprecated
	public ItemBuilder addEnchantment(Enchantment type, int level){
		return enchant(type, level);
	}
	
	public ItemBuilder enchant(Enchantment type, int level){
		item.addEnchantment(type, level);
		return this;
	}
	
	public ItemBuilder unsafeEnchant(Enchantment type, int level){
		final ItemMeta meta = item.getItemMeta();
		meta.addEnchant(type, level, true);
		return this;
	}
	
	public ItemBuilder material(Material material){
		item.setType(material);
		return this;
	}
	
	public ItemBuilder type(Material type){
		item.setType(type);
		return this;
	}
	
	public ItemStack create(){
		return item;
	}

}
