package paths.project2.engine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class MazeBoard extends Board{

    private final Stack<MazeSquare> stack = new Stack<>();

    public MazeBoard(int width, int height){
        this.width = width;
        this.height = height;

        upperRight = new Vector2d(width-1, height-1);

        board = new Square[width][height];

        for (int i = 0; i < width; i++){
            for (int j = 0; j < height; j++){
                board[i][j] = new Square(i, j);
            }
        }

        generateMaze();
    }

    public void generateMaze(){
        stack.push(new MazeSquare(0,0,1,1));
    }

    public boolean generateMazeStep() {
        if (stack.isEmpty()){
            for (Square[] squares : board) {
                for (Square square : squares) {
                    square.state = SquareState.BLANK;
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
                square.state = SquareState.BLANK;
            }
        }
        board[i][j].state = SquareState.MAZECRAWLER;

        List<int[]> neighbors = new ArrayList<>();
        neighbors.addAll(Collections.singleton(new int[]{i + 1, j}));
        neighbors.addAll(Collections.singleton(new int[]{i - 1, j}));
        neighbors.addAll(Collections.singleton(new int[]{i, j - 1}));
        neighbors.addAll(Collections.singleton(new int[]{i, j + 1}));

        Collections.shuffle(neighbors);

//        boolean deadend = true;
//
//        for (int[] neighbor: neighbors){
//            if (isWithinBoard(new Vector2d(neighbor[0], neighbor[1])) && !board[neighbor[0]][neighbor[1]].visited) {
//                deadend = false;
//                break;
//            }
//        }
//
//        if (deadend){
//            return true;
//        }


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
