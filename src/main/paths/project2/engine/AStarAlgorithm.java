package paths.project2.engine;

import java.util.Comparator;
import java.util.PriorityQueue;

public class AStarAlgorithm extends PathFindingAlgorithm{

    private final PriorityQueue<Square> open = new PriorityQueue<>(Comparator.comparing((x) -> x.f));

    public AStarAlgorithm(PathBoard board){
        super(board);
        this.board = board;

        this.start.g = 0;
        this.start.f = h(this.start);
        open.add(this.start);
    }

    public boolean step(){

        if (current == null){
            current = start;
            start.g = 0;
            start.f = h(this.start);
            open.add(this.start);
        }

        if (ended) return false;

        if (open.isEmpty()) {
            pathNotFound();
            return false;
        }
        current = open.remove();

        if (current == end) {
            getPath();
            return false;
        }

        for (Square neighbor : getNeighbors(current)){
            double gScore = current.g + 1;
            if (gScore < neighbor.g){
                neighbor.cameFrom = current;
                neighbor.g = gScore;
                neighbor.f = gScore + h(neighbor);
                if (neighbor.state != SquareState.OPEN){
                    open.add(neighbor);
                    if (neighbor != end){
                        neighbor.state = SquareState.OPEN;
                    } else {
                        getPath();
                        return false;
                    }
                }
            }
        }

        if (current != start) {
            current.state = SquareState.CLOSED;
        }
        return true;
    }

    private double h(Square square){
        return Math.pow(square.getX() - end.getX(), 2) +
                Math.pow(square.getY() - end.getY(), 2);
    }
}
