package topbar;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.BorderFactory;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.io.File;
import java.util.HashSet;

import util.FileUtils;
import event.CommandSource;
import event.CommandEvent;
import event.CommandListener;
import main.Text;

public class TopBar extends JPanel implements CommandSource {
    private static final long serialVersionUID = 1L;

    // general variable
    public File directory;
    public String directoryName;
    public String directorySize;
    public int totalPictureCount;
    public int selectedPicturesCount;
    protected HashSet<CommandListener> listeners;

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
    public JButton buttonOpen;
    public JButton buttonRemove;
    public JButton buttonSlideShow;

    /**
     * initializing topbar with a specify directory
     * 
     * @param directory a directory as form of java.io.File
     */
    public TopBar(File directory) {
        // initializing variable
        this.listeners = new HashSet<CommandListener>();
        this.totalPictureCount = 0;
        this.selectedPicturesCount = 0;
        ButtonListener bl = new ButtonListener();
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
        this.buttonBackward = new JButton(Text.BACKWARD);
        this.buttonForward = new JButton(Text.FORWARD);
        this.buttonUpward = new JButton(Text.UPWARD);
        this.buttonBackward.addActionListener(bl);
        this.buttonForward.addActionListener(bl);
        this.buttonUpward.addActionListener(bl);

        // initializing address bar
        this.addressBar = new JLabel("address");
        this.addressBar.setBorder(BorderFactory.createEtchedBorder());

        // initializing informations
        this.directoryTitle = new JLabel("directory name");
        this.directoryStats = new JLabel("directory stats");

        // initializing operation buttons
        this.buttonOpen = new JButton(Text.OPEN);
        this.buttonRemove = new JButton(Text.REMOVE);
        this.buttonSlideShow = new JButton(Text.SLIDESHOW);
        this.buttonOpen.addActionListener(bl);
        this.buttonRemove.addActionListener(bl);
        this.buttonSlideShow.addActionListener(bl);

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
        this.container3.add(this.buttonOpen, gbc);
        gbc.gridx = 1;
        this.container3.add(this.buttonRemove, gbc);
        gbc.gridx = 2;
        this.container3.add(this.buttonSlideShow, gbc);

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

    public void setSelectedPicturesCount(int count) {
        this.selectedPicturesCount = count;
    }

    public void updateDirectory(File directory) {
        // update the built in directory variable
        this.directory = directory.getAbsoluteFile();
        this.directoryName = this.directory.getName();
        this.directorySize = FileUtils.sizeToString(
            FileUtils.getPicturesSize(directory));
        this.totalPictureCount = FileUtils.getPicturesCount(directory);
        // update address bar
        this.addressBar.setText(this.directory.getAbsolutePath());
        // update informations
        this.directoryTitle.setText(this.directoryName);
        this.directoryStats.setText(String.format("%d张图片(%s) - 选中%d张图片",
            this.totalPictureCount, this.directorySize,
            this.selectedPicturesCount));
    }

    public void freezeButton(String type) {
        if (type.equals("back"))
            this.buttonBackward.setEnabled(false);
        else if (type.equals("forward"))
            this.buttonForward.setEnabled(false);
        else if (type.equals("up"))
            this.buttonUpward.setEnabled(false);
        else if (type.equals("open"))
            this.buttonOpen.setEnabled(false);
        else if (type.equals("remove"))
            this.buttonRemove.setEnabled(false);
        else if (type.equals("slideshow"))
            this.buttonSlideShow.setEnabled(false);
    }
    public void unlockButton(String type) {
        if (type.equals("back"))
            this.buttonBackward.setEnabled(true);
        else if (type.equals("forward"))
            this.buttonForward.setEnabled(true);
        else if (type.equals("up"))
            this.buttonUpward.setEnabled(true);
        else if (type.equals("open"))
            this.buttonOpen.setEnabled(true);
        else if (type.equals("remove"))
            this.buttonRemove.setEnabled(true);
        else if (type.equals("slideshow"))
            this.buttonSlideShow.setEnabled(true);
    }

    public class ButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();
            if (command.equals(Text.BACKWARD)) {
                TopBar.this.notifyAll(
                    new CommandEvent(TopBar.this, "back"));
            } else if (command.equals(Text.FORWARD)) {
                TopBar.this.notifyAll(
                    new CommandEvent(TopBar.this, "forward"));
            } else if (command.equals(Text.UPWARD)) {
                TopBar.this.notifyAll(
                    new CommandEvent(TopBar.this, "parent"));
            } else if (command.equals(Text.OPEN)) {
                TopBar.this.notifyAll(
                    new CommandEvent(TopBar.this, "open"));
            } else if (command.equals(Text.REMOVE)) {
                TopBar.this.notifyAll(
                    new CommandEvent(TopBar.this, "remove"));
            } else if (command.equals(Text.SLIDESHOW)) {
                TopBar.this.notifyAll(
                    new CommandEvent(TopBar.this, "slideshow"));
            }
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