package topbar;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

public class TopBar extends JPanel {
    private static final long serialVersionUID = 1L;
    
    public DiretoryButtons db;
    public AddressBar ab;
    public OperationButtons ob;
    public ImformationBar ib;

    public TopBar() {
        GridBagLayout gbl = new GridBagLayout();
        this.setLayout(gbl);
        GridBagConstraints gbc = new GridBagConstraints();

        this.db = new DiretoryButtons();
        this.ab = new AddressBar("d:/document");
        this.ob = new OperationButtons();
        this.ib = new ImformationBar();

        gbc.fill = GridBagConstraints.HORIZONTAL;

        // buttons that browse between diretory
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 0.2;
        gbc.weighty = 0.5;
        this.add(this.db, gbc);
        // address bar
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.weightx = 0.8;
        gbc.weighty = 0.5;
        this.add(this.ab, gbc);
        // Information Bar
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.weightx = 0.6;
        gbc.weighty = 0.5;
        this.add(this.ib, gbc);
        // Operation Buttons
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 0.4;
        gbc.weighty = 0.5;
        this.add(this.ob, gbc);
    }
}