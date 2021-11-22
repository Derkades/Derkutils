package xyz.derkades.derkutils.bukkit;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.Callable;
import java.util.concurrent.Semaphore;
import java.util.function.Consumer;

@Deprecated
public class BukkitFuture<T> {

	@NotNull private final Plugin plugin;
	@NotNull private final Callable<T> action;
	@NotNull private final Deque<Consumer<T>> onCompleteCallbacks;
	@NotNull private final Deque<Consumer<Exception>> onExceptionCallbacks;
	private volatile boolean retrieving = false;
	private volatile boolean done = false;

	public BukkitFuture(@NotNull final Plugin plugin, @NotNull final Callable<T> action) {
		this.plugin = plugin;
		this.action = action;
		this.onCompleteCallbacks = new ArrayDeque<>();
		this.onExceptionCallbacks = new ArrayDeque<>();
	}

	@Deprecated
	public synchronized BukkitFuture<T> retrieveAsync() {
		return callAsync();
	}

	/**
	 * Runs callable asynchronously, then runs onComplete and onException handlers
	 */
	public synchronized BukkitFuture<T> callAsync() {
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

		if (this.retrieving) {
			throw new IllegalStateException("Already retrieving");
		}

		final Semaphore semaphore = new Semaphore(1);
		this.onComplete(a -> semaphore.release());
		semaphore.acquireUninterruptibly();
		return this;
	}

	@Deprecated
	public synchronized T get() throws Exception {
		return callBlocking();
	}

	/**
	 * Call synchronously
	 * @return
	 * @throws Exception
	 */
	public synchronized T callBlocking() throws Exception {
		if (this.done) {
			throw new IllegalStateException("Already retrieved");
		}

		this.retrieving = true;
		final T t = this.action.call();
		this.done = true;
		return t;
	}

	public BukkitFuture<T> onComplete(final Consumer<T> callback) {
		if (this.done) {
			throw new IllegalStateException("Already retrieved");
		}

		synchronized(this) {
			this.onCompleteCallbacks.add(callback);
		}

		return this;
	}

	public BukkitFuture<T> onException(final Consumer<Exception> callback) {
		if (this.done) {
			throw new IllegalStateException("Already retrieved");
		}

		synchronized(this) {
			this.onExceptionCallbacks.add(callback);
		}

		return this;
	}

}
