package preview;

import java.io.File;

import java.awt.BorderLayout;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class ThumbNail extends JPanel {
    private static final long serialVersionUID = 1L;

    public JTextField text;
    public JLabel picture;
    public File file;

    public ThumbNail(File file) {
        // initializing variable
        if (file != null)
            this.file = file.getAbsoluteFile();
        this.text = new JTextField();
        this.picture = new JLabel();
        // create thumbnail
        ImageIcon image = new ImageIcon(this.file.getAbsolutePath());
        double h1 = image.getIconHeight();
        double w1 = image.getIconWidth();
        if(h1<77 && w1<100) {
            Image im = image.getImage().getScaledInstance((int)w1, (int)h1, Image.SCALE_DEFAULT);	//创建缩放图  hints参数是选择加载的算法 这里用的默认的
            ImageIcon ic2 = new ImageIcon(im);
            this.picture.setIcon(ic2);
            ic2.setImageObserver(this.picture);
            this.text.setText(this.file.getName());
        }
        else {
            if(h1*180>w1*142) {
                Image im = image.getImage().getScaledInstance((int)(w1/(h1/81)), 81, Image.SCALE_DEFAULT);
                ImageIcon ic2 = new ImageIcon(im);
                this.picture.setIcon(ic2);
                ic2.setImageObserver(this.picture);
                this.text.setText(this.file.getName());
            }
            else {
                Image im = image.getImage().getScaledInstance(105,(int)(h1/(w1/105)),Image.SCALE_DEFAULT);
                ImageIcon ic2 = new ImageIcon(im);
                this.picture.setIcon(ic2);
                ic2.setImageObserver(this.picture);
                this.text.setText(this.file.getName());
            }
        }
        this.text.setText(this.file.getName());
        this.picture.setVisible(true);
        this.text.setBorder(null);
        this.text.setHorizontalAlignment(SwingConstants.CENTER);
        this.text.setEditable(false);

        this.add(this.picture, BorderLayout.NORTH);
        this.add(this.text, BorderLayout.SOUTH);
    }
}