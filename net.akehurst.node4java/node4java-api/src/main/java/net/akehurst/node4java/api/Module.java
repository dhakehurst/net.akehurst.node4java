package net.akehurst.node4java.api;

import java.util.Map;

public interface Module {

	Map<String, Object> exports();

	/**
	 *
	 * @param scriptPath
	 * @return exports of the module
	 */
	Map<String, Object> resolve(final String scriptPath);

}
