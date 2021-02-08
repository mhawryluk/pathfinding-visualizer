package paths.project2.engine;

public abstract class Board {

    public final int width;
    public final int height;
    public final Vector2d lowerLeft = new Vector2d(0, 0);
    public Vector2d upperRight;
    protected Square[][] board;

    protected Board(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public Square getSquareAt(int i, int j) {
        return board[i][j];
    }

    public Square getSquareAt(Vector2d position) {
        return board[position.x][position.y];
    }

    public boolean isWithinBoard(Vector2d field) {
        return field.follows(lowerLeft) && field.precedes(upperRight);
    }

    public void restart() {
        for (Square[] squares : board) {
            for (Square square : squares) {
                square.reset();
                if (square.state == SquareState.OPEN || square.state == SquareState.PATH
                        || square.state == SquareState.CLOSED) {
                    square.state = SquareState.BLANK;
                }
            }
        }
    }
}
