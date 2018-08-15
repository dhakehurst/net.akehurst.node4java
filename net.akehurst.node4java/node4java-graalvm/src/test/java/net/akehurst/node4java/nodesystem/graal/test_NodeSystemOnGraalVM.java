package net.akehurst.node4java.nodesystem.graal;

import java.io.File;
import java.util.Properties;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import net.akehurst.filesystem.api.readonly.FileReadOnly;
import net.akehurst.filesystem.api.readwrite.FilesystemReadWrite;
import net.akehurst.filesystem.api.virtual.FilesystemVirtual;
import net.akehurst.filesystem.vfs.local.FilesystemLocal;
import net.akehurst.filesystem.vfs.virtual.FilesystemVfsVirtual;
import net.akehurst.node4java.api.JavascriptEngine;
import net.akehurst.node4java.api.NodeSystem;

public class test_NodeSystemOnGraalVM {

    private NodeSystem sut;
    private JavascriptEngine jse;
    private FilesystemVirtual fs;

    @Before
    public void setup() {
        this.fs = FilesystemVfsVirtual.create();
        this.jse = new JavascriptEngineGraalVM();
        final Properties config = new Properties();
        this.sut = NodeSystemOnGraalVM.create(config);
    }

    @Test
    public void initialise() {
        this.sut.initialise(this.jse, this.fs);
    }

    @Test
    public void attachWebJar() {
        this.sut.initialise(this.jse, this.fs);

        final String cwd = new File(".").getAbsolutePath();

        final FilesystemReadWrite localFs = FilesystemLocal.create(cwd + "/src/main/resources");
        final FileReadOnly jarFile = localFs.resolveEntry("typescript-2.9.2.jar").asFile();

        this.sut.addWebjarModule("typescript", "2.9.2", jarFile);

    }

    @Test
    public void process() {
        this.sut.initialise(this.jse, this.fs);

        final Object r = this.sut.eval("process");
        Assert.assertNotNull(r);
    }

    @Test
    public void process_version() {
        this.sut.initialise(this.jse, this.fs);

        final Object r = this.sut.eval("process.version");
        Assert.assertEquals("10.7.0", r.toString());
    }

    @Test
    public void process_argv() {
        this.sut.initialise(this.jse, this.fs);

        final Object r = this.sut.eval("process.argv[0]");
        Assert.assertEquals("<from jvm>", r.toString());
    }

    @Test
    public void process_argv_slice() {
        this.sut.initialise(this.jse, this.fs);

        final Object r = this.sut.eval("process.argv.slice(2)[0]");
        Assert.assertEquals("<unknown>", r.toString());
    }

    @Test
    public void console() {
        this.sut.initialise(this.jse, this.fs);

        this.sut.eval("console.log('Hello World!')");
    }

    @Test
    public void typescript() {
        this.sut.initialise(this.jse, this.fs);

        final FilesystemReadWrite localFs = FilesystemLocal.create("src/main/resources");
        final FileReadOnly jarFile = localFs.resolveEntry("typescript-2.9.2.jar").asFile();

        this.sut.addWebjarModule("typescript", "2.9.2", jarFile);

        this.sut.eval("tsc", "process.argv()[4]='-v'; require('typescript/lib/tsc.js');");
        // this.sut.eval("tsc", "tsc('-v')");
    }

    @Test
    public void rollup() {
        this.sut.initialise(this.jse, this.fs);

        final FilesystemReadWrite localFs = FilesystemLocal.create("src/main/resources");
        final FileReadOnly jarFile = localFs.resolveEntry("rollup-0.62.0.jar").asFile();
        this.sut.addWebjarModule("rollup", "0.62.0", jarFile);

        this.sut.eval("const rollup = require('rollup/lib/rollup.js');");
    }
}
