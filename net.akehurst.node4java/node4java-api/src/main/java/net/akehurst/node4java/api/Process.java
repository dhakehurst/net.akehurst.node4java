package net.akehurst.node4java.api;

import java.util.Map;

public interface Process extends Module {

	String getVersion();

	void nextTick(Runnable callback);

	String platform();

	Map<String, Object> env();

	String[] argv();

	Stream stdout();

	String cwd();

	void exit(int code);

	void setScriptName(String name);
}
