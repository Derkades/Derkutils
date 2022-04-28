package xyz.derkades.derkutils.bungee;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.io.IOException;
import java.io.OutputStream;

public class CommandSenderOutputStream extends OutputStream {

	private final @NonNull CommandSender sender;
	private final @NonNull StringBuilder buffer;

	public CommandSenderOutputStream(final @NonNull CommandSender sender) {
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
				this.sender.sendMessage(TextComponent.fromLegacyText(this.buffer.toString()));
				this.buffer.setLength(0);
			} else {
				this.buffer.append((char) b);
			}
		}
	}

	@Override
	public void close() throws IOException {
		this.sender.sendMessage(TextComponent.fromLegacyText(this.buffer.toString()));
		super.close();
	}

}
