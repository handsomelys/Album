package topbar;

import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.FlowLayout;

public class DiretoryButtons extends JPanel {
    private static final long serialVersionUID = 1L;
    
    public static final String BACKWARD = "←";
    public static final String FORWARD = "→";
    public static final String UPWARD = "↑";

    public JButton backward;
    public JButton forward;
    public JButton upward;

    public DiretoryButtons() {
        FlowLayout fl = new FlowLayout();
        this.setLayout(fl);

        this.backward = new JButton(DiretoryButtons.BACKWARD);
        this.forward = new JButton(DiretoryButtons.FORWARD);
        this.upward = new JButton(DiretoryButtons.UPWARD);

        this.add(this.backward);
        this.add(this.forward);
        this.add(this.upward);
    }
}