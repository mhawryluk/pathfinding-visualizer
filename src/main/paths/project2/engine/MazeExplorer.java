package paths.project2.engine;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MazeExplorer implements KeyListener {

    private final MazeBoard board;
    private Square currentSquare;

    public MazeExplorer(MazeBoard board) {
        this.board = board;
        currentSquare = board.getSquareAt(new Vector2d(0, 0));
        currentSquare.setState(SquareState.MAZECRAWLER);
        board.getSquareAt(new Vector2d(board.width - 1, board.height - 1)).setState(SquareState.END);
    }

    public Square getCurrentSquare() {
        return currentSquare;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP && !currentSquare.up) {
            currentSquare.setState(SquareState.BLANK);
            currentSquare = board.getSquareAt(currentSquare.getPosition().add(new Vector2d(0, -1)));
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN && !currentSquare.down) {
            currentSquare.setState(SquareState.BLANK);
            currentSquare = board.getSquareAt(currentSquare.getPosition().add(new Vector2d(0, 1)));
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT && !currentSquare.left) {

            currentSquare.setState(SquareState.BLANK);
            currentSquare = board.getSquareAt(currentSquare.getPosition().add(new Vector2d(-1, 0)));
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT && !currentSquare.right) {
            currentSquare.setState(SquareState.BLANK);
            currentSquare = board.getSquareAt(currentSquare.getPosition().add(new Vector2d(1, 0)));
        }

        if (currentSquare.getState() == SquareState.END) {
            JOptionPane.showMessageDialog(null, "congrats! you've reached the end", "the end", JOptionPane.INFORMATION_MESSAGE);
        }

        currentSquare.setState(SquareState.MAZECRAWLER);
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
}
