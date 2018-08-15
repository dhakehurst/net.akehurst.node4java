package net.akehurst.node4java.nodesystem.common;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.akehurst.node4java.api.JSObject;
import net.akehurst.node4java.api.JavascriptEngine;
import net.akehurst.node4java.api.Process;
import net.akehurst.node4java.api.Stream;

public class ProcessDefault extends ModuleDefault implements Process {

    protected final Logger LOGGER;
    private String scriptName;

    // part of the interface to the script engine
    public final String version;
    public final Stream stdout;
    public final Stream stderr;
    private final JSObject exports;

    public ProcessDefault(final ModuleLoader loader, final JavascriptEngine jse) {
        super(loader, jse);
        this.LOGGER = LoggerFactory.getLogger(this.getClass());
        this.scriptName = "<unknown>";
        this.version = "10.7.0";
        this.stdout = new TTYWriteStreamDefault(System.out);
        this.stderr = new TTYWriteStreamDefault(System.err);
        this.exports = jse.newObject();
    }

    @Override
    public String getVersion() {
        return this.version;
    }

    @Override
    public void nextTick(final Runnable callback) {
        this.LOGGER.warn(String.format("nextTick(...)"));

    }

    @Override
    public String platform() {
        // TODO Auto-generated method stub
        return "";
    }

    @Override
    public Map<String, Object> env() {
        final Map<String, Object> env = (Map<String, Object>) (Object) System.getenv();
        return env;
    }

    @Override
    public String[] argv() {
        final String[] argv = new String[3];
        argv[0] = "<from jvm>"; // the absolute pathname of the executable that started the Node.js process
        argv[1] = "<from jvm>"; // the path to the JavaScript file being executed
        argv[2] = this.scriptName; // argument
        return argv;
    }

    @Override
    public Stream stdout() {
        return this.stdout;
    }

    @Override
    public String cwd() {
        // TODO Auto-generated method stub
        return "";
    }

    @Override
    public void exit(final int code) {
        this.jse.close(true);
        System.err.println("exit " + code);
    }

    @Override
    public void setScriptName(final String name) {
        this.scriptName = name;
    }

    // --- Module ---
    @Override
    public JSObject resolve(final String scriptPath) {
        throw new UnsupportedOperationException();
    }

    @Override
    public JSObject exports() {
        return this.exports;
    }
}
