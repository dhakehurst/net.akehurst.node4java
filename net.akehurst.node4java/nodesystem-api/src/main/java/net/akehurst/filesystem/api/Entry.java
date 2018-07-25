package net.akehurst.filesystem.api;

public interface Entry {

	boolean isFile();

	boolean isDirectory();

	boolean exists();

}
