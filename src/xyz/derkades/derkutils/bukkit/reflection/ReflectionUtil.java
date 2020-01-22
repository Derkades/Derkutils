package xyz.derkades.derkutils.bukkit.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ReflectionUtil {

	/**
	 *
	 * @param pathToClass Path to a Minecraft class, with %s where the version string would usually be. For example: <i>org.bukkit.craftbukkit.%s.entity.CraftPlayer</i>
	 * @return Class from formatted string
	 * @throws ClassNotFoundException
	 */
	public static Class<?> getMinecraftClass(final String pathToClass) throws ClassNotFoundException {
		final String version = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
		return Class.forName(String.format(pathToClass, version));
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

	public static ItemStack addCanPlaceOn(final ItemStack item, final String... minecraftItemNames) {
		try {
			final Class<?> craftItemStackClass = getMinecraftClass("org.bukkit.craftbukkit.%s.inventory.CraftItemStack");
			final Object nmsItemStack = craftItemStackClass.getMethod("asNMSCopy", ItemStack.class).invoke(null, item);
			final Class<?> nbtClass = getMinecraftClass("net.minecraft.server.%s.NBTTagCompound");
			Object nbt = nmsItemStack.getClass().getMethod("getTag").invoke(null);
			if (nbt == null) {
				nbt = nbtClass.getConstructor().newInstance();
			}
			final Object nbtList = getMinecraftClass("net.minecraft.server.%s.NBTTagList").getConstructor().newInstance();
			for (String minecraftItemName : minecraftItemNames) {
				if (!minecraftItemName.contains("minecraft")) {
					minecraftItemName = "minecraft:" + minecraftItemName;
				}

				final Object nbtString = getMinecraftClass("net.minecraft.server.%s.NBTTagString").getConstructor(String.class).newInstance(minecraftItemName);
				nbtList.getClass().getMethod("add", nbtString.getClass()).invoke(nbtList, nbtString);
			}
			nbtClass.getMethod("set", String.class, nbtList.getClass()).invoke(nbt, "CanPlaceOn", nbtList);
			nmsItemStack.getClass().getMethod("setTag", nbtClass).invoke(nmsItemStack, nbt);
			final Object bukkitItemStack = craftItemStackClass.getMethod("asBukkitCopy", nmsItemStack.getClass()).invoke(null, nmsItemStack);
			return (ItemStack) bukkitItemStack;
		} catch (ClassNotFoundException | IllegalAccessException | IllegalArgumentException |
				InvocationTargetException | NoSuchMethodException | SecurityException | InstantiationException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static ItemStack addCanDestroy(final ItemStack item, final String... minecraftItemNames) {
		try {
			final Class<?> craftItemStackClass = getMinecraftClass("org.bukkit.craftbukkit.%s.inventory.CraftItemStack");
			final Object nmsItemStack = craftItemStackClass.getMethod("asNMSCopy", ItemStack.class).invoke(null, item);
			final Class<?> nbtClass = getMinecraftClass("net.minecraft.server.%s.NBTTagCompound");
			Object nbt = nmsItemStack.getClass().getMethod("getTag").invoke(null);
			if (nbt == null) {
				nbt = nbtClass.getConstructor().newInstance();
			}
			final Object nbtList = getMinecraftClass("net.minecraft.server.%s.NBTTagList").getConstructor().newInstance();
			for (String minecraftItemName : minecraftItemNames) {
				if (!minecraftItemName.contains("minecraft")) {
					minecraftItemName = "minecraft:" + minecraftItemName;
				}

				final Object nbtString = getMinecraftClass("net.minecraft.server.%s.NBTTagString").getConstructor(String.class).newInstance(minecraftItemName);
				nbtList.getClass().getMethod("add", nbtString.getClass()).invoke(nbtList, nbtString);
			}
			nbtClass.getMethod("set", String.class, nbtList.getClass()).invoke(nbt, "CanDestroy", nbtList);
			nmsItemStack.getClass().getMethod("setTag", nbtClass).invoke(nmsItemStack, nbt);
			final Object bukkitItemStack = craftItemStackClass.getMethod("asBukkitCopy", nmsItemStack.getClass()).invoke(null, nmsItemStack);
			return (ItemStack) bukkitItemStack;
		} catch (ClassNotFoundException | IllegalAccessException | IllegalArgumentException |
				InvocationTargetException | NoSuchMethodException | SecurityException | InstantiationException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static CommandMap getCommandMap() {
		try {
			final Field field = Bukkit.getServer().getClass().getDeclaredField("commandMap");
			field.setAccessible(true);
			return (CommandMap) field.get(Bukkit.getServer());
		} catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static void registerCommand(String name, Command command) {
		getCommandMap().register(name, command);
	}

}
