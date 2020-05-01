package main;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.UIManager;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.io.File;
import java.util.ArrayList;

import tree.DiskTree;
import topbar.TopBar;
import preview.PopupMenu;
import viewframe.ViewFrame;
import operation.DirectoryOperationList;
import event.CommandEvent;
import event.CommandListener;
import util.FileUtils;

public class Main {
    File directory;
    JFrame mainFrame;
    DiskTree tree;
    JPanel previewPanel;
    PopupMenu popupMenu;
    TopBar topbar;
    DirectoryOperationList dol;
    ArrayList<File> selectedPictures;
    ArrayList<File> heldPictures;

    public Main(File directory) {
        // initializing variable
        this.directory = directory;
        this.mainFrame = new JFrame();
        this.tree = new DiskTree();
        this.previewPanel = new JPanel();
        this.popupMenu = new PopupMenu();
        this.topbar = new TopBar(directory);
        this.dol = new DirectoryOperationList();
        this.selectedPictures = new ArrayList<File>();
        MainListener ml = new MainListener();

        // configuring top bar
        this.configureDirectoryButtons();
        this.configureFileOperationButtons();
        this.dol.push(this.directory);

        // assigning listener
        this.topbar.addListener(ml);
        this.previewPanel.addMouseListener(new PopupMenuOpenListener());
        this.tree.addListener(ml);
        this.popupMenu.addListener(ml);
        
        // initializing the main frame
        mainFrame.setTitle(Text.SOFTWARENAME);
        mainFrame.setLayout(new GridBagLayout());
        mainFrame.setBounds(100, 100, 800, 600);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
        gbc.weightx = 4;
        gbc.weighty = 10;
        mainFrame.add(tree, gbc);
        // deploying top bar on the above of the right
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 6;
        gbc.weighty = 1;
        mainFrame.add(topbar, gbc);
        // TODO: deploying preview panel on the center of the right
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 6;
        gbc.weighty = 9;
        mainFrame.add(previewPanel, gbc);
    }
    public void updateDirectory(File directory) {
        if (directory.isDirectory()) {
            this.directory = directory;
            this.topbar.setSelectedPicturesCount(this.selectedPictures.size());
            this.topbar.updateDirectory(directory);
        }
    }
    public void configureDirectoryButtons() {
        File parent = Main.this.directory.getParentFile();
        if(parent == null)
            Main.this.topbar.freezeButton("up");
        
        if (this.dol.getPrior() == null)
            this.topbar.freezeButton("back");
        else
            this.topbar.unlockButton("back");
        
        if (this.dol.getNext() == null)
            this.topbar.freezeButton("forward");
        else
            this.topbar.unlockButton("forward");
    }
    public void configureFileOperationButtons() {
        if (this.selectedPictures == null ||
            this.selectedPictures.size() == 0) {
            this.topbar.freezeButton("open");
            this.topbar.freezeButton("remove");
            this.topbar.freezeButton("slideshow");
        } else {
            this.topbar.unlockButton("open");
            this.topbar.unlockButton("remove");
            if (this.selectedPictures.size() > 1)
                this.topbar.freezeButton("slideshow");
            else
                this.topbar.unlockButton("slideshow");
        }
    }

    public class MainListener implements CommandListener {
        @Override
        public void actionPerformed(CommandEvent ce) {
            String[] command = ce.getCommand();
            if (command[0].equals("back")) {
                File prior = Main.this.dol.getPrior();
                if (prior != null)
                    Main.this.updateDirectory(prior);
                Main.this.dol.rewind();
                Main.this.configureDirectoryButtons();
            } else if (command[0].equals("forward")) {
                File next = Main.this.dol.getNext();
                if (next != null)
                    Main.this.updateDirectory(next);
                Main.this.dol.push(next);
                Main.this.configureDirectoryButtons();
            } else if (command[0].equals("parent")) {
                File parent = Main.this.directory.getParentFile();
                if (parent != null) {
                    Main.this.updateDirectory(parent);
                    Main.this.dol.push(Main.this.directory);
                    Main.this.configureDirectoryButtons();
                }
            } else if (command[0].equals("switch")) {
                File dest = new File(command[1]);
                Main.this.updateDirectory(dest);
                Main.this.configureFileOperationButtons();
            } else if (command[0].equals("open")) {
                for (File f: Main.this.selectedPictures)
                    new ViewFrame(f.getName(), f);
            } else if (command[0].equals("slideshow")) {
                // TODO: add slideshow
            } else if (command[0].equals("remove")) {
                int result = JOptionPane.showConfirmDialog(Main.this.mainFrame,
                    Text.CONFIRMREMOVE, Text.CONFIRMREMOVETITLE, 0);
                if (result == 0)
                    for (File f: Main.this.selectedPictures)
                        FileUtils.removeFile(f);
            } else if (command[0].equals("copy")) {   
                if (Main.this.selectedPictures != null &&
                    Main.this.selectedPictures.size() != 0) {
                    Main.this.heldPictures =
                        new ArrayList<File>(Main.this.selectedPictures);
                    }
            } else if (command[0].equals("paste")) {
                FileUtils.copyFiles(Main.this.heldPictures,
                    Main.this.directory.getAbsolutePath());
            } else if (command[0].equals("rename")) {
                
            }
        }
    }
    public class PopupMenuOpenListener extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent e) {
            showPopupMenu(e);
        }
        @Override
        public void mouseReleased(MouseEvent e) {
            showPopupMenu(e);
        }
        private void showPopupMenu(MouseEvent e) {
            if (e.isPopupTrigger())
                Main.this.popupMenu.show(e.getComponent(),e.getX(),e.getY());
        }
    }
    public static void main(String[] args) {
        // setting the ui to windows default
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch(Throwable e) {
            e.printStackTrace();
        }
        File d = new File("image");
        File f1 = new File(d, "gugugu.jpg");
        Main m = new Main(d);
        m.selectedPictures.add(f1);
        m.updateDirectory(m.directory);
        m.configureFileOperationButtons();
        new ViewFrame(f1.getName(), f1);
    }
}