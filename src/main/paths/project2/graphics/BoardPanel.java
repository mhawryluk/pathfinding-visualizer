package paths.project2.graphics;

import paths.project2.engine.*;
import paths.project2.engine.algorithms.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class BoardPanel extends JPanel implements ActionListener {
    private Board board;
    private SidePanel sidePanel;
    private final Timer timer;
    private PathFindingAlgorithm algorithm;
    private MazeExplorer mazeExplorer;

    private int squareSize;
    private int width;
    private int height;

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


        setBounds(0, 0, squareSize * width, squareSize * height);
        setPreferredSize(new Dimension(width * squareSize, height * squareSize));
        setLayout(null);

        addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (state == VisualizationState.OBSTACLES_PLACING) {
                    Vector2d clickedField = new Vector2d(e.getX() / squareSize, e.getY() / squareSize);
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

                Vector2d clickedField = new Vector2d(e.getX() / squareSize, e.getY() / squareSize);

                switch(state){
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

        for (int i = 0; i < board.width; i++) {
            for (int j = 0; j < board.height; j++) {
                Square square = board.getSquareAt(i, j);
                g2D.setPaint(getSquareColor(square));

                if (state == VisualizationState.MAZE_CRAWLING && !showingMaze) {
                    if (mazeExplorer.getCurrentSquare().dist(new Vector2d(i, j)) > 2) {
                        g2D.setPaint(new Color(0x00383D));
                        g2D.fillRect(square.getX() * squareSize, square.getY() * squareSize, squareSize, squareSize);
                        continue;
                    }
                }
                g2D.fillRect(square.getX() * squareSize, square.getY() * squareSize, squareSize, squareSize);


                g2D.setPaint(new Color(137, 176, 174));
                g2D.setStroke(new BasicStroke(5));

                if (square.up) g2D.drawLine(square.getX() * squareSize, square.getY() * squareSize,
                        (square.getX() + 1) * squareSize, square.getY() * squareSize);
                if (square.down) g2D.drawLine(square.getX() * squareSize, (square.getY() + 1) * squareSize,
                        (square.getX() + 1) * squareSize, (square.getY() + 1) * squareSize);
                if (square.left) g2D.drawLine(square.getX() * squareSize, square.getY() * squareSize,
                        square.getX() * squareSize, (square.getY() + 1) * squareSize);
                if (square.right) g2D.drawLine((square.getX() + 1) * squareSize, square.getY() * squareSize,
                        (square.getX() + 1) * squareSize, (square.getY() + 1) * squareSize);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == timer && state == VisualizationState.ALGORITHM_RUNNING){
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
        if (state == VisualizationState.MAZE_GENERATING || state == VisualizationState.MAZE_CRAWLING){
            board = new MazeBoard(width, height);
            state = VisualizationState.MAZE_GENERATING;
        } else {
            this.board = new PathBoard(width, height);
            algorithm = new AStarAlgorithm((PathBoard) this.board);
            state = VisualizationState.DEFAULT;
        }
    }

    public void restart() {
        board.restart();
        state = VisualizationState.DEFAULT;
    }

    protected final void placeObstacle(Vector2d clickedField) {
        Square squareSelected = board.getSquareAt(clickedField);
        squareSelected.state = SquareState.OBSTACLE;
    }

    protected final void setStartPosition(Vector2d clickedField) {
        algorithm.setStartPosition(clickedField);
    }

    protected final void setEndPosition(Vector2d clickedField) {
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

        setBounds(0, 0, squareSize * board.width, squareSize * board.height);
        setPreferredSize(new Dimension(board.width * squareSize, board.height * squareSize));
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
        switch (selectedAlgorithm) {
            case "A*": {
                AStarAlgorithm newAlgorithm = new AStarAlgorithm((PathBoard) board);
                newAlgorithm.setStartPosition(algorithm.getStartSquare());
                newAlgorithm.setEndPosition(algorithm.getEndSquare());
                algorithm = newAlgorithm;
                break;
            }
            case "Dijkstra": {
                DijkstraAlgorithm newAlgorithm = new DijkstraAlgorithm((PathBoard) board);
                newAlgorithm.setStartPosition(algorithm.getStartSquare());
                newAlgorithm.setEndPosition(algorithm.getEndSquare());
                algorithm = newAlgorithm;
                break;
            }
            case "BreadthFirstSearch": {
                BFSAlgorithm newAlgorithm = new BFSAlgorithm((PathBoard) board);
                newAlgorithm.setStartPosition(algorithm.getStartSquare());
                newAlgorithm.setEndPosition(algorithm.getEndSquare());
                algorithm = newAlgorithm;
                break;
            }
            case "DepthFirstSearch": {
                DFSAlgorithm newAlgorithm = new DFSAlgorithm((PathBoard) board);
                newAlgorithm.setStartPosition(algorithm.getStartSquare());
                newAlgorithm.setEndPosition(algorithm.getEndSquare());
                algorithm = newAlgorithm;
                break;
            }
        }
    }

    public VisualizationState getState() {
        return state;
    }

    public void setState(VisualizationState state){
        this.state = state;
    }
}
