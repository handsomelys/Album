package main;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.UIManager;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTree;
import java.io.File;
import java.util.ArrayList;

import tree.*;
import util.FileUtils;
import viewframe.ViewFrame;
import topbar.*;
import event.*;
import preview.PopupMenu;
import operation.DirectoryOperationList;

public class Main {
    File directory;
    JFrame mainFrame;
    JTree jtree;
    JPanel previewPanel;
    PopupMenu popupMenu;
    TopBar topbar;
    DirectoryOperationList dol;
    ArrayList<File> selectedPictures;

    public Main(File directory) {
        // initializing variable
        this.directory = directory;
        this.mainFrame = new JFrame();
        this.jtree = new JTree();
        this.previewPanel = new JPanel();
        this.popupMenu = new PopupMenu();
        this.topbar = new TopBar(directory);
        this.dol = new DirectoryOperationList();
        this.selectedPictures = new ArrayList<File>();
        RunTree.Runtree(jtree);

        // configuring top bar
        this.topbar.freezeDirectoryButton("back");
        this.topbar.freezeDirectoryButton("forward");
        this.dol.push(this.directory);

        // assigning listener
        this.topbar.addListener(new TopBarListener());
        this.previewPanel.addMouseListener(new PopupMenuListener());
        
        // initializing the main frame
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setBounds(200,100,800,600);
        mainFrame.setLayout(new GridBagLayout());
        mainFrame.setVisible(true);

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
        mainFrame.add(jtree, gbc);
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
        mainFrame.add(previewPanel, gbc);
    }
    public void updateDirectory(File directory) {
        this.directory = directory;
        this.topbar.updateDirectory(directory);
    }
    public void configureDirectoryButtons() {
        File parent = Main.this.directory.getParentFile();
        if(parent == null)
            Main.this.topbar.freezeDirectoryButton("up");
        
        if (this.dol.getPrior() == null)
            this.topbar.freezeDirectoryButton("back");
        else
            this.topbar.unlockDirectoryButton("back");
        
        if (this.dol.getNext() == null)
            this.topbar.freezeDirectoryButton("forward");
        else
            this.topbar.unlockDirectoryButton("forward");
    }
    public class TopBarListener implements InformationListener {
        @Override
        public void actionPerformed(InformationEvent ie) {
            String command = ie.getCommand();
            if (command.equals("back")) {
                File prior = Main.this.dol.getPrior();
                if (prior != null)
                    Main.this.updateDirectory(prior);
                Main.this.dol.rewind();
                configureDirectoryButtons();
            } else if (command.equals("forward")) {
                File next = Main.this.dol.getNext();
                if (next != null)
                    Main.this.updateDirectory(next);
                Main.this.dol.push(next);
                configureDirectoryButtons();
            } else if (command.equals("up")) {
                File parent = Main.this.directory.getParentFile();
                if (parent != null) {
                    Main.this.updateDirectory(parent);
                    Main.this.dol.push(Main.this.directory);
                    configureDirectoryButtons();
                }
            } else if (command.equals("open")) {
                for (File f: Main.this.selectedPictures)
                    new ViewFrame(f.getName(), f);
            } else if (command.equals("remove")) {
                for (File f: Main.this.selectedPictures)
                    FileUtils.removeFile(f);
            }
        }
    }
    public class PopupMenuListener extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent e) {
            showPopupMenu(e);
        }
        @Override
        public void mouseReleased(MouseEvent e) {
            showPopupMenu(e);
        }
        private void showPopupMenu(MouseEvent e) {
            if(e.isPopupTrigger()){
                //如果当前事件与鼠标事件相关，则弹出菜单
                Main.this.popupMenu.show(e.getComponent(),e.getX(),e.getY());
            }
        }
    }
    public static void main(String[] args) {
        // setting the ui to windows default
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch(Throwable e) {
            e.printStackTrace();
        }
        File d = new File("C:\\Users\\lsn\\Desktop\\New folder\\test\\");
        File f1 = new File(d, "5d31db8dc9cb3.jpg");
        //File f2 = new File(d, "The Starry Night.png");
        Main m = new Main(d);
        m.selectedPictures.add(f1);
    }
}
