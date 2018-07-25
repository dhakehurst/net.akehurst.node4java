package net.akehurst.node4java.nodesystem.api;

public interface JSObject {

	Object getMember(String name);

	void setMember(String name, Object value);

}
