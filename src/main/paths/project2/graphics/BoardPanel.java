package paths.project2.graphics;

import paths.project2.engine.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class BoardPanel extends JPanel implements ActionListener {
    private Board board;
    private final int squareSize;
    private final Timer timer;
    private PathFindingAlgorithm algorithm;
    public boolean placingObstacles = false;
    public boolean algorithmRunning = false;
    public boolean selectingStart = false;
    public boolean selectingEnd = false;

    public BoardPanel(Board board, int squareSize, PathFindingAlgorithm algorithm){

        this.board = board;
        this.squareSize = squareSize;
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

                System.out.println(algorithm);
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
                g2D.drawRect(square.getX() * squareSize, square.getY() * squareSize, squareSize, squareSize);
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
        this.board = new Board(board.width, board.height);
        algorithm = new AStarAlgorithm(this.board);
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
}
