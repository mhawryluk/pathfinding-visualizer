package paths.project2.graphics;

import paths.project2.engine.*;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {
    public MainWindow(){
        int WIDTH = 100;
        int HEIGHT = 600;
        int squareSize = 30;
        setTitle("Pathfinding Visualization");
        setLayout(new BorderLayout(10, 10));

        Board board = new Board(20, 20);
        BoardPanel boardPanel = new BoardPanel(board, squareSize, new AStarAlgorithm(board));
        add(boardPanel, BorderLayout.CENTER);

        SidePanel sidePanel = new SidePanel(boardPanel);
        add(sidePanel, BorderLayout.WEST);

//        setBackground(Color.BLACK);
//        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Dimension screenDim = Toolkit.getDefaultToolkit().getScreenSize();
//        setLocation(screenDim.width / 2 - getSize().width / 2, screenDim.height / 2 - getSize().height / 2);
        pack();
        setVisible(true);
    }
}
