package paths.project2.engine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MazeGeneratorSquare {

    public final int x;
    public final int y;
    public final int cameFromX;
    public final int cameFromY;

    public MazeGeneratorSquare(int cameFromX, int cameFromY, int x, int y) {
        this.x = x;
        this.y = y;
        this.cameFromX = cameFromX;
        this.cameFromY = cameFromY;
    }

    public char cameFrom() {
        if (cameFromX + 1 == x && cameFromY == y) return 'l';
        else if (cameFromX - 1 == x && cameFromY == y) return 'r';
        else if (cameFromX == x && cameFromY - 1 == y) return 'd';
        else if (cameFromX == x && cameFromY + 1 == y) return 'u';
        else
            throw new IllegalStateException("squares at (" + x + ", " + y + ") " + "and (" + cameFromX + ", " + cameFromY + ") are not adjacent");
    }

    public List<int[]> getNeighbors() {
        List<int[]> neighbors = new ArrayList<>();
        neighbors.addAll(Collections.singleton(new int[]{x + 1, y}));
        neighbors.addAll(Collections.singleton(new int[]{x - 1, y}));
        neighbors.addAll(Collections.singleton(new int[]{x, y - 1}));
        neighbors.addAll(Collections.singleton(new int[]{x, y + 1}));

        Collections.shuffle(neighbors);
        return neighbors;
    }
}