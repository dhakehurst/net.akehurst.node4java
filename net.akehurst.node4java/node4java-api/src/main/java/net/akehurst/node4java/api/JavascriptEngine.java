package net.akehurst.node4java.api;

import java.io.Reader;
import java.util.Map;

import net.akehurst.filesystem.api.readonly.DirectoryReadOnly;

public interface JavascriptEngine {

    JSObject newObject();

    void setGlobalBindings(Map<String, Object> bindings);

    void setGlobalBinding(String name, Object value);

    void close(boolean cancelIfExecuting);

    JSObject eval(String name, Reader script, DirectoryReadOnly workingDirectory);

}
