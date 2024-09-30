package xyz.derkades.derkutils.bukkit;

import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Consumer;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.checkerframework.checker.nullness.qual.NonNull;

import de.tr7zw.changeme.nbtapi.NBT;
import de.tr7zw.changeme.nbtapi.iface.ReadWriteItemNBT;
import de.tr7zw.changeme.nbtapi.iface.ReadWriteNBT;

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

	public @NonNull NbtItemBuilder canDestroy(final String... vanillaNamespacedNames) {
		Objects.requireNonNull(vanillaNamespacedNames, "names varargs is null");
		NBT.modify(this.item, nbt -> {
			nbt.getStringList("CanDestroy").addAll(Arrays.asList(vanillaNamespacedNames));
		});
		return this;
	}

	public @NonNull NbtItemBuilder canPlaceOn(final String... vanillaNamespacedNames) {
		Objects.requireNonNull(vanillaNamespacedNames, "names varargs is null");
		NBT.modify(this.item, nbt -> {
			nbt.getStringList("CanPlaceOn").addAll(Arrays.asList(vanillaNamespacedNames));
		});
		return this;
	}

	@Override
	public @NonNull NbtItemBuilder skullTexture(final @NonNull String texture) {
		Objects.requireNonNull(texture, "Texture string is null");
		this.editNbt(nbt -> {
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
