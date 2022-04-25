package xyz.derkades.derkutils.bukkit.reflection;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReflectionUtil {

	private static final Map<String, Class<?>> classCache = new HashMap<>();
	private static final String version = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];

	/**
	 *
	 * @param pathToClass Path to a Minecraft class, with %s where the version string would usually be. For example: <i>org.bukkit.craftbukkit.%s.entity.CraftPlayer</i>
	 * @return Class from formatted string
	 * @throws ClassNotFoundException
	 */
	public static Class<?> getMinecraftClass(final String pathToClass) throws ClassNotFoundException {
		Class<?> cached = classCache.get(pathToClass);

		if (cached == null) {
			String className = String.format(pathToClass, version);
			className = className.replace(".v1_17_R1", "");
			cached = Class.forName(className);
			classCache.put(pathToClass, cached);
		}

		return cached;
	}

	/**
	 *
	 * @param player
	 * @return Player ping or -1 if an error occurred
	 */
	public static int getPing(final Player player) {
		try {
			final Object entityPlayer = getMinecraftClass("org.bukkit.craftbukkit.%s.entity.CraftPlayer").getMethod("getHandle").invoke(player);
			final Object ping = getMinecraftClass("net.minecraft.server.%s.EntityPlayer").getField("ping").get(entityPlayer);
			return (int) ping;
		} catch (final ClassNotFoundException | NoSuchMethodException | SecurityException |
				IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException e) {
			e.printStackTrace();
			return -1;
		}
	}

	@Deprecated // broken in 1.15+
	public static ItemStack addCanPlaceOn(final ItemStack item, final String... minecraftItemNames) {
		try {
			final Class<?> craftItemStackClass = getMinecraftClass("org.bukkit.craftbukkit.%s.inventory.CraftItemStack");
			final Object nmsItemStack = craftItemStackClass.getMethod("asNMSCopy", ItemStack.class).invoke(null, item);
			final Class<?> nbtClass = getMinecraftClass("net.minecraft.server.%s.NBTTagCompound");
			Object nbt = nmsItemStack.getClass().getMethod("getTag").invoke(nmsItemStack);
			if (nbt == null) {
				nbt = nbtClass.getConstructor().newInstance();
			}

			final Object nbtList = getMinecraftClass("net.minecraft.server.%s.NBTTagList").getConstructor().newInstance();
			for (String minecraftItemName : minecraftItemNames) {
				if (!minecraftItemName.contains("minecraft")) {
					minecraftItemName = "minecraft:" + minecraftItemName;
				}

				final Constructor<?> constr = getMinecraftClass("net.minecraft.server.%s.NBTTagString").getConstructor(String.class);
				constr.setAccessible(true);
				final Object nbtString = constr.newInstance(minecraftItemName);
				nbtList.getClass().getMethod("add", Object.class).invoke(nbtList, nbtString);
			}

			nbtClass.getMethod("set", String.class, getMinecraftClass("net.minecraft.server.%s.NBTBase")).invoke(nbt, "CanPlaceOn", nbtList);
			nmsItemStack.getClass().getMethod("setTag", nbtClass).invoke(nmsItemStack, nbt);
			final Object bukkitItemStack = craftItemStackClass.getMethod("asBukkitCopy", nmsItemStack.getClass()).invoke(null, nmsItemStack);
			return (ItemStack) bukkitItemStack;
		} catch (ClassNotFoundException | IllegalAccessException | IllegalArgumentException |
				InvocationTargetException | NoSuchMethodException | SecurityException | InstantiationException e) {
			e.printStackTrace();
			return item;
		}
	}

	@Deprecated // broken in 1.15+
	public static ItemStack addCanDestroy(final ItemStack item, final String... minecraftItemNames) {
		try {
			final Class<?> craftItemStackClass = getMinecraftClass("org.bukkit.craftbukkit.%s.inventory.CraftItemStack");
			final Object nmsItemStack = craftItemStackClass.getMethod("asNMSCopy", ItemStack.class).invoke(null, item);
			final Class<?> nbtClass = getMinecraftClass("net.minecraft.server.%s.NBTTagCompound");
			Object nbt = nmsItemStack.getClass().getMethod("getTag").invoke(nmsItemStack);
			if (nbt == null) {
				nbt = nbtClass.getConstructor().newInstance();
			}
			final Object nbtList = getMinecraftClass("net.minecraft.server.%s.NBTTagList").getConstructor().newInstance();
			for (String minecraftItemName : minecraftItemNames) {
				if (!minecraftItemName.contains("minecraft")) {
					minecraftItemName = "minecraft:" + minecraftItemName;
				}

				final Constructor<?> constr = getMinecraftClass("net.minecraft.server.%s.NBTTagString").getConstructor(String.class);
				constr.setAccessible(true);
				final Object nbtString = constr.newInstance(minecraftItemName);
				nbtList.getClass().getMethod("add", Object.class).invoke(nbtList, nbtString);
			}
			nbtClass.getMethod("set", String.class, getMinecraftClass("net.minecraft.server.%s.NBTBase")).invoke(nbt, "CanDestroy", nbtList);
			nmsItemStack.getClass().getMethod("setTag", nbtClass).invoke(nmsItemStack, nbt);
			final Object bukkitItemStack = craftItemStackClass.getMethod("asBukkitCopy", nmsItemStack.getClass()).invoke(null, nmsItemStack);
			return (ItemStack) bukkitItemStack;
		} catch (ClassNotFoundException | IllegalAccessException | IllegalArgumentException |
				InvocationTargetException | NoSuchMethodException | SecurityException | InstantiationException e) {
			e.printStackTrace();
			return item;
		}
	}

	@SuppressWarnings("null")
	@NonNull
	public static CommandMap getCommandMap() {
		try {
			final Field field = Bukkit.getServer().getClass().getDeclaredField("commandMap");
			field.setAccessible(true);
			return (CommandMap) field.get(Bukkit.getServer());
		} catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	public static void registerCommand(final @NonNull String name, final @NonNull Command command) {
		getCommandMap().register(name, command);
	}

	@SuppressWarnings({ "unchecked", "null" })
	@NonNull
	public static Map<String, Command> getKnownCommands() {
		try {
			final CommandMap map = getCommandMap();
			return (Map<String, Command>) map.getClass().getMethod("getKnownCommands").invoke(map);
		} catch (final InvocationTargetException | IllegalAccessException | SecurityException | NoSuchMethodException e) {
			throw new RuntimeException(e);
		}
	}

	public static void unregisterCommand(final Command command) {
		final List<String> names = new ArrayList<>();
		names.add(command.getName());
		names.addAll(command.getAliases());
		command.unregister(getCommandMap());
		names.forEach(getKnownCommands()::remove);
	}

	/**
	 *
	 * @param materials
	 * @return
	 * @deprecated Broken in 1.17
	 */
	@Deprecated
	public static List<String> materialToMinecraftName(final Material... materials) {
		final List<String> itemNames = new ArrayList<>();
		try {
			final Class<?> magicNumbersClass = ReflectionUtil.getMinecraftClass("org.bukkit.craftbukkit.%s.util.CraftMagicNumbers");
			final Class<?> nmsItemClass = ReflectionUtil.getMinecraftClass("net.minecraft.server.%s.Item");
			final Method getItemMethod = magicNumbersClass.getMethod("getItem", Material.class);
			final Method getNameMethod = nmsItemClass.getMethod("getName");
			for (final Material material : materials) {
				final Object nmsItem = getItemMethod.invoke(null, material); // CraftMagicNumbers.getItem(material)
				String minecraftName = (String) getNameMethod.invoke(nmsItem); // nmsItem.getName()
				if (minecraftName.startsWith("item.minecraft.")) { // 1.16 hack
					minecraftName = minecraftName.replace("item.minecraft.", "minecraft:");
				}
				if (minecraftName.startsWith("block.minecraft.")) { // 1.16 hack
					minecraftName = minecraftName.replace("block.minecraft.", "minecraft:");
				}
				itemNames.add(minecraftName);
			}
		} catch (final ClassNotFoundException | NoSuchMethodException | SecurityException | ClassCastException | IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return itemNames;
	}


}
