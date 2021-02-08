package paths.project2.engine.algorithms;

import org.junit.Assert;
import org.junit.Test;
import paths.project2.engine.*;

public class AlgorithmsTest {

    private PathBoard makeBoard() {
        PathBoard board = new PathBoard(4, 4);
        for (int i = 1; i < 4; i++)
            for (int j = 0; j < 4; j++)
                board.getSquareAt(new Vector2d(i, j)).setState(SquareState.OBSTACLE);

        return board;
    }

    @Test
    public void testAStar() {
        PathBoard board = makeBoard();
        AStarAlgorithm algorithm = new AStarAlgorithm(board);
        algorithm.setStartPosition(new Vector2d(0, 0));
        algorithm.setEndPosition(new Vector2d(0, 3));

        while (algorithm.step()) ;

        Assert.assertEquals(SquareState.PATH, board.getSquareAt(new Vector2d(0, 1)).getState());
        Assert.assertEquals(SquareState.PATH, board.getSquareAt(new Vector2d(0, 2)).getState());
    }

    @Test
    public void testDijkstra() {
        PathBoard board = makeBoard();
        DijkstraAlgorithm algorithm = new DijkstraAlgorithm(board);
        algorithm.setStartPosition(new Vector2d(0, 0));
        algorithm.setEndPosition(new Vector2d(0, 3));

        while (algorithm.step()) ;

        Assert.assertEquals(SquareState.PATH, board.getSquareAt(new Vector2d(0, 1)).getState());
        Assert.assertEquals(SquareState.PATH, board.getSquareAt(new Vector2d(0, 2)).getState());
    }

    @Test
    public void testBFS() {
        PathBoard board = makeBoard();
        BFSAlgorithm algorithm = new BFSAlgorithm(board);
        algorithm.setStartPosition(new Vector2d(0, 0));
        algorithm.setEndPosition(new Vector2d(0, 3));

        while (algorithm.step()) ;

        Assert.assertEquals(SquareState.PATH, board.getSquareAt(new Vector2d(0, 1)).getState());
        Assert.assertEquals(SquareState.PATH, board.getSquareAt(new Vector2d(0, 2)).getState());
    }

    @Test
    public void testDFS() {
        PathBoard board = makeBoard();
        DFSAlgorithm algorithm = new DFSAlgorithm(board);
        algorithm.setStartPosition(new Vector2d(0, 0));
        algorithm.setEndPosition(new Vector2d(0, 3));

        while (algorithm.step()) ;

        Assert.assertEquals(SquareState.PATH, board.getSquareAt(new Vector2d(0, 1)).getState());
        Assert.assertEquals(SquareState.PATH, board.getSquareAt(new Vector2d(0, 2)).getState());
    }
}
