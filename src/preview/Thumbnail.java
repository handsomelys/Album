package preview;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import java.io.File;

import main.Colors;

public class Thumbnail extends JPanel {
    private static final long serialVersionUID = 1L;

    public static final int WIDTH = 150;
    public static final int PICTUREHEIGHT = 130;
    public static final int TEXTHEIGHT = 20;
    public static final int TOTALHEIGHT = Thumbnail.PICTUREHEIGHT + Thumbnail.TEXTHEIGHT;

    public File file;
    public JTextField text;
    public JLabel picture;
    public PopupMenu popupMenu;
    public boolean selected;
    public int centerX;
    public int centerY;

    public boolean isSelected() {
        return this.selected;
    }

    public Thumbnail(File file) {
        // initializing variables
        this.file = file.getAbsoluteFile();
        this.text = new JTextField();
        this.picture = new JLabel();
        this.popupMenu = new PopupMenu("thumbnail");
        this.selected = false;
        this.centerX = 0;
        this.centerY = 0;

        // configuring self
        this.setLayout(new BorderLayout());
        this.setFocusable(true);
        this.setPreferredSize(new Dimension(Thumbnail.WIDTH, Thumbnail.TOTALHEIGHT));

        // configuring components
        this.text.setPreferredSize(new Dimension(Thumbnail.WIDTH, Thumbnail.TEXTHEIGHT));
        this.text.setBorder(null);
        this.text.setEditable(false);
        this.text.setHorizontalAlignment(SwingConstants.CENTER);
        this.text.setText(this.file.getName());
        this.picture.setPreferredSize(new Dimension(Thumbnail.WIDTH, Thumbnail.PICTUREHEIGHT));
        this.picture.setOpaque(true);
        this.picture.setHorizontalAlignment(SwingConstants.CENTER);
        this.createThumbnail();

        // adding components to this panel
        this.add(this.text, BorderLayout.SOUTH);
        this.add(this.picture, BorderLayout.CENTER);

        // configuring default color
        this.deselect();
    }

    public void createThumbnail() {
        ImageIcon image = new ImageIcon(this.file.getAbsolutePath());
        Image i = image.getImage();
        double proportion = (double) image.getIconWidth() / image.getIconHeight();
        if (image.getIconWidth() > Thumbnail.WIDTH || image.getIconWidth() > Thumbnail.PICTUREHEIGHT)
            if (proportion > Thumbnail.WIDTH / Thumbnail.PICTUREHEIGHT)
                i = i.getScaledInstance(Thumbnail.WIDTH, (int) (Thumbnail.WIDTH / proportion), Image.SCALE_FAST);
            else
                i = i.getScaledInstance((int) (Thumbnail.PICTUREHEIGHT * proportion), Thumbnail.PICTUREHEIGHT,
                        Image.SCALE_FAST);
        image = new ImageIcon(i);
        this.picture.setIcon(image);
        image.setImageObserver(this.picture);
    }

    public void setCenterLocation() {
        this.centerX = this.getX() + Thumbnail.WIDTH / 2;
        this.centerY = this.getY() + Thumbnail.TOTALHEIGHT / 2;
    }

    public boolean isInside(int x1, int x2, int y1, int y2) {
        return Math.max(x1, x2) > this.centerX && Math.min(x1, x2) < this.centerX && Math.max(y1, y2) > this.centerY
                && Math.min(y1, y2) < this.centerY;
    }

    public void select() {
        this.selected = true;
        this.setBackground(Colors.AZURE);
        this.picture.setBackground(Colors.AZURE);
        this.text.setBackground(Colors.AZURE);
    }
    public void deselect() {
        this.selected = false;
        this.setBackground(Colors.DEFAULT);
        this.picture.setBackground(Colors.DEFAULT);
        this.text.setBackground(Colors.DEFAULT);
    }
}