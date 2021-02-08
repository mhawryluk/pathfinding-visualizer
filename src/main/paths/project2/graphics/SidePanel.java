package paths.project2.graphics;

import paths.project2.engine.MazeBoard;
import paths.project2.engine.PathBoard;
import paths.project2.engine.VisualizationState;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

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
    private final JToggleButton showMazeButton = new JToggleButton("SHOW MAZE");
    private final JButton restartButton = new JButton("RESTART");
    private boolean mazeMode = false;
    private final BoardPanel boardPanel;

    public SidePanel(BoardPanel boardPanel) {
        this.boardPanel = boardPanel;

        String[] algorithms = {"A*", "Dijkstra", "BreadthFirstSearch", "DepthFirstSearch"};
        algorithmBox = new JComboBox<>(algorithms);
        algorithmBox.addActionListener(this);
        algorithmBox.setOpaque(true);
        algorithmBox.setAlignmentX(SwingConstants.CENTER);
        algorithmBox.setAlignmentY(SwingConstants.CENTER);
        ((JLabel) algorithmBox.getRenderer()).setHorizontalAlignment(JLabel.CENTER);

        generateObstaclesButton.addActionListener(this);
        addObstaclesButton.addActionListener(this);
        selectStartButton.addActionListener(this);
        selectEndButton.addActionListener(this);
        startStopButton.addActionListener(this);
        resetButton.addActionListener(this);
        modeButton.addActionListener(this);
        showMazeButton.addActionListener(this);
        skipGeneratingButton.addActionListener(this);
        restartButton.addActionListener(this);

        showMazeButton.setFocusable(false);
        skipGeneratingButton.setFocusable(false);

        Font font = new Font(Font.MONOSPACED, Font.PLAIN, 10);
        showMazeButton.setFont(font);
        skipGeneratingButton.setFont(font);
        restartButton.setFont(font);

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

        Color BACKGROUND_COLOR = Color.white;
        setBackground(BACKGROUND_COLOR);

        for (Component component : getComponents()) {
            component.setBackground(BACKGROUND_COLOR);
        }

        setPreferredSize(new Dimension(200, 500));
        setLayout(new GridLayout(11, 1, 5, 5));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == generateObstaclesButton) {
            boardPanel.generateObstacles();
            boardPanel.repaint();
        }

        if (e.getSource() == algorithmBox) {
            if (boardPanel.getState() != VisualizationState.ALGORITHM_RUNNING)
                restart();
        }

        if (e.getSource() == startStopButton) {
            buttonReset();
            if (boardPanel.getState() == VisualizationState.ALGORITHM_RUNNING) {
                boardPanel.pause();
                startStopButton.setText("START");
            } else {
                boardPanel.resume();
                startStopButton.setText("PAUSE");
                add(restartButton);
            }
        }

        if (e.getSource() == restartButton) {
            restart();
        }

        if (e.getSource() == resetButton) {
            reset();
        }

        if (e.getSource() == addObstaclesButton) {
            if (boardPanel.getState() == VisualizationState.OBSTACLES_PLACING) {
                boardPanel.setState(VisualizationState.DEFAULT);
                addObstaclesButton.setSelected(false);
            } else if (boardPanel.getState() != VisualizationState.ALGORITHM_RUNNING) {
                boardPanel.setState(VisualizationState.OBSTACLES_PLACING);
                selectStartButton.setSelected(false);
                selectEndButton.setSelected(false);
            } else {
                addObstaclesButton.setSelected(false);
            }
        }

        if (e.getSource() == selectStartButton) {
            if (boardPanel.getState() != VisualizationState.ALGORITHM_RUNNING) {
                boardPanel.setState(VisualizationState.START_SELECTING);
                selectEndButton.setSelected(false);
                addObstaclesButton.setSelected(false);
            } else {
                selectStartButton.setSelected(false);
            }
        }

        if (e.getSource() == selectEndButton) {
            if (boardPanel.getState() != VisualizationState.ALGORITHM_RUNNING) {
                boardPanel.setState(VisualizationState.END_SELECTING);
                selectStartButton.setSelected(false);
                addObstaclesButton.setSelected(false);
            } else {
                selectEndButton.setSelected(false);
            }
        }

        if (e.getSource() == skipGeneratingButton) {
            boardPanel.skipMazeGenerating();
            skipGeneratingButton.setVisible(false);
        }

        if (e.getSource() == showMazeButton) {
            boardPanel.changeMazeVisibility();
        }

        if (e.getSource() == modeButton) {
            if (mazeMode) {
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
                reset();
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
                remove(restartButton);
                add(showMazeButton);
                add(skipGeneratingButton);
            }
        }
    }

    private void restart() {
        boardPanel.pause();
        startStopButton.setText("START");
        changeAlgorithm();
        boardPanel.restart();
        startStopButton.setVisible(true);
    }

    private void buttonReset() {
        addObstaclesButton.setSelected(false);
        selectStartButton.setSelected(false);
        selectEndButton.setSelected(false);
    }

    private void changeAlgorithm() {
        boardPanel.changeAlgorithm((String) Objects.requireNonNull(algorithmBox.getSelectedItem()));
    }

    private void reset() {
        boardPanel.setDimensions(widthLabel.getValue(), heightLabel.getValue());
        boardPanel.reset();

        if (!mazeMode) {
            changeAlgorithm();
            boardPanel.setState(VisualizationState.DEFAULT);
            startStopButton.setText("START");
            addObstaclesButton.setText("SELECT OBSTACLES");

            remove(restartButton);
            startStopButton.setVisible(true);
            selectStartButton.setSelected(false);
            selectEndButton.setSelected(false);
            addObstaclesButton.setSelected(false);
        } else {
            skipGeneratingButton.setVisible(true);
        }
    }

    public void algorithmEnded() {
        startStopButton.setVisible(false);
    }

    public void hideSkipButton() {
        skipGeneratingButton.setVisible(false);
    }
}
