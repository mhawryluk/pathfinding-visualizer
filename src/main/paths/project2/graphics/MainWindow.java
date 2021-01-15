package paths.project2.graphics;

import paths.project2.engine.Board;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {
    public MainWindow(){
        int WIDTH = 600;
        int HEIGHT = 630;
        int squareSize = 30;

        BoardPanel boardPanel = new BoardPanel(new Board(20,20), squareSize);
        add(boardPanel);

        setTitle("Pathfinding Visualization");
        setBackground(Color.BLACK);
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Dimension screenDim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(screenDim.width / 2 - getSize().width / 2, screenDim.height / 2 - getSize().height / 2);
        setVisible(true);
    }
}
