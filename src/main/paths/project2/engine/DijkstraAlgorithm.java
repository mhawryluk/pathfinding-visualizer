package paths.project2.engine;

import java.util.Comparator;
import java.util.PriorityQueue;

public class DijkstraAlgorithm extends PathFindingAlgorithm{

    private final PriorityQueue <Square> queue = new PriorityQueue<>(Comparator.comparing((x) -> x.dist));

    public DijkstraAlgorithm(Board board){
        super(board);
    }


    public void step(){

        if (current == null){
            start.dist = 0;
            queue.add(start);
            current = start;
        }

        if (queue.isEmpty()) return;
        current = queue.remove();

        if (current == end){
            getPath();
            return;
        }

        current.visited = true;

        for (Square neighbor: getNeighbors(current)){
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
        current.state = SquareState.CLOSED;
    }

    @Override
    public String toString(){
        return "Dijkstra";
    }
}
