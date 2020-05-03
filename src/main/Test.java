package main;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Test {
	public static void main(String[] args) {
        JPanel p = new JPanel();
        JFrame f = new JFrame();
        f.add(p);
        f.setVisible(true);
        p.setVisible(true);
        //p.requestFocus();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        p.addKeyListener(new KeyAdapter() {
        	public void keyPressed(KeyEvent e) {
        		if(e.getKeyCode()==KeyEvent.VK_CONTROL) {
        			System.out.println("press ctrl");
        		}
        	}
        	@Override
        	public void keyReleased(KeyEvent e) {
        		if(e.getKeyCode()==KeyEvent.VK_CONTROL)
        			System.out.println("release ctrl");
        	}
        });
    }
}

