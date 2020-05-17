package preview;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import event.CommandEvent;
import event.CommandListener;
import event.CommandSource;
import event.FileEvent;
import event.FileListener;
import event.FileSource;
import main.Colors;
import util.FileUtils;

public class PreviewPanel extends JPanel implements FileSource, CommandSource {
    private static final long serialVersionUID = 1L;
    
    private static final int WIDTH1 = 632;
    private static final int HEIGHT1 = 158;
    private static final int WIDTH2 = 166;
    private static final int WIDTH3 = 830;
    private static final int HEIGHT3 = 725;
    private static final int THUMBNAILX = 175;
    private static final int THUMBNAILY = 150;
    private static final int THUMBNAILWIDTH = 120;
    private static final int THUMBNAILHEIGHT = 110;
    private static final int PIC_PER_ROW = 5;
    private static final int EXTEND_X = 50;
    private static final int EXTEND_Y = 30;

    public File directory;
    public ArrayList<File>	picFiles;
    public ArrayList<Thumbnail> pictures;
    public ArrayList<Thumbnail> selectedPictures;
    public boolean isFileSelected;
    public boolean isCtrlPressed;
    public boolean isInSelection;
    public int sx; // start x
    public int sy; // start y
    public int ex; // end x
    public int ey; // end y

    public PopupMenu panelPopupMenu;
    public PopupMenu thumbnailPopupMenu;
    public JScrollPane previewScrollPane = new JScrollPane(this);

    protected HashSet<FileListener> FileListeners;
    protected HashSet<CommandListener> CommandListeners;

    public PreviewPanel(File directory) {
        this.isFileSelected = false;
        this.isCtrlPressed = false;
        this.isInSelection = false;
        this.sx = 0;
        this.sy = 0;
        this.ex = 0;
        this.ey = 0;
        this.directory = directory.getAbsoluteFile();
        this.picFiles = new ArrayList<File>();
        this.pictures = new ArrayList<Thumbnail>();
        this.selectedPictures = new ArrayList<Thumbnail>();
        this.FileListeners = new HashSet<FileListener>();
        this.CommandListeners = new HashSet<CommandListener>();

        this.panelPopupMenu = new PopupMenu("panel");
        this.thumbnailPopupMenu = new PopupMenu("thumbnail");

        this.addMouseListener(new RectangularSelectListener());
        this.addMouseMotionListener(new InSelectionListener());
        this.addMouseListener(new CancelSelectListener());
        this.addMouseListener(new PopupMenuOpenListener());
        this.panelPopupMenu.addListener(new PopupMenuCommandListener());
        this.thumbnailPopupMenu.addListener(new PopupMenuCommandListener());

        this.updateDirectory(directory);
    }

