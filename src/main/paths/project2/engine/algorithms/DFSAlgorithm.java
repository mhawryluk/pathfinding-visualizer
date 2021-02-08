package paths.project2.engine.algorithms;

import paths.project2.engine.PathBoard;
import paths.project2.engine.Square;
import paths.project2.engine.SquareState;

import java.util.Stack;

public class DFSAlgorithm extends PathFindingAlgorithm{

    Stack<Square> stack = new Stack<>();

    public DFSAlgorithm(PathBoard board){
        super(board);
    }

    @Override
    public boolean step() {
        if (current == null){
            current = start;
            current.visited = true;
            stack.push(current);
        }

        if (ended) {
            getPath();
            return false;
        }

        if (stack.isEmpty()) {
            pathNotFound();
            return false;
        }

        do {
            current = stack.pop();
        } while (!stack.isEmpty() && current.state == SquareState.CLOSED);

        for (Square neighbor: getNeighbors(current)){
            if (!neighbor.visited){
                if (neighbor == end){
                    getPath();
                    return false;
                }
                stack.add(neighbor);
                neighbor.visited = true;
                neighbor.cameFrom = current;
            }
        }

        if (current != start) current.state = SquareState.CLOSED;
        if (! stack.isEmpty()) stack.peek().state = SquareState.OPEN;
        return true;
    }
}
