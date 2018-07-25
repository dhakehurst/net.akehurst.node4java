package net.akehurst.node4java.nodesystem.common;

import java.util.function.Consumer;
import java.util.function.Function;

import net.akehurst.filesystem.api.FileSystem;
import net.akehurst.node4java.nodesystem.api.Globals;
import net.akehurst.node4java.nodesystem.api.JavascriptEngine;
import net.akehurst.node4java.nodesystem.api.NodeSystem;
import net.akehurst.node4java.nodesystem.api.Timeout;
import net.akehurst.node4java.nodesystem.graal.NodeOnGraalException;

abstract public class NodeSystemAbstract implements NodeSystem {

	private final JavascriptEngine jse;
	private final FileSystem fileSystem;
	private final Globals globals;

	public NodeSystemAbstract(final JavascriptEngine javascriptSystem, final FileSystem fileSystem) {
		this.jse = javascriptSystem;
		this.fileSystem = fileSystem;
		this.globals = new GlobalsDefault(this.jse, fileSystem);
	}

	@Override
	public JavascriptEngine getJavascriptSystem() {
		return this.javascriptSystem;
	}

	@Override
	public FileSystem getFileSystem() {
		// TODO Auto-generated method stub
		return this.fileSystem;
	}

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

}