    public void updateDirectory(File directory) {
        this.setBackground(Colors.DEFAULT);
        this.setLayout(null);
        this.directory = directory.getAbsoluteFile();

        // remove old
        this.removeListenerForThumbnails();
        if (this.picFiles.size() > 0)
            this.picFiles.clear();
        if (this.pictures.size() > 0) {
            for (Thumbnail t : this.pictures)
                this.remove(t);
            this.pictures.clear();
        }
        if (this.selectedPictures.size() > 0)
            this.selectedPictures.clear();

        // load thumbnails with multithreading
        for (File f: this.directory.listFiles()) {
            if (FileUtils.isPicture(f)) {
                this.picFiles.add(f);
            }
        }

        ExecutorService exector = Executors.newFixedThreadPool(6);
        int size = this.picFiles.size();
        int interval = size/6;
        
        ArrayList<Thumbnail> tmparray1 = new ArrayList<Thumbnail>();
        ArrayList<Thumbnail> tmparray2 = new ArrayList<Thumbnail>();
        ArrayList<Thumbnail> tmparray3 = new ArrayList<Thumbnail>();
        ArrayList<Thumbnail> tmparray4 = new ArrayList<Thumbnail>();
        ArrayList<Thumbnail> tmparray5 = new ArrayList<Thumbnail>();
        ArrayList<Thumbnail> tmparray6 = new ArrayList<Thumbnail>();

        Runnable threading1 = new PicThreading(this.picFiles, this, tmparray1, this.directory, 0, interval);
        Runnable threading2 = new PicThreading(this.picFiles, this, tmparray2, this.directory, interval, interval*2);
        Runnable threading3 = new PicThreading(this.picFiles, this, tmparray3, this.directory, interval*2, interval*3);
        Runnable threading4 = new PicThreading(this.picFiles, this, tmparray4, this.directory, interval*3, interval*4);
        Runnable threading5 = new PicThreading(this.picFiles, this, tmparray5, this.directory, interval*4, interval*5);
        Runnable threading6 = new PicThreading(this.picFiles, this, tmparray6, this.directory, interval*5, size);

        exector.submit(threading1);
        exector.submit(threading2);
        exector.submit(threading3);
        exector.submit(threading4);
        exector.submit(threading5);
        exector.submit(threading6);
        exector.shutdown();
       
        while(!exector.isTerminated()); // wait until exector done
        
        this.pictures.addAll(tmparray1);
        this.pictures.addAll(tmparray2);
        this.pictures.addAll(tmparray3);
        this.pictures.addAll(tmparray4);
        this.pictures.addAll(tmparray5);
        this.pictures.addAll(tmparray6);
        
        for(int i=0;i<this.pictures.size();i++) {
            this.pictures.get(i).setBounds(
                i%PreviewPanel.PIC_PER_ROW*PreviewPanel.THUMBNAILX+PreviewPanel.EXTEND_X,
                i/PreviewPanel.PIC_PER_ROW*PreviewPanel.THUMBNAILY+PreviewPanel.EXTEND_Y,
                PreviewPanel.THUMBNAILWIDTH,
                PreviewPanel.THUMBNAILHEIGHT);
        }
        
        this.addListenerForThumbnails();
        // configurating scroll pane
        if(this.pictures.size()>PreviewPanel.PIC_PER_ROW*3) {
            this.previewScrollPane.getVerticalScrollBar().setVisible(true);
            if(this.pictures.size()%PreviewPanel.PIC_PER_ROW==0) {
                this.setPreferredSize(new Dimension(PreviewPanel.WIDTH1,PreviewPanel.HEIGHT1*(this.pictures.size()/PreviewPanel.PIC_PER_ROW)));
            }else {
                this.setPreferredSize(new Dimension(PreviewPanel.WIDTH1,PreviewPanel.HEIGHT1*(this.pictures.size()/PreviewPanel.PIC_PER_ROW+1)));
            }
            this.previewScrollPane.getVerticalScrollBar().setValue(0);
            
        } else if(this.pictures.size()>=PreviewPanel.PIC_PER_ROW&&this.pictures.size()<PreviewPanel.PIC_PER_ROW*3){
            
            this.setSize(new Dimension(PreviewPanel.WIDTH3,PreviewPanel.HEIGHT3));
        }	else {
            this.setPreferredSize(new Dimension(this.pictures.size()*PreviewPanel.WIDTH2,this.pictures.size()/PreviewPanel.PIC_PER_ROW*PreviewPanel.HEIGHT1));
        }
        
        this.setCenterLocation();
    }

    protected void panintComponent(Graphics g) {
        super.paintComponent(g);
        if (this.isInSelection)
            return;
        g.setColor(new java.awt.Color(135, 206, 235));
        g.fillRect(Math.min(this.sx, this.ex), Math.min(this.sy, this.ey), Math.abs(this.sx - this.ex),
                Math.abs(this.sy - this.ey));
    }

    protected void paintBorder(Graphics g) {
        if (!this.isInSelection)
            return;
        g.setColor(getForeground());
        g.drawRect(Math.min(sx, ex), Math.min(sy, ey), Math.abs(sx - ex), Math.abs(sy - ey));
    }

    public void setCenterLocation() {
        for (int i = 0; i < this.pictures.size(); i++) {
            // System.out.println("location:"+this.pictures.get(i).getLocation()+this.pictures.get(i).text.getText());
            this.pictures.get(i).centerX = (this.pictures.get(i).getX() + 150) / 2;
            this.pictures.get(i).centerY = (this.pictures.get(i).getY() + 142) / 2;
        }
    }

    public void clearSeletedPictures() {
        for (int i = 0; i < this.selectedPictures.size(); i++) {
            Thumbnail t = this.selectedPictures.get(i);
            t.deselect();
        }
        this.selectedPictures.clear();
        this.notifySelectEvent();
    }

    public void addListenerForThumbnails() {
        for (int i = 0; i < this.pictures.size(); i++) {
            Thumbnail t = this.pictures.get(i);
            t.addMouseListener(new SingleSelectListener());
            t.addMouseListener(new PopupMenuOpenListener());
        }
    }
    public void removeListenerForThumbnails() {
        for (int i = 0; i < this.pictures.size(); i++) {
            Thumbnail t = this.pictures.get(i);
            t.removeMouseListener(new SingleSelectListener());
            t.removeMouseListener(new PopupMenuOpenListener());
        }
    }

