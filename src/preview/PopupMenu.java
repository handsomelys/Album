package preview;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import java.util.HashSet;

import event.CommandEvent;
import event.CommandListener;
import event.CommandSource;
import main.Text;

public class PopupMenu extends JPopupMenu implements CommandSource {
    private static final long serialVersionUID = 1L;

    public String type;
    protected JMenuItem[] items;
    protected HashSet<CommandListener> listeners;

    public PopupMenu(String type) {
        this.type = type;
        this.listeners = new HashSet<CommandListener>();
        ItemListener il = new ItemListener();
        if (this.type.equals("panel")) {
            // panel popup menu
            this.items = new JMenuItem[3];
            this.items[0] = new JMenuItem(Text.SLIDESHOW);
            this.items[1] = new JMenuItem(Text.REFRESH);
            this.items[2] = new JMenuItem(Text.PASTE);
        } else if (this.type.equals("thumbnail")) {
            // thumbnail popup menu
            this.items = new JMenuItem[8];
            this.items[0] = new JMenuItem(Text.OPEN);
            this.items[1] = new JMenuItem(Text.SLIDESHOW);
            this.items[2] = null;
            this.items[3] = new JMenuItem(Text.COPY);
            this.items[4] = new JMenuItem(Text.PASTE);
            this.items[5] = new JMenuItem(Text.RENAME);
            this.items[6] = null;
            this.items[7] = new JMenuItem(Text.REMOVE);
        }
        for (JMenuItem jmi: this.items)
            if (jmi == null)
                this.addSeparator();
            else {
                this.add(jmi);
                jmi.addActionListener(il);
            }
    }

    private class ItemListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();
            if (command.equals(Text.OPEN)) {
                PopupMenu.this.notifyAll(new CommandEvent(PopupMenu.this, "open"));
            } else if (command.equals(Text.SLIDESHOW)) {
                PopupMenu.this.notifyAll(new CommandEvent(PopupMenu.this, "slideshow"));
            } else if (command.equals(Text.COPY)) {
                PopupMenu.this.notifyAll(new CommandEvent(PopupMenu.this, "copy"));
            } else if (command.equals(Text.PASTE)) {
                PopupMenu.this.notifyAll(new CommandEvent(PopupMenu.this, "paste"));
            } else if (command.equals(Text.REMOVE)) {
                PopupMenu.this.notifyAll(new CommandEvent(PopupMenu.this, "remove"));
            } else if (command.equals(Text.RENAME)) {
                PopupMenu.this.notifyAll(new CommandEvent(PopupMenu.this, "rename"));
            } else if (command.equals(Text.REFRESH)) {
                PopupMenu.this.notifyAll(new CommandEvent(PopupMenu.this, "refresh"));
            }
        }
    }
    // command event source method
    @Override
    public void addListener(CommandListener cl) {
        this.listeners.add(cl);
    }
    @Override
    public void removeListener(CommandListener cl) {
        this.listeners.remove(cl);
    }
    @Override
    public void notifyAll(CommandEvent ce) {
        for (CommandListener cl: listeners)
            cl.actionPerformed(ce);
    }
}