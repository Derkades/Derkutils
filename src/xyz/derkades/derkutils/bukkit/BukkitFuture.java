package xyz.derkades.derkutils.bukkit;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import xyz.derkades.derkutils.ThrowingSupplier;

/**
 * @deprecated Use java future
 */
@Deprecated
public class BukkitFuture<T> {

	private final Plugin plugin;
	private final List<Consumer<T>> onCompleteCallbacks;
	private final List<Consumer<Exception>> onExceptionCallbacks;

	public BukkitFuture(final Plugin plugin, final ThrowingSupplier<T, Exception> action) {
		Validate.notNull(plugin, "plugin must not be null");
		Validate.notNull(action, "action must not be null");
		
		this.plugin = plugin;
		this.onCompleteCallbacks = new ArrayList<>();
		this.onExceptionCallbacks = new ArrayList<>();

		Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
			try {
				final T result = action.get();
				Bukkit.getScheduler().runTask(this.plugin, () -> this.onCompleteCallbacks.forEach((c) -> c.accept(result)));
			} catch (final Exception e) {
				if (this.onExceptionCallbacks.isEmpty()) {
					throw new RuntimeException(e);
				} else {
					Bukkit.getScheduler().runTask(this.plugin, () -> this.onExceptionCallbacks.forEach((c) -> c.accept(e)));
				}
			}
		});
	}
	
	public void awaitCompletion() {
		this.onComplete(a -> this.notify());
		try {
			this.wait();
		} catch (final InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}

	public void onComplete(final Consumer<T> callback) {
		Validate.notNull(callback, "callback must not be null");
		this.onCompleteCallbacks.add(callback);
	}

	public void onException(final Consumer<Exception> callback) {
		Validate.notNull(callback, "callback must not be null");
		this.onExceptionCallbacks.add(callback);
	}

}