package paths.project2.graphics;

import javax.swing.*;
import java.awt.*;

public class InputSpinner extends JPanel {

    public JLabel textLabel;
    public JSpinner inputSpinner;

    public InputSpinner(String text) {
        setLayout(null);
        setSize(300, 100);
        setBackground(Color.white);

        textLabel = new JLabel(text);
        textLabel.setBounds(5, 10, 200, 30);
        textLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        add(textLabel);


        inputSpinner = new JSpinner(new SpinnerNumberModel(20, 1, 200, 1));
        inputSpinner.setBounds(220, 10, 50, 30);
        add(inputSpinner);
    }

    public int getValue() {
        return (int) inputSpinner.getValue();
    }

    public void setValue(int value) {
        inputSpinner.setValue(value);
    }
}
