package net.akehurst.node4java.api;

import java.util.Set;

public interface JSObject {

    Object asJava();

    Object getMember(String name);

    JSObject getMemberAsJs(String name);

    void setMember(String name, Object value);

    JSObject execute(Object... args);

    Set<String> getMemberNames();

}
