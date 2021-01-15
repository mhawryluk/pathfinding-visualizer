package paths.project2.graphics;

import paths.project2.engine.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class BoardPanel extends JPanel implements ActionListener {
    private Board board;
    private final int squareSize;
    private final Timer timer;
    private AStarAlgorithm algorithm;

    public BoardPanel(Board board, int squareSize){

        this.board = board;
        this.squareSize = squareSize;
        timer = new Timer(50, this);
        timer.start();

        algorithm = new AStarAlgorithm(board,
                new Vector2d(0,0),
                new Vector2d(19,19));

        setBackground(Color.WHITE);
        setBounds(0, 0, 800, 500);
        setLayout(null);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {

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

        if (e.getSource() == timer) {
            algorithm.step();
        }
        repaint();
    }

    private Color getSquareColor(Square square){
        SquareState state = square.getState();
        return state.color;
    }
}
