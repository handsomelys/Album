package topbar;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JLabel;

public class ImformationBar extends JPanel {
    private static final long serialVersionUID = 1L;
    
    public static final String NOTOPEN = "未打开文件夹";

    public int totalPictureCount;
    public int selectedPictureCount;
    public String diretoryName;
    public String diretorySize;

    public JLabel title;
    public JLabel count;

    public ImformationBar() {
        this(ImformationBar.NOTOPEN, 0, 0, "0.0KB");
    }
    public ImformationBar(
        String diretory, int total, int selected, String size) {
        this.title = new JLabel();
        this.count = new JLabel();
        this.add(this.title, BorderLayout.NORTH);
        this.add(this.count, BorderLayout.SOUTH);
        this.update(diretory, total, selected, size);
    }

    public void update(String diretory, int total, int selected, String size) {
        this.diretoryName = diretory;
        this.totalPictureCount = total;
        this.selectedPictureCount = selected;
        this.title.setText(this.diretoryName);
        this.count.setText(ImformationBar.formatCount(total, selected, size));
    }
    public static String formatCount(int total, int selected, String size) {
        return String.format(
            "%d张图片(%s) - 选中%d张图片", total, size, selected);
    }
}