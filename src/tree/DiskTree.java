package tree;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.Icon;
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
	private static final long serialVersionUID = 1L;
	// TreeCellRenderer 改变JTree的显示方式的接口
	private final class FileRenderer implements TreeCellRenderer {
		@Override 
		public Component getTreeCellRendererComponent(JTree tree,Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
			
			//转换value为节点对象
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
			Object userObject = node.getUserObject();
			if(!(userObject instanceof File)) {
				return new JLabel(userObject.toString());	//返回磁盘名
			}
			File folder = (File)userObject;
			//FileSystemView.getFileSystemView() 获取目录
			FileSystemView view = FileSystemView.getFileSystemView();
			Icon icon = view.getSystemIcon(folder);	//获得系统的图标
			//JLabel.LEADING 从右往左读组件
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
	
	//private JPanel contentpane;
	private JTree tree;
	private DefaultMutableTreeNode rootNode;
	
	public DiskTree() {
		//创建目录树
		/*addWindowListener(new WindowAdapter() {
			@Override
			//获取计算机中的磁盘列表
			//窗体激活事件监听器
			public void windowActivated(WindowEvent e) {
				do_WindowActivated(e);
			}
		});*/
		//设置属性
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
		//滚动条 树模型需要在滚动条才能显示完全
		JScrollPane scrollPane = new JScrollPane();
		this.add(scrollPane,BorderLayout.CENTER);
		//JTree实例
		tree = new JTree();
		//添加监听器 添加选中事件		
		tree.addTreeSelectionListener(new TreeSelectionListener() {
			public void valueChanged(TreeSelectionEvent e) {
				do_TreeValue_Changed(e);
			}
		});
		
		tree.setCellRenderer(new FileRenderer()); //渲染
		scrollPane.setViewportView(tree);
		rootNode = new DefaultMutableTreeNode("此电脑");
		DefaultTreeModel model = new DefaultTreeModel(rootNode);
		tree.setModel(model);
	}
	
	public void do_WindowActivated(WindowEvent e) {
		File[] disks = File.listRoots();//获取磁盘列表 根目录盘符
		for(File file:disks) {
			//循环创建树节点
			DefaultMutableTreeNode node = new DefaultMutableTreeNode(file);
			rootNode.add(node);//添加节点到树控件的根节点
		}
		tree.expandPath(new TreePath(rootNode));//展开根节点
	}
	public JTree getTree() {
		return tree;
	}
	//改变节点选项时执行的方法
	public void do_TreeValue_Changed(TreeSelectionEvent e) {
		TreePath path = e.getPath();//获取树选择路径
		//获取选择路径中的节点
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) path.getLastPathComponent();//返回最后选中的节点
		Object userObject = node.getUserObject();//获取结点中的用户对象
		if(!(userObject instanceof File)) {
			return ;
		}
		File folder = (File) userObject;
		if(!folder.isDirectory()) {
			return ;//过滤非文件夹
		}
		File[] files = folder.listFiles();//获取文件夹中的文件列表
		for(File file:files) {
			node.add(new DefaultMutableTreeNode(file));
		}
	}
}
