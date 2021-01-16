package paths.project2.engine;

public class Square {
    private Vector2d position;
    public SquareState state;
    public double g;
    public double f;
    public double dist;
    public Square cameFrom;

    public Square(int x, int y){
        position = new Vector2d(x, y);
        this.g = Double.POSITIVE_INFINITY;
        this.f = Double.POSITIVE_INFINITY;
        this.state = SquareState.BLANK;
    }

    public SquareState getState(){
        return state;
    }

    public int getX(){
        return position.x;
    }

    public int getY(){
        return position.y;
    }
}
