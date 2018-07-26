package net.akehurst.node4java.nodesystem.graal;

import org.graalvm.polyglot.Context;

import net.akehurst.node4java.api.JavascriptEngine;

public class JavascriptEngineGraalVM implements JavascriptEngine {

	private final Context graal;

	public JavascriptEngineGraalVM() {
		this.graal = Context.create("js");
	}

}
