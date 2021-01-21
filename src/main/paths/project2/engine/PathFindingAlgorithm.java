package paths.project2.engine;

import java.util.ArrayList;

public abstract class PathFindingAlgorithm {

    protected Square current;
    protected Square start;
    protected Square end;
    protected PathBoard board;
    protected boolean ended = false;


    public PathFindingAlgorithm(PathBoard board){
        this.board = board;
        this.start = board.getSquareAt(0,0);
        this.start.state = SquareState.START;

        this.end = board.getSquareAt(board.width-1, board.height-1);
        this.end.state = SquareState.END;
    }

    abstract public void step();

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
                if ((i != x || j != x) && i >= 0 && j >= 0 &&
                        i < board.width && j < board.height){
                    Square neighbor = board.getSquareAt(i, j);
                    if (neighbor.state != SquareState.OBSTACLE) {
                        neighbors.add(neighbor);
                    }
                }
            }
        }
        return neighbors;
    }

    protected final void getPath(){
        current = current.cameFrom;
        while (current != start) {
            current.state = SquareState.PATH;
            current = current.cameFrom;
        }
        ended = true;
    }
}
