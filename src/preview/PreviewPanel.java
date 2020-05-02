package preview;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;

import javax.swing.JPanel;

import event.FileSelectEvent;
import event.FileSelectListener;
import event.FileSelectSource;
import util.FileUtils;

public class PreviewPanel extends JPanel implements FileSelectSource {
    private static final long serialVersionUID = 1L;

    public ArrayList<ThumbNail> pictures;
    protected HashSet<FileSelectListener> listeners;

    public File directory;

    public PreviewPanel(File directory) {
        this.listeners = new HashSet<FileSelectListener>();
        this.directory = directory.getAbsoluteFile();
        updateDirectory(this.directory);
    }
    public void updateDirectory(File directory) {
        this.directory = directory;
        if (this.pictures == null)
            this.pictures = new ArrayList<ThumbNail>();
        // remove old
        if (this.pictures.size() > 0) {
            for (ThumbNail tb : this.pictures) {
                this.remove(tb);
            }
            this.pictures.clear();
        }
        // add new
        for (File f : this.directory.listFiles()) {
            if (FileUtils.isPicture(f)) {
                ThumbNail tn = new ThumbNail(f);
                this.pictures.add(tn);
            }
        }
        for (ThumbNail tb : this.pictures) {
            this.add(tb);
        }
        this.repaint();
    }
    // event source method
    @Override
    public void addListener(FileSelectListener fsl) {
        this.listeners.add(fsl);
    }
    @Override
    public void removeListener(FileSelectListener fsl) {
        this.listeners.remove(fsl);
    }
    @Override
    public void notifyAll(FileSelectEvent fse) {
        for (FileSelectListener fsl: listeners)
            fsl.actionPerformed(fse);
    }
}
