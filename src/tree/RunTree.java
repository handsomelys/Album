package tree;

import java.io.File;

import javax.swing.JTree;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

//import tree.PFileSysView;

public class RunTree {
	 public  static void Runtree(final JTree jTree1) {
			File[] roots = (new PFileSysView()).getRoots();
			FileNode nod = null;
			nod = new FileNode(roots[0]);
			nod.explore();
			jTree1.setModel(new DefaultTreeModel(nod));
			jTree1.addTreeExpansionListener(new TreeExpansionListener() {

				public void treeExpanded(TreeExpansionEvent event) {
					TreePath path = event.getPath();
					FileNode node = (FileNode) path.getLastPathComponent();
					if (!node.isExplored()) {
						DefaultTreeModel model = ((DefaultTreeModel) jTree1.getModel());
						node.explore();
						model.nodeStructureChanged(node);
					}
				}

				@Override
				public void treeCollapsed(TreeExpansionEvent event) {
					
				}
			});

	    }}

