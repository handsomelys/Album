package main;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class TestPanel extends JPanel{
	private static final long serialVersionUID = 1L;
	
	Image image = null;
	public void paint(Graphics g) {
		try {
			image = ImageIO.read(new File("F:\\test__pic\\123.jpg"));
			g.drawImage(image,0,0,550,400,null);
		}	catch (Exception e) {
			e.printStackTrace();
		}
	}

}
