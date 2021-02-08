package paths.project2.graphics;

import paths.project2.engine.*;
import paths.project2.engine.algorithms.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class BoardPanel extends JPanel implements ActionListener {
    public Board board;
    private SidePanel sidePanel;
    private final Timer timer;
    private PathFindingAlgorithm algorithm;
    private MazeExplorer mazeExplorer;

    private int squareSize;
    private int width;
    private int height;
    private Vector2d upperLeft;

    private final int PANEL_WIDTH = Toolkit.getDefaultToolkit().getScreenSize().width - 700;
    private final int PANEL_HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().height - 200;

    private VisualizationState state = VisualizationState.DEFAULT;
    private boolean showingMaze;


    public BoardPanel(PathBoard board) {
        this.board = board;
        this.algorithm = new AStarAlgorithm(board);

        setDimensions(board.width, board.height);
        timer = new Timer(50, this);
        timer.start();

        setBounds(0, 0, squareSize * width + 5, squareSize * height + 5);
        setPreferredSize(new Dimension(width * squareSize, height * squareSize));
        setLayout(null);

        addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (state == VisualizationState.OBSTACLES_PLACING) {
                    Vector2d clickedField = new Vector2d((e.getX() - upperLeft.x) / squareSize, (e.getY() - upperLeft.y) / squareSize);
                    if (board.isWithinBoard(clickedField))
                        placeObstacle(clickedField);
                }
            }

            @Override
            public void mouseMoved(MouseEvent e) {
            }
        });

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Vector2d clickedField = new Vector2d((e.getX() - upperLeft.x) / squareSize, (e.getY() - upperLeft.y) / squareSize);
                switch (state) {
                    case OBSTACLES_PLACING:
                        placeObstacle(clickedField);
                        break;

                    case START_SELECTING:
                        setStartPosition(clickedField);
                        break;

                    case END_SELECTING:
                        setEndPosition(clickedField);
                        break;
                }
            }
        });
    }

    public void paint(Graphics g) {
        Graphics2D g2D = (Graphics2D) g;
        super.repaint();

        upperLeft = new Vector2d((getWidth() - squareSize * board.width - 5) / 2, (getHeight() - squareSize * board.height - 5) / 2);

        for (int x = 0; x < board.width; x++) {
            for (int y = 0; y < board.height; y++) {
                Square square = board.getSquareAt(new Vector2d(x, y));
                g2D.setPaint(getSquareColor(square));

                Vector2d paintPosition = upperLeft.add(new Vector2d(x * squareSize, y * squareSize));

                if (state == VisualizationState.MAZE_CRAWLING && !showingMaze) {
                    if (mazeExplorer.getCurrentSquare().dist(new Vector2d(x, y)) > 2) {
                        g2D.setPaint(new Color(0x00383D));
                        g2D.fillRect(paintPosition.x, paintPosition.y, squareSize, squareSize);
                        continue;
                    }
                }

                g2D.fillRect(paintPosition.x, paintPosition.y, squareSize, squareSize);

                g2D.setPaint(new Color(137, 176, 174));
                g2D.setStroke(new BasicStroke(5));

                if (square.up) g2D.drawLine(paintPosition.x, paintPosition.y,
                        paintPosition.x + squareSize, paintPosition.y);
                if (square.down) g2D.drawLine(paintPosition.x, paintPosition.y + squareSize,
                        paintPosition.x + squareSize, paintPosition.y + squareSize);
                if (square.left) g2D.drawLine(paintPosition.x, paintPosition.y,
                        paintPosition.x, paintPosition.y + squareSize);
                if (square.right) g2D.drawLine(paintPosition.x + squareSize, paintPosition.y,
                        paintPosition.x + squareSize, paintPosition.y + squareSize);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == timer && state == VisualizationState.ALGORITHM_RUNNING) {
            boolean ongoing = algorithm.step();
            if (!ongoing) {
                state = VisualizationState.DEFAULT;
                sidePanel.algorithmEnded();
            }
        }
        if (e.getSource() == timer && state == VisualizationState.MAZE_GENERATING) {
            boolean ongoing = ((MazeBoard) board).generateMazeStep();
            if (!ongoing) {
                state = VisualizationState.MAZE_CRAWLING;
                mazeExplorer = new MazeExplorer((MazeBoard) board);
                addKeyListener(mazeExplorer);
                requestFocus();
                sidePanel.hideSkipButton();
            }
        }
        repaint();
    }

    public void skipMazeGenerating() {
        while (((MazeBoard) board).generateMazeStep()) ;
    }

    private Color getSquareColor(Square square) {
        SquareState state = square.getState();
        return state.color;
    }

    public void reset() {
        if (state == VisualizationState.MAZE_GENERATING || state == VisualizationState.MAZE_CRAWLING) {
            board = new MazeBoard(width, height);
            state = VisualizationState.MAZE_GENERATING;
        } else {
            this.board = new PathBoard(width, height);
            state = VisualizationState.DEFAULT;
        }
    }

    public void restart() {
        board.restart();
        state = VisualizationState.DEFAULT;
    }

    protected final void placeObstacle(Vector2d clickedField) {
        Square squareSelected = board.getSquareAt(clickedField);
        squareSelected.setState(SquareState.OBSTACLE);
    }

    private void setStartPosition(Vector2d clickedField) {
        algorithm.setStartPosition(clickedField);
    }

    private void setEndPosition(Vector2d clickedField) {
        algorithm.setEndPosition(clickedField);
    }

    public void changeMazeVisibility() {
        showingMaze = !showingMaze;
    }

    public void generateObstacles() {
        ((PathBoard) board).generateRandomObstacles((board.width * board.height) / 3);
    }

    public void changeMode(Board board, boolean mazeGenerating) {
        this.board = board;
        setDimensions(board.width, board.height);
        timer.start();

        if (mazeGenerating) state = VisualizationState.MAZE_GENERATING;
        else state = VisualizationState.DEFAULT;

        setBounds(0, 0, squareSize * board.width + 5, squareSize * board.height + 5);
        setPreferredSize(new Dimension(board.width * squareSize + 5, board.height * squareSize + 5));
        setLayout(null);
    }

    public void setDimensions(int width, int height) {
        this.width = width;
        this.height = height;
        this.squareSize = Math.min(PANEL_WIDTH / width, PANEL_HEIGHT / height);
    }

    public void setSidePanel(SidePanel panel) {
        sidePanel = panel;
    }

    public void pause() {
        timer.stop();
        state = VisualizationState.DEFAULT;
    }

    public void resume() {
        timer.start();
        state = VisualizationState.ALGORITHM_RUNNING;
    }

    public void changeAlgorithm(String selectedAlgorithm) {
        PathFindingAlgorithm newAlgorithm;

        switch (selectedAlgorithm) {
            case "A*":
                newAlgorithm = new AStarAlgorithm((PathBoard) board);
                break;

            case "Dijkstra":
                newAlgorithm = new DijkstraAlgorithm((PathBoard) board);
                break;

            case "BreadthFirstSearch":
                newAlgorithm = new BFSAlgorithm((PathBoard) board);
                break;

            case "DepthFirstSearch":
                newAlgorithm = new DFSAlgorithm((PathBoard) board);
                break;

            default:
                throw new IllegalStateException("Unknown algorithm selected: " + selectedAlgorithm);
        }

        newAlgorithm.setStartPosition(algorithm.getStartSquare());
        newAlgorithm.setEndPosition(algorithm.getEndSquare());
        algorithm = newAlgorithm;
    }

    public VisualizationState getState() {
        return state;
    }

    public void setState(VisualizationState state) {
        this.state = state;
    }
}
