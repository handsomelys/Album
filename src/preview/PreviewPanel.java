package preview;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;

import javax.swing.JPanel;

import event.FileEvent;
import event.FileListener;
import event.FileSource;
import util.FileUtils;

public class PreviewPanel extends JPanel implements FileSource {
    private static final long serialVersionUID = 1L;

    public ArrayList<ThumbNail> pictures;
    protected HashSet<FileListener> listeners;

    public File directory;

    public PreviewPanel(File directory) {
        this.listeners = new HashSet<FileListener>();
        this.directory = directory.getAbsoluteFile();
        updateDirectory(this.directory);
    }
    public void updateDirectory(File directory) {
    	this.setLayout(null);
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
        
        for(int i=0;i<this.pictures.size();i++) {
        		this.pictures.get(i).setBounds(i%5*175,i/5*120,150,142);
        }
        
        //this.repaint();
    }
    // event source method
    @Override
    public void addListener(FileListener fl) {
        this.listeners.add(fl);
    }
    @Override
    public void removeListener(FileListener fl) {
        this.listeners.remove(fl);
    }
    @Override
    public void notifyAll(FileEvent fe) {
        for (FileListener fl: listeners)
            fl.actionPerformed(fe);
    }
}
