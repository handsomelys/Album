package main;

import java.awt.GridBagLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.UIManager;

import dialog.FileRenameDialog;
import dialog.FilesRenameDialog;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

import java.io.File;
import java.util.ArrayList;

import tree.DiskTree;
import topbar.TopBar;
import preview.PreviewPanel;
import viewframe.ViewFrame;
import operation.DirectoryOperationList;
import event.CommandEvent;
import event.CommandListener;
import event.FileEvent;
import event.FileListener;
import util.FileUtils;

public class Main {
    File directory;
    JFrame mainFrame;
    DiskTree tree;
    PreviewPanel previewPanel;
    TopBar topbar;
    DirectoryOperationList dol;
    ArrayList<File> selectedPictures;
    ArrayList<File> heldPictures;
    
    public Main(File directory) {
        // initializing variable
        this.directory = directory.getAbsoluteFile();
        this.mainFrame = new JFrame();
        this.tree = new DiskTree();
        this.previewPanel = new PreviewPanel(directory);
        this.topbar = new TopBar(directory);
        this.dol = new DirectoryOperationList();
        this.selectedPictures = new ArrayList<File>();
        this.heldPictures = new ArrayList<File>();
        GridBagConstraints gbc = new GridBagConstraints();
        MainCommandListener mcl = new MainCommandListener();
        MainFileListener mfl = new MainFileListener();

        final int TREEWIDTH = 20;
        final int TREEHEIGHT = 100000;
        JScrollPane treeScrollPane = new JScrollPane(tree);
        //JScrollPane treeScrollPane = new JScrollPane();
        JScrollPane previewScrollPane = new JScrollPane(previewPanel);
        
        this.updateDirectory(directory);
       
        // initializing the main frame
        this.mainFrame.setTitle(Text.SOFTWARENAME);
        this.mainFrame.setLayout(new GridBagLayout());
        this.mainFrame.setBounds(100, 100, 1200, 600);
        this.mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.mainFrame.setVisible(true);

        // configuring components
        // topbar
        this.configureDirectoryButtons();
        this.configureFileOperationButtons();
        this.dol.push(this.directory);
        // preview panel
        /*
        if(previewPanel.pictures.size()>20) {
        	previewScrollPane.getVerticalScrollBar().setVisible(true);
        	if(previewPanel.pictures.size()%5==0) {
        		previewPanel.setPreferredSize(new Dimension(632,125*(previewPanel.pictures.size()/5)));
        	
        	}else {
        		previewPanel.setPreferredSize(new Dimension(632,125*(previewPanel.pictures.size()/5+1)));
        	}
        	previewScrollPane.getVerticalScrollBar().setValue(0);
        	
        } else if(previewPanel.pictures.size()>=5&&previewPanel.pictures.size()<20){
        	//previewPanel.setPreferredSize(new Dimension(previewPanel.pictures.size()*180,0));
        	previewPanel.setSize(new Dimension(830,725));
        }	else {
        	previewPanel.setPreferredSize(new Dimension(previewPanel.pictures.size()*166,previewPanel.pictures.size()/5*166));
        }
        */
        //FileUtils.picListener_keyboard(this.mainFrame);

        // assigning listener
        this.topbar.addListener(mcl);
        this.previewPanel.addListener(mfl);
        this.previewPanel.addListener(mcl);
        this.tree.addListener(mcl);
        this.mainFrame.addKeyListener(new CtrlListener());

        // deploying the components
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.CENTER;
        // deploying disk tree with scroller on the left side
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 2;
        gbc.weightx = 2;
        gbc.weighty = 10;
        //tree.setPreferredSize(new Dimension(50,1000));
        //tree.setPreferredSize(tree.getMaximumSize());
        treeScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        
        treeScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        treeScrollPane.setPreferredSize(new Dimension(TREEWIDTH,TREEHEIGHT));	
        
        mainFrame.add(treeScrollPane, gbc);
        // deploying top bar on the above
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 6;
        gbc.weighty = 1;
        this.mainFrame.add(this.topbar, gbc);
        // deploying preview panel with scroller on the center
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 6;
        gbc.weighty = 9;
        //previewScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        this.mainFrame.add(previewScrollPane, gbc);
        System.out.println(previewPanel.getSize());
        this.mainFrame.requestFocus();
    }

