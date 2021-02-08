package paths.project2.engine.algorithms;

import paths.project2.engine.PathBoard;
import paths.project2.engine.Square;
import paths.project2.engine.SquareState;

import java.util.LinkedList;
import java.util.Queue;

public class BFSAlgorithm extends PathFindingAlgorithm {

    Queue<Square> queue = new LinkedList<>();

    public BFSAlgorithm(PathBoard board) {
        super(board);
    }

    @Override
    public boolean step() {
        if (current == null) {
            current = start;
            current.visited = true;
            queue.add(current);
        }

        if (ended) {
            getPath();
            return false;
        }

        if (queue.isEmpty()) {
            pathNotFound();
            return false;
        }

        current = queue.remove();

        for (Square neighbor : getNeighbors(current)) {
            if (!neighbor.visited) {
                if (neighbor == end) {
                    getPath();
                    return false;
                }
                neighbor.state = SquareState.OPEN;
                queue.add(neighbor);
                neighbor.visited = true;
                neighbor.cameFrom = current;
            }
        }
        if (current != start) current.state = SquareState.CLOSED;
        return true;
    }
}
