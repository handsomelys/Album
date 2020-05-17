package tree;

import java.awt.Dimension;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import java.io.File;
import java.util.ArrayList;

import event.CommandEvent;
import event.CommandListener;
import event.CommandSource;

public class DiskTree extends JTree implements CommandSource {
    private static final long serialVersionUID = 1L;

    public static final int TREEWIDTH = 20;
    public static final int TREEHEIGHT = 100000;

    private ArrayList<CommandListener> listeners;

    public DiskTree() {
        this.listeners = new ArrayList<CommandListener>();

        File[] roots = (new PFileSysView()).getRoots();
        FileNode node = null;
        node = new FileNode(roots[0]);
        node.explore();
        this.setModel(new DefaultTreeModel(node));

        this.addTreeExpansionListener(new DiskTreeExpansionListener());
        this.addTreeSelectionListener(new DiskTreeSelectionListener());
    }

    public JScrollPane getScrollPane() {
        JScrollPane scrollPane = new JScrollPane(this);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setPreferredSize(new Dimension(DiskTree.TREEWIDTH, DiskTree.TREEHEIGHT));	
        return scrollPane;
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
            // DiskTree.this.setPreferredSize(getPreferredScrollableViewportSize());
        }
        @Override
        public void treeCollapsed(TreeExpansionEvent event) {
        }
    }

    public class DiskTreeSelectionListener implements TreeSelectionListener {
        @Override
        public void valueChanged(TreeSelectionEvent e) {
            TreePath path = e.getPath();
            FileNode node = (FileNode) path.getLastPathComponent();
            DiskTree.this.notifyAll(new CommandEvent(DiskTree.this, "switch", node.getString()));
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