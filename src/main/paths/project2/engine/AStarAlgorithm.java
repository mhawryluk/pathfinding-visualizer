package paths.project2.engine;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

public class AStarAlgorithm extends PathFindingAlgorithm{

    private final PriorityQueue<Square> open = new PriorityQueue<>(Comparator.comparing((x) -> x.f));


    public AStarAlgorithm(Board board){
        this.board = board;

        this.start = board.getSquareAt(0,0);
        this.start.state = SquareState.START;

        this.end = board.getSquareAt(board.width-1, board.height-1);
        this.end.state = SquareState.END;

        this.start.g = 0;
        this.start.f = h(this.start);
        open.add(this.start);
    }

    public void step(){

        if (current == null){
            current = start;
            start.g = 0;
            start.f = h(this.start);
            open.add(this.start);
        }

        if (ended) return;
        if (open.isEmpty()) return;
        current = open.remove();

        if (current == end) {
           current = current.cameFrom;
            while (current != start) {
                current.state = SquareState.PATH;
                current = current.cameFrom;
            }
            ended = true;
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
                    }
                }
            }
        }

        if (current != start) {
            current.state = SquareState.CLOSED;
        }
    }

    private ArrayList <Square> getNeighbors(Square square){
        ArrayList <Square> neighbors = new ArrayList<>();
        int x = square.getX();
        int y = square.getY();

        for (int i = x - 1; i <= x + 1; i++){
            for (int j = y - 1; j <= y + 1; j++){
                if ((i != x || j != x) && i >= 0 && j >= 0 &&
                        i < board.width && j < board.height){
                        Square neighbor = board.getSquareAt(i, j);
                        if (neighbor.state != SquareState.OBSTACLE) {
                            neighbors.add(neighbor);
                        }
                }
            }
        }
        return neighbors;
    }

    private double h(Square square){
        return Math.pow(square.getX() - end.getX(), 2) +
                Math.pow(square.getY() - end.getY(), 2);
    }
}
