package main;

import topbar.*;
import javax.swing.*;

public class Main {
    public static void main(String args[]) throws Exception {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        JFrame window = new JFrame("picture");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(400, 300);
        window.setVisible(true);

        TopBar tb = new TopBar();
        window.add(tb);
    }
}