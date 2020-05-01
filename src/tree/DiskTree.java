package tree;

import java.io.File;
import java.util.ArrayList;

import javax.swing.JTree;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import event.InformationEvent;
import event.InformationListener;
import event.InformationSource;

public class DiskTree extends JTree implements InformationSource {
    private static final long serialVersionUID = 1L;

    private ArrayList<InformationListener> listeners;

    public DiskTree() {
        this.listeners = new ArrayList<InformationListener>();
        File[] roots = (new PFileSysView()).getRoots();
        FileNode nod = null;
        nod = new FileNode(roots[0]);
        nod.explore();
        this.setModel(new DefaultTreeModel(nod));
        this.addTreeExpansionListener(new DiskTreeExpansionListener());
    }

    public class DiskTreeExpansionListener implements TreeExpansionListener {
        @Override
        public void treeExpanded(TreeExpansionEvent event) {
            TreePath path = event.getPath();
            FileNode node = (FileNode) path.getLastPathComponent();
            if (!node.isExplored()) {
                DefaultTreeModel model = ((DefaultTreeModel) DiskTree.this.getModel());
                node.explore();
                model.nodeStructureChanged(node);
            }
            DiskTree.this.notifyAll(new InformationEvent(DiskTree.this,
                "switch", node.getString()));
        }
        @Override
        public void treeCollapsed(TreeExpansionEvent event) {

        }
    }

    @Override
    public void addListener(InformationListener e) {
        this.listeners.add(e);
    }
    @Override
    public void removeListener(InformationListener e) {
        this.listeners.remove(e);
    }
    @Override
    public void notifyAll(InformationEvent ie) {
        for (InformationListener il: listeners)
            il.actionPerformed(ie);
    }
}