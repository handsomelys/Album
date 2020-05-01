package preview;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

public class PopupMenu extends JPopupMenu {
    private static final long serialVersionUID = 1L;

    public static final String OPEN = "打开";
    public static final String COPY = "复制";
    public static final String PASTE = "粘贴";
    public static final String RENAME = "重命名";
    public static final String REMOVE = "删除";
    public static final String SLIDESHOW = "幻灯片";
    public static final String PROPERTIES = "属性";

    JMenuItem open;
    JMenuItem copy;
    JMenuItem paste;
    JMenuItem rename;
    JMenuItem remove;
    JMenuItem slideshow;
    JMenuItem properties;

    public PopupMenu() {
        super();
        this.open = new JMenuItem(PopupMenu.OPEN);
        this.copy = new JMenuItem(PopupMenu.COPY);
        this.paste = new JMenuItem(PopupMenu.PASTE);
        this.rename = new JMenuItem(PopupMenu.RENAME);
        this.remove = new JMenuItem(PopupMenu.REMOVE);
        this.slideshow = new JMenuItem(PopupMenu.SLIDESHOW);
        this.properties = new JMenuItem(PopupMenu.PROPERTIES);

        this.add(this.open);
        this.add(this.slideshow);
        this.addSeparator();
        this.add(this.copy);
        this.add(this.paste);
        this.addSeparator();
        this.add(this.remove);
        this.add(this.remove);
        this.addSeparator();
        this.add(this.properties);
    }
}