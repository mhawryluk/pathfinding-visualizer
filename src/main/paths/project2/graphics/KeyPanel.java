package paths.project2.graphics;

import paths.project2.engine.SquareState;

import javax.swing.*;
import java.awt.*;

public class KeyPanel extends JPanel {

    public KeyPanel(SquareState state){


        setLayout(new GridLayout(2,1));

        setBounds(0,0,800,40);

        JLabel nameField = new JLabel(state.description);
        nameField.setHorizontalAlignment(SwingConstants.CENTER);
        nameField.setPreferredSize(new Dimension(20,20));
        add(nameField);

        JLabel colorLabel = new JLabel();
        colorLabel.setPreferredSize(new Dimension(20,20));
        colorLabel.setBackground(state.color);
        colorLabel.setOpaque(true);
        add(colorLabel);

        Color backgroundColor = Color.lightGray;

        setBackground(backgroundColor);
        setBorder(BorderFactory.createLineBorder(Color.black));
        nameField.setBackground(backgroundColor);
    }
}
