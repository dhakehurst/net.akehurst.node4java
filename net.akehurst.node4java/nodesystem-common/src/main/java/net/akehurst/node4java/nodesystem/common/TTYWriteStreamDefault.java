package net.akehurst.node4java.nodesystem.common;

import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

import net.akehurst.node4java.nodesystem.api.TTYWriteStream;

public class TTYWriteStreamDefault implements TTYWriteStream {

	public boolean isTTY;
	OutputStream out;

	public TTYWriteStreamDefault(final OutputStream out) {
		this.out = out;
		this.isTTY = true;
	}

	@Override
	public boolean getIsTTY() {
		return this.isTTY;
	}

	@Override
	public boolean write(final Object chunk, final String encoding, final Runnable callback) {
		try {
			if (null == encoding) {
				this.out.write(chunk.toString().getBytes(StandardCharsets.UTF_8));
			} else {
				this.out.write(chunk.toString().getBytes(encoding));
			}

			if (null != callback) {
				callback.run();
			}
		} catch (final Exception e) {
			//TODO: emit error event
			System.err.println(e.getMessage());
		}
		return false;
	}

	@Override
	public boolean write(final Object chunk) {
		return this.write(chunk, null, null);
	}

	@Override
	public boolean write(final Object chunk, final String encoding) {
		return this.write(chunk, encoding, null);
	}

	@Override
	public boolean write(final Object chunk, final Runnable callback) {
		return this.write(chunk, null, callback);

	}
}
