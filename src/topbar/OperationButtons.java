package topbar;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import java.awt.FlowLayout;

public class OperationButtons extends JPanel {
    private static final long serialVersionUID = 1L;
    
    public static final String UNDO = "撤销";
    public static final String REDO = "重做";
    public static final String DELETE = "删除";
    public static final String SLIDESHOW = "幻灯片";
    public static final String SORTMETHOD = "排序";

    public JButton undo;
    public JButton redo;
    public JButton delete;
    public JButton slideShow;
    // public JComboBox<String> sortMethod;
    
    public OperationButtons() {
        FlowLayout fl = new FlowLayout();
        this.setLayout(fl);

        this.undo = new JButton(OperationButtons.UNDO);
        this.redo = new JButton(OperationButtons.REDO);
        this.delete = new JButton(OperationButtons.DELETE);
        this.slideShow = new JButton(OperationButtons.SLIDESHOW);
        // this.sortMethod = new JComboBox<>();

        this.add(this.undo);
        this.add(this.redo);
        this.add(this.delete);
        this.add(this.slideShow);
        // this.add(this.sortMethod);
    }
}