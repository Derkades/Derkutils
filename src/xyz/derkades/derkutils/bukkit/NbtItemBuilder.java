package xyz.derkades.derkutils.bukkit;

import de.tr7zw.changeme.nbtapi.NBTCompound;
import de.tr7zw.changeme.nbtapi.NBTItem;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Consumer;

/**
 * Extension of ItemBuilder with functionality that requires NBT API
 */
public class NbtItemBuilder extends AbstractItemBuilder<NbtItemBuilder> {

	public NbtItemBuilder(@NotNull Material material) {
		super(material);
	}
	
	public NbtItemBuilder(@NotNull ItemStack item) {
		super(item);
	}
	
	@Override
	@NotNull
	public NbtItemBuilder getInstance() {
		return this;
	}
	
	@NotNull
	public NbtItemBuilder canDestroy(@NotNull final String... vanillaNamespacedNames) {
		Objects.requireNonNull(vanillaNamespacedNames, "names varargs is null");
		final NBTItem nbt = new NBTItem(this.item);
		nbt.getStringList("CanDestroy").addAll(Arrays.asList(vanillaNamespacedNames));
		this.item = nbt.getItem();
		return this;
	}
	
	@NotNull
	public NbtItemBuilder canPlaceOn(@NotNull final String... vanillaNamespacedNames) {
		Objects.requireNonNull(vanillaNamespacedNames, "names varargs is null");
		final NBTItem nbt = new NBTItem(this.item);
		nbt.getStringList("CanPlaceOn").addAll(Arrays.asList(vanillaNamespacedNames));
		this.item = nbt.getItem();
		return this;
	}
	
	@NotNull
	public NbtItemBuilder skullTexture(@NotNull final String texture) {
		Objects.requireNonNull(texture, "Texture string is null");
		final NBTItem nbt = new NBTItem(this.item);
		final NBTCompound skullOwner = nbt.addCompound("SkullOwner");
		skullOwner.setString("Id", UUID.randomUUID().toString());
		skullOwner.addCompound("Properties").getCompoundList("textures").addCompound().setString("Value", texture);
		this.item = nbt.getItem();
		return this;
	}
	
	@NotNull
	public NbtItemBuilder hideFlags(final int hideFlags) {
		final NBTItem nbt = new NBTItem(this.item);
		nbt.setInteger("HideFlags", hideFlags);
		this.item = nbt.getItem();
		return this;
	}

	public NbtItemBuilder editNbt(Consumer<NBTItem> nbtModifier) {
		final NBTItem nbt = new NBTItem(this.item);
		nbtModifier.accept(nbt);
		this.item = nbt.getItem();
		return this;
	}

}
