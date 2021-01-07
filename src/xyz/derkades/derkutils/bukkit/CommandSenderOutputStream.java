package xyz.derkades.derkutils.bukkit;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;

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
		write(new byte[] {(byte) arg});
	}
	
	@Override
	public void write(final byte[] bytes) throws IOException {
		final String string = new String(bytes, Charset.forName("UTF-8"));
		for (final char c : string.toCharArray()) {
			if (c == '\n') {
				this.sender.sendMessage(this.buffer.toString());
				this.buffer.setLength(0);
			} else {
				this.buffer.append(c);
			}
		}
	}

}
