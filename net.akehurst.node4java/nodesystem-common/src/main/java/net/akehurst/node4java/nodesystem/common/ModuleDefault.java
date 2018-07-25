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
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.logging.Logger;

import javax.tools.FileObject;
import javax.xml.transform.Source;

import org.graalvm.polyglot.Value;
import org.slf4j.LoggerFactory;

import net.akehurst.node4java.nodesystem.api.Module;

public class ModuleDefault implements Module {

	protected final Logger LOGGER;

	private final ModuleLoader loader;
	protected final Context graal;
	protected Map<String, Object> exports;

	public ModuleDefault(final ModuleLoader loader, final Context graal) {
		this.LOGGER = LoggerFactory.getLogger(this.getClass());
		this.loader = loader;
		this.graal = graal;
		this.exports = new HashMap<String, Object>() {
			public Object get(final String key) {
				ModuleDefault.this.LOGGER.warn(String.format("trying to get, %s", key));
				return super.get(key);
			}
		};
	}

	@Override
	public Value exports() {
		final Value v = this.graal.asValue(this.exports);
		return v;
	}

	public Object require(final String name) {
		return this.loader.require(name);
	}

	@Override
	public Value resolve(final String scriptPath) {
		try {
			// need to wrap loaded file as follows
			//(function(exports, require, module, __filename, __dirname) {
			//	// Module code actually lives in here
			//});

			final ByteArrayInputStream start = new ByteArrayInputStream(
					"(function(exports, require, module, __filename, __dirname) {".getBytes(StandardCharsets.UTF_8));

			final FileObject fo = this.loader.resolveFile(scriptPath);
			final InputStream script = fo.getContent().getInputStream();

			final ByteArrayInputStream end = new ByteArrayInputStream((System.lineSeparator() + "});").getBytes(StandardCharsets.UTF_8));

			final Enumeration<? extends InputStream> streams = Collections.enumeration(Arrays.asList(start, script, end));
			final SequenceInputStream completeStream = new SequenceInputStream(streams);
			final Reader reader = new InputStreamReader(completeStream);
			final Source src = Source.newBuilder("js", reader, scriptPath).build();
			final Value funVal = this.graal.eval(src);

			final Object require = (Function<String, Object>) (n) -> this.require(n);
			final Object __filename = scriptPath;
			final Object __dirname = scriptPath.substring(0, scriptPath.lastIndexOf('/'));
			final Value val = funVal.execute(this.exports, require, this, __filename, __dirname);
			this.exports = val.getMember("exports").as(Map.class);
			return this.exports();
		} catch (final Exception e) {
			this.LOGGER.error(String.format("in requireScript, %s", e.getMessage() + e.getStackTrace()[0]));
			this.LOGGER.error(String.format("%s", e.getStackTrace()[0]));

		}
		return null;
	}

}
