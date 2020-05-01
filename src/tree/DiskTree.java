package tree;

import java.io.File;
import java.util.ArrayList;

import javax.swing.JTree;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import event.CommandEvent;
import event.CommandListener;
import event.CommandSource;

public class DiskTree extends JTree implements CommandSource {
    private static final long serialVersionUID = 1L;

    private ArrayList<CommandListener> listeners;

    public DiskTree() {
        this.listeners = new ArrayList<CommandListener>();
        File[] roots = (new PFileSysView()).getRoots();
        FileNode node = null;
        node = new FileNode(roots[0]);
        node.explore();
        this.setModel(new DefaultTreeModel(node));
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
            DiskTree.this.notifyAll(new CommandEvent(DiskTree.this,
                "switch", node.getString()));
        }
        @Override
        public void treeCollapsed(TreeExpansionEvent event) {

        }
    }

    @Override
    public void addListener(CommandListener cl) {
        this.listeners.add(cl);
    }
    @Override
    public void removeListener(CommandListener cl) {
        this.listeners.remove(cl);
    }
    @Override
    public void notifyAll(CommandEvent ce) {
        for (CommandListener cl: listeners)
            cl.actionPerformed(ce);
    }
}