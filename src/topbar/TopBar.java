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

    public File diretory;
    public String diretoryName;
    public String diretorySize;
    public int totalPictureCount;
    public int selectedPictureCount;

    // directory operation buttons
    public JButton buttonBackward;
    public JButton buttonForward;
    public JButton buttonUpward;
    public DiretoryChangedManager dcm;

    public JPanel spam;
    
    public JLabel diretoryTitle;
    public JLabel diretoryStats;
    
    public AddressBar ab;
    public OperationButtons ob;
    public ImformationBar ib;
    
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

    public TopBar(String d) {
        this.setBorder(BorderFactory.createEtchedBorder());
        GridBagLayout gbl = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        this.setLayout(gbl);

        this.spam = new JPanel();
        this.diretory = new File(d);
        this.diretoryName = this.diretory.getName();
        this.diretorySize = String.valueOf(this.diretory.length());
        this.totalPictureCount = 1;
        this.selectedPictureCount = -1;

        this.buttonBackward = new JButton(TopBar.BACKWARD);
        this.buttonForward = new JButton(TopBar.FORWARD);
        this.buttonUpward = new JButton(TopBar.UPWARD);
        DiretoryButtonActionListener dbal = 
            new DiretoryButtonActionListener(this.dcm);
        this.buttonBackward.addActionListener(dbal);
        this.buttonForward.addActionListener(dbal);
        this.buttonUpward.addActionListener(dbal);

        this.diretoryTitle = new JLabel(this.diretoryName);
        this.diretoryStats = new JLabel(String.format(
            "%d张图片(%s) - 选中%d张图片",
            this.totalPictureCount,
            this.diretorySize,
            this.selectedPictureCount));
        
        this.ab = new AddressBar("d:/document");
        this.ob = new OperationButtons();
        this.ib = new ImformationBar();

        // buttons that browse between diretory
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 1;
        gbc.weighty = 4;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.WEST;
        this.add(this.buttonBackward, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        this.add(this.buttonForward, gbc);

        gbc.gridx = 2;
        gbc.gridy = 0;
        this.add(this.buttonUpward, gbc);

        // address bar
        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 7;
        gbc.weighty = 4;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        this.add(this.ab, gbc);
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

        // Information Bar
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        gbc.gridheight = 1;
        gbc.weightx = 3;
        gbc.weighty = 3;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.WEST;
        this.add(this.diretoryTitle, gbc);
        gbc.gridy = 2;
        this.add(this.diretoryStats, gbc);

        // Operation Buttons
        gbc.gridx = 3;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.gridheight = 2;
        gbc.weightx = 7;
        gbc.weighty = 6;
        gbc.anchor = GridBagConstraints.EAST;
        this.add(this.ob, gbc);

    }
}