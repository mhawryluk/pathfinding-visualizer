package paths.project2.engine;

import java.util.concurrent.ThreadLocalRandom;

public class PathBoard extends Board{

    public PathBoard(int width, int height){
        this.width = width;
        this.height = height;

        upperRight = new Vector2d(width-1, height-1);

        board = new Square[width][height];
        for (int i = 0; i < width; i++){
            for (int j = 0; j < height; j++){
                board[i][j] = new Square(i, j);
            }
        }
    }

    public void generateRandomObstacles(int maxNumberOfObstacles){
        for (int i = 0; i < maxNumberOfObstacles; i++){
            int x = ThreadLocalRandom.current().nextInt(0, width);
            int y = ThreadLocalRandom.current().nextInt(0, height);

            Square square = getSquareAt(x, y);
            if (square.state == SquareState.BLANK){
                square.state = SquareState.OBSTACLE;
            }
        }
    }
}
