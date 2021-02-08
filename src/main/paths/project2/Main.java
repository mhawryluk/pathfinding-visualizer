package paths.project2;

import paths.project2.graphics.MainWindow;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {

        try {
            new MainWindow();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "error :(", JOptionPane.ERROR_MESSAGE);
        }
    }
}
