package xyz.derkades.derkutils;

/**
 * A supplier that allows throwing exceptions.
 * @param <T>
 * @deprecated use {@link java.util.concurrent.Callable}
 */
@Deprecated
@FunctionalInterface
public interface ThrowingSupplier<T, E extends Exception> {

	public T get() throws E;

}