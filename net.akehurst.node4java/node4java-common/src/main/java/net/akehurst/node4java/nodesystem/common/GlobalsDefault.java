package net.akehurst.node4java.nodesystem.common;

import org.jooq.lambda.function.Consumer1;
import org.jooq.lambda.function.Function1;
import org.jooq.lambda.function.Function2;

import net.akehurst.filesystem.api.Filesystem;
import net.akehurst.node4java.api.Console;
import net.akehurst.node4java.api.Globals;
import net.akehurst.node4java.api.Immediate;
import net.akehurst.node4java.api.Interval;
import net.akehurst.node4java.api.JSObject;
import net.akehurst.node4java.api.JavascriptEngine;
import net.akehurst.node4java.api.Process;
import net.akehurst.node4java.api.Timeout;

public class GlobalsDefault implements Globals {

	private final JavascriptEngine jse;
	private final JSObject exports;

	private final ModuleLoader loader;
	private final Console console;
	private final Process process;

	public GlobalsDefault(final JavascriptEngine jse, final Filesystem fileSystem) {
		this.jse = jse;
		this.loader = new ModuleLoader(jse, fileSystem);
		this.console = new ConsoleDefault();
		this.process = new ProcessDefault(this.loader, jse);

		this.exports = jse.newObject();
		this.exports.setMember("clearImmediate", (Consumer1<Immediate>) (immediateObject) -> this.clearImmediate(immediateObject));
		this.exports.setMember("clearInterval", (Consumer1<Interval>) (intervalObject) -> this.clearInterval(intervalObject));
		this.exports.setMember("clearTimeout", (Consumer1<Timeout>) (timeoutObject) -> this.clearTimeout(timeoutObject));
		this.exports.setMember("console", this.console());
		this.exports.setMember("global", this.global());
		this.exports.setMember("process", this.process());
		this.exports.setMember("require", (Function1<String, Object>) (path) -> this.require(path));
		this.exports.setMember("setImmediate", (Function1<Runnable, Immediate>) (callback) -> this.setImmediate(callback));
		this.exports.setMember("setInterval", (Function2<Runnable, Double, Interval>) (callback, delay) -> this.setInterval(callback, delay));
		this.exports.setMember("setTimeout", (Function1<Runnable, Timeout>) (callback) -> this.setTimeout(callback));
	}

	@Override
	public Timeout setTimeout(final Runnable callback) {
		return null;
	}

	@Override
	public void clearTimeout(final Timeout timeout) {

	}

	@Override
	public void clearImmediate(final Immediate immediateObject) {
		// TODO Auto-generated method stub

	}

	@Override
	public void clearInterval(final Interval intervalObject) {
		// TODO Auto-generated method stub

	}

	@Override
	public Console console() {
		return this.console;
	}

	@Override
	public Globals global() {
		return this;
	}

	@Override
	public Process process() {
		return this.process;
	}

	@Override
	public Object require(final String path) {
		return this.loader.require(path);
	}

	@Override
	public Immediate setImmediate(final Runnable callback, final Object... args) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Interval setInterval(final Runnable callback, final double delay, final Object... args) {
		// TODO Auto-generated method stub
		return null;
	}
}
