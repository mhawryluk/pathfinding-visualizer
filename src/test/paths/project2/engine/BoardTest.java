package paths.project2.engine;

import org.junit.Assert;
import org.junit.Test;

public class BoardTest {

    @Test
    public void testBoardDimensions() {
        Board board = new PathBoard(10,10);
        Assert.assertTrue(board.isWithinBoard(new Vector2d(0,0)));
        Assert.assertTrue(board.isWithinBoard(new Vector2d(4,5)));
        Assert.assertFalse(board.isWithinBoard(new Vector2d(10,10)));
        Assert.assertFalse(board.isWithinBoard(new Vector2d(-1,3)));

        board = new MazeBoard(3,2);
        Assert.assertTrue(board.isWithinBoard(new Vector2d(0,0)));
        Assert.assertTrue(board.isWithinBoard(new Vector2d(2,1)));
        Assert.assertFalse(board.isWithinBoard(new Vector2d(3,2)));
        Assert.assertFalse(board.isWithinBoard(new Vector2d(2,-2)));
    }

    @Test
    public void testRestart() {
        PathBoard board = new PathBoard(3,3);
        board.getSquareAt(new Vector2d(1,1)).setState(SquareState.OPEN);
        Assert.assertEquals(board.getSquareAt(new Vector2d(1,1)).getState(), SquareState.OPEN);

        board.restart();
        Assert.assertEquals(board.getSquareAt(new Vector2d(1,1)).getState(), SquareState.BLANK);

        board.getSquareAt(new Vector2d(2,2)).setState(SquareState.OBSTACLE);
        Assert.assertEquals(board.getSquareAt(new Vector2d(2,2)).getState(), SquareState.OBSTACLE);

        board.restart();
        Assert.assertEquals(board.getSquareAt(new Vector2d(2,2)).getState(), SquareState.OBSTACLE);
    }

    @Test
    public void testGenerateObstacles(){
        PathBoard board = new PathBoard(5,5);
        board.generateRandomObstacles(10);

        int numObstacles = 0;
        for (int i = 0; i < 5; i++){
            for (int j = 0; j < 5; j++){
                if (board.getSquareAt(new Vector2d(i, j)).getState() == SquareState.OBSTACLE)
                    numObstacles++;
            }
        }

        Assert.assertTrue(numObstacles > 0 && numObstacles <= 10);
    }
}
