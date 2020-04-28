package main;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.UIManager;
import javax.swing.JFrame;
import javax.swing.JButton;
import java.io.File;

import preview.*;
import tree.*;
import topbar.*;
import event.*;
import operation.DirectoryOperationList;

public class Main {
    File directory;
    JFrame mainFrame;
    DiskTree disktree;
    PicPreviewDialog previewFrame;
    JButton previewButton;
    TopBar topbar;
    DirectoryOperationList dol;

    public Main(File directory) {
        // setting the ui to windows default
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch(Throwable e) {
            e.printStackTrace();
        }

        // initializing variable
        this.directory = directory;
        this.mainFrame = new JFrame();
        this.disktree = new DiskTree();
        this.previewFrame = new PicPreviewDialog();
        this.previewButton = new JButton("preview");
        this.topbar = new TopBar(directory);
        this.dol = new DirectoryOperationList();

        // configuring top bar
        this.topbar.freezeDirectoryButton("init");

        // assigning listener
        mainFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowActivated(WindowEvent e) {
                disktree.do_WindowActivated(e);
            }
        });
        previewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                previewFrame.setVisible(true);
            }
        });
        topbar.addListener(new InformationListener() {
            @Override
            public void actionPerformed(InformationEvent ie) {
                String command = ie.getCommand();
                if (command.equals("back")) {
                    File f = Main.this.dol.prior();
                    Main.this.updateDirectory(f);
                } else if (command.equals("forward")) {
                    File f = Main.this.dol.next();
                    Main.this.updateDirectory(f);
                } else if (command.equals("up")) {
                    File parent = Main.this.directory.getParentFile();
                    if(parent == null)
                        Main.this.topbar.freezeDirectoryButton("root");
                    else {
                        if (!Main.this.dol.push(Main.this.directory))
                            Main.this.topbar.freezeDirectoryButton("nofuture");
                        else
                            Main.this.topbar.freezeDirectoryButton("unlock");
                        Main.this.updateDirectory(parent);
                    }
                }
            }
        });
        
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
        mainFrame.add(disktree, gbc);
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
    public void updateDirectory(File directory) {
        this.dol.push(this.directory);
        this.directory = directory;
        this.topbar.updateDirectory(directory);
    }
    public void beforeDirectory(File directory) {
        this.directory = directory;
        this.topbar.updateDirectory(directory);
    }
    public static void main(String[] args) {
        File directory = new File("D:\\picture\\sticker\\猫");
        new Main(directory);
    }
}
