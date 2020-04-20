package topbar;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.GridBagLayout;
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
    
    public JPanel db;
    public AddressBar ab;
    public OperationButtons ob;
    public ImformationBar ib;

    public TopBar() {
        this.setBorder(BorderFactory.createEtchedBorder());
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        this.db = new DiretoryButtons();
        this.ab = new AddressBar("d:/document");
        this.ob = new OperationButtons();
        this.ib = new ImformationBar();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 0.1;
        gbc.weighty = 0.4;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        this.add(this.buttonBackward, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        this.add(this.buttonForward, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        this.add(this.buttonUpward, gbc);

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