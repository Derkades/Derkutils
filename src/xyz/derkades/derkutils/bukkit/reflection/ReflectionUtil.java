package xyz.derkades.derkutils.bukkit.reflection;


import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;

public class ReflectionUtil {

	private static final Map<String, Class<?>> classCache = new HashMap<>();
	private static Method playerPingMethod;
	
	static {
		try {
			playerPingMethod = Player.class.getMethod("ping");
		} catch (NoSuchMethodException | SecurityException e) {
			playerPingMethod = null;
		}
	}
	

	private static Class<?> getCB(final String pathAfterCB) {
		classCache.computeIfAbsent("CB" + pathAfterCB, key -> {
			try {
				return Class.forName(Bukkit.getServer().getClass().getPackage().getName() + "." + pathAfterCB);
			} catch (ClassNotFoundException e) {
				throw new RuntimeException(e);
			}
		});
		return classCache.get("CB" + pathAfterCB);
	}


	/**
	 *
	 * @param player
	 * @return Player ping or -1 if an error occurred
	 */
	public static int getPing(final Player player) {
		if (playerPingMethod != null) {
			try {
				return (int) playerPingMethod.invoke(player);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				e.printStackTrace();
				return 0;
			}
		}
		
		try {
			// Does not exist pre-1.17
			// Use NMS hack instead
			String version = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
			Class<?> entityPlayerClass = Class.forName("net.minecraft.server." + version + ".EntityPlayer");
			final Object entityPlayer = getCB(".entity.CraftPlayer").getMethod("getHandle").invoke(player);
			final Object ping = entityPlayerClass.getField("ping").get(entityPlayer);
			return (Integer) ping;
		} catch (final ClassNotFoundException | NoSuchMethodException | SecurityException |
				IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException e2) {
			e2.printStackTrace();
			return 0;
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

	public static void registerCommand(final @NonNull String name, final @NonNull Command command) {
		getCommandMap().register(name, command);
	}

	@SuppressWarnings({ "unchecked" })
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

}
