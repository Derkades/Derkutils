package xyz.derkades.derkutils.bukkit;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

import org.bukkit.command.CommandSender;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

public class CommandSenderOutputStream extends OutputStream {

	private final CommandSender sender;
	private ByteArrayDataOutput out;

	public CommandSenderOutputStream(final CommandSender sender) {
		this.sender = Objects.requireNonNull(sender, "Command sender is null");
		this.out = ByteStreams.newDataOutput();
	}

	private void sendMessage() {
		this.sender.sendMessage(new String(this.out.toByteArray(), StandardCharsets.UTF_8));
	}

	@Override
	public void write(final int arg) {
		this.out.writeByte(arg);
	}

	@Override
	public void write(final byte[] bytes) {
		for (final byte b : bytes) {
			if (b == '\n') {
				sendMessage();
				this.out = ByteStreams.newDataOutput();
			} else {
				this.out.writeByte(b);
			}
		}
	}

	@Override
	public void close() throws IOException {
		sendMessage();
		super.close();
	}

}
