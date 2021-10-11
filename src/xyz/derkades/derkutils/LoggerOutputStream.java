package xyz.derkades.derkutils;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoggerOutputStream extends OutputStream {

	private final @NotNull Logger logger;
	private final @NotNull Level level;
	private @NotNull StringBuilder buf = new StringBuilder();

	public LoggerOutputStream(@NotNull Logger logger, @NotNull Level level) {
		this.logger = logger;
		this.level = level;
	}

	@Override
	public void write(int i) throws IOException {
		char c = (char) i;
		buf.append(c);
		if (c == '\n') {
			this.logger.log(this.level, buf.toString());
			buf = new StringBuilder();
		}
	}

}
