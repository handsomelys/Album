package tree;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.Icon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.filechooser.FileSystemView;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreePath;
public class DiskTree extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// TreeCellRenderer �ı�JTree����ʾ��ʽ�Ľӿ�
	private final class FileRenderer implements TreeCellRenderer{


		
		@Override 
		public Component getTreeCellRendererComponent(JTree tree,Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
			
			//ת��valueΪ�ڵ����
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
			Object userObject = node.getUserObject();
			if(!(userObject instanceof File)) {
				return new JLabel(userObject.toString());	//���ش�����
			}
			File folder = (File)userObject;
			//FileSystemView.getFileSystemView() ��ȡĿ¼
			FileSystemView view = FileSystemView.getFileSystemView();
			Icon icon = view.getSystemIcon(folder);	//���ϵͳ��ͼ��
			//JLabel.LEADING ������������
			JLabel label = new JLabel(""+folder,icon,JLabel.LEADING);
			label.setBackground(Color.CYAN);
			if(selected) {
				label.setOpaque(true);
			} else {
				label.setOpaque(false);
			}
			return label;
		}
	}
	
	private JPanel contentpane;
	private JTree tree;
	private DefaultMutableTreeNode rootNode;
	
	public DiskTree() {
		//����Ŀ¼��
		/*addWindowListener(new WindowAdapter() {
			@Override
			//��ȡ������еĴ����б�
			//���弤���¼�������
			public void windowActivated(WindowEvent e) {
				do_WindowActivated(e);
			}
		});*/
		//��������
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//setBounds(100,100,450,300);
		/*
		contentpane = new JPanel();
		contentpane.setBorder(new EmptyBorder(5,5,5,5));
		contentpane.setLayout(new BorderLayout(0,0));
		*/
		this.setBorder(new EmptyBorder(5,5,5,5));
		this.setLayout(new BorderLayout(0,0));
		
		//setContentPane(contentpane);
		//������ ��ģ����Ҫ�ڹ�����������ʾ��ȫ
		JScrollPane scrollPane = new JScrollPane();
		this.add(scrollPane,BorderLayout.CENTER);
		//JTreeʵ��
		tree = new JTree();
		//��Ӽ����� ���ѡ���¼�		
		tree.addTreeSelectionListener(new TreeSelectionListener() {
			public void valueChanged(TreeSelectionEvent e) {
				do_TreeValue_Changed(e);
			}
		});
		
		tree.setCellRenderer(new FileRenderer()); //��Ⱦ
		scrollPane.setViewportView(tree);
		rootNode = new DefaultMutableTreeNode("�˵���");
		DefaultTreeModel model = new DefaultTreeModel(rootNode);
		tree.setModel(model);
	}
	
	public void do_WindowActivated(WindowEvent e) {
		File[] disks = File.listRoots();//��ȡ�����б� ��Ŀ¼�̷�
		for(File file:disks) {
			//ѭ���������ڵ�
			DefaultMutableTreeNode node = new DefaultMutableTreeNode(file);
			rootNode.add(node);//��ӽڵ㵽���ؼ��ĸ��ڵ�
		}
		tree.expandPath(new TreePath(rootNode));//չ�����ڵ�
	}
	public JTree getTree() {
		return tree;
	}
	//�ı�ڵ�ѡ��ʱִ�еķ���
	public void do_TreeValue_Changed(TreeSelectionEvent e) {
		TreePath path = e.getPath();//��ȡ��ѡ��·��
		//��ȡѡ��·���еĽڵ�
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) path.getLastPathComponent();//�������ѡ�еĽڵ�
		Object userObject = node.getUserObject();//��ȡ����е��û�����
		if(!(userObject instanceof File)) {
			return ;
		}
		File folder = (File) userObject;
		if(!folder.isDirectory()) {
			return ;//���˷��ļ���
		}
		File[] files = folder.listFiles();//��ȡ�ļ����е��ļ��б�
		for(File file:files) {
			node.add(new DefaultMutableTreeNode(file));
		}
	}
}
