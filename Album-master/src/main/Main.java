package main;

import java.awt.event.WindowAdapter;

import java.awt.event.WindowEvent;
import java.io.File;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

import javax.swing.UIManager;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.JFrame;
import javax.swing.JTree;
import javax.swing.JButton;

import preview.*;
import tree.*;
import topbar.*;
import viewpanel.*;
import viewframe.*;
public class Main{
    public static void main(String[] args) {
        // setting the ui to windows default
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch(Throwable e) {
            e.printStackTrace();
        }

        // initializing variable
        JFrame mainFrame = new JFrame();
        PicPreviewDialog previewFrame = new PicPreviewDialog();
        DiskTree disktree = new DiskTree();
        TopBar topbar = new TopBar();
        JButton previewButton = new JButton("preview");
        JTree jtree = new JTree();
        // assigning listener
        mainFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowActivated(WindowEvent e) {
                disktree.do_WindowActivated(e);
            }
        });
        previewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                previewFrame.setVisible(true);
                previewFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
            }
        });
        
        // initializing the main frame
        mainFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        mainFrame.setBounds(200,100,800,600);
        mainFrame.setLayout(new GridBagLayout());
        mainFrame.setVisible(true);
        RunTree.Runtree(jtree);
        
        // deploying the component
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.CENTER;
        // deploying disk tree on the left side
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 2;
        gbc.weightx = 0.5;
        gbc.weighty = 1.0;
        mainFrame.add(jtree,gbc);
        //mainFrame.add(disktree, gbc);
        // deploying top bar on the above of the right
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 0.5;
        gbc.weighty = 0.1;
        mainFrame.add(topbar, gbc);
        // TODO: deploying preview panel on the center of the right
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 0.5;
        gbc.weighty = 0.9;
        mainFrame.add(previewButton, gbc);
        
        
        
    }
    /*
    public  static void RunTree(final JTree jTree1) {
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

			public void treeCollapsed1(TreeExpansionEvent event) {
			}

			public void treeExpanded1(TreeExpansionEvent event) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void treeCollapsed(TreeExpansionEvent event) {
				// TODO Auto-generated method stub
				
			}
		});

    }*/
    }
