package dialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import java.io.File;
import java.util.HashSet;

import event.CommandEvent;
import event.CommandListener;
import event.CommandSource;
import main.Text;
import util.FileUtils;

public class FileRenameDialog extends JFrame implements CommandSource {
    private static final long serialVersionUID = 1L;

    public File file;
    public JLabel labelName;
    public JTextField textName;
    public JButton buttonConfirm;
    public JButton buttonCancel;
    protected HashSet<CommandListener> listeners;

    public String getName() {
        return this.textName.getText();
    }

    public FileRenameDialog(File file) {
        this.file = file;
        this.labelName = new JLabel(Text.NAME);
        this.textName = new JTextField(this.file.getName());
        this.buttonConfirm = new JButton(Text.CONFIRM);
        this.buttonCancel = new JButton(Text.CANCEL);
        this.listeners = new HashSet<CommandListener>();

        this.setTitle(Text.RENAME);
        this.setLayout(new GridBagLayout());
        this.setBounds(100, 100, 300, 100);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

        this.buttonConfirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int result = JOptionPane.showConfirmDialog(FileRenameDialog.this, Text.CONFIRMRENAME, Text.RENAME, 0);
                if (result == 0) {
                    FileUtils.renameFile(FileRenameDialog.this.file, FileRenameDialog.this.getName());
                    FileRenameDialog.this.notifyAll(new CommandEvent(FileRenameDialog.this, "refresh"));
                    FileRenameDialog.this.dispose();
                }
            }
        });
        this.buttonCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FileRenameDialog.this.dispose();
            }
        });

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 2;
        gbc.weighty = 1;
        this.add(this.labelName, gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        this.add(this.buttonConfirm, gbc);
        gbc.gridx = 2;
        this.add(this.buttonCancel, gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 8;
        gbc.gridheight = 1;
        gbc.weightx = 8;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        this.add(this.textName, gbc);
    }
    // event source method
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