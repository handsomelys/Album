package topbar;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.event.*;

import event.DiretoryChangedManager;

import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
import java.awt.GridBagConstraints;

public class TopBar extends JPanel {
    private static final long serialVersionUID = 1L;
    
    public static final String BACKWARD = "←";
    public static final String FORWARD = "→";
    public static final String UPWARD = "↑";

    // directory operation buttons
    public JButton buttonBackward;
    public JButton buttonForward;
    public JButton buttonUpward;
    public DiretoryChangedManager dcm;
    
    public DiretoryButtons db;
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

    public TopBar() {
        this.setBorder(BorderFactory.createEtchedBorder());
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        this.buttonBackward = new JButton(TopBar.BACKWARD);
        this.buttonForward = new JButton(TopBar.FORWARD);
        this.buttonUpward = new JButton(TopBar.UPWARD);
        DiretoryButtonActionListener dbal = 
            new DiretoryButtonActionListener(this.dcm);
        this.buttonBackward.addActionListener(dbal);
        this.buttonForward.addActionListener(dbal);
        this.buttonUpward.addActionListener(dbal);
    
        this.ab = new AddressBar("d:/document");
        this.ob = new OperationButtons();
        this.ib = new ImformationBar();

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

        // buttons that browse between diretory
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 0.2;
        gbc.weighty = 0.4;
        gbc.anchor = GridBagConstraints.WEST;
        this.add(this.db, gbc);

        // address bar
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 4;
        gbc.gridheight = 1;
        gbc.weightx = 0.8;
        gbc.weighty = 0.4;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        this.add(this.ab, gbc);

        // Information Bar
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 3;
        gbc.gridheight = 1;
        gbc.weightx = 0.6;
        gbc.weighty = 0.6;
        gbc.anchor = GridBagConstraints.WEST;
        this.add(this.ib, gbc);

        // Operation Buttons
        gbc.gridx = 3;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.weightx = 0.4;
        gbc.weighty = 0.6;
        gbc.anchor = GridBagConstraints.EAST;
        this.add(this.ob, gbc);
    }
}