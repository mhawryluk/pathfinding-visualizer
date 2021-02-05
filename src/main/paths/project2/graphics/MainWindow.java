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

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        pack();
        setVisible(true);

        Dimension screenDim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(screenDim.width / 2 - getSize().width / 2, screenDim.height / 2 - getSize().height / 2);
    }
}
