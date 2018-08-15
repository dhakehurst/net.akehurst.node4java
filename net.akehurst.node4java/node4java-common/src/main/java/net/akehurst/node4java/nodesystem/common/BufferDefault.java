package net.akehurst.node4java.nodesystem.common;

import net.akehurst.node4java.api.Buffer;
import net.akehurst.node4java.api.JavascriptEngine;

public class BufferDefault extends ModuleDefault implements Buffer {

    public BufferDefault(final ModuleLoader loader, final JavascriptEngine jse) {
        super(loader, jse);
    }

    public Object Buffer() {
        return null;
    }

}
