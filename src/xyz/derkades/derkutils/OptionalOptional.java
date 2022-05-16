package xyz.derkades.derkutils;

import com.google.common.base.Preconditions;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;

public class OptionalOptional<T> {

	private static final OptionalOptional<Object> UNKNOWN = new OptionalOptional<>(false, null);
	private static final OptionalOptional<Object> KNOWN_EMPTY = new OptionalOptional<>(true, null);

	private final boolean known;
	private final @Nullable T value;

	public OptionalOptional(final boolean known, final @Nullable T value) {
		this.known = known;
		this.value = value;

		if (!known) {
			Preconditions.checkArgument(value == null, "When known == false, value must be null.");
		}
	}

	public boolean isKnown() {
		return this.known;
	}

	public boolean isPresent() {
		if (!this.known) {
			throw new IllegalStateException("Cannot know if present, value is not known");
		}
		return this.value != null;
	}

	public @NonNull T getOrThrow() {
		final T value = this.getNullable();
		if (value == null) {
			throw new IllegalStateException("Value is known not present");
		}
		return value;
	}

	public @Nullable T getNullable() {
		if (!this.known) {
			throw new IllegalStateException("Value is not known");
		}
		return Objects.requireNonNull(this.value, "Value is not present");
	}

	public Optional<T> getOptional() {
		if (!this.known) {
			throw new IllegalStateException("Value is not known");
		}
		return this.value != null ? Optional.of(this.value) : Optional.empty();
	}

	public void ifKnown(final Consumer<Optional<T>> optionalConsumer) {
		if (this.known) {
			optionalConsumer.accept(this.getOptional());
		}
	}

	public void ifKnownPresent(final Consumer<T> consumer) {
		if (this.known && this.value != null) {
			consumer.accept(this.value);
		}
	}

	public static <T> OptionalOptional<T> unknown() {
		return (OptionalOptional<T>) UNKNOWN;
	}

	public static <T> OptionalOptional<T> knownEmpty() {
		return (OptionalOptional<T>) KNOWN_EMPTY;
	}

	public static <T> OptionalOptional<T> knownPresent(@NonNull T value) {
		return new OptionalOptional<>(true, Objects.requireNonNull(value));
	}

}
