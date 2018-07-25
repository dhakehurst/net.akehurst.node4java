package net.akehurst.filesystem.virtual;

import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.VFS;

import net.akehurst.filesystem.api.Filesystem;
import net.akehurst.filesystem.api.FilesystemException;

public class FilesystemVirtual implements Filesystem {

	public static Filesystem create() {
		return new FilesystemVirtual();
	}

	private final FileObject fsRoot;

	protected FilesystemVirtual() {
		try {
			this.fsRoot = VFS.getManager().createVirtualFileSystem("vfs://");
		} catch (final Exception e) {
			throw new FilesystemException("Unable to create Virtual Filesystem", e);
		}
	}
}
