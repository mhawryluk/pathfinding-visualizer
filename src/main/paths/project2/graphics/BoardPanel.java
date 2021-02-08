package paths.project2.graphics;

import paths.project2.engine.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class BoardPanel extends JPanel implements ActionListener {
    public Board board;
    private SidePanel sidePanel;
    private int squareSize;
    public final Timer timer;
    public PathFindingAlgorithm algorithm;
    private MazeExplorer mazeExplorer;
    private int width;
    private int height;
    private final int panelWidth = Toolkit.getDefaultToolkit().getScreenSize().width - 700;
    private final int panelHeight = Toolkit.getDefaultToolkit().getScreenSize().height - 200;

    public boolean placingObstacles;
    public boolean algorithmRunning;
    public boolean mazeGenerating;
    public boolean selectingStart;
    public boolean selectingEnd;
    public boolean mazeCrawling;
    private boolean showingMaze;


    public BoardPanel(Board board, PathFindingAlgorithm algorithm){

        this.board = board;
        setDimensions(board.width, board.height);
        timer = new Timer(50, this);
        timer.start();

        this.algorithm = algorithm;

        setBounds(0, 0, squareSize * width, squareSize * height);
        setPreferredSize(new Dimension( width * squareSize, height * squareSize));
        setLayout(null);

        addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (placingObstacles) {
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

                if (placingObstacles) {
                    Vector2d clickedField = new Vector2d(e.getX() / squareSize, e.getY() / squareSize);
                    placeObstacle(clickedField);
                }

                if (selectingStart){
                    Vector2d clickedField = new Vector2d(e.getX() / squareSize, e.getY() / squareSize);
                    setStartPosition(clickedField);
                }

                if (selectingEnd){
                    Vector2d clickedField = new Vector2d(e.getX() / squareSize, e.getY() / squareSize);
                    setEndPosition(clickedField);
                }
            }
        });
    }

    public void paint(Graphics g) {

        Graphics2D g2D = (Graphics2D) g;
        super.repaint();

        for (int i = 0; i < board.width; i++){
            for (int j = 0; j < board.height; j++){
                Square square = board.getSquareAt(i,j);
                g2D.setPaint(getSquareColor(square));

                if (mazeCrawling && !showingMaze){
                    if (mazeExplorer.getCurrentSquare().dist(new Vector2d(i, j)) > 2){
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
                if (square.down) g2D.drawLine(square.getX() * squareSize, (square.getY() + 1)* squareSize,
                            (square.getX()+1) * squareSize, (square.getY() + 1)* squareSize);
                if (square.left) g2D.drawLine(square.getX() * squareSize, square.getY() * squareSize,
                            square.getX() * squareSize, (square.getY() + 1)* squareSize);
                if (square.right) g2D.drawLine((square.getX() + 1)* squareSize, square.getY() * squareSize,
                            (square.getX() + 1)* squareSize, (square.getY() + 1)* squareSize);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == timer && algorithmRunning) {
            boolean ongoing = algorithm.step();
            if (!ongoing) {
                algorithmRunning = false;
                sidePanel.algorithmEnded();
            }
        }
        if (e.getSource() == timer && mazeGenerating) {
            boolean ongoing = ((MazeBoard) board).generateMazeStep();
            if (!ongoing) {
                mazeGenerating = false;
                mazeCrawling = true;
                mazeExplorer = new MazeExplorer((MazeBoard) board);
                addKeyListener(mazeExplorer);
                requestFocus();
            }
        }
        repaint();
    }

    public void skipMazeGenerating(){
        while (((MazeBoard) board).generateMazeStep());
    }

    private Color getSquareColor(Square square){
        SquareState state = square.getState();
        return state.color;
    }

    public void reset(){
        if (mazeGenerating || mazeCrawling){
            board = new MazeBoard(width, height);
            mazeCrawling = false;
            mazeGenerating = true;
        } else {
            this.board = new PathBoard(width, height);
            algorithm = new AStarAlgorithm((PathBoard)this.board);
            algorithmRunning = false;
        }
    }

    public void restart(){
        board.restart();
        algorithmRunning = false;
    }

    protected final void placeObstacle(Vector2d clickedField){
        Square squareSelected = board.getSquareAt(clickedField);
        squareSelected.state = SquareState.OBSTACLE;
    }

    protected final void setStartPosition(Vector2d clickedField){
        algorithm.setStartPosition(clickedField);
    }

    protected final void setEndPosition(Vector2d clickedField){
        algorithm.setEndPosition(clickedField);
    }

    public Board getBoard() {
        return board;
    }

    public void changeMazeVisibility(){
        showingMaze = !showingMaze;
    }

    public void generateObstacles() {
        ((PathBoard)board).generateRandomObstacles((board.width * board.height) / 3);
    }

    public void changeMode(Board board, boolean mazeGenerating){
        this.board = board;
        setDimensions(board.width, board.height);
        timer.start();

        this.mazeGenerating = mazeGenerating;

        setBounds(0, 0, squareSize * board.width, squareSize * board.height);
        setPreferredSize(new Dimension( board.width * squareSize, board.height * squareSize));
        setLayout(null);
    }

    public void setDimensions(int width, int height){
        this.width = width;
        this.height = height;
        this.squareSize = Math.min(panelWidth/width, panelHeight/height);
    }

    public void setSidePanel(SidePanel panel){
        sidePanel = panel;
    }
}