    public void notifySelectEvent() {
        ArrayList<File> event = new ArrayList<File>();
        for (Thumbnail t : this.selectedPictures)
            event.add(t.file);
        FileEvent fe = new FileEvent(this, "select", event);
        this.notifyAll(fe);
    }

    public class SingleSelectListener extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent e) {
            Thumbnail t = (Thumbnail) e.getSource();
            if (PreviewPanel.this.isFileSelected) {
                if (t.isSelected()) {
                    if (e.getButton() == MouseEvent.BUTTON1) {
                        t.deselect();
                        PreviewPanel.this.selectedPictures.remove(t);
                    }
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
            if (PreviewPanel.this.selectedPictures.isEmpty())
                PreviewPanel.this.isFileSelected = false;
            PreviewPanel.this.notifySelectEvent();
        }
    }

    public class RectangularSelectListener extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent e) {
            PreviewPanel.this.sx = e.getX() - PreviewPanel.this.getX();
            PreviewPanel.this.sy = e.getY() - PreviewPanel.this.getY();
            PreviewPanel.this.isInSelection = true;
            // System.out.println("start x: "+PreviewPanel.this.sx);
            // System.out.println("start y: "+PreviewPanel.this.sy);
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            PreviewPanel.this.ex = e.getX() - PreviewPanel.this.getX();
            PreviewPanel.this.ey = e.getY() - PreviewPanel.this.getY();
            PreviewPanel.this.isInSelection = false;
            for (Thumbnail t : PreviewPanel.this.pictures)
                t.setCenterLocation();
            if (PreviewPanel.this.isCtrlPressed) {
                for (Thumbnail t : PreviewPanel.this.pictures) {
                    if (t.isInside(PreviewPanel.this.sx, PreviewPanel.this.ex, PreviewPanel.this.sy,
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
                for (Thumbnail t : PreviewPanel.this.pictures) {
                    if (t.isInside(PreviewPanel.this.sx, PreviewPanel.this.ex, PreviewPanel.this.sy,
                            PreviewPanel.this.ey)) {
                        t.select();
                        PreviewPanel.this.selectedPictures.add(t);
                    }
                }
            }
            PreviewPanel.this.notifySelectEvent();
            PreviewPanel.this.repaint();
            // System.out.println("end x: "+PreviewPanel.this.ex);
            // System.out.println("end y: "+PreviewPanel.this.ey);
        }
    }

    public class InSelectionListener extends MouseMotionAdapter {
        @Override
        public void mouseDragged(MouseEvent e) {
            if (PreviewPanel.this.isInSelection) {
                PreviewPanel.this.ex = e.getX();
                PreviewPanel.this.ey = e.getY();
                PreviewPanel.this.repaint();
            }
        }
    }

    public class CancelSelectListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getButton() == MouseEvent.BUTTON1 && !PreviewPanel.this.isCtrlPressed) {
                PreviewPanel.this.clearSeletedPictures();
            }
        }
    }

    public class PopupMenuOpenListener extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent e) {
            showPopupMenu(e);
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            showPopupMenu(e);
        }

        private void showPopupMenu(MouseEvent e) {
            if (e.isPopupTrigger()) {
                Object o = e.getSource();
                if (o instanceof PreviewPanel) 
                    PreviewPanel.this.panelPopupMenu.show(
                        e.getComponent(), e.getX(), e.getY());
                else if (o instanceof Thumbnail)
                    PreviewPanel.this.thumbnailPopupMenu.show(
                        e.getComponent(), e.getX(), e.getY());
            }
        }
    }
    public class PopupMenuCommandListener implements CommandListener {
        @Override
        public void actionPerformed(CommandEvent ce) {
            String command = ce.getCommand()[0];
            CommandEvent nce = new CommandEvent(PreviewPanel.this, command);
            PreviewPanel.this.notifyAll(nce);
        }
    }
    // file event source method
    @Override
    public void addListener(FileListener fl) {
        this.FileListeners.add(fl);
    }

    @Override
    public void removeListener(FileListener fl) {
        this.FileListeners.remove(fl);
    }

    @Override
    public void notifyAll(FileEvent fe) {
        for (FileListener fl : this.FileListeners)
            fl.actionPerformed(fe);
    }
    // command event source method
    @Override
    public void addListener(CommandListener cl) {
        this.CommandListeners.add(cl);
    }
    @Override
    public void removeListener(CommandListener cl) {
        this.CommandListeners.remove(cl);
    }
    @Override
    public void notifyAll(CommandEvent ce) {
        for (CommandListener cl : this.CommandListeners)
            cl.actionPerformed(ce);
    }
    public JScrollPane getPreviewScrollPane() {
        return previewScrollPane;
    }
    
}
