package paths.project2.graphics;

import javax.swing.*;
import java.awt.*;

public class InputSpinner extends JPanel {

    public final JLabel textLabel;
    public final JSpinner inputSpinner;

    public InputSpinner(String text) {
        setLayout(null);
        setSize(200, 100);
        setBackground(Color.white);

        textLabel = new JLabel(text);
        textLabel.setBounds(5, 10, 100, 30);
        textLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        add(textLabel);

        inputSpinner = new JSpinner(new SpinnerNumberModel(20, 1, 200, 1));
        inputSpinner.setBounds(120, 10, 50, 30);
        add(inputSpinner);
    }

    public int getValue() {
        return (int) inputSpinner.getValue();
    }
}
