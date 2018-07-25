package net.akehurst.filesystem.api;

public class FilesystemException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public FilesystemException(final String message, final Throwable cause) {
		super(message, cause);
	}

}
