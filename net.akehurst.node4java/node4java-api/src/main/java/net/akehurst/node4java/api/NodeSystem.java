package net.akehurst.node4java.api;

import java.io.Reader;

import net.akehurst.filesystem.api.readonly.DirectoryReadOnly;
import net.akehurst.filesystem.api.readonly.FileReadOnly;
import net.akehurst.filesystem.api.virtual.FilesystemVirtual;

public interface NodeSystem {

    /**
     * initialise the system with the given filesystem and javascript engine.
     *
     * @param jse
     *            the javascript engine to use
     * @param filesystem
     *            the filesystem for the 'node' system to work on
     */

    void initialise(final JavascriptEngine jse, final FilesystemVirtual filesystem);

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

    /**
     * evaluate the script,
     * <p> the name of the script will be '&lt;unknown>',
     * <p> the working directory will be '/src' in the provided filesystem
     *
     * @param script content
     * @return result of evaluating the script
     */
    Object eval(String script);

    /**
     * evaluate the script, the name of the script will be as given
     * <p> the working directory will be '/src' in the provided filesystem
     *
     * @param name name of the script
     * @param script content
     * @return result of evaluating the script
     */
    Object eval(String name, String script);

    /**
     * evaluate the script, the name of the script will be as given
     *
     * @param name name of the script
     * @param script content
     * @param workingDirectory
     * @return result of evaluating the script
     */
    Object eval(String name, String script, DirectoryReadOnly workingDirectory);

    /**
     * evaluate the script, the name of the script will be as given
     *
     * @param name name of the script
     * @param script content
     * @param workingDirectory
     * @return result of evaluating the script
     */
    Object eval(String name, Reader script, DirectoryReadOnly workingDirectory);
}
