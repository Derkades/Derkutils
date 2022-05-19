package xyz.derkades.derkutils;

import org.checkerframework.checker.nullness.qual.Nullable;

public class Tristate<T> {

	private final boolean known;
	private final @Nullable T value;

	private Tristate(final boolean known, final @Nullable T value) {
		this.known = known;
		this.value = value;
	}

	public boolean known() {
		return this.known;
	}

	public @Nullable T value() {
		return this.value;
	}

	public static <T> Tristate<T> known(final @Nullable T value) {
		return new Tristate<>(true, value);
	}

	public static <T> Tristate<T> unknown() {
		return new Tristate<>(false, null);
	}

}
