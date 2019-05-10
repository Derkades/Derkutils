package xyz.derkades.derkutils.bukkit;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.craftbukkit.v1_14_R1.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

import net.minecraft.server.v1_14_R1.NBTTagCompound;
import net.minecraft.server.v1_14_R1.NBTTagList;
import net.minecraft.server.v1_14_R1.NBTTagString;

public class ItemBuilder {
	
	private ItemStack item;
	
	public ItemBuilder(final Material material){
		item = new ItemStack(material);
	}
	
	public ItemBuilder(final ItemStack item){
		this.item = item;
	}
	
	public ItemBuilder(final OfflinePlayer skullOwner){
		item = new ItemBuilder(Material.PLAYER_HEAD).skullOwner(skullOwner).create();
	}
	
	public ItemBuilder amount(final int amount){
		item.setAmount(amount);
		return this;
	}
	
	public ItemBuilder name(final String name){
		final ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(name);
		item.setItemMeta(meta);
		return this;
	}
	
	public ItemBuilder coloredName(final String name){
		final ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(Colors.parseColors(name));
		item.setItemMeta(meta);
		return this;
	}
	
	public ItemBuilder lore(final String... lore){
		final ItemMeta meta = item.getItemMeta();
		meta.setLore(Arrays.asList(lore));
		item.setItemMeta(meta);
		return this;
	}
	
	public ItemBuilder coloredLore(final String... lore){
		final ItemMeta meta = item.getItemMeta();
		meta.setLore(Colors.parseColors(Arrays.asList(lore)));
		item.setItemMeta(meta);
		return this;
	}
	
	public ItemBuilder lore(final List<String> lore){
		final ItemMeta meta = item.getItemMeta();
		meta.setLore(lore);
		item.setItemMeta(meta);
		return this;
	}
	
	public ItemBuilder coloredLore(final List<String> lore){
		final ItemMeta meta = item.getItemMeta();
		meta.setLore(Colors.parseColors(lore));
		item.setItemMeta(meta);
		return this;
	}
	
	public ItemBuilder skullOwner(final OfflinePlayer player){
		final SkullMeta meta = (SkullMeta) item.getItemMeta();
		meta.setOwningPlayer(player);
		item.setItemMeta(meta);
		return this;
	}

	public ItemBuilder leatherArmorColor(final Color color){
		final LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
		meta.setColor(color);
		return this;
	}
	
	public ItemBuilder enchant(final Enchantment type, final int level){
		item.addEnchantment(type, level);
		return this;
	}
	
	public ItemBuilder unsafeEnchant(final Enchantment type, final int level){
		final ItemMeta meta = item.getItemMeta();
		meta.addEnchant(type, level, true);
		return this;
	}
	
	public ItemBuilder material(final Material material){
		item.setType(material);
		return this;
	}
	
	public ItemBuilder type(final Material type){
		item.setType(type);
		return this;
	}
	
	public ItemBuilder unbreakable() {
		final ItemMeta meta = item.getItemMeta();
		meta.setUnbreakable(true);
		item.setItemMeta(meta);
		return this;
	}
	
	public ItemBuilder canDestroy(final String... minecraftItemNames) {
		net.minecraft.server.v1_14_R1.ItemStack nms = CraftItemStack.asNMSCopy(item);
		NBTTagCompound tag = nms.getTag();
		
		NBTTagList list = new NBTTagList();
		Arrays.asList(minecraftItemNames).forEach(name -> list.add(new NBTTagString("minecraft:" + name)));
		
		tag.set("CanDestroy", list);
		
		item = CraftItemStack.asBukkitCopy(nms);
		
		return this;
	}
	
	public ItemBuilder damage(final int damage) {
		final Damageable damageable = (Damageable) item.getItemMeta();
		damageable.setDamage(damage);
		item.setItemMeta((ItemMeta) damageable);
		return this;
	}
	
	public ItemStack create(){
		return item;
	}

}
