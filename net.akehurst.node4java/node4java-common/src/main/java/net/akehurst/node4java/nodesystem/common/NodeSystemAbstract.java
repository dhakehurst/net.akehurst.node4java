package net.akehurst.node4java.nodesystem.common;

import java.util.function.Consumer;
import java.util.function.Function;

import net.akehurst.filesystem.api.DirectoryEntry;
import net.akehurst.filesystem.api.readonly.DirectoryReadOnly;
import net.akehurst.filesystem.api.readonly.FileReadOnly;
import net.akehurst.filesystem.api.readonly.FilesystemReadOnly;
import net.akehurst.filesystem.api.virtual.FilesystemVirtual;
import net.akehurst.filesystem.vfs.jar.FilesystemJar;
import net.akehurst.node4java.api.Globals;
import net.akehurst.node4java.api.JavascriptEngine;
import net.akehurst.node4java.api.NodeSystem;
import net.akehurst.node4java.api.Timeout;
import net.akehurst.node4java.nodesystem.graal.NodeOnGraalException;

abstract public class NodeSystemAbstract implements NodeSystem {

	private final String modulesPath = "/modules"; //TODO: configurable

	private final JavascriptEngine jse;
	private final FilesystemVirtual filesystem;
	private final Globals globals;

	public NodeSystemAbstract(final JavascriptEngine javascriptSystem, final FilesystemVirtual filesystem) {
		this.jse = javascriptSystem;
		this.filesystem = filesystem;
		this.globals = new GlobalsDefault(this.jse, filesystem);
	}

	@Override
	public JavascriptEngine getJavascriptSystem() {
		return this.javascriptSystem;
	}

	@Override
	public FilesystemVirtual getFileSystem() {
		return this.filesystem;
	}

	@Override
	public void initialise() {

		try {
			this.loader = new ModuleLoader(this.javascriptSystem, this.fileSystem);
			this.globals = new GlobalsDefault(this.graal);
			//this.graal.getBindings("js").putMember("require", (RequireFunction) this.loader::require);
			this.graal.getBindings("js").putMember("require", (Function<String, Object>) (n) -> this.loader.require(n));
			this.graal.getBindings("js").putMember("process", this.process.exports());
			this.graal.getBindings("js").putMember("setTimeout", (Function<Runnable, Timeout>) (callback) -> this.globalFunctions.setTimeout(callback));
			this.graal.getBindings("js").putMember("clearTimeout", (Consumer<Timeout>) (timeout) -> this.globalFunctions.clearTimeout(timeout));

		} catch (final Exception e) {
			throw new NodeOnGraalException("Unable to initialise NodeOnGraal", e);
		}
	}

	@Override
	public void addModuleLocation(final String moduleName, final DirectoryReadOnly location) {
		final String pathInVfs = this.modulesPath + "/" + moduleName;
		this.filesystem.addJunction(this.modulesPath, location);
	}

	@Override
	public void addWebjarModule(final String moduleName, final String moduleVersion, final FileReadOnly jarFile) {
		final FilesystemReadOnly jarFs = FilesystemJar.create(jarFile);
		final String pathInJar = String.format("/META-INF/resources/webjars/%s/%s/", moduleName, moduleVersion);
		final DirectoryEntry entryInJar = jarFs.resolveEntry(pathInJar);
		final DirectoryReadOnly location = entryInJar.asDirectory();
		this.addModuleLocation(moduleName, location);
	}

}
