package paths.project2.graphics;

import paths.project2.engine.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class BoardPanel extends JPanel implements ActionListener {
    public Board board;
    private int squareSize;
    public final Timer timer;
    public PathFindingAlgorithm algorithm;
    public boolean placingObstacles = false;
    public boolean algorithmRunning = false;
    public boolean selectingStart = false;
    public boolean selectingEnd = false;

    public BoardPanel(Board board, PathFindingAlgorithm algorithm){

        this.board = board;
        this.squareSize = Math.min(1000/board.width, 600/board.height);
        timer = new Timer(50, this);
        timer.start();

        this.algorithm = algorithm;

        setBackground(Color.WHITE);
        setBounds(0, 0, squareSize * board.width, squareSize * board.height);
        setPreferredSize(new Dimension( board.width * squareSize, board.height * squareSize));
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
                g2D.fillRect(square.getX() * squareSize, square.getY() * squareSize, squareSize, squareSize);

                g2D.setPaint(Color.black);

                if (square.up){
                    g2D.drawLine(square.getX() * squareSize, square.getY() * squareSize,
                            (square.getX() + 1) * squareSize, square.getY() * squareSize);
                }

                if (square.down){
                    g2D.drawLine(square.getX() * squareSize, (square.getY() + 1)* squareSize,
                            (square.getX()+1) * squareSize, (square.getY() + 1)* squareSize);
                }

                if (square.left){
                    g2D.drawLine(square.getX() * squareSize, square.getY() * squareSize,
                            square.getX() * squareSize, (square.getY() + 1)* squareSize);
                }

                if (square.right){
                    g2D.drawLine((square.getX() + 1)* squareSize, square.getY() * squareSize,
                            (square.getX() + 1)* squareSize, (square.getY() + 1)* squareSize);
                }
                //g2D.drawRect(square.getX() * squareSize, square.getY() * squareSize, squareSize, squareSize);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == timer && algorithmRunning) {
            algorithm.step();
        }
        repaint();
    }

    private Color getSquareColor(Square square){
        SquareState state = square.getState();
        return state.color;
    }

    public void reset(){
        this.board = new PathBoard(board.width, board.height);
        algorithm = new AStarAlgorithm((PathBoard)this.board);
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

    public void newBoardDimensions(int width, int height) {
        board = new PathBoard(width, height);
        squareSize = Math.min(1000/board.width, 600/ board.height);
        setBounds(300, 0, squareSize* board.width, squareSize* board.width);
        reset();
    }

    public void generateObstacles() {
        ((PathBoard)board).generateRandomObstacles((board.width * board.height) / 3);
    }

    public void changeMode(Board board){
        this.board = board;
        this.squareSize = Math.min(1000/board.width, 600/board.height);
        timer.start();

        setBackground(Color.WHITE);
        setBounds(0, 0, squareSize * board.width, squareSize * board.height);
        setPreferredSize(new Dimension( board.width * squareSize, board.height * squareSize));
        setLayout(null);
    }
}
