package xyz.derkades.derkutils.bungee;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;

public class CommandSenderOutputStream extends OutputStream {

	private final CommandSender sender;
	private final StringBuilder buffer;

	public CommandSenderOutputStream(final CommandSender sender) {
		this.sender = sender;
		this.buffer = new StringBuilder();
	}

	@Override
	public void write(final int arg) throws IOException {
		write(new byte[] { (byte) arg });
	}

	@Override
	public void write(final byte[] bytes) throws IOException {
		final String string = new String(bytes, Charset.forName("UTF-8"));
		for (final char c : string.toCharArray()) {
			if (c == '\n') {
				this.sender.sendMessage(TextComponent.fromLegacyText(this.buffer.toString()));
				this.buffer.setLength(0);
			} else {
				this.buffer.append(c);
			}
		}
	}

	@Override
	public void close() throws IOException {
		this.sender.sendMessage(TextComponent.fromLegacyText(this.buffer.toString()));
		super.close();
	}

}
