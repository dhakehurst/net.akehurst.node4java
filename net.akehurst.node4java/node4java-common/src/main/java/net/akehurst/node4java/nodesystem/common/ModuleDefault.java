package net.akehurst.node4java.nodesystem.common;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.SequenceInputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.akehurst.filesystem.api.readonly.FileReadOnly;
import net.akehurst.node4java.api.JSObject;
import net.akehurst.node4java.api.JavascriptEngine;
import net.akehurst.node4java.api.Module;

public class ModuleDefault implements Module {

    protected final Logger LOGGER;

    private final ModuleLoader loader;
    protected final JavascriptEngine jse;
    protected JSObject exports;
    private final Set<String> exportedNames;

    public ModuleDefault(final ModuleLoader loader, final JavascriptEngine jse) {
        this.LOGGER = LoggerFactory.getLogger(this.getClass());
        this.loader = loader;
        this.jse = jse;
        this.exports = jse.newObject();
        this.exportedNames = new HashSet<>();
    }

    void export(final String name, final Object value) {
        this.exports.setMember(name, value);
        this.exportedNames.add(name);
    }

    @Override
    public JSObject exports() {
        return this.exports;
    }

    @Override
    public Set<String> getExportedNames() {
        // TODO Auto-generated method stub
        return null;
    }

    public Object require(final String name) {
        return this.loader.require(name);
    }

    @Override
    public JSObject resolve(final String scriptPath) {
        try {
            // need to wrap loaded file as follows
            // (function(exports, require, module, __filename, __dirname) {
            // // Module code actually lives in here
            // });

            final ByteArrayInputStream start = new ByteArrayInputStream(
                    "(function(exports, require, module, __filename, __dirname) {".getBytes(StandardCharsets.UTF_8));

            final FileReadOnly fo = this.loader.resolveFile(scriptPath);
            final InputStream script = fo.inputStream();

            final ByteArrayInputStream end = new ByteArrayInputStream((System.lineSeparator() + "});").getBytes(StandardCharsets.UTF_8));

            final Enumeration<? extends InputStream> streams = Collections.enumeration(Arrays.asList(start, script, end));
            final SequenceInputStream completeStream = new SequenceInputStream(streams);
            final Reader reader = new InputStreamReader(completeStream);
            // final Source src = Source.newBuilder("js", reader, scriptPath).build();
            // final Value funVal = this.graal.eval(src);

            final Function<String, Object> require = (Function<String, Object>) (n) -> this.require(n);
            final String __filename = scriptPath;
            final String __dirname = scriptPath.substring(0, scriptPath.lastIndexOf('/'));

            final JSObject funVal = this.jse.eval(scriptPath, reader, this.loader.resolveDirectory(__dirname));
            final JSObject val = funVal.execute(this.exports, require, this, __filename, __dirname);
            this.exports = val.getMemberAsJs("exports");
            return this.exports();
        } catch (final Exception e) {
            this.LOGGER.error(String.format("in requireScript, %s", e.getMessage() + e.getStackTrace()[0]));
            this.LOGGER.error(String.format("%s", e.getStackTrace()[0]));

        }
        return null;
    }

}
