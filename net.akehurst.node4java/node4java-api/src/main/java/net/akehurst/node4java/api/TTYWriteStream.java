package net.akehurst.node4java.api;

public interface TTYWriteStream extends Stream {

	boolean write(Object chunk);

	boolean write(Object chunk, String encoding);

	boolean write(Object chunk, Runnable callback);

	boolean write(Object chunk, String encoding, Runnable callback);

}
