package paths.project2.engine;

import java.util.Comparator;
import java.util.PriorityQueue;

public class DijkstraAlgorithm extends PathFindingAlgorithm{

    private final PriorityQueue <Square> queue = new PriorityQueue<>(Comparator.comparing((x) -> x.dist));

    public DijkstraAlgorithm(PathBoard board){
        super(board);
    }


    public boolean step(){

        if (current == null){
            start.dist = 0;
            queue.add(start);
            current = start;
        }

        if (ended) return false;

        if (queue.isEmpty()) {
            pathNotFound();
            ended = true;
            return false;
        }

        current = queue.remove();
        current.visited = true;

        for (Square neighbor: getNeighbors(current)){
            if (neighbor == end){
                end.cameFrom = current;
                getPath();
                ended = true;
                return false;
            }
            if (!neighbor.visited) {
                neighbor.state = SquareState.OPEN;
                if (neighbor.dist > current.dist + 1) {
                    neighbor.dist = current.dist + 1;
                    neighbor.cameFrom = current;
                    queue.remove(neighbor);
                    queue.add(neighbor);
                }
            }
        }
        if (current != start) current.state = SquareState.CLOSED;
        return true;
    }

    @Override
    public String toString(){
        return "Dijkstra";
    }
}
