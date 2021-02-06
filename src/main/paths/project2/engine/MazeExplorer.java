package paths.project2.engine;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MazeExplorer implements KeyListener {

    private final MazeBoard board;
    private Square currentSquare;

    public MazeExplorer(MazeBoard board) {
        this.board = board;
        currentSquare = board.getSquareAt(new Vector2d(0,0));
        currentSquare.state = SquareState.MAZECRAWLER;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        System.out.println(e.getKeyCode());
    }

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println(e.getKeyCode());

        if (e.getKeyCode() == KeyEvent.VK_UP){
            if (!currentSquare.up){
                currentSquare.state = SquareState.BLANK;
                currentSquare = board.getSquareAt(currentSquare.getPosition().add(new Vector2d(0,-1)));
                currentSquare.state = SquareState.MAZECRAWLER;
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN){
            if (!currentSquare.down){
                currentSquare.state = SquareState.BLANK;
                currentSquare = board.getSquareAt(currentSquare.getPosition().add(new Vector2d(0,1)));
                currentSquare.state = SquareState.MAZECRAWLER;
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT){
            if (!currentSquare.left){
                currentSquare.state = SquareState.BLANK;
                currentSquare = board.getSquareAt(currentSquare.getPosition().add(new Vector2d(-1,0)));
                currentSquare.state = SquareState.MAZECRAWLER;
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT){
            if (!currentSquare.right){
                currentSquare.state = SquareState.BLANK;
                currentSquare = board.getSquareAt(currentSquare.getPosition().add(new Vector2d(1,0)));
                currentSquare.state = SquareState.MAZECRAWLER;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        System.out.println(e.getKeyCode());
    }
}
