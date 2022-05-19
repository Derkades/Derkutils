package xyz.derkades.derkutils;

import org.checkerframework.checker.nullness.qual.Nullable;

public class Tristate<T> {

	private static final Tristate<Object> UNKNOWN = new Tristate<>(false, null);
	private static final Tristate<Object> KNOWN_EMPTY = new Tristate<>(true, null);

	private final boolean known;
	private final @Nullable T value;

	private Tristate(final boolean known, final @Nullable T value) {
		this.known = known;
		this.value = value;
	}

	public boolean known() {
		return this.known;
	}

	public boolean present() {
		return this.known && this.value != null;
	}

	public @Nullable T value() {
		return this.value;
	}

	public static <T> Tristate<T> known(final @Nullable T value) {
		return new Tristate<>(true, value);
	}

	public static <T> Tristate<T> unknown() {
		return (Tristate<T>) UNKNOWN;
	}

	public static <T> Tristate<T> knownEmpty() {
		return (Tristate<T>) KNOWN_EMPTY;
	}

}
