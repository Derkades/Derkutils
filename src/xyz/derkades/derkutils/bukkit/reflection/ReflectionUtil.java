package xyz.derkades.derkutils.bukkit.reflection;

import java.lang.reflect.InvocationTargetException;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

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

}
