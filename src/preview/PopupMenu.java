package preview;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

import event.CommandEvent;
import event.CommandListener;
import event.CommandSource;
import main.Text;

public class PopupMenu extends JPopupMenu implements CommandSource {
    private static final long serialVersionUID = 1L;

    ArrayList<CommandListener> listeners;

    JMenuItem open;
    JMenuItem copy;
    JMenuItem paste;
    JMenuItem rename;
    JMenuItem remove;
    JMenuItem slideshow;

    public PopupMenu() {
        super();
        this.listeners = new ArrayList<CommandListener>();
        this.open = new JMenuItem(Text.OPEN);
        this.copy = new JMenuItem(Text.COPY);
        this.paste = new JMenuItem(Text.PASTE);
        this.rename = new JMenuItem(Text.RENAME);
        this.remove = new JMenuItem(Text.REMOVE);
        this.slideshow = new JMenuItem(Text.SLIDESHOW);

        ItemListener il = new ItemListener();
        this.open.addActionListener(il);
        this.copy.addActionListener(il);
        this.paste.addActionListener(il);
        this.rename.addActionListener(il);
        this.remove.addActionListener(il);
        this.slideshow.addActionListener(il);

        this.add(this.open);
        this.add(this.slideshow);
        this.addSeparator();
        this.add(this.copy);
        this.add(this.paste);
        this.addSeparator();
        this.add(this.remove);
        this.add(this.remove);
    }

    private class ItemListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();
            if (command.equals(Text.OPEN)) {
                PopupMenu.this.notifyAll(
                    new CommandEvent(PopupMenu.this, "open"));
            } else if (command.equals(Text.SLIDESHOW)) {
                PopupMenu.this.notifyAll(
                    new CommandEvent(PopupMenu.this, "slideshow"));
            } else if (command.equals(Text.COPY)) {
                PopupMenu.this.notifyAll(
                    new CommandEvent(PopupMenu.this, "copy"));
            } else if (command.equals(Text.PASTE)) {
                PopupMenu.this.notifyAll(
                    new CommandEvent(PopupMenu.this, "paste"));
            } else if (command.equals(Text.REMOVE)) {
                PopupMenu.this.notifyAll(
                    new CommandEvent(PopupMenu.this, "remove"));
            } else if (command.equals(Text.RENAME)) {
                PopupMenu.this.notifyAll(
                    new CommandEvent(PopupMenu.this, "rename"));
            }
        }
    }
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