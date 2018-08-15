package net.akehurst.node4java.nodesystem.common;

import java.io.Reader;
import java.io.StringReader;
import java.util.Properties;

import net.akehurst.filesystem.api.DirectoryEntry;
import net.akehurst.filesystem.api.readonly.DirectoryReadOnly;
import net.akehurst.filesystem.api.readonly.FileReadOnly;
import net.akehurst.filesystem.api.readonly.FilesystemReadOnly;
import net.akehurst.filesystem.api.virtual.FilesystemVirtual;
import net.akehurst.filesystem.vfs.jar.FilesystemJar;
import net.akehurst.node4java.api.Globals;
import net.akehurst.node4java.api.JavascriptEngine;
import net.akehurst.node4java.api.NodeSystem;
import net.akehurst.node4java.api.NodeSystemException;

abstract public class NodeSystemAbstract implements NodeSystem {

    private final String modulesPath;

    private JavascriptEngine jse;
    private FilesystemVirtual filesystem;
    private Globals globals;

    public NodeSystemAbstract(final Properties configuration) {
        this.modulesPath = configuration.getProperty("modulesPath", "/modules");
    }

    public JavascriptEngine getJavascriptSystem() {
        return this.jse;
    }

    public FilesystemVirtual getFileSystem() {
        return this.filesystem;
    }

    @Override
    public void initialise(final JavascriptEngine javascriptSystem, final FilesystemVirtual filesystem) {

        try {
            this.jse = javascriptSystem;
            this.filesystem = filesystem;
            this.globals = new GlobalsDefault(this.jse, this.filesystem);

            this.jse.setGlobalBinding("globals", this.globals.exports());
            for (final String n : this.globals.getExportedNames()) {
                final Object o = this.globals.exports().getMember(n);
                this.jse.setGlobalBinding(n, o);
            }
            // this.graal.getBindings("js").putMember("require", (RequireFunction) this.loader::require);
            // this.graal.getBindings("js").putMember("require", (Function<String, Object>) (n) -> this.loader.require(n));
            // this.graal.getBindings("js").putMember("process", this.process.exports());
            // this.graal.getBindings("js").putMember("setTimeout", (Function<Runnable, Timeout>) (callback) -> this.globalFunctions.setTimeout(callback));
            // this.graal.getBindings("js").putMember("clearTimeout", (Consumer<Timeout>) (timeout) -> this.globalFunctions.clearTimeout(timeout));

        } catch (final Exception e) {
            throw new NodeSystemException("Unable to initialise Node System", e);
        }
    }

    @Override
    public void addModuleLocation(final String moduleName, final DirectoryReadOnly location) {
        final String pathInVfs = this.modulesPath + "/" + moduleName;
        this.filesystem.addJunction(pathInVfs, location);
    }

    @Override
    public void addWebjarModule(final String moduleName, final String moduleVersion, final FileReadOnly jarFile) {
        final FilesystemReadOnly jarFs = FilesystemJar.create(jarFile);
        final String pathInJar = String.format("/META-INF/resources/webjars/%s/%s/", moduleName, moduleVersion);
        final DirectoryEntry entryInJar = jarFs.resolveEntry(pathInJar);
        final DirectoryReadOnly location = entryInJar.asDirectory();
        this.addModuleLocation(moduleName, location);
    }

    @Override
    public Object eval(final String script) {
        final Reader reader = new StringReader(script);
        final DirectoryReadOnly wd = this.filesystem.resolveEntry("/src").asDirectory();
        return this.jse.eval("<unknown>", reader, wd).asJava();
    }

    @Override
    public Object eval(final String name, final String script) {
        final Reader reader = new StringReader(script);
        final DirectoryReadOnly wd = this.filesystem.resolveEntry("/src").asDirectory();
        return this.jse.eval(name, reader, wd).asJava();
    }

    @Override
    public Object eval(final String name, final String script, final DirectoryReadOnly workingDirectory) {
        final Reader reader = new StringReader(script);
        return this.jse.eval(name, reader, workingDirectory).asJava();
    }

    @Override
    public Object eval(final String name, final Reader script, final DirectoryReadOnly workingDirectory) {
        return this.jse.eval(name, script, workingDirectory).asJava();
    }
}
