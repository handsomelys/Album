package preview;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.Graphics;
import javax.swing.JPanel;
import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;

import event.FileEvent;
import event.FileListener;
import event.FileSource;
import main.Colors;
import util.FileUtils;


public class PreviewPanel extends JPanel implements FileSource {
    private static final long serialVersionUID = 1L;

    public ArrayList<Thumbnail> pictures;
    public ArrayList<Thumbnail> selectedPictures;
    public File directory;
    public boolean isFileSelected;
    public boolean isCtrlPressed;
    public boolean isInSelection = true;
    public int sx; // start x
    public int sy; // start y
    public int ex; // end x
    public int ey; // end y
    protected HashSet<FileListener> listeners;

    public PreviewPanel(File directory) {
        this.setBackground(Colors.DEFAULT);
        this.directory = directory.getAbsoluteFile();
        this.pictures = new ArrayList<Thumbnail>();
        this.selectedPictures = new ArrayList<Thumbnail>();
        this.isFileSelected = false;
        this.isCtrlPressed = false;
        this.listeners = new HashSet<FileListener>();

        this.addMouseListener(new RectangularSelectListener());
        this.addMouseMotionListener(new picListener3());
        this.addMouseListener(new CancelSelectListener());
        updateDirectory(this.directory);
    }
    public void updateDirectory(File directory) {
        this.setLayout(null);
        this.directory = directory;
        // remove old
        if (this.pictures.size() > 0) {
            for (Thumbnail tb : this.pictures) {
                this.remove(tb);
            }
            this.pictures.clear();
        }
        // add new
        for (File f : this.directory.listFiles()) {
            if (FileUtils.isPicture(f)) {
                Thumbnail tn = new Thumbnail(f);
                this.pictures.add(tn);
            }
        }
        for (Thumbnail tb : this.pictures) {
            this.add(tb);
        }
        
        for(int i=0;i<this.pictures.size();i++) {
                this.pictures.get(i).setBounds(i%5*175,i/5*150,150,142);
        }
        this.addListenerForThumbnails();
        
        this.setCenterLocation();
        //this.printLocation();
    }
    
    protected void panintComponent(Graphics g) {
        super.paintComponent(g);
        if(!this.isInSelection) return;
        g.setColor(new java.awt.Color(135,206,235));
        g.fillRect(Math.min(this.sx, this.ex), Math.min(this.sy, this.ey), Math.abs(this.sx-this.ex), Math.abs(this.sy-this.ey));
    }
    protected void paintBorder(Graphics g) {
        if (this.isInSelection)
            return;
        g.setColor(getForeground());
        g.drawRect(Math.min(sx, ex), Math.min(sy, ey), Math.abs(sx - ex),
                Math.abs(sy - ey));
    }
    public void setCenterLocation() {
        for(int i=0;i<this.pictures.size();i++) {
            //System.out.println("location:"+this.pictures.get(i).getLocation()+this.pictures.get(i).text.getText());
            this.pictures.get(i).centerX = (this.pictures.get(i).getX()+150)/2;
            this.pictures.get(i).centerY = (this.pictures.get(i).getY()+142)/2;
        }
    }
    
    public void printLocation() {
        for(int i=0;i<this.pictures.size();i++) {
            //System.out.println("location:"+this.pictures.get(i).getLocation()+this.pictures.get(i).text.getText());
            System.out.println("x:"+this.pictures.get(i).centerX+"  y:"+this.pictures.get(i).centerY);
        }
    }
    public void clearSeletedPictures() {
        for(int i = 0; i < this.selectedPictures.size(); i++) {
            Thumbnail t = this.selectedPictures.get(i);
            t.deselect();
        }
        this.selectedPictures.clear();
        this.notifySelectEvent();
    }
    public void notifySelectEvent(){
        ArrayList<File> event = new ArrayList<File>();
        for (Thumbnail t: this.selectedPictures)
            event.add(t.file);
        FileEvent fe = new FileEvent(this, "select", event);
        this.notifyAll(fe);
    }
    public void addListenerForThumbnails() {
        for(int i = 0; i < this.pictures.size(); i++) {
            this.pictures.get(i).addMouseListener(new SingleSelectListener());
        }
    }
    public class SingleSelectListener extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent e) {
            Thumbnail t = (Thumbnail) e.getSource();
            if (PreviewPanel.this.isFileSelected) {
                if (t.isSelected()) {
                    t.deselect();
                    PreviewPanel.this.selectedPictures.remove(t);
                    if (PreviewPanel.this.selectedPictures.isEmpty())
                        PreviewPanel.this.isFileSelected = false;
                } else {
                    if (!PreviewPanel.this.isCtrlPressed)
                        PreviewPanel.this.clearSeletedPictures();
                    t.select();
                    PreviewPanel.this.selectedPictures.add(t);
                }
            } else {
                PreviewPanel.this.isFileSelected = true;
                t.select();
                PreviewPanel.this.selectedPictures.add(t);
            }
            PreviewPanel.this.notifySelectEvent();
        }
    }
    public class RectangularSelectListener extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent e) {
            if (e.getButton() == MouseEvent.BUTTON1) {
                PreviewPanel.this.sx = e.getX()-PreviewPanel.this.getX();
                PreviewPanel.this.sy = e.getY()-PreviewPanel.this.getY();
            }
            // System.out.println("start x: "+PreviewPanel.this.sx);
            // System.out.println("start y: "+PreviewPanel.this.sy);
        }
        @Override
        public void mouseReleased(MouseEvent e) {
            if (e.getButton() == MouseEvent.BUTTON1) {
                PreviewPanel.this.ex = e.getX()-PreviewPanel.this.getX();
                PreviewPanel.this.ey = e.getY()-PreviewPanel.this.getY();
                PreviewPanel.this.isInSelection = true;
                for(Thumbnail t : PreviewPanel.this.pictures)
                    t.setCenterLocation();
                if (PreviewPanel.this.isCtrlPressed) {
                    for(Thumbnail t : PreviewPanel.this.pictures) {
                        if (t.isInside(PreviewPanel.this.sx,
                                PreviewPanel.this.ex,
                                PreviewPanel.this.sy,
                                PreviewPanel.this.ey)) {
                            if (t.isSelected()) {
                                t.deselect();
                                PreviewPanel.this.selectedPictures.remove(t);
                            } else {
                                t.select();
                                PreviewPanel.this.selectedPictures.add(t);
                            }
                        }
                    }
                } else {
                    PreviewPanel.this.clearSeletedPictures();
                    for(Thumbnail t : PreviewPanel.this.pictures) {
                        if (t.isInside(PreviewPanel.this.sx,
                                PreviewPanel.this.ex,
                                PreviewPanel.this.sy,
                                PreviewPanel.this.ey)) {
                            t.select();
                            PreviewPanel.this.selectedPictures.add(t);
                        }
                    }
                }
                PreviewPanel.this.notifySelectEvent();
                PreviewPanel.this.repaint();
            }
            // System.out.println("end x: "+PreviewPanel.this.ex);
            // System.out.println("end y: "+PreviewPanel.this.ey);
        }
    }   
    public class picListener3 extends MouseMotionAdapter {
        @Override
        public void mouseDragged(MouseEvent e) {
            PreviewPanel.this.ex = e.getX();
            PreviewPanel.this.ey = e.getY();
            PreviewPanel.this.isInSelection = false;
            PreviewPanel.this.repaint();
        }
    }
    public class CancelSelectListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getButton() == MouseEvent.BUTTON1
                    && !PreviewPanel.this.isCtrlPressed) {
                PreviewPanel.this.clearSeletedPictures();
            }
        }
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
