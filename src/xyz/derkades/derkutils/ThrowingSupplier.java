package xyz.derkades.derkutils;

/**
 * A supplier that allows throwing exceptions.
 * @param <T>
 */
@FunctionalInterface
public interface ThrowingSupplier<T, E extends Exception> {

	public T get() throws E;

}
