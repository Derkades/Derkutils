package xyz.derkades.derkutils;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoggerOutputStream extends OutputStream {

	private final @NonNull Logger logger;
	private final @NonNull Level level;
	private @NonNull StringBuilder buf = new StringBuilder();

	public LoggerOutputStream(final @NonNull Logger logger,
							  final @NonNull Level level) {
		this.logger = logger;
		this.level = level;
	}

	@Override
	public void write(int i) throws IOException {
		char c = (char) i;
		if (c == '\n') {
			this.logger.log(this.level, buf.toString());
			buf = new StringBuilder();
		} else {
			buf.append(c);
		}
	}

}
