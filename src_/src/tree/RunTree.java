package tree;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.JTree;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import thumbnail.*;
//import tree.PFileSysView;

public class RunTree {
	static ThumbNail thumbnail = new ThumbNail();
	 public  static void Runtree(final JTree jtree) {
			File[] roots = (new PFileSysView()).getRoots();
			FileNode nod = null;
			nod = new FileNode(roots[0]);
			nod.explore();
			jtree.setModel(new DefaultTreeModel(nod));
			jtree.addTreeExpansionListener(new TreeExpansionListener() {

				public void treeExpanded(TreeExpansionEvent event) {
					TreePath path = event.getPath();
					FileNode node = (FileNode) path.getLastPathComponent();
					if (!node.isExplored()) {
						DefaultTreeModel model = ((DefaultTreeModel) jtree.getModel());
						node.explore();
						model.nodeStructureChanged(node);
					}
				}

				@Override
				public void treeCollapsed(TreeExpansionEvent event) {
					
				}
			});
			/*
			jtree.addMouseListener(new MouseAdapter() {
				public void mousePressed(MouseEvent e) {

					thumbnail.ShowThumbNail(e, 0, new TreePath(0));
					//E = e;

				}
			
				
			});*/

	    }
	 }

