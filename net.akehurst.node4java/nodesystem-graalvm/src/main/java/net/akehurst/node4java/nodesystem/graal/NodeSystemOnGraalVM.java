package net.akehurst.node4java.nodesystem.graal;

import net.akehurst.filesystem.api.FileSystem;
import net.akehurst.node4java.nodesystem.api.JavascriptEngine;
import net.akehurst.node4java.nodesystem.api.NodeSystem;
import net.akehurst.node4java.nodesystem.common.NodeSystemAbstract;

public class NodeSystemOnGraalVM extends NodeSystemAbstract {

	public static NodeSystem create() {
		final JavascriptEngine js = new JavascriptEngineGraalVM();
		return new NodeSystemOnGraalVM(js);
	}

	public NodeSystemOnGraalVM(final JavascriptEngine javascriptSystem, final FileSystem fileSystem) {
		super(javascriptSystem, fileSystem);
	}

}
