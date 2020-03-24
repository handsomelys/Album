package main;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridBagLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.JPanel;

import preview.*;
import tree.*;
import background.*;
import albummenu.*;
import gui.*;
public class Main{

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			
		} catch(Throwable e) {
			e.printStackTrace();
		}
		/*EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JMenuBar jmenu = new JMenuBar();
					
					JFrame mainFrame = new JFrame();
					PicPreviewDialog previewFrame = new PicPreviewDialog();
					DiskTree disktree = new DiskTree();
					JButton buttonPreview = new JButton("preview");
					
					
					mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					mainFrame.setBounds(250,250,500,500);
					
					
					mainFrame.addWindowListener(new WindowAdapter() {
						@Override
						public void windowActivated(WindowEvent e) {
							disktree.do_WindowActivated(e);
						}
					});
					
					mainFrame.setLayout(new BorderLayout());
					disktree.setPreferredSize(new Dimension(200,150));
					mainFrame.add(disktree,BorderLayout.WEST);
					
					buttonPreview.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							previewFrame.setVisible(true);
						}
					});
					//mainFrame.setVisible(true);
					buttonPreview.setBounds(175, 175, 100, 100);
					mainFrame.add(buttonPreview,BorderLayout.NORTH);
					mainFrame.setVisible(true);
					
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
		});*/
		
		AlbumMenu menu = new AlbumMenu();
		JFrame mainFrame = new JFrame();
		PicPreviewDialog previewFrame = new PicPreviewDialog();
		DiskTree disktree = new DiskTree();
		JButton buttonPreview = new JButton("preview");
		
		
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setBounds(250,250,500,500);
		
		
		mainFrame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
				disktree.do_WindowActivated(e);
			}
		});
		
		mainFrame.setLayout(new BorderLayout());
		disktree.setPreferredSize(new Dimension(200,150));
		mainFrame.add(disktree,BorderLayout.WEST);
		
		buttonPreview.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				previewFrame.setVisible(true);
			}
		});
		//mainFrame.setVisible(true);
		buttonPreview.setBounds(175, 175, 100, 100);
		mainFrame.add(buttonPreview,BorderLayout.EAST);
		mainFrame.setVisible(true);
		mainFrame.add(menu,BorderLayout.NORTH);
	}

}
