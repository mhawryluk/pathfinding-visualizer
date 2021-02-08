package paths.project2.engine;

import java.util.List;
import java.util.Stack;

public class MazeBoard extends Board {

    private final Stack<MazeGeneratorSquare> stack = new Stack<>();

    public MazeBoard(int width, int height) {
        super(width, height);
        stack.push(new MazeGeneratorSquare(0, 0, 0, 1));
    }

    public boolean generateMazeStep() {
        if (stack.isEmpty()) {
            clearBoard();
            return false;
        }

        MazeGeneratorSquare current = stack.pop();

        clearBoard();
        Square currentSquare = board[current.x][current.y];
        currentSquare.setState(SquareState.MAZECRAWLER);
        currentSquare.removeWall(current.cameFrom(), board[current.cameFromX][current.cameFromY]);

        List<int[]> neighbors = current.getNeighbors();

        for (int[] neighbor : neighbors) {
            if (isWithinBoard(new Vector2d(neighbor[0], neighbor[1]))
                    && !(board[neighbor[0]][neighbor[1]].visited)) {
                stack.push(new MazeGeneratorSquare(current.x, current.y, neighbor[0], neighbor[1]));
                board[neighbor[0]][neighbor[1]].visited = true;
            }
        }
        return true;
    }

    private void clearBoard() {
        for (Square[] squares : board)
            for (Square square : squares)
                square.setState(SquareState.BLANK);
    }
}
