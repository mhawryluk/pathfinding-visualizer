package paths.project2.engine;

public abstract class PathFindingAlgorithm {

    protected Square current;
    protected Square start;
    protected Square end;
    protected Board board;
    protected boolean ended = false;

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
}
