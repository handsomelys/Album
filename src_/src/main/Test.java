package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;
import java.io.File;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;

import thumbnail.ThumbNail;
import tree.RunTree;

public class Test {
    @SuppressWarnings("deprecation")
	public static void main(String[] args) {
        JFrame testf = new JFrame();
        /*TestPanel tp = new TestPanel();
        testf.add(tp);
        testf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        testf.setVisible(true);*/
        
        String path  = "F:\\test__pic\\123.jpg";
        ImageIcon icon = new ImageIcon(path);
        icon.getImage().getScaledInstance(10, 10, Image.SCALE_DEFAULT);
        JLabel l = null;
        l = new JLabel(icon,JLabel.CENTER);
        l.setBounds(10, 10, 10, 10);
        l.setForeground(Color.red);
        l.setBackground(Color.black);
        JPanel jp = new JPanel();
        jp.setBounds(100, 100, 100, 100);
        jp.add(l);
        //testf.setLayout(null);
        testf.add(jp);
        testf.setSize(1500,1600);
        testf.setVisible(true);
       
    
    }
    
}

