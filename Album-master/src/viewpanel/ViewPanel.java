package viewpanel;

import java.awt.event.*;
import javax.swing.*;

import java.io.*;




public class ViewPanel extends JPanel {
	private JLabel label;
	private JFileChooser chooser;
	private static final int DEFAULT_WIDTH = 300;
	private static final int DEFAULT_HEIGHT = 400;
	
	public ViewPanel() {
		label = new JLabel();
		this.add(label);
		chooser = new JFileChooser();
		chooser.setCurrentDirectory(new File("."));
		//JMenuBar menubar = new JMenuBar();
		//setJMenuBar(menubar);
		JButton open = new JButton("open");
		this.add(open);
		JButton close = new JButton("close");
		this.add(close);
		open.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int result = chooser.showOpenDialog(null);
				if(result == JFileChooser.APPROVE_OPTION) {
					String path = chooser.getSelectedFile().getPath();
					label.setIcon(new ImageIcon(path));
				}
			}
		});
		
		close.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				return ;
			}
		});
		
		
	}
}
