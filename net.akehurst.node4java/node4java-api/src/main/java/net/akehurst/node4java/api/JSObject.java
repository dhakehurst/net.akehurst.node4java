package net.akehurst.node4java.api;

public interface JSObject {

	Object getMember(String name);

	void setMember(String name, Object value);

}
