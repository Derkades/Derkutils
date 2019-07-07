package xyz.derkades.derkutils;

/**
 * A supplier that allows throwing exceptions.
 * @param <T>
 */
@FunctionalInterface
public interface ThrowingSupplier<T> {

	public T get() throws Exception;

}
