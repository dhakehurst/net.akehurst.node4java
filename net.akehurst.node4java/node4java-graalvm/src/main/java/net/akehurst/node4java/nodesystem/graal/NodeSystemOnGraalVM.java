package net.akehurst.node4java.nodesystem.graal;

import net.akehurst.filesystem.api.virtual.FilesystemVirtual;
import net.akehurst.node4java.api.JavascriptEngine;
import net.akehurst.node4java.api.NodeSystem;
import net.akehurst.node4java.nodesystem.common.NodeSystemAbstract;

public class NodeSystemOnGraalVM extends NodeSystemAbstract {

	public static NodeSystem create(final FilesystemVirtual fileSystem) {
		final JavascriptEngine js = new JavascriptEngineGraalVM();
		return new NodeSystemOnGraalVM(js, fileSystem);
	}

	protected NodeSystemOnGraalVM(final JavascriptEngine javascriptSystem, final Filesystem fileSystem) {
		super(javascriptSystem, fileSystem);
	}

	public Object eval(final String script) {
		return this.jse.eval(script);
	}

}
