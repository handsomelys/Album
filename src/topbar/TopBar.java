package topbar;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.event.*;

import event.DiretoryChangedManager;

import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
import java.io.File;
import java.awt.GridBagConstraints;

public class TopBar extends JPanel {
    private static final long serialVersionUID = 1L;
    
    public static final String BACKWARD = "←";
    public static final String FORWARD = "→";
    public static final String UPWARD = "↑";
    public static final String UNDO = "撤销";
    public static final String REDO = "重做";
    public static final String DELETE = "删除";
    public static final String SLIDESHOW = "幻灯片";
    public static final String SORTMETHOD = "排序";

    // general
    public File diretory;
    public String diretoryName;
    public String diretorySize;
    public int totalPictureCount;
    public int selectedPictureCount;

    // container
    public JPanel container1;
    public JPanel container2;
    public JPanel container3;
    
    // directory operation buttons and address bar
    public JButton buttonBackward;
    public JButton buttonForward;
    public JButton buttonUpward;
    public JLabel addressBar;
    public DiretoryChangedManager dcm;
    
    // informations
    public JLabel diretoryTitle;
    public JLabel diretoryStats;

    // operation buttons
    public JButton undo;
    public JButton redo;
    public JButton delete;
    public JButton slideShow;
    
    public class DiretoryButtonActionListener implements ActionListener {
        public DiretoryChangedManager dcm;
        public DiretoryButtonActionListener(DiretoryChangedManager dcm) {
            this.dcm = dcm;
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();
            if (command.equals(TopBar.BACKWARD)) {
                this.dcm.back();
            } else if (command.equals(TopBar.FORWARD)) {
                this.dcm.forward();
            } else if (command.equals(TopBar.UPWARD)) {
                this.dcm.up();
            }
        }  
    }

    public TopBar(File diretory) {
        GridBagLayout gbl = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        this.setBorder(BorderFactory.createEtchedBorder());
        this.setLayout(gbl);

        this.diretory = diretory;
        this.diretoryName = this.diretory.getName();
        this.diretorySize = String.valueOf(this.diretory.length());
        this.totalPictureCount = 1;
        this.selectedPictureCount = -1;

        // configuring containers
        this.container1 = new JPanel();
        this.container1.setLayout(gbl);
        this.container2 = new JPanel();
        this.container2.setLayout(gbl);
        this.container2.setBorder(BorderFactory.createEtchedBorder());
        this.container3 = new JPanel();
        this.container3.setLayout(gbl);

        // configuring directory buttons
        this.buttonBackward = new JButton(TopBar.BACKWARD);
        this.buttonForward = new JButton(TopBar.FORWARD);
        this.buttonUpward = new JButton(TopBar.UPWARD);
        DiretoryButtonActionListener dbal = 
            new DiretoryButtonActionListener(this.dcm);
        this.buttonBackward.addActionListener(dbal);
        this.buttonForward.addActionListener(dbal);
        this.buttonUpward.addActionListener(dbal);

        // configuring address bar
        this.addressBar = new JLabel(this.diretory.getAbsolutePath());
        this.addressBar.setBorder(BorderFactory.createEtchedBorder());

        // configuring informations
        this.diretoryTitle = new JLabel(this.diretoryName);
        this.diretoryStats = new JLabel(String.format(
            "%d张图片(%s) - 选中%d张图片",
            this.totalPictureCount,
            this.diretorySize,
            this.selectedPictureCount));

        // configuring operation buttons
        this.undo = new JButton(TopBar.UNDO);
        this.redo = new JButton(TopBar.REDO);
        this.delete = new JButton(TopBar.DELETE);
        this.slideShow = new JButton(TopBar.SLIDESHOW);

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
        this.container2.add(this.diretoryTitle, gbc);
        gbc.gridy = 1;
        this.container2.add(this.diretoryStats, gbc);

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
        /*
        // creating spam panel to align components
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 0.2;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        for(int i = 0; i < 5; ++i) {
            JPanel spam = new JPanel();
            gbc.gridx = i;
            this.add(spam, gbc);
        }
        */
    }
    public TopBar(String diretory) {
        this(new File(diretory));
    }
}