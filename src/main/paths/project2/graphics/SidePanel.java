package paths.project2.graphics;

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
    private final Color BACKGROUND_COLOR = Color.WHITE;
    private final BoardPanel boardPanel;

    public SidePanel(BoardPanel boardPanel){
        this.boardPanel = boardPanel;

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
                startStopButton.setText("START");
            } else {
                boardPanel.algorithmRunning = true;
                startStopButton.setText("STOP");
            }
        }
        if (e.getSource() == resetButton) {
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
