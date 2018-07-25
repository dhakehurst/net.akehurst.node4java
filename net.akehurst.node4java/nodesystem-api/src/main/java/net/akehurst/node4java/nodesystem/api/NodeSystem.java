package net.akehurst.node4java.nodesystem.api;

import net.akehurst.filesystem.api.Filesystem;

public interface NodeSystem {

	JavascriptEngine getJavascriptSystem();

	Filesystem getFileSystem();
}
