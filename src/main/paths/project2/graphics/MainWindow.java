package paths.project2.graphics;

import paths.project2.engine.*;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {
    public MainWindow(){
        setTitle("Pathfinding Visualization");
        setLayout(new BorderLayout(10, 10));

        PathBoard board = new PathBoard(20, 20);
        BoardPanel boardPanel = new BoardPanel(board, new AStarAlgorithm(board));
        add(boardPanel, BorderLayout.CENTER);

        SidePanel sidePanel = new SidePanel(boardPanel);
        add(sidePanel, BorderLayout.WEST);

        LegendPanel legendPanel = new LegendPanel();
        add(legendPanel, BorderLayout.SOUTH);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        pack();
        setVisible(true);

        Font font = new Font(Font.MONOSPACED, Font.PLAIN, 10);
        changeFont(this, font);

        Dimension screenDim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(screenDim.width / 2 - getSize().width / 2, screenDim.height / 2 - getSize().height / 2);
        setBackground(new Color(85, 91, 110));
    }

    public static void changeFont(Component component, Font font){
        component.setFont(font);
        if (component instanceof Container){
            for (Component child : ((Container) component).getComponents()) {
                changeFont(child, font);
            }
        }
    }
}


