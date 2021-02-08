package paths.project2.engine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class MazeBoard extends Board {

    private final Stack<MazeSquare> stack = new Stack<>();

    public MazeBoard(int width, int height) {
        super(width, height);
        generateMaze();
    }

    public void generateMaze() {
        stack.push(new MazeSquare(0, 0, 1, 1));
    }

    public boolean generateMazeStep() {
        if (stack.isEmpty()) {
            for (Square[] squares : board) {
                for (Square square : squares) {
                    square.setState(SquareState.BLANK);
                }
            }
            return false;
        }

        MazeSquare current = stack.pop();
        int i = current.i;
        int j = current.j;
        int preI = current.preI;
        int preJ = current.preJ;

        for (Square[] squares : board) {
            for (Square square : squares) {
                square.setState(SquareState.BLANK);
            }
        }
        board[i][j].setState(SquareState.MAZECRAWLER);

        List<int[]> neighbors = new ArrayList<>();
        neighbors.addAll(Collections.singleton(new int[]{i + 1, j}));
        neighbors.addAll(Collections.singleton(new int[]{i - 1, j}));
        neighbors.addAll(Collections.singleton(new int[]{i, j - 1}));
        neighbors.addAll(Collections.singleton(new int[]{i, j + 1}));

        Collections.shuffle(neighbors);

        if (preI + 1 == i && preJ == j) {
            board[preI][preJ].right = false;
            board[i][j].left = false;
        }

        if (preI - 1 == i && preJ == j) {
            board[preI][preJ].left = false;
            board[i][j].right = false;
        }

        if (preI == i && preJ - 1 == j) {
            board[preI][preJ].up = false;
            board[i][j].down = false;
        }

        if (preI == i && preJ + 1 == j) {
            board[preI][preJ].down = false;
            board[i][j].up = false;
        }

        for (int[] neighbor : neighbors) {
            if (isWithinBoard(new Vector2d(neighbor[0], neighbor[1]))
                    && !(board[neighbor[0]][neighbor[1]].visited)) {
                stack.push(new MazeSquare(i, j, neighbor[0], neighbor[1]));
                board[neighbor[0]][neighbor[1]].visited = true;
            }
        }

        return true;
    }
}
