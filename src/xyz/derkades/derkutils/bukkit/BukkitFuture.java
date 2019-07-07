package xyz.derkades.derkutils.bukkit;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import xyz.derkades.derkutils.ThrowingSupplier;

public class BukkitFuture<T> {

	private final Plugin plugin;
	private final List<Consumer<T>> onCompleteCallbacks;
	private final List<Consumer<Exception>> onExceptionCallbacks;

	public BukkitFuture(final Plugin plugin, final ThrowingSupplier<T> action) {
		this.plugin = plugin;
		this.onCompleteCallbacks = new ArrayList<>();
		this.onExceptionCallbacks = new ArrayList<>();

		Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
			try {
				final T result = action.get();
				Bukkit.getScheduler().runTask(this.plugin, () -> this.onCompleteCallbacks.forEach((c) -> c.accept(result)));
			} catch (final Exception e) {
				Bukkit.getScheduler().runTask(this.plugin, () -> this.onExceptionCallbacks.forEach((c) -> c.accept(e)));
			}
		});
	}

	public void onComplete(final Consumer<T> callback) {
		this.onCompleteCallbacks.add(callback);
	}

	public void onException(final Consumer<Exception> callback) {
		this.onExceptionCallbacks.add(callback);
	}

}
