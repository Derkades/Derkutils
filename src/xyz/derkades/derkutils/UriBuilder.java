package xyz.derkades.derkutils;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Objects;

public class UriBuilder {

	private final StringBuilder builder;
	private boolean noParams = true;

	public UriBuilder(final String base) {
		this.builder = new StringBuilder(base);
	}

	public UriBuilder(final String base, final boolean hasParameters) {
		this.builder = new StringBuilder(base);
		this.noParams = !hasParameters;
	}

	public UriBuilder(final URI base) {
		this(base.toString());
	}

	public UriBuilder(final URL base) {
		this(base.toString());
	}

	public UriBuilder appendEncoded(final String string) {
		Objects.requireNonNull(string, "The provided string is null");
		try {
			this.builder.append(URLEncoder.encode(string, "UTF-8"));
		} catch (final UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
		return this;
	}

	public UriBuilder appendRaw(final String string) {
		Objects.requireNonNull(string, "The provided string is null");
		this.builder.append(string);
		return this;
	}

	public UriBuilder appendRaw(final char c) {
		this.builder.append(c);
		return this;
	}

	public UriBuilder slash(final String part) {
		Objects.requireNonNull(part, "Part is null");
		if (!this.noParams) {
			throw new IllegalArgumentException("Cannot append after starting parameters");
		}
		appendRaw('/');
		appendEncoded(part);
		return this;
	}

	public UriBuilder param(final String key, final String value) {
		Objects.requireNonNull(key, "Key is null");
		Objects.requireNonNull(value, "Value is null");
		appendRaw(this.noParams ? '?' : '&');
		appendEncoded(key);
		appendRaw('=');
		appendEncoded(value.toString());
		this.noParams = false;
		return this;
	}

	public URI build() {
		return URI.create(this.builder.toString());
	}

	@Override
	public String toString() {
		return this.builder.toString();
	}

	@Override
	public boolean equals(final Object other) {
		return other != null &&
				other instanceof UriBuilder &&
				Objects.equals(((UriBuilder) other).toString(), this.toString());
	}

}
