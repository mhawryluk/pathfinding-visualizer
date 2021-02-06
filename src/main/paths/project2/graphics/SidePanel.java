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
    private final JButton generateObstaclesButton = new JButton("GENERATE OBSTACLES");
    private final JButton modeButton = new JButton("MAZE EXPLORER");
    private final JButton skipGeneratingButton = new JButton("SKIP GENERATING");
    private final JButton showMazeButton = new JButton("SHOW MAZE");
    private boolean mazeMode = false;
    private final BoardPanel boardPanel;
    public final Color BACKGROUND_COLOR = Color.white;

    public SidePanel(BoardPanel boardPanel){
        this.boardPanel = boardPanel;

        String[] algorithms = {"A*", "Dijkstra", "BreadthFirstSearch"};

        algorithmBox = new JComboBox<>(algorithms);
        algorithmBox.setOpaque(true);
        algorithmBox.setAlignmentX(SwingConstants.CENTER);
        algorithmBox.setAlignmentY(SwingConstants.CENTER);
        ((JLabel)algorithmBox.getRenderer()).setHorizontalAlignment(JLabel.CENTER);

        generateObstaclesButton.addActionListener(this);
        addObstaclesButton.addActionListener(this);
        selectStartButton.addActionListener(this);
        selectEndButton.addActionListener(this);
        startStopButton.addActionListener(this);
        resetButton.addActionListener(this);
        modeButton.addActionListener(this);
        showMazeButton.addActionListener(this);
        skipGeneratingButton.addActionListener(this);

        showMazeButton.setFocusable(false);
        skipGeneratingButton.setFocusable(false);

        showMazeButton.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 10));
        skipGeneratingButton.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 10));

        add(modeButton);
        add(widthLabel);
        add(heightLabel);
        add(resetButton);
        add(generateObstaclesButton);
        add(addObstaclesButton);
        add(selectStartButton);
        add(selectEndButton);
        add(algorithmBox);
        add(startStopButton);

        setBackground(BACKGROUND_COLOR);

        for (Component component: getComponents()){
            component.setBackground(BACKGROUND_COLOR);
        }

        setPreferredSize(new Dimension(200, 500));
        setLayout(new GridLayout(11, 1, 5, 5));
    }

    @Override
    public void actionPerformed(ActionEvent e) {

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

            boardPanel.setDimensions(widthLabel.getValue(), heightLabel.getValue());
            boardPanel.reset();

            if (!mazeMode){
                boardPanel.algorithmRunning = false;
                startStopButton.setText("START");

                boardPanel.selectingStart = false;
                selectStartButton.setSelected(false);

                boardPanel.selectingEnd = false;
                selectEndButton.setSelected(false);

                boardPanel.placingObstacles = false;
                addObstaclesButton.setText("SELECT OBSTACLES");
            }
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

        if (e.getSource() == skipGeneratingButton) {
            boardPanel.skipMazeGenerating();
        }

        if (e.getSource() == showMazeButton){
            boardPanel.changeMazeVisibility();
        }

        if (e.getSource() == modeButton){
            if (mazeMode){
                mazeMode = false;
                modeButton.setText("MAZE EXPLORER");
                PathBoard board = new PathBoard(widthLabel.getValue(), heightLabel.getValue());
                boardPanel.changeMode(board, false);

                remove(skipGeneratingButton);
                remove(showMazeButton);
                add(generateObstaclesButton);
                add(addObstaclesButton);
                add(selectStartButton);
                add(selectEndButton);
                add(algorithmBox);
                add(startStopButton);

                changeAlgorithm();
                boardPanel.reset();
            } else {
                mazeMode = true;
                modeButton.setText("PATH VISUALIZATION");
                MazeBoard board = new MazeBoard(widthLabel.getValue(), heightLabel.getValue());
                boardPanel.changeMode(board, true);

                remove(algorithmBox);
                remove(generateObstaclesButton);
                remove(addObstaclesButton);
                remove(selectStartButton);
                remove(selectEndButton);
                remove(startStopButton);
                add(skipGeneratingButton);
                add(showMazeButton);
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
