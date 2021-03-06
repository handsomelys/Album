package tree;

import javax.swing.tree.DefaultMutableTreeNode;

import java.io.File;
import java.io.IOException;

public class FileNode extends DefaultMutableTreeNode {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean explored_ = false;

	public FileNode(File file) {
		setUserObject(file);
	}

	public FileNode(String string) {
		super();
	}

	public boolean getAllowChildren() {
		return isDirectory();
	}

	public boolean isLeaf() {
		return !isDirectory();
	}

	public File getFile() {
		return (File) getUserObject();
	}

	public boolean isExplored() {
		return explored_;
	}

	public boolean isDirectory() {
		File file = getFile();
		return file.isDirectory();
	}

	public String toString() {
		File file = getFile();
		String filename = file.toString();
		int index = filename.lastIndexOf("\\");
		return (index != -1 && index != filename.length() - 1) ? filename.substring(index + 1) : filename;
	}

	public String getString() {
		File file = getFile();
		String filename = file.getAbsolutePath();
		return filename;
	}

	public File getAbFile() {
		File file = getFile();
		File file1 = file.getAbsoluteFile();
		return file1;
	}

	public String getFPath() throws IOException {
		File file = getFile();
		String file1 = file.getPath();
		return file1;
	}

	public void explore() {
		if (!isDirectory()) {
			return;
		}
		if (!isExplored()) {
			File file = getFile();
			File[] children = file.listFiles();
			for (int i = 0; i < children.length; ++i) {
				if (children[i].isDirectory()) {
					add(new FileNode(children[i]));
				}
			}
			explored_ = true;
		}
	}
}
