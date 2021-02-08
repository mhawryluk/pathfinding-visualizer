package paths.project2.engine;

public class Square {
    private final Vector2d position;
    private SquareState state;

    public double g;
    public double f;
    public double dist = Double.POSITIVE_INFINITY;
    public boolean visited = false;
    public Square cameFrom;

    private boolean up = true;
    private boolean down = true;
    private boolean right = true;
    private boolean left = true;

    public Square(int x, int y) {
        position = new Vector2d(x, y);
        this.g = Double.POSITIVE_INFINITY;
        this.f = Double.POSITIVE_INFINITY;
        this.state = SquareState.BLANK;
    }

    public Vector2d getPosition() {
        return position;
    }

    public void setState(SquareState state) {
        this.state = state;
    }

    public SquareState getState() {
        return state;
    }

    public int dist(Vector2d position) {
        return Math.max(Math.abs(this.position.x - position.x), Math.abs(this.position.y - position.y));
    }

    public void reset() {
        g = Double.POSITIVE_INFINITY;
        f = Double.POSITIVE_INFINITY;
        dist = Double.POSITIVE_INFINITY;
        visited = false;
        cameFrom = null;
    }

    public void removeWall(char cameFrom, Square neighbor) {
        switch (cameFrom) {
            case 'l':
                neighbor.right = false;
                left = false;
                break;

            case 'r':
                neighbor.left = false;
                right = false;
                break;

            case 'd':
                neighbor.up = false;
                down = false;
                break;

            case 'u':
                neighbor.down = false;
                up = false;
                break;
        }
    }

    public boolean isWallUp() {
        return up;
    }

    public boolean isWallDown() {
        return down;
    }

    public boolean isWallRight() {
        return right;
    }

    public boolean isWallLeft() {
        return left;
    }
}
