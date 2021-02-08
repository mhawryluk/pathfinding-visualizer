package paths.project2.engine.algorithms;

import paths.project2.engine.PathBoard;
import paths.project2.engine.Square;
import paths.project2.engine.SquareState;
import paths.project2.engine.Vector2d;

import java.util.Comparator;
import java.util.PriorityQueue;

public class AStarAlgorithm extends PathFindingAlgorithm {

    private final PriorityQueue<Square> openSquares = new PriorityQueue<>(Comparator.comparing((x) -> x.f));

    public AStarAlgorithm(PathBoard board) {
        super(board);

        start.g = 0;
        start.f = h(this.start);

        openSquares.add(this.start);
    }

    public boolean step() {

        if (current == null) {
            current = start;
            start.g = 0;
            start.f = h(this.start);
            openSquares.add(this.start);
        }

        if (ended) return false;

        if (openSquares.isEmpty()) {
            pathNotFound();
            return false;
        }

        current = openSquares.remove();

        if (current == end) {
            getPath();
            return false;
        }

        for (Square neighbor : getNeighbors(current)) {
            double gScore = current.g + 1;
            if (gScore < neighbor.g) {
                neighbor.cameFrom = current;
                neighbor.g = gScore;
                neighbor.f = gScore + h(neighbor);
                if (neighbor.getState() != SquareState.OPEN) {
                    openSquares.add(neighbor);
                    if (neighbor != end) {
                        neighbor.setState(SquareState.OPEN);
                    } else {
                        getPath();
                        return false;
                    }
                }
            }
        }

        if (current != start) {
            current.setState(SquareState.CLOSED);
        }
        return true;
    }

    private double h(Square square) {
        Vector2d vector = square.getPosition().subtract(end.getPosition());
        return Math.pow(vector.x, 2) +
                Math.pow(vector.y, 2);
    }
}
