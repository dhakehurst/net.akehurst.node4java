package net.akehurst.node4java.api;

import net.akehurst.filesystem.api.readonly.DirectoryReadOnly;
import net.akehurst.filesystem.api.readonly.FileReadOnly;
import net.akehurst.filesystem.api.virtual.FilesystemVirtual;

public interface NodeSystem {

	JavascriptEngine getJavascriptSystem();

	FilesystemVirtual getFileSystem();

	/**
	 * initialise the system
	 */
	void initialise();

	/**
	 * add a new module to the virtual filesystem in the designated "node_modules" directory
	 *
	 * @param moduleName
	 *            the name of the module
	 * @param location
	 *            the Directory (from another filesystem) that contains the module content
	 */
	void addModuleLocation(String moduleName, DirectoryReadOnly location);

	/**
	 * add a webjar as a module
	 *
	 * @param moduleName
	 *            name of the module
	 * @param moduleVersion
	 *            version of the module
	 * @param jarFile
	 *            the jar file on the local filesystem to add
	 */
	void addWebjarModule(String moduleName, String moduleVersion, FileReadOnly jarFile);
}
