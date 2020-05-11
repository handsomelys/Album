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
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import event.FileEvent;
import event.FileListener;
import event.FileSource;
import main.Colors;
import util.FileUtils;


public class PreviewPanel extends JPanel implements FileSource {
    private static final long serialVersionUID = 1L;

    public ArrayList<Thumbnail> pictures;
    public ArrayList<Thumbnail> selectedPictures;
    public ArrayList<File>	picFiles;

    public File directory;
    public boolean isFileSelected;
    public boolean isCtrlPressed;
    public boolean isInSelection = true;
    public int sx; // start x
    public int sy; // start y
    public int ex; // end x
    public int ey; // end y
    protected HashSet<FileListener> listeners;
    public JScrollPane previewScrollPane = new JScrollPane(this);
    private ThreadPoolExecutor pool = null;
    
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
    public PreviewPanel(File directory) {
        this.setBackground(Colors.DEFAULT);
        this.directory = directory.getAbsoluteFile();
        this.pictures = new ArrayList<Thumbnail>();
        this.selectedPictures = new ArrayList<Thumbnail>();
        this.isFileSelected = false;
        this.isCtrlPressed = false;
        this.listeners = new HashSet<FileListener>();
        this.picFiles = new ArrayList<File>();
        this.addMouseListener(new RectangularSelectListener());
        this.addMouseMotionListener(new picListener3());
        this.addMouseListener(new CancelSelectListener());
        updateDirectory(this.directory);
    }
    public void updateDirectory(File directory) {
        this.setLayout(null);
        this.directory = directory;
        // remove old
        this.picFiles.clear();
        System.out.println("init size:"+this.pictures.size());
        if (this.pictures.size() > 0) {
            for (Thumbnail tb : this.pictures) {
                this.remove(tb);
            }
            this.pictures.clear();
        }
        
        // add new
        
        /*
        for (File f : this.directory.listFiles()) {
            if (FileUtils.isPicture(f)) {
                Thumbnail tn = new Thumbnail(f);
                this.pictures.add(tn);
            }
        }
        
        
        for (Thumbnail tb : this.pictures) {
            this.add(tb);
        }
        */

        ExecutorService exector = Executors.newFixedThreadPool(6);
        
        for (File f : this.directory.listFiles()) {
            if (FileUtils.isPicture(f)) {
                this.picFiles.add(f);
            }
        }
        
        
        int temp = this.picFiles.size();
        
        ArrayList<Thumbnail>	tmparray1 = new ArrayList<Thumbnail>();
        ArrayList<Thumbnail>	tmparray2 = new ArrayList<Thumbnail>();
        ArrayList<Thumbnail>	tmparray3 = new ArrayList<Thumbnail>();
        ArrayList<Thumbnail>	tmparray4 = new ArrayList<Thumbnail>();
        ArrayList<Thumbnail>	tmparray5 = new ArrayList<Thumbnail>();
        ArrayList<Thumbnail>	tmparray6 = new ArrayList<Thumbnail>();
         
        Runnable threading1 = new PicThreading(this.picFiles,this,tmparray1,this.directory,0,temp/6);
        Runnable threading2 = new PicThreading(this.picFiles,this,tmparray2,this.directory,temp/6,temp/6*2);
        Runnable threading3 = new PicThreading(this.picFiles,this,tmparray3,this.directory,temp/6*2,temp/6*3);
        Runnable threading4 = new PicThreading(this.picFiles,this,tmparray4,this.directory,temp/6*3,temp/6*4);
        Runnable threading5 = new PicThreading(this.picFiles,this,tmparray5,this.directory,temp/6*4,temp/6*5);
        Runnable threading6 = new PicThreading(this.picFiles,this,tmparray6,this.directory,temp/6*5,temp);
        exector.submit(threading1);
        exector.submit(threading2);
        exector.submit(threading3);
        exector.submit(threading4);
        exector.submit(threading5);
        exector.submit(threading6);
        exector.shutdown();
        while(true) {
        	if(exector.isTerminated()) {
        		System.out.println("all threading is done");
        		break;
        	}
        	
        }

        this.pictures.addAll(tmparray1);
        this.pictures.addAll(tmparray2);
        this.pictures.addAll(tmparray3);
        this.pictures.addAll(tmparray4);
        this.pictures.addAll(tmparray5);
        this.pictures.addAll(tmparray6);
        System.out.println("tmparray1:"+tmparray1.size());
        System.out.println("tmparray2:"+tmparray2.size());
        System.out.println("tmparray3:"+tmparray3.size());
        System.out.println("tmparray4:"+tmparray4.size());
        System.out.println("tmparray5:"+tmparray5.size());
        System.out.println("tmparray6:"+tmparray6.size());
        System.out.println("thispictures:"+this.pictures.size());
        for(int i=0;i<this.pictures.size();i++) {
                this.pictures.get(i).setBounds(i%PIC_PER_ROW*THUMBNAILX+EXTEND_X,i/PIC_PER_ROW*THUMBNAILY+EXTEND_Y,THUMBNAILWIDTH,THUMBNAILHEIGHT);
        }
        
        this.addListenerForThumbnails();
        
        if(this.pictures.size()>PIC_PER_ROW*3) {
        	previewScrollPane.getVerticalScrollBar().setVisible(true);
        	if(this.pictures.size()%PIC_PER_ROW==0) {
        		this.setPreferredSize(new Dimension(WIDTH1,HEIGHT1*(this.pictures.size()/PIC_PER_ROW)));
        	
        	}else {
        		this.setPreferredSize(new Dimension(WIDTH1,HEIGHT1*(this.pictures.size()/PIC_PER_ROW+1)));
        	}
        	previewScrollPane.getVerticalScrollBar().setValue(0);
        	
        } else if(this.pictures.size()>=PIC_PER_ROW&&this.pictures.size()<PIC_PER_ROW*3){
        	
        	this.setSize(new Dimension(WIDTH3,HEIGHT3));
        }	else {
        	this.setPreferredSize(new Dimension(this.pictures.size()*WIDTH2,this.pictures.size()/PIC_PER_ROW*HEIGHT1));
        }
        
        
        this.setCenterLocation();
        
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
	public JScrollPane getPreviewScrollPane() {
		return previewScrollPane;
	}
    
}
