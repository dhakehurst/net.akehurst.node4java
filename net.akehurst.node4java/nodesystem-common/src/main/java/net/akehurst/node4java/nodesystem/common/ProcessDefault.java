package net.akehurst.node4java.nodesystem.common;

import java.util.Map;

import org.graalvm.polyglot.Value;
import org.graalvm.polyglot.proxy.ProxyObject;

import net.akehurst.node4java.nodesystem.api.JavascriptEngine;
import net.akehurst.node4java.nodesystem.api.Process;
import net.akehurst.node4java.nodesystem.api.Stream;

public class ProcessDefault implements Process {

	private String scriptName;

	// part of the interface to the script engine
	public final String version;
	public final ProxyObject env;
	public Value argv;
	public final Stream stdout;
	public final Stream stderr;

	public ProcessDefault(final ModuleLoader loader, final JavascriptEngine jse) {
		this.scriptName = "<unknown>";
		this.version = "10.7.0";
		this.env = this.getEnv();
		this.argv = this.getArgv();
		this.stdout = new TTYWriteStreamDefault(System.out);
		this.stderr = new TTYWriteStreamDefault(System.err);
	}

	@Override
	public String getVersion() {
		return this.version;
	}

	@Override
	public void nextTick(final Runnable callback) {
		this.LOGGER.warn(String.format("nextTick(...)"));

	}

	@Override
	public String platform() {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public ProxyObject getEnv() {
		final ProxyObject env = ProxyObject.fromMap((Map<String, Object>) (Object) System.getenv());
		return env;
	}

	@Override
	public Value getArgv() {
		final Value argv = this.graal.eval("js", "[]");
		argv.setArrayElement(0, "<from jvm>"); // the absolute pathname of the executable that started the Node.js process
		argv.setArrayElement(1, "<from jvm>"); //  the path to the JavaScript file being executed
		argv.setArrayElement(2, this.scriptName); //  argument
		return argv;
	}

	@Override
	public Stream getStdout() {
		return this.stdout;
	}

	@Override
	public String cwd() {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public void exit(final int code) {
		this.graal.getEngine().close(true);
		System.err.println("exit " + code);
	}

	@Override
	public void setScriptName(final String name) {
		this.scriptName = name;
		this.argv = this.getArgv();
	}

	// --- Module ---
	@Override
	public Value exports() {
		return this.graal.asValue(this);
	}
}
