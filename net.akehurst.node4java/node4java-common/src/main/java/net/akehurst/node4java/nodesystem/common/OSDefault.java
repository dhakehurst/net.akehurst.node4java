package net.akehurst.node4java.nodesystem.common;

import org.graalvm.polyglot.Value;

import net.akehurst.node4java.api.OS;

public class OSDefault extends ModuleDefault implements OS {

	private final String platform;
	public final String EOL;

	public OSDefault(final ModuleLoader loader, final Context graal) {
		super(loader, graal);
		this.platform = "darwin";
		this.EOL = this.getEOL();
	}

	@Override
	public String platform() {
		return this.platform;
	}

	@Override
	public String getEOL() {
		return System.lineSeparator();
	}

	// --- Module ---
	@Override
	public Value exports() {
		return this.graal.asValue(this);
	}

}
