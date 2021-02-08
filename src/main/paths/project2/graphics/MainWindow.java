package paths.project2.graphics;

import paths.project2.engine.PathBoard;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {
    public MainWindow() {
        setTitle("Pathfinder");
        setLayout(new BorderLayout(0, 0));

        PathBoard board = new PathBoard(20, 20);
        BoardPanel boardPanel = new BoardPanel(board);
        add(boardPanel, BorderLayout.CENTER);

        SidePanel sidePanel = new SidePanel(boardPanel);
        add(sidePanel, BorderLayout.WEST);
        boardPanel.setSidePanel(sidePanel);

        LegendPanel legendPanel = new LegendPanel();
        add(legendPanel, BorderLayout.SOUTH);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        pack();
        setVisible(true);

        Font font = new Font(Font.MONOSPACED, Font.PLAIN, 10);
        changeFont(this, font);

        Dimension screenDim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(screenDim.width / 2 - getSize().width / 2, screenDim.height / 2 - getSize().height / 2);
    }

    private void changeFont(Component component, Font font) {
        component.setFont(font);
        if (component instanceof Container) {
            for (Component child : ((Container) component).getComponents()) {
                changeFont(child, font);
            }
        }
    }
}


