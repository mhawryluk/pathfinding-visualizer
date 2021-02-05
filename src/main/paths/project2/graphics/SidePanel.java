package paths.project2.graphics;

import paths.project2.engine.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SidePanel extends JPanel implements ActionListener {

    private final JButton startStopButton = new JButton("START");
    private final JButton resetButton = new JButton("RESET");
    private final JToggleButton addObstaclesButton = new JToggleButton("SELECT OBSTACLES");
    private final JToggleButton selectStartButton = new JToggleButton("SELECT START SQUARE");
    private final JToggleButton selectEndButton = new JToggleButton("SELECT END SQUARE");
    private final JComboBox<String> algorithmBox;
    private final InputSpinner widthLabel = new InputSpinner("width: ");
    private final InputSpinner heightLabel = new InputSpinner("height: ");
    private final JButton setDimensionsButton = new JButton("SET DIMENSIONS");
    private final JButton generateObstaclesButton = new JButton("GENERATE OBSTACLES");
    private final Color BACKGROUND_COLOR = Color.WHITE;
    private final JButton modeButton = new JButton("MAZE EXPLORER");
    private boolean mazeMode = false;
    private BoardPanel boardPanel;

    public SidePanel(BoardPanel boardPanel){
        this.boardPanel = boardPanel;

        String[] algorithms = {"A*", "Dijkstra", "BreadthFirstSearch"};

        algorithmBox = new JComboBox<>(algorithms);

        setDimensionsButton.addActionListener(this);
        generateObstaclesButton.addActionListener(this);
        addObstaclesButton.addActionListener(this);
        selectStartButton.addActionListener(this);
        selectEndButton.addActionListener(this);
        startStopButton.addActionListener(this);
        resetButton.addActionListener(this);
        modeButton.addActionListener(this);

        modeButton.setFocusable(false);
        algorithmBox.setFocusable(false);
        widthLabel.setFocusable(false);
        heightLabel.setFocusable(false);
        setDimensionsButton.setFocusable(false);
        generateObstaclesButton.setFocusable(false);
        addObstaclesButton.setFocusable(false);
        selectStartButton.setFocusable(false);
        selectEndButton.setFocusable(false);
        startStopButton.setFocusable(false);
        resetButton.setFocusable(false);


        add(modeButton);
        add(algorithmBox);
        add(widthLabel);
        add(heightLabel);
        add(setDimensionsButton);
        add(generateObstaclesButton);
        add(addObstaclesButton);
        add(selectStartButton);
        add(selectEndButton);
        add(startStopButton);
        add(resetButton);

        setBackground(BACKGROUND_COLOR);
        setPreferredSize(new Dimension(300, 600));
        setLayout(new GridLayout(11, 1, 5, 5));
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == setDimensionsButton){
            boardPanel.newBoardDimensions(widthLabel.getValue(), heightLabel.getValue());
            boardPanel.repaint();
        }

        if (e.getSource() == generateObstaclesButton){
            boardPanel.generateObstacles();
            boardPanel.repaint();
        }

        if (e.getSource() == startStopButton) {
            if (boardPanel.algorithmRunning){
                boardPanel.algorithmRunning = false;
                boardPanel.timer.stop();
                startStopButton.setText("START");
            } else {
                changeAlgorithm();
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

        if (e.getSource() == modeButton){
            if (mazeMode){
                mazeMode = false;
                modeButton.setText("MAZE EXPLORER");
                PathBoard board = new PathBoard(widthLabel.getValue(), heightLabel.getValue());
                boardPanel.changeMode(board, false);
                algorithmBox.setVisible(true);
                generateObstaclesButton.setVisible(true);
                addObstaclesButton.setVisible(true);
                selectStartButton.setVisible(true);
                selectEndButton.setVisible(true);
                startStopButton.setVisible(true);
                resetButton.setVisible(true);
                changeAlgorithm();
                boardPanel.reset();
            } else {
                mazeMode = true;
                modeButton.setText("PATH VISUALIZATION");
                MazeBoard board = new MazeBoard(widthLabel.getValue(), heightLabel.getValue());
                boardPanel.changeMode(board, true);
                algorithmBox.setVisible(false);
                generateObstaclesButton.setVisible(false);
                addObstaclesButton.setVisible(false);
                selectStartButton.setVisible(false);
                selectEndButton.setVisible(false);
                startStopButton.setVisible(false);
                resetButton.setVisible(false);
            }
        }
    }

    private void changeAlgorithm() {
        switch ((String)algorithmBox.getSelectedItem()) {
            case "A*": {
                AStarAlgorithm newAlgorithm = new AStarAlgorithm((PathBoard)boardPanel.getBoard());
                newAlgorithm.setStartPosition(boardPanel.algorithm.getStartSquare());
                newAlgorithm.setEndPosition(boardPanel.algorithm.getEndSquare());
                boardPanel.algorithm = newAlgorithm;
                break;
            }
            case "Dijkstra": {
                DijkstraAlgorithm newAlgorithm = new DijkstraAlgorithm((PathBoard)boardPanel.getBoard());
                newAlgorithm.setStartPosition(boardPanel.algorithm.getStartSquare());
                newAlgorithm.setEndPosition(boardPanel.algorithm.getEndSquare());
                boardPanel.algorithm = newAlgorithm;
                break;
            }
            case "BreadthFirstSearch": {
                BFSAlgorithm newAlgorithm = new BFSAlgorithm((PathBoard)boardPanel.getBoard());
                newAlgorithm.setStartPosition(boardPanel.algorithm.getStartSquare());
                newAlgorithm.setEndPosition(boardPanel.algorithm.getEndSquare());
                boardPanel.algorithm = newAlgorithm;
                break;
            }
        }
    }
}
