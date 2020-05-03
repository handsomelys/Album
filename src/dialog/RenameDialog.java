package dialog;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.event.ActionListener;
import java.io.File;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import java.util.ArrayList;

import event.FileEvent;
import event.FileListener;
import event.FileSource;
import main.Text;

public class RenameDialog extends JFrame implements FileSource {
    private static final long serialVersionUID = 1L;

    public ArrayList<File> files;
    public JLabel labelName;
    public JLabel labelIndex;
    public JLabel labelBit;
    public JTextField textName;
    public JTextField textIndex;
    public JTextField textBit;
    public JButton buttonConfirm;
    public JButton buttonCancel;
    private ArrayList<FileListener> listeners;

    public String getName() {
        return this.textName.getText();
    }
    public String getIndex() {
        return this.textIndex.getText();
    }
    public String getBit() {
        return this.textBit.getText();
    }

    public RenameDialog(ArrayList<File> files) {
        super();
        this.labelName = new JLabel(Text.NAME);
        this.labelIndex = new JLabel(Text.STARTINDEX);
        this.labelBit = new JLabel(Text.BIT);
        this.textName = new JTextField();
        this.textIndex = new JTextField("0");
        this.textBit = new JTextField("4");
        this.buttonConfirm = new JButton(Text.CONFIRM);
        this.buttonCancel = new JButton(Text.CANCEL);
        this.listeners = new ArrayList<FileListener>();

        this.setTitle(Text.RENAME);
        this.setLayout(new GridBagLayout());
        this.setBounds(100, 100, 300, 250);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        
        this.buttonConfirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int result = JOptionPane.showConfirmDialog(RenameDialog.this,
                    Text.CONFIRMRENAME, Text.RENAME, 0);
                if (result == 0) {
                    FileEvent fe = new FileEvent(RenameDialog.this, "rename");
                    RenameDialog.this.notifyAll(fe);
                }
            }
        });
        this.buttonCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RenameDialog.this.dispose();
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
        gbc.gridy = 1;
        this.add(this.labelIndex, gbc);
        gbc.gridy = 2;
        this.add(this.labelBit, gbc);
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.NONE;
        this.add(this.buttonConfirm, gbc);
        gbc.gridx = 2;
        this.add(this.buttonCancel, gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.weightx = 8;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        this.add(this.textName, gbc);
        gbc.gridy = 1;
        this.add(this.textIndex, gbc);
        gbc.gridy = 2;
        this.add(this.textBit, gbc);
    }
    // event source method
    @Override
    public void addListener(FileListener fl) {
        this.listeners.add(fl);
    }
    @Override
    public void removeListener(FileListener fl) {
        this.listeners.remove(fl);
    }
    @Override
    public void notifyAll(FileEvent fe) {
        for (FileListener fl: listeners)
            fl.actionPerformed(fe);
    }
    public static void main(String[] args) {
        //new RenameDialog();
    }
}