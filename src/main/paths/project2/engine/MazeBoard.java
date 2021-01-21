package paths.project2.engine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MazeBoard extends Board{

    public MazeBoard(int width, int height){

        System.out.println(width+ " " +height);
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
        generateMazeRec(0, 0, 1, 1);
    }

    private void generateMazeRec(int preI, int preJ, int i, int j) {
        board[i][j].visited = true;

        if (preI + 1 == i && preJ == j){
            board[preI][preJ].down = false;
            board[i][j].up = false;
        }

        if (preI - 1 == i && preJ == j){
            board[preI][preJ].up = false;
            board[i][j].down = false;
        }

        if (preI == i && preJ - 1 == j){
            board[preI][preJ].left = false;
            board[i][j].right = false;
        }

        if (preI == i && preJ + 1 == j){
            board[preI][preJ].right = false;
            board[i][j].left = false;
        }

        List<int[]> neighbors = new ArrayList<>();
        neighbors.addAll(Collections.singleton(new int[]{i + 1, j}));
        neighbors.addAll(Collections.singleton(new int[]{i - 1, j}));
        neighbors.addAll(Collections.singleton(new int[]{i, j - 1}));
        neighbors.addAll(Collections.singleton(new int[]{i, j + 1}));

        Collections.shuffle(neighbors);

        for (int[] neighbor :  neighbors){
            if (isWithinBoard(new Vector2d(neighbor[0], neighbor[1]))
                && !(board[neighbor[0]][neighbor[1]].visited)){
                generateMazeRec(i, j, neighbor[0], neighbor[1]);
            }
        }
    }
}
