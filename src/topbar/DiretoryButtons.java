package topbar;

import javax.swing.JPanel;

import event.*;

import javax.swing.JButton;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DiretoryButtons extends JPanel {
    private static final long serialVersionUID = 1L;
    
    public static final String BACKWARD = "←";
    public static final String FORWARD = "→";
    public static final String UPWARD = "↑";

    public JButton backward;
    public JButton forward;
    public JButton upward;

    public Manager m;

    public DiretoryButtons() {
        FlowLayout fl = new FlowLayout();
        this.setLayout(fl);

        this.backward = new JButton(DiretoryButtons.BACKWARD);
        this.forward = new JButton(DiretoryButtons.FORWARD);
        this.upward = new JButton(DiretoryButtons.UPWARD);

        this.backward.setActionCommand(DiretoryButtons.BACKWARD);
        this.forward.setActionCommand(DiretoryButtons.FORWARD);
        this.upward.setActionCommand(DiretoryButtons.UPWARD);

        this.add(this.backward);
        this.add(this.forward);
        this.add(this.upward);
    }
    protected class DiretoryChangedListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();
            if (command.equals(DiretoryButtons.BACKWARD)) {

            }

        }
    }
}