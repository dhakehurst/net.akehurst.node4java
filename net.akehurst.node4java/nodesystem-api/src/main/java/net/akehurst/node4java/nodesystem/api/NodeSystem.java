package net.akehurst.node4java.nodesystem.api;

import net.akehurst.filesystem.api.FileSystem;

public interface NodeSystem {

	JavascriptEngine getJavascriptSystem();

	FileSystem getFileSystem();
}
