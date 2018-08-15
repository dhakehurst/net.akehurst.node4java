package net.akehurst.node4java.api;

import java.util.Set;

public interface Module {

    Set<String> getExportedNames();

    JSObject exports();

    /**
     *
     * @param scriptPath
     * @return exports of the module
     */
    JSObject resolve(final String scriptPath);

}
