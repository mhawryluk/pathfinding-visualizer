package paths.project2.engine;

import java.util.concurrent.ThreadLocalRandom;

public class PathBoard extends Board {

    public PathBoard(int width, int height) {
        super(width, height);
    }

    public void generateRandomObstacles(int maxNumberOfObstacles) {
        for (int i = 0; i < maxNumberOfObstacles; i++) {
            int x = ThreadLocalRandom.current().nextInt(0, width);
            int y = ThreadLocalRandom.current().nextInt(0, height);

            Square square = getSquareAt(new Vector2d(x, y));
            if (square.getState() == SquareState.BLANK)
                square.setState(SquareState.OBSTACLE);
        }
    }
}
