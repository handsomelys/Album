package viewframe;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JScrollPane;

public class ScrollPane extends JScrollPane {
	
	public int pic_Width=0;
	public int pic_Height=0;
	public int pic_x;
	public int pic_y;
	public ArrayList<BufferedImage> imgs=null;
	public int contentnum;
	public ScrollPane(ArrayList<BufferedImage> img) {
		
		this.imgs=img;
	}
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.clearRect(0, 0, this.getWidth(), this.getHeight());
		g.drawImage(imgs.get(contentnum),pic_x,pic_y,pic_Width,pic_Height,null);
	}

}
