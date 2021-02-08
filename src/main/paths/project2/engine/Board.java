package paths.project2.engine;

public abstract class Board {

    protected Square[][] board;
    public final int width;
    public final int height;
    private final Vector2d lowerLeft = new Vector2d(0, 0);
    private final Vector2d upperRight;

    protected Board(int width, int height) {
        this.width = width;
        this.height = height;
        upperRight = new Vector2d(width - 1, height - 1);

        board = new Square[width][height];
        for (int x = 0; x < width; x++)
            for (int y = 0; y < height; y++)
                board[x][y] = new Square(x, y);
    }

    public Square getSquareAt(Vector2d position) {
        return board[position.x][position.y];
    }

    public boolean isWithinBoard(Vector2d field) {
        return field.follows(lowerLeft) && field.precedes(upperRight);
    }

    public void restart() {
        for (Square[] squares : board)
            for (Square square : squares) {
                square.reset();
                SquareState squareState = square.getState();
                if (squareState == SquareState.OPEN || squareState == SquareState.PATH || squareState == SquareState.CLOSED)
                    square.setState(SquareState.BLANK);
            }
    }
}
