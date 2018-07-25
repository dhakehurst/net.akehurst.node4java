package net.akehurst.filesystem.virtual;

import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.VFS;

import net.akehurst.filesystem.api.FileSystem;

public class FileSystemVirtual implements FileSystem {

	public static FileSystem create() {
		return new FileSystemVirtual();
	}

	private final FileObject fsRoot;

	protected FileSystemVirtual() {
		this.fsRoot = VFS.getManager().createVirtualFileSystem("vfs://");
	}
}
