package paths.project2.engine;

import java.util.LinkedList;
import java.util.Queue;

public class BFSAlgorithm extends PathFindingAlgorithm{

    Queue<Square> queue = new LinkedList<>();

    public BFSAlgorithm(PathBoard board){
        super(board);
    }

    @Override
    public void step() {
        if (current == null){
            current = start;
            current.visited = true;
            queue.add(current);
        }

        if (queue == null || queue.isEmpty()){
            return;
        }

        current = queue.remove();

        for (Square neighbor: getNeighbors(current)){
            if (!neighbor.visited){
                neighbor.state = SquareState.OPEN;
                queue.add(neighbor);
                neighbor.visited = true;
                neighbor.cameFrom = current;

                if (neighbor == end){
                    getPath();
                    queue = null;
                    return;
                }
            }
        }

        current.state = SquareState.CLOSED;
    }
}
