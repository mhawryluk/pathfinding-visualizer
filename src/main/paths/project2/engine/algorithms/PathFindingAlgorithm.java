package paths.project2.engine.algorithms;

import paths.project2.engine.PathBoard;
import paths.project2.engine.Square;
import paths.project2.engine.SquareState;
import paths.project2.engine.Vector2d;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;

public abstract class PathFindingAlgorithm {

    protected Square current;
    protected Square start;
    protected Square end;
    protected PathBoard board;
    protected boolean ended;


    public PathFindingAlgorithm(PathBoard board) {
        this.board = board;
        start = board.getSquareAt(new Vector2d(0, 0));
        start.setState(SquareState.START);

        end = board.getSquareAt(new Vector2d(board.width - 1, board.height - 1));
        end.setState(SquareState.END);
    }

    abstract public boolean step();

    public void setStartPosition(Vector2d position) {
        if (!board.isWithinBoard(position)) {
            position = new Vector2d(0, 0);
        }

        start.setState(SquareState.BLANK);
        start = board.getSquareAt(position);
        start.setState(SquareState.START);
    }

    public void setEndPosition(Vector2d position) {
        if (!board.isWithinBoard(position)) {
            position = new Vector2d(board.width - 1, board.height - 1);
        }

        end.setState(SquareState.BLANK);
        end = board.getSquareAt(position);
        end.setState(SquareState.END);
    }

    public Vector2d getStartSquare() {
        return start.getPosition();
    }

    public Vector2d getEndSquare() {
        return end.getPosition();
    }

    protected ArrayList<Square> getNeighbors(Square square) {
        ArrayList<Square> neighbors = new ArrayList<>();

        Vector2d position = square.getPosition();
        int x = position.x;
        int y = position.y;

        for (int i = x - 1; i <= x + 1; i++) {
            for (int j = y - 1; j <= y + 1; j++) {
                if ((i != x || j != y) && board.isWithinBoard(new Vector2d(i, j))) {
                    Square neighbor = board.getSquareAt(new Vector2d(i, j));
                    if (neighbor.getState() == SquareState.OBSTACLE) continue;

                    if (i != x && j != y) {
                        if (board.getSquareAt(new Vector2d(i, y)).getState() == SquareState.OBSTACLE &&
                                board.getSquareAt(new Vector2d(x, j)).getState() == SquareState.OBSTACLE) {
                            continue;
                        }
                    }
                    neighbors.add(neighbor);
                }
            }
        }

        Collections.shuffle(neighbors);
        return neighbors;
    }

    protected final void getPath() {
        while (current != start) {
            current.setState(SquareState.PATH);
            current = current.cameFrom;
        }
        ended = true;
    }

    protected final void pathNotFound() {
        JOptionPane.showMessageDialog(null,
                "end square is not reachable from the starting point",
                "no path",
                JOptionPane.INFORMATION_MESSAGE);
        ended = true;
    }
}
