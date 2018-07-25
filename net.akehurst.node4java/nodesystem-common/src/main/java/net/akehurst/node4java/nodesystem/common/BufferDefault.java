package net.akehurst.node4java.nodesystem.common;

import org.graalvm.polyglot.Value;

import net.akehurst.node4java.nodesystem.api.Buffer;

public class BufferDefault extends ModuleDefault implements Buffer {

	public BufferDefault(final ModuleLoader loader, final Context graal) {
		super(loader, graal);
	}

	public Object Buffer() {
		return null;
	}

	// --- Module ---
	@Override
	public Value exports() {
		return this.graal.asValue(this);
	}
}
