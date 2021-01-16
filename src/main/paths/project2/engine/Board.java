package paths.project2.engine;

public class Board {

    public final int width;
    public final int height;
    private Square[][] board;

    public Board(int width, int height){
        this.width = width;
        this.height = height;

        board = new Square[width][height];
        for (int i = 0; i < width; i++){
            for (int j = 0; j < height; j++){
                board[i][j] = new Square(i, j);
            }
        }
    }

    public Square getSquareAt(int i, int j){
        return board[i][j];
    }

    public Square getSquareAt(Vector2d position){
        return board[position.x][position.y];
    }

}
