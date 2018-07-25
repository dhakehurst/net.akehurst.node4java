package net.akehurst.node4java.nodesystem.api;

public interface Globals {

	void clearImmediate(Immediate immediateObject);

	void clearInterval(Interval intervalObject);

	void clearTimeout(Timeout timeoutObject);

	Console console();

	Globals global();

	Process process();

	Object require(String path);

	Immediate setImmediate(Runnable callback, Object... args);

	Interval setInterval(Runnable callback, double delay, Object... args);

	Timeout setTimeout(Runnable callback);

	//	URL URL();
	//
	//	URLSearchParam URLSearchParam();
}
