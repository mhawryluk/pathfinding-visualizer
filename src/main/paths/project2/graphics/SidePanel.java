package paths.project2.graphics;

import paths.project2.engine.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SidePanel extends JPanel implements ActionListener {

    private final JButton startStopButton;
    private final JButton resetButton;
    private final JToggleButton addObstaclesButton;
    private final JToggleButton selectStartButton;
    private final JToggleButton selectEndButton;
    private final JComboBox<String> algorithmBox;
    private final Color BACKGROUND_COLOR = Color.WHITE;
    private final BoardPanel boardPanel;

    public SidePanel(BoardPanel boardPanel){
        this.boardPanel = boardPanel;

        String[] algorithms = {"A*", "Dijkstra", "BreadthFirstSearch"};

        algorithmBox = new JComboBox<>(algorithms);
        add(algorithmBox);

        startStopButton = new JButton("START");
        startStopButton.addActionListener(this);
        add(startStopButton);

        resetButton = new JButton("RESET");
        resetButton.addActionListener(this);
        add(resetButton);

        addObstaclesButton = new JToggleButton("ADD OBSTACLES");
        addObstaclesButton.addActionListener(this);
        add(addObstaclesButton);

        selectStartButton = new JToggleButton("SELECT START SQUARE");
        selectStartButton.addActionListener(this);
        add(selectStartButton);

        selectEndButton = new JToggleButton("SELECT END SQUARE");
        selectEndButton.addActionListener(this);
        add(selectEndButton);

        setBackground(BACKGROUND_COLOR);
        setPreferredSize(new Dimension(300, 600));
        setLayout(new GridLayout(11, 1, 5, 5));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == startStopButton) {
            if (boardPanel.algorithmRunning){
                boardPanel.algorithmRunning = false;
                boardPanel.timer.stop();
                startStopButton.setText("START");
            } else {

                switch ((String)algorithmBox.getSelectedItem()) {
                    case "A*": {
                        AStarAlgorithm newAlgorithm = new AStarAlgorithm(boardPanel.getBoard());
                        newAlgorithm.setStartPosition(boardPanel.algorithm.getStartSquare());
                        newAlgorithm.setEndPosition(boardPanel.algorithm.getEndSquare());
                        boardPanel.algorithm = newAlgorithm;
                        break;
                    }
                    case "Dijkstra": {
                        DijkstraAlgorithm newAlgorithm = new DijkstraAlgorithm(boardPanel.getBoard());
                        newAlgorithm.setStartPosition(boardPanel.algorithm.getStartSquare());
                        newAlgorithm.setEndPosition(boardPanel.algorithm.getEndSquare());
                        boardPanel.algorithm = newAlgorithm;
                        break;
                    }
                    case "BreadthFirstSearch": {
                        BFSAlgorithm newAlgorithm = new BFSAlgorithm(boardPanel.getBoard());
                        newAlgorithm.setStartPosition(boardPanel.algorithm.getStartSquare());
                        newAlgorithm.setEndPosition(boardPanel.algorithm.getEndSquare());
                        boardPanel.algorithm = newAlgorithm;
                        break;
                    }
                }
                boardPanel.algorithmRunning = true;
                startStopButton.setText("STOP");
                boardPanel.timer.start();
            }
        }
        if (e.getSource() == resetButton) {
            boardPanel.algorithmRunning = false;
            boardPanel.reset();
            startStopButton.setText("START");

            boardPanel.selectingStart = false;
            selectStartButton.setSelected(false);

            boardPanel.selectingEnd = false;
            selectEndButton.setSelected(false);

            boardPanel.placingObstacles = false;
            addObstaclesButton.setText("ADD OBSTACLES");
        }

        if (e.getSource() == addObstaclesButton) {
            if (boardPanel.placingObstacles){
                boardPanel.placingObstacles = false;
                addObstaclesButton.setSelected(false);
            } else {
                boardPanel.placingObstacles = true;
                boardPanel.selectingStart = false;
                selectStartButton.setSelected(false);
                boardPanel.selectingEnd = false;
                selectEndButton.setSelected(false);
            }
        }

        if (e.getSource() == selectStartButton) {
            boardPanel.selectingStart = true;
            boardPanel.selectingEnd = false;
            selectEndButton.setSelected(false);
            boardPanel.placingObstacles = false;
            addObstaclesButton.setSelected(false);
        }

        if (e.getSource() == selectEndButton) {
            boardPanel.selectingEnd = true;
            boardPanel.selectingStart = false;
            selectStartButton.setSelected(false);
            boardPanel.placingObstacles = false;
            addObstaclesButton.setSelected(false);
        }
    }
}
