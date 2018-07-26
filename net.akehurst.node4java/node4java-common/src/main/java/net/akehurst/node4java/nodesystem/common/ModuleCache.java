package net.akehurst.node4java.nodesystem.common;

import java.util.HashMap;
import java.util.Map;

import net.akehurst.node4java.api.Module;

public class ModuleCache {

	private final Map<String, Module> cache;

	public ModuleCache() {
		this.cache = new HashMap<>();
	}

	public void add(final String name, final Module module) {
		this.cache.put(name, module);
	}

	public Module find(final String name) {
		return this.cache.get(name);
	}
}
