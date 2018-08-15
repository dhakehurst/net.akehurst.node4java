package net.akehurst.node4java.nodesystem.graal;

import java.util.Set;

import org.graalvm.polyglot.Value;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.akehurst.node4java.api.JSObject;

public class JSObjectGraalVM implements JSObject {

    protected final Logger LOGGER;
    private final Value graal;

    public JSObjectGraalVM(final Value graal) {
        this.LOGGER = LoggerFactory.getLogger(this.getClass());
        this.graal = graal;
    }

    @Override
    public Object asJava() {
        if (this.graal.isHostObject()) {
            return this.graal.asHostObject();
        } else {
            if (this.graal.isNull()) {
                return null;
            }
        }
        return null;
    }

    @Override
    public Set<String> getMemberNames() {
        return this.graal.getMemberKeys();
    }

    @Override
    public Object getMember(final String name) {
        this.LOGGER.warn(String.format("trying to get, %s", name)); // TODO: make this debug
        final Value v = this.graal.getMember(name);
        return v.asHostObject();
    }

    @Override
    public JSObject getMemberAsJs(final String name) {
        this.LOGGER.warn(String.format("trying to get, %s", name)); // TODO: make this debug
        final Value v = this.graal.getMember(name);
        return new JSObjectGraalVM(v);
    }

    @Override
    public void setMember(final String name, final Object value) {
        this.graal.putMember(name, value);
    }

    @Override
    public JSObject execute(final Object... args) {
        final Value v = this.graal.execute(args);
        return new JSObjectGraalVM(v);
    }

}
