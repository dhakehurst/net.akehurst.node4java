package net.akehurst.node4java.nodesystem.graal;

import java.io.File;

import org.graalvm.polyglot.Value;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import net.akehurst.filesystem.api.readonly.FileReadOnly;
import net.akehurst.filesystem.api.readwrite.FilesystemReadWrite;
import net.akehurst.filesystem.api.virtual.FilesystemVirtual;
import net.akehurst.filesystem.vfs.local.FilesystemLocal;
import net.akehurst.filesystem.vfs.virtual.FilesystemVfsVirtual;
import net.akehurst.node4java.api.NodeSystem;

public class test_NodeSystemOnGraalVM {

	private NodeSystem sut;
	private FilesystemVirtual fs;

	@Before
	public void setup() {
		this.fs = FilesystemVfsVirtual.create();
		this.sut = NodeSystemOnGraalVM.create(this.fs);
	}

	@Test
	public void initialise() {
		this.sut.initialise();
	}

	@Test
	public void attachWebJar() {
		this.sut.initialise();

		final FilesystemReadWrite localFs = FilesystemLocal.create("src/main/resources");
		final FileReadOnly jarFile = localFs.resolveEntry("typescript-2.9.2.jar").asFile();

		this.sut.addWebjarModule("typescript", "2.9.2", jarFile);

	}

	@Test
	public void process() {
		this.sut.initialise();
		final Value v = this.sut.eval("process");
		Assert.assertNotNull(v);
	}

	@Test
	public void process_version() {
		this.sut.initialise();
		final Value v = this.sut.eval("process.version");
		Assert.assertEquals("10.7.0", v.asString());
	}

	@Test
	public void process_argv() {
		this.sut.initialise();
		final Value v = this.sut.eval("process.argv[0]");
		Assert.assertEquals("<from jvm>", v.asString());
	}

	@Test
	public void process_argv_slice() {
		this.sut.initialise();
		final Value v = this.sut.eval("process.argv.slice(2)[0]");
		Assert.assertEquals("<unknown>", v.asString());
	}

	@Test
	public void eval1() {
		this.sut.initialise();
		this.sut.eval("console.log('Hello World!')");
	}

	@Test
	public void typescript() {
		this.sut.initialise();
		final File webjarFile = new File("webjars/typescript-2.9.2.jar");
		this.sut.attachWebJar(webjarFile, "typescript", "2.9.2");

		this.sut.eval("tsc", "const tsc = require('typescript/lib/tsc.js');");
	}

	@Test
	public void rollup() {
		this.sut.initialise();
		final File webjarFile = new File("webjars/rollup-0.62.0.jar");
		this.sut.attachWebJar(webjarFile, "rollup", "0.62.0");

		this.sut.eval("const rollup = require('rollup/lib/rollup.js');");
	}
}