    /**
     * updating the built in directory of the main program, and also invoke the
     * same method of subcomponents
     * @param directory the directory that wish to update
     */
    public void updateDirectory(File directory) {
        if (directory.isDirectory()) {
            this.directory = directory.getAbsoluteFile();
            this.topbar.setSelectedPicturesCount(this.selectedPictures.size());
            this.topbar.updateDirectory(this.directory);
            this.previewPanel.updateDirectory(this.directory);
        }
    }
    /**
     * invoke the updating method of subcomponents.
     */
    public void updateDirectory() {
        updateDirectory(this.directory);
    }

    /**
     * configuring the directory buttons on topbar, depending on the
     * availability of these functions
     */
    public void configureDirectoryButtons() {
        if (this.topbar != null) {
            File parent = Main.this.directory.getParentFile();
            if(parent == null)
                this.topbar.freezeButton("up");
            else
                this.topbar.unlockButton("up");

            if (this.dol.getPrior() == null)
                this.topbar.freezeButton("back");
            else
                this.topbar.unlockButton("back");
            
            if (this.dol.getNext() == null)
                this.topbar.freezeButton("forward");
            else
                this.topbar.unlockButton("forward");
        }
    }
    /**
     * configuring the file operation buttons on the topbar, depending on how
     * much files selected
     */
    public void configureFileOperationButtons() {
        if (this.selectedPictures == null ||
            this.selectedPictures.size() == 0) {
            this.topbar.freezeButton("open");
            this.topbar.freezeButton("remove");
            this.topbar.freezeButton("slideshow");
        } else {
            this.topbar.unlockButton("open");
            this.topbar.unlockButton("remove");
        }
        if (FileUtils.getPicturesCount(this.directory) == 0)
            this.topbar.freezeButton("slideshow");
        else
            this.topbar.unlockButton("slideshow");
    }

    public class MainCommandListener implements CommandListener {
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
                if (dest != null) {
                    Main.this.updateDirectory(dest);
                    Main.this.dol.push(Main.this.directory);
                    Main.this.configureDirectoryButtons();
                }
            } else if (command[0].equals("open")) {
                for (File f: Main.this.selectedPictures)
                    new ViewFrame(f.getName(), f, 0);
            } else if (command[0].equals("slideshow")) {
                new ViewFrame(
                    Main.this.directory.getName()+" - "+Text.SLIDESHOW,
                    Main.this.directory,
                    1);
            } else if (command[0].equals("remove")) {
                int result = JOptionPane.showConfirmDialog(Main.this.mainFrame,
                    Text.CONFIRMREMOVE, Text.REMOVE, 0);
                if (result == 0) {
                    FileUtils.removeFiles(Main.this.selectedPictures);
                    Main.this.selectedPictures.clear();
                    Main.this.updateDirectory();
                    Main.this.configureFileOperationButtons();
                }
            } else if (command[0].equals("copy")) {   
                if (Main.this.selectedPictures != null &&
                    Main.this.selectedPictures.size() != 0) {
                    Main.this.heldPictures =
                        new ArrayList<File>(Main.this.selectedPictures);
                    }
            } else if (command[0].equals("paste")) {
                FileUtils.copyFiles(Main.this.heldPictures,
                    Main.this.directory.getAbsolutePath());
                Main.this.updateDirectory();
            } else if (command[0].equals("rename")) {
                if (Main.this.selectedPictures.size() == 1)
                    new FileRenameDialog(Main.this.selectedPictures.get(0));
                else if (Main.this.selectedPictures.size() > 1)
                new FilesRenameDialog(Main.this.selectedPictures);
                Main.this.updateDirectory();
            } else if (command[0].equals("refresh")) {
                Main.this.updateDirectory();
            }
        }
    }
    public class MainFileListener implements FileListener {
        @Override
        public void actionPerformed(FileEvent fe) {
            if (fe.getCommand() == "select") {
                Main.this.selectedPictures = fe.getFiles();
                Main.this.topbar.setSelectedPicturesCount(Main.this.selectedPictures.size());
                Main.this.topbar.updateDirectory(Main.this.directory);
                Main.this.configureFileOperationButtons();
            }
        }
    }

    public class CtrlListener implements KeyListener {
        @Override
        public void keyTyped(KeyEvent e) {
            
        }
        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_CONTROL)
                Main.this.previewPanel.isCtrlPressed = true;
        }
        @Override
        public void keyReleased(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_CONTROL)
                Main.this.previewPanel.isCtrlPressed = false;
        }
    }
    public static void main(String[] args) {
        // setting the ui to windows default
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch(Throwable e) {
            e.printStackTrace();
        }
        // File d1 = new File("F:\\test__pic\\123");
        // File f1 = new File(d1, "123.jpg");
        File d2 = new File("image");
        Main m = new Main(d2);
        m.configureFileOperationButtons();
    }
}