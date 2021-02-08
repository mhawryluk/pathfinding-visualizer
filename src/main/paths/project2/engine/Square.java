package paths.project2.engine;

public class Square {
    private final Vector2d position;
    public SquareState state;

    public double g;
    public double f;
    public double dist = Double.POSITIVE_INFINITY;
    public boolean visited = false;
    public Square cameFrom;

    public boolean up = true;
    public boolean down = true;
    public boolean right = true;
    public boolean left = true;

    public Square(int x, int y) {
        position = new Vector2d(x, y);
        this.g = Double.POSITIVE_INFINITY;
        this.f = Double.POSITIVE_INFINITY;
        this.state = SquareState.BLANK;
    }

    public SquareState getState() {
        return state;
    }

    public int getX() {
        return position.x;
    }

    public int getY() {
        return position.y;
    }

    public Vector2d getPosition() {
        return position;
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
}
