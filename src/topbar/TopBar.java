package topbar;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.BorderFactory;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.event.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.HashSet;

import util.FileUtils;
import event.InformationSource;
import event.InformationEvent;
import event.InformationListener;

public class TopBar extends JPanel implements InformationSource {
    private static final long serialVersionUID = 1L;

    public static final String BACKWARD = "←";
    public static final String FORWARD = "→";
    public static final String UPWARD = "↑";
    public static final String UNDO = "撤销";
    public static final String REDO = "重做";
    public static final String DELETE = "删除";
    public static final String SLIDESHOW = "幻灯片";

    // general variable
    public File directory;
    public String directoryName;
    public String directorySize;
    public int totalPictureCount;
    public int selectedPictureCount;
    protected HashSet<InformationListener> listeners;

    // containers
    public JPanel container1;
    public JPanel container2;
    public JPanel container3;

    // directory operation buttons and address bar
    public JButton buttonBackward;
    public JButton buttonForward;
    public JButton buttonUpward;
    public JLabel addressBar;

    // informations
    public JLabel directoryTitle;
    public JLabel directoryStats;

    // operation buttons
    public JButton undo;
    public JButton redo;
    public JButton delete;
    public JButton slideShow;

    /**
     * initializing topbar with a specify directory
     * 
     * @param directory a directory as form of java.io.File
     */
    public TopBar(File directory) {
        this.listeners = new HashSet<InformationListener>();
        GridBagLayout gbl = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        this.setBorder(BorderFactory.createEtchedBorder());
        this.setLayout(gbl);

        // initializing containers
        this.container1 = new JPanel();
        this.container1.setLayout(gbl);
        this.container2 = new JPanel();
        this.container2.setLayout(gbl);
        this.container2.setBorder(BorderFactory.createEtchedBorder());
        this.container3 = new JPanel();
        this.container3.setLayout(gbl);

        // initializing directory buttons
        this.buttonBackward = new JButton(TopBar.BACKWARD);
        this.buttonForward = new JButton(TopBar.FORWARD);
        this.buttonUpward = new JButton(TopBar.UPWARD);
        DirectoryButtonListener dbal = new DirectoryButtonListener();
        this.buttonBackward.addActionListener(dbal);
        this.buttonForward.addActionListener(dbal);
        this.buttonUpward.addActionListener(dbal);

        // initializing address bar
        this.addressBar = new JLabel("address");
        this.addressBar.setBorder(BorderFactory.createEtchedBorder());

        // initializing informations
        this.directoryTitle = new JLabel("directory name");
        this.directoryStats = new JLabel("directory stats");

        // initializing operation buttons
        this.undo = new JButton(TopBar.UNDO);
        this.redo = new JButton(TopBar.REDO);
        this.delete = new JButton(TopBar.DELETE);
        this.slideShow = new JButton(TopBar.SLIDESHOW);

        updateDirectory(directory);

        // put directory button and address bar on the container1
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        this.container1.add(this.buttonBackward, gbc);
        gbc.gridx = 1;
        this.container1.add(this.buttonForward, gbc);
        gbc.gridx = 2;
        this.container1.add(this.buttonUpward, gbc);

        gbc.gridx = 3;
        gbc.weightx = 10;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        this.container1.add(this.addressBar, gbc);

        // put informations on the container2
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        this.container2.add(this.directoryTitle, gbc);
        gbc.gridy = 1;
        this.container2.add(this.directoryStats, gbc);

        // put the operation buttons on the container3
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        this.container3.add(this.undo, gbc);
        gbc.gridx = 1;
        this.container3.add(this.redo, gbc);
        gbc.gridx = 2;
        this.container3.add(this.delete, gbc);
        gbc.gridx = 3;
        this.container3.add(this.slideShow, gbc);

        // put containers on the topbar
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.weightx = 2;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        this.add(container1, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.anchor = GridBagConstraints.EAST;
        this.add(container2, gbc);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        this.add(container3, gbc);
    }

    public TopBar(String directory) {
        this(new File(directory));
    }

    public void updateDirectory(File directory) {
        // update the built in directory variable
        this.directory = directory;
        this.directoryName = this.directory.getName();
        this.directorySize = FileUtils.sizeToString(
            FileUtils.getPicturesSize(directory));
        this.totalPictureCount = FileUtils.getPicturesCount(directory);
        this.selectedPictureCount = 0;
        // update address bar
        this.addressBar.setText(this.directory.getAbsolutePath());
        // update informations
        this.directoryTitle.setText(this.directoryName);
        this.directoryStats.setText(String.format("%d张图片(%s) - 选中%d张图片",
            this.totalPictureCount, this.directorySize,
            this.selectedPictureCount));
    }
    public void freezeDirectoryButton(String type) {
        if (type.equals("back"))
            this.buttonBackward.setEnabled(false);
        else if (type.equals("forward"))
            this.buttonForward.setEnabled(false);
        else if (type.equals("up"))
            this.buttonUpward.setEnabled(false);
    }
    public void unlockDirectoryButton(String type) {
        if (type.equals("back"))
            this.buttonBackward.setEnabled(true);
        else if (type.equals("forward"))
            this.buttonForward.setEnabled(true);
        else if (type.equals("up"))
            this.buttonUpward.setEnabled(true);
    }

    public class DirectoryButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();
            if (command.equals(TopBar.BACKWARD)) {
                TopBar.this.notifyAll(
                    new InformationEvent(TopBar.this, "back"));
            } else if (command.equals(TopBar.FORWARD)) {
                TopBar.this.notifyAll(
                    new InformationEvent(TopBar.this, "forward"));
            } else if (command.equals(TopBar.UPWARD)) {
                TopBar.this.notifyAll(
                    new InformationEvent(TopBar.this, "up"));
            }
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