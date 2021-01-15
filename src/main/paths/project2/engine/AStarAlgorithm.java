package paths.project2.engine;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

public class AStarAlgorithm {

    private Square current;
    private final Square start;
    private final Square end;
    private final Board board;
    private int count;
    private boolean ended = false;
    private final PriorityQueue<Square> open = new PriorityQueue<>(Comparator.comparing((x) -> x.g));


    public AStarAlgorithm(Board board, Vector2d start, Vector2d end){
        this.board = board;

        this.start = board.getSquareAt(start.x, start.y);
        this.end = board.getSquareAt(end.x, end.y);
        current = this.start;
        this.start.state = SquareState.OPEN;
        this.start.g = h(this.start, this.end);
        open.add(this.start);
    }

    public void step(){

        if (ended) return;

        System.out.println(current);
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
                neighbor.f = gScore + h(neighbor, end);
                if (neighbor.state != SquareState.OPEN){
                    count++;
                    open.add(neighbor); //(neighbor.f, count, neighbor)
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
                if (i != x || j != x){
                    if (i >= 0 && j >= 0 && i < board.width && j < board.height){
                        neighbors.add(board.getSquareAt(i, j));
                    }
                }
            }
        }
        return neighbors;
    }

    private double h(Square square, Square end){
        return Math.pow(square.getX() - end.getX(), 2) +
                Math.pow(square.getY() - end.getY(), 2);
    }
}
