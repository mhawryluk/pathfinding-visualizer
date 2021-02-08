package paths.project2.engine;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;

public abstract class PathFindingAlgorithm {

    protected Square current;
    protected Square start;
    protected Square end;
    protected PathBoard board;
    protected boolean ended;


    public PathFindingAlgorithm(PathBoard board){
        this.board = board;
        this.start = board.getSquareAt(0,0);
        this.start.state = SquareState.START;

        this.end = board.getSquareAt(board.width-1, board.height-1);
        this.end.state = SquareState.END;
    }

    abstract public boolean step();

    public void setStartPosition(Vector2d position){
        this.start.state = SquareState.BLANK;
        this.start = board.getSquareAt(position);
        this.start.state = SquareState.START;
    }

    public void setEndPosition(Vector2d position){
        this.end.state = SquareState.BLANK;
        this.end = board.getSquareAt(position);
        this.end.state = SquareState.END;
    }

    public void setStartPosition(Square square){
        this.start.state = SquareState.BLANK;
        this.start = square;
        this.start.state = SquareState.START;
    }

    public void setEndPosition(Square square){
        this.end.state = SquareState.BLANK;
        this.end = square;
        this.end.state = SquareState.END;
    }

    public Square getStartSquare(){
        return start;
    }

    public Square getEndSquare(){
        return end;
    }

    protected ArrayList<Square> getNeighbors(Square square){
        ArrayList <Square> neighbors = new ArrayList<>();
        int x = square.getX();
        int y = square.getY();

        for (int i = x - 1; i <= x + 1; i++){
            for (int j = y - 1; j <= y + 1; j++){
                if ((i != x || j != x) && board.isWithinBoard(new Vector2d(i, j))){
                    Square neighbor = board.getSquareAt(i, j);
                    if (neighbor.state == SquareState.OBSTACLE) continue;

                    if (i != x && j != y){
                        if (board.getSquareAt(i, y).state == SquareState.OBSTACLE &&
                        board.getSquareAt(x, j).state == SquareState.OBSTACLE){
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

    protected final void getPath(){
        while (current != start) {
            current.state = SquareState.PATH;
            current = current.cameFrom;
        }
        ended = true;
    }

    protected final void pathNotFound(){
        JOptionPane.showMessageDialog(null,
                "end square is not reachable from the starting point",
                "no path",
                JOptionPane.INFORMATION_MESSAGE);
        ended = true;
    }
}
