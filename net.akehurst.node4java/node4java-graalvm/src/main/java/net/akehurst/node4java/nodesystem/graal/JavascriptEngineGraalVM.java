package net.akehurst.node4java.nodesystem.graal;

import java.io.Reader;
import java.util.Map;

import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Source;
import org.graalvm.polyglot.Value;

import net.akehurst.filesystem.api.readonly.DirectoryReadOnly;
import net.akehurst.node4java.api.JSObject;
import net.akehurst.node4java.api.JavascriptEngine;
import net.akehurst.node4java.api.NodeSystemException;

public class JavascriptEngineGraalVM implements JavascriptEngine {

    private final Context graal;

    public JavascriptEngineGraalVM() {
        this.graal = Context.create("js");
    }

    @Override
    public JSObject newObject() {
        final Value v = this.graal.eval("js", "[]"); // TODO: really want to use '{}' here! but it doesn't work!
        return new JSObjectGraalVM(v);
    }

    @Override
    public void setGlobalBindings(final Map<String, Object> bindings) {
        for (final Map.Entry<String, Object> me : bindings.entrySet()) {
            this.setGlobalBinding(me.getKey(), me.getValue());
        }
    }

    @Override
    public void setGlobalBinding(final String name, final Object value) {
        this.graal.getBindings("js").putMember(name, value);
    }

    @Override
    public void close(final boolean cancelIfExecuting) {
        this.graal.getEngine().close(cancelIfExecuting);
    }

    @Override
    public JSObject eval(final String name, final Reader script, final DirectoryReadOnly workingDirectory) {
        try {
            final Source source = Source.newBuilder("js", script, name).build();
            final Value v = this.graal.eval(source);
            return new JSObjectGraalVM(v);
        } catch (final Exception e) {
            throw new NodeSystemException("Failed eval", e);
        }
    }
}
