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

import main.Text;
import util.FileUtils;

public class FilesRenameDialog extends JFrame {
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

    public String getName() {
        return this.textName.getText();
    }
    public int getIndex() {
        return Integer.parseInt(this.textIndex.getText());
    }
    public int getBit() {
        return Integer.parseInt(this.textBit.getText());
    }

    public FilesRenameDialog(ArrayList<File> files) {
        super();
        this.files = files;
        this.labelName = new JLabel(Text.NAME);
        this.labelIndex = new JLabel(Text.STARTINDEX);
        this.labelBit = new JLabel(Text.BIT);
        this.textName = new JTextField(
            FileUtils.getFileNameNoExtension(this.files.get(0)));
        this.textIndex = new JTextField("0");
        this.textBit = new JTextField("4");
        this.buttonConfirm = new JButton(Text.CONFIRM);
        this.buttonCancel = new JButton(Text.CANCEL);

        this.setTitle(Text.RENAME);
        this.setLayout(new GridBagLayout());
        this.setBounds(100, 100, 300, 250);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        
        this.buttonConfirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int result = JOptionPane.showConfirmDialog(FilesRenameDialog.this,
                    Text.CONFIRMRENAME, Text.RENAME, 0);
                if (result == 0) {
                    FileUtils.renameFiles(FilesRenameDialog.this.files,
                        FilesRenameDialog.this.getName(),
                        FilesRenameDialog.this.getIndex(),
                        FilesRenameDialog.this.getBit());
                    JOptionPane.showMessageDialog(FilesRenameDialog.this,
                        Text.RENAMESUCCESS, "success", 1);
                    FilesRenameDialog.this.dispose();
                }
            }
        });
        this.buttonCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FilesRenameDialog.this.dispose();
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
}