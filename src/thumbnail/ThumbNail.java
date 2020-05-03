package thumbnail;

//import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.SwingConstants;
import javax.swing.tree.TreePath;

import tree.FileNode;

public class ThumbNail {
	public JScrollPane scrollpaneForThumbNail;
	public ArrayList<JPanel>	lilPanels = new ArrayList<JPanel>();	//放label和textfield
	public ArrayList<JLabel>	lilLabels = new ArrayList<JLabel>();	//放图片
	public ArrayList<JTextField>	lilTextFields	= new ArrayList<JTextField>();	//放图片对应得名称
	public ArrayList<File>	picFiles = new ArrayList<File>();
	public ArrayList<TreePath>	treePaths = new ArrayList<TreePath>();
	public ArrayList<File>	clickedFile = new ArrayList<File>();
	public JPanel picPanel = new JPanel();
	public String filepath = null;
	public String filepath2 = null;
	public int picNumber = 0;
	public int picQuantity = 0;
	public void init() {
		scrollpaneForThumbNail = new JScrollPane(picPanel);
		lilPanels.clear();
		lilLabels.clear();
		lilTextFields.clear();
		picPanel.removeAll();
		picPanel.repaint();
		picFiles.clear();
		//picPanel.setLayout(null);
		treePaths.clear();
		clickedFile.clear();
		picPanel.setVisible(true);
	}
	public void ShowThumbNail(MouseEvent e,int flag, TreePath path) {
		try {
			init();
		JTree tree = (JTree) e.getSource();
		int row = tree.getRowForLocation(e.getX(), e.getY());//返回节点所在行
		System.out.println(row);
		if (row == -1) {
			return ;
			//-1 if the location is not within the bounds of a displayed cell
		}
		if(flag == 0) {
			path = tree.getPathForRow(row);
			System.out.println("path"+path);
		}
		if(path == null) {
			return ;
		}
		FileNode node = (FileNode) path.getLastPathComponent();	//return this path last component
		if(node == null) {
			return ;
		}
		try {
			filepath = node.getFPath();
			filepath2 = node.getFPath();
			System.out.println("node = " + path);
		}	catch (IOException ex) {
			ex.printStackTrace();
		}
		System.out.println("filepath"+filepath);
		
		File [] files = node.getAbFile().listFiles();
		
		for(int indexOfFiles = 0;indexOfFiles<files.length;indexOfFiles++) {
			clickedFile.add(files[indexOfFiles]);
		}
		
		for(int indexOfFiles = 0;indexOfFiles<files.length;indexOfFiles++) {
			String ext = files[indexOfFiles].getName().substring(files[indexOfFiles].getName().lastIndexOf("."),files[indexOfFiles].getName().length()).toLowerCase();
			if(ext.equals(".jpg")||ext.equals(".gif")||ext.equals(".bmp")||ext.equals(".png")) {
				picNumber++;
				picFiles.add(files[indexOfFiles]);
			}
		}
		for(int i=0;i<picFiles.size();i++) {
			lilLabels.add(new JLabel());
			lilPanels.add(new JPanel());
			lilTextFields.add(new JTextField());
			
		}
		int tmp	= picFiles.size();
		System.out.println("img size:"+tmp);
		/*
		 * 多线程放图片 未实现
		 */
		for(File f:picFiles) {
			System.out.println(f.getAbsolutePath());
		}
		
		for(int i=0;i<tmp;i++) {
			ImageIcon ic1 = new ImageIcon(picFiles.get(i).getAbsolutePath());
			lilLabels.get(i).setVisible(true);
			lilLabels.get(i).setIcon(ic1);
			lilTextFields.get(i).setText(picFiles.get(i).getName());
			lilTextFields.get(i).setBorder(null);
			lilTextFields.get(i).setHorizontalAlignment(SwingConstants.CENTER);	//居中对齐
			lilTextFields.get(i).setEditable(false);
			//lilPanels.get(i).setLayout(new BorderLayout(0,0));
			//lilPanels.get(i).add(lilLabels.get(i),BorderLayout.CENTER);
			//lilPanels.get(i).add(lilTextFields.get(i),BorderLayout.PAGE_END);
			picPanel.add(lilPanels.get(i));
		}
		
		
		/*
		for(int i=0;i<tmp;i++) {
			ImageIcon ic1 = new ImageIcon(picFiles.get(i).getAbsolutePath());
			double h1 = ic1.getIconHeight();
			double w1 = ic1.getIconWidth();
			if(h1<77 && w1<100) {
				Image im = ic1.getImage().getScaledInstance((int)w1, (int)h1, Image.SCALE_DEFAULT);	//创建缩放图  hints参数是选择加载的算法 这里用的默认的
				ImageIcon ic2 = new ImageIcon(im);
				lilLabels.get(i).setIcon(ic2);
				ic2.setImageObserver(lilLabels.get(i));
				lilTextFields.get(i).setText(picFiles.get(i).getName());
			}
			else {
				if(h1*180>w1*142) {
					Image im = ic1.getImage().getScaledInstance((int)(w1/(h1/81)), 81, Image.SCALE_DEFAULT);
					ImageIcon ic2 = new ImageIcon(im);
					lilLabels.get(i).setIcon(ic2);
					ic2.setImageObserver(lilLabels.get(i));
					lilTextFields.get(i).setText(picFiles.get(i).getName());
					
				}
				else {
					Image im = ic1.getImage().getScaledInstance(105,(int)(h1/(w1/105)),Image.SCALE_DEFAULT);
					ImageIcon ic2 = new ImageIcon(im);
					lilLabels.get(i).setIcon(ic2);
					ic2.setImageObserver(lilLabels.get(i));
					lilTextFields.get(i).setText(picFiles.get(i).getName());
				}
			}
			picPanel.add(lilPanels.get(i));
			if(picFiles.size()>20) {
				lilPanels.get(i).setBounds(i%5*131,1+(i/5*125),120,110);
			}
			else {
				lilPanels.get(i).setBounds(i%5*135,1+(i/5*125),120,110);
			}
			lilPanels.get(i).setLayout(new BorderLayout(0,0));
			lilPanels.get(i).add(lilLabels.get(i),BorderLayout.CENTER);
			lilPanels.get(i).add(lilTextFields.get(i),BorderLayout.PAGE_END);
			lilTextFields.get(i).setBorder(null);
			lilTextFields.get(i).setHorizontalAlignment(SwingConstants.CENTER);	//居中对齐
			lilTextFields.get(i).setEditable(false);
			if(i==0) {
				lilLabels.get(0).setDisplayedMnemonic(501);
				
			}
			else {
				lilLabels.get(i).setDisplayedMnemonic(i);;
			}
			lilLabels.get(i).setHorizontalAlignment(SwingConstants.CENTER);;
			lilLabels.get(i).setOpaque(true);
			lilLabels.get(i).setBackground(new java.awt.Color(244,244,244));
		}
		
		*/
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		/*
		picQuantity = picFiles.size();	//图片总数
		System.out.println("picQuantity: "+picQuantity);
		if(picQuantity>20) {
			scrollpaneForThumbNail.getVerticalScrollBar().setVisible(true);
			if(picQuantity % 5 ==0) {
				picPanel.setPreferredSize(new Dimension(632,125*(picQuantity/5)));
			}
			else {
				picPanel.setPreferredSize(new Dimension(632,125*(picQuantity/5+1)));
				
			}
			scrollpaneForThumbNail.getVerticalScrollBar().setValue(0);
	
			
		}
		else {
			picPanel.setPreferredSize(new Dimension(632,399));
		}*/
		}	catch (StringIndexOutOfBoundsException ex) {
			picPanel.setPreferredSize(new Dimension(632,399));
		}
		
		
		
	}
}
