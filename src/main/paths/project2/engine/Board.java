package paths.project2.engine;

public abstract class Board {

    public int width;
    public int height;
    public final Vector2d lowerLeft = new Vector2d(0,0);
    public Vector2d upperRight;
    protected Square[][] board;

    public Square getSquareAt(int i, int j){
        return board[i][j];
    }

    public Square getSquareAt(Vector2d position){
        return board[position.x][position.y];
    }

    public boolean isWithinBoard(Vector2d field) {
        return field.follows(lowerLeft) && field.precedes(upperRight);
    }
}
