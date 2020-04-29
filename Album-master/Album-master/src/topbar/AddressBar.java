package topbar;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import javax.swing.BorderFactory;

public class AddressBar extends JPanel {
    private static final long serialVersionUID = 1L;

    public String address;
    public JLabel label;
    public AddressBar(String address) {
        this.setBorder(BorderFactory.createEtchedBorder());
        setAddress(address);
    }

    public void setAddress(String address) {
        this.address = address;
        if(this.label != null)
            this.remove(this.label);
        label = new JLabel(address);
        this.setLayout(new BorderLayout());
        this.add(label, BorderLayout.WEST);
    }
    public String getAddress() {
        return this.address;
    }
}