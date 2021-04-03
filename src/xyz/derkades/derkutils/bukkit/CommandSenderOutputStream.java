package xyz.derkades.derkutils.bukkit;

import java.io.IOException;
import java.io.OutputStream;

import org.bukkit.command.CommandSender;

public class CommandSenderOutputStream extends OutputStream {

	private final CommandSender sender;
	private final StringBuilder buffer;

	public CommandSenderOutputStream(final CommandSender sender) {
		this.sender = sender;
		this.buffer = new StringBuilder();
	}

	@Override
	public void write(final int arg) throws IOException {
		this.buffer.append((char) ((byte) arg));
	}

	@Override
	public void write(final byte[] bytes) throws IOException {
		for (final byte b : bytes) {
			if (b == '\n') {
				this.sender.sendMessage(this.buffer.toString());
				this.buffer.setLength(0);
			} else {
				this.buffer.append((char) b);
			}
		}
	}

	@Override
	public void close() throws IOException {
		this.sender.sendMessage(this.buffer.toString());
		super.close();
	}

}
