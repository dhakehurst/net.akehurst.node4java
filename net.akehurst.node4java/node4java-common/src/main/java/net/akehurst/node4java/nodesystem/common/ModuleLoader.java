package net.akehurst.node4java.nodesystem.common;

import javax.tools.FileObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.akehurst.filesystem.api.Filesystem;
import net.akehurst.node4java.api.Buffer;
import net.akehurst.node4java.api.JavascriptEngine;
import net.akehurst.node4java.api.Module;
import net.akehurst.node4java.api.OS;

public class ModuleLoader {

	private static final Logger LOGGER = LoggerFactory.getLogger(ModuleLoader.class);

	private final JavascriptEngine jse;
	private final Filesystem fileSystem;
	private final ModuleCache cache;

	// -- default modules
	private final OS os;
	private final Buffer buffer;

	public ModuleLoader(final JavascriptEngine jse, final Filesystem fileSystem) {
		this.jse = jse;
		this.fileSystem = fileSystem;
		this.os = new OSDefault(this, jse);
		this.buffer = new BufferDefault(this, jse);

		this.cache = new ModuleCache();
		this.cache.add("os", this.os);
		this.cache.add("buffer", this.buffer);
	}

	public Object require(final String name) {
		ModuleLoader.LOGGER.warn(String.format("require('%s')", name));
		Module module = this.cache.find(name);
		if (null != module) {
			return module.exports();
		} else {
			module = new ModuleDefault(this, this.graal);
			this.cache.add(name, module);

			final String scriptPath = "/modules/" + name;
			final Value exports = module.resolve(scriptPath);
			return exports;
		}
	}

	public FileObject resolveFile(final String scriptPath) {
		try {
			return this.fileSystem.getFile(scriptPath);
		} catch (final Exception e) {
			ModuleLoader.LOGGER.error(String.format("Unable to resolve script %s", scriptPath));
			return null;
		}
	}

}
