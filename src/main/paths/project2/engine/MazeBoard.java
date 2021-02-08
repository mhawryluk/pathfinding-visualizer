package paths.project2.engine;

import java.util.List;
import java.util.Stack;

public class MazeBoard extends Board {

    private final Stack<MazeSquare> stack = new Stack<>();

    public MazeBoard(int width, int height) {
        super(width, height);
        stack.push(new MazeSquare(0, 0, 0, 1));
    }

    public boolean generateMazeStep() {
        if (stack.isEmpty()) {
            clearBoard();
            return false;
        }

        MazeSquare current = stack.pop();

        clearBoard();
        board[current.x][current.y].setState(SquareState.MAZECRAWLER);
        List<int[]> neighbors = current.getNeighbors();

        char cameFrom = current.cameFrom();

        switch (cameFrom){
            case 'l':
                board[current.cameFromX][current.cameFromY].right = false;
                board[current.x][current.y].left = false;
                break;

            case 'r':
                board[current.cameFromX][current.cameFromY].left = false;
                board[current.x][current.y].right = false;
                break;

            case 'd':
                board[current.cameFromX][current.cameFromY].up = false;
                board[current.x][current.y].down = false;
                break;

            case 'u':
                board[current.cameFromX][current.cameFromY].down = false;
                board[current.x][current.y].up = false;
                break;
        }

        for (int[] neighbor : neighbors) {
            if (isWithinBoard(new Vector2d(neighbor[0], neighbor[1]))
                    && !(board[neighbor[0]][neighbor[1]].visited)) {
                stack.push(new MazeSquare(current.x, current.y, neighbor[0], neighbor[1]));
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
