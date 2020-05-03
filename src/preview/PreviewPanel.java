package preview;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;

import javax.swing.JPanel;

import event.FileSelectEvent;
import event.FileSelectListener;
import event.FileSelectSource;
import util.FileUtils;

public class PreviewPanel extends JPanel implements FileSelectSource {
    private static final long serialVersionUID = 1L;

    public ArrayList<ThumbNail> pictures;
    protected HashSet<FileSelectListener> listeners;
    public int sx;
	public int sy;
	public int ex;
	public int ey;
    public File directory;
    public boolean current = true;
    
    public PreviewPanel(File directory) {
        this.listeners = new HashSet<FileSelectListener>();
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
        		this.pictures.get(i).setBounds(i%5*175,i/5*150,150,142);
        }
        this.setFocusable(true);
        
        //FileUtils.picListener_keyboard(this);
        FileUtils.picListener1(this);
        FileUtils.picListener2(this);
        FileUtils.picListener3(this);
        
        //this.repaint();
        
        this.setCenterLocation();
        //this.printLocation();
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
    
    protected void panintComponent(Graphics g) {
    	if(!this.current) return;
    	g.setColor(new java.awt.Color(135,206,235));
    	g.fillRect(Math.min(this.sx, this.ex), Math.min(this.sy, this.ey), Math.abs(this.sx-this.ex), Math.abs(this.sy-this.ey));
    	super.paintComponent(g);
    }
    protected void paintBorder(Graphics g) {
        if (this.current)
            return;
        g.setColor(getForeground());
        g.drawRect(Math.min(sx, ex), Math.min(sy, ey), Math.abs(sx - ex),
                Math.abs(sy - ey));
    }
    public void setCenterLocation() {
    	for(int i=0;i<this.pictures.size();i++) {
    		//System.out.println("location:"+this.pictures.get(i).getLocation()+this.pictures.get(i).text.getText());
    		this.pictures.get(i).centerx = (this.pictures.get(i).getX()+150)/2;
    		this.pictures.get(i).centery = (this.pictures.get(i).getY()+142)/2;
    	}
    }
    
    public void printLocation() {
    	for(int i=0;i<this.pictures.size();i++) {
    		//System.out.println("location:"+this.pictures.get(i).getLocation()+this.pictures.get(i).text.getText());
    		System.out.println("x:"+this.pictures.get(i).centerx+"  y:"+this.pictures.get(i).centery);
    	}
    }
	
}
