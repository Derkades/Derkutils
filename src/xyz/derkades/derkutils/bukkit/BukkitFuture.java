package xyz.derkades.derkutils.bukkit;

import java.util.Stack;
import java.util.concurrent.Callable;
import java.util.function.Consumer;

import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class BukkitFuture<T> {

	private final Plugin plugin;
	private final Callable<T> action;
	private final Stack<Consumer<T>> onCompleteCallbacks;
	private final Stack<Consumer<Exception>> onExceptionCallbacks;
	private boolean retrieving = false;
	private boolean done = false;

	public BukkitFuture(final Plugin plugin, final Callable<T> action) {
		Validate.notNull(plugin, "plugin must not be null");
		Validate.notNull(action, "action must not be null");
		
		this.plugin = plugin;
		this.action = action;
		this.onCompleteCallbacks = new Stack<>();
		this.onExceptionCallbacks = new Stack<>();
	}
	
	/**
	 * Runs callable asynchronously, then runs onComplete and onException handlers
	 */
	public synchronized BukkitFuture<T> retrieveAsync() {
		if (this.done) {
			throw new IllegalStateException("Already retrieved");
		}
		
		if (this.retrieving) {
			throw new IllegalStateException("Already retrieving");
		}
		
		this.retrieving = true;
		
		Bukkit.getScheduler().runTaskAsynchronously(this.plugin, () -> {
			try {
				final T result = this.action.call();
				this.done = true;
				Bukkit.getScheduler().runTask(this.plugin, () -> this.onCompleteCallbacks.forEach((c) -> c.accept(result)));
			} catch (final Exception e) {
				if (this.onExceptionCallbacks.isEmpty()) {
					throw new RuntimeException(e);
				} else {
					Bukkit.getScheduler().runTask(this.plugin, () -> this.onExceptionCallbacks.forEach((c) -> c.accept(e)));
				}
			}
		});
		
		return this;
	}
	
	/**
	 * Freeze thread
	 */
	public synchronized BukkitFuture<T> awaitCompletion() {
		if (this.done) {
			throw new IllegalStateException("Already retrieved");
		}
		
		this.onComplete(a -> this.notify());
		try {
			this.wait();
		} catch (final InterruptedException e) {
			Thread.currentThread().interrupt();
		}
		
		return this;
	}
	
	/**
	 * Call synchronously
	 * @return
	 * @throws Exception
	 */
	public synchronized T get() throws Exception {
		if (this.done) {
			throw new IllegalStateException("Already retrieved");
		}
		
		if (this.done) {
			throw new IllegalStateException("Already retrieved");
		}
		
		this.retrieving = true;
		final T t = this.action.call();
		this.done = true;
		return t;
	}

	public BukkitFuture<T> onComplete(final Consumer<T> callback) {
		Validate.notNull(callback, "callback must not be null");
		
		if (this.done) {
			throw new IllegalStateException("Already retrieved");
		}
		
		synchronized(this) {
			this.onCompleteCallbacks.add(callback);
		}
		
		return this;
	}

	public BukkitFuture<T> onException(final Consumer<Exception> callback) {
		Validate.notNull(callback, "callback must not be null");
		
		if (this.done) {
			throw new IllegalStateException("Already retrieved");
		}
		
		synchronized(this) {
			this.onExceptionCallbacks.add(callback);
		}
		
		return this;
	}

}