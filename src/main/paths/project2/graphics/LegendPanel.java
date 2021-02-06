package paths.project2.graphics;

import paths.project2.engine.*;
import javax.swing.*;
import java.awt.*;

public class LegendPanel extends JPanel {

    public LegendPanel(){
        setLayout(new GridLayout(1, 10));
        setBackground(new Color(85, 91, 110));

        for (SquareState state : SquareState.class.getEnumConstants()){
            KeyPanel key = new KeyPanel(state);
            add(key);
        }
    }
}
