package xyz.derkades.derkutils.bukkit;

import de.tr7zw.changeme.nbtapi.NBT;
import de.tr7zw.changeme.nbtapi.NBTCompound;
import de.tr7zw.changeme.nbtapi.NBTItem;
import de.tr7zw.changeme.nbtapi.iface.ReadWriteItemNBT;
import de.tr7zw.changeme.nbtapi.iface.ReadWriteNBT;
import de.tr7zw.changeme.nbtapi.iface.ReadWriteNBTList;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;

/**
 * Extension of ItemBuilder with functionality that requires NBT API
 */
public class NbtItemBuilder extends AbstractItemBuilder<NbtItemBuilder> {

	public NbtItemBuilder(final @NonNull Material material) {
		super(material);
	}
	
	public NbtItemBuilder(final @NonNull ItemStack item) {
		super(item);
	}
	
	@Override
	public @NonNull NbtItemBuilder getInstance() {
		return this;
	}

	@Deprecated
	public @NonNull NbtItemBuilder canDestroy(final @NonNull String@NonNull... vanillaNamespacedNames) {
		Objects.requireNonNull(vanillaNamespacedNames, "names varargs is null");
		NBT.modify(this.item, nbt -> {
			nbt.getStringList("CanDestroy").addAll(List.of(vanillaNamespacedNames));
		});
		return this;
	}

	public NbtItemBuilder canDestroy(final Set<Material> materials) {
		NBT.modify(this.item, nbt -> {
			ReadWriteNBTList<String> list = nbt.getStringList("CanDestroy");
			for (Material material : materials) {
				list.add(material.getKey().toString());
			}
		});
		return this;
	}

	@Deprecated
	public @NonNull NbtItemBuilder canPlaceOn(final @NonNull String@NonNull... vanillaNamespacedNames) {
		Objects.requireNonNull(vanillaNamespacedNames, "names varargs is null");
		NBT.modify(this.item, nbt -> {
			nbt.getStringList("CanPlaceOn").addAll(List.of(vanillaNamespacedNames));
		});
		return this;
	}

	public NbtItemBuilder canPlaceOn(final Set<Material> materials) {
		NBT.modify(this.item, nbt -> {
			ReadWriteNBTList<String> list = nbt.getStringList("CanPlaceOn");
			for (Material material : materials) {
				list.add(material.getKey().toString());
			}
		});
		return this;
	}

	public @NonNull NbtItemBuilder skullTexture(final @NonNull String texture) {
		Objects.requireNonNull(texture, "Texture string is null");
		editNbt(nbt -> {
			final ReadWriteNBT skullOwner = nbt.getOrCreateCompound("SkullOwner");
			skullOwner.setString("Id", UUID.randomUUID().toString());
			skullOwner.getOrCreateCompound("Properties").getCompoundList("textures").addCompound().setString("Value", texture);
		});
		return this;
	}

	public @NonNull NbtItemBuilder hideFlags(final int hideFlags) {
		NBT.modify(this.item, nbt -> {
			nbt.setInteger("HideFlags", hideFlags);
		});
		return this;
	}

	public @NonNull NbtItemBuilder editNbt(final @NonNull Consumer<ReadWriteItemNBT> nbtModifier) {
		NBT.modify(this.item, nbtModifier);
		return this;
	}

}
