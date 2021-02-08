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
        board.getSquareAt(new Vector2d(1,1)).state = SquareState.OPEN;
        Assert.assertEquals(board.getSquareAt(new Vector2d(1,1)).state, SquareState.OPEN);

        board.restart();
        Assert.assertEquals(board.getSquareAt(new Vector2d(1,1)).state, SquareState.BLANK);

        board.getSquareAt(new Vector2d(2,2)).state = SquareState.OBSTACLE;
        Assert.assertEquals(board.getSquareAt(new Vector2d(2,2)).state, SquareState.OBSTACLE);

        board.restart();
        Assert.assertEquals(board.getSquareAt(new Vector2d(2,2)).state, SquareState.OBSTACLE);
    }

    @Test
    public void testGenerateObstacles(){
        PathBoard board = new PathBoard(5,5);
        board.generateRandomObstacles(10);

        int numObsticles = 0;
        for (int i = 0; i < 5; i++){
            for (int j = 0; j < 5; j++){
                if (board.getSquareAt(new Vector2d(i, j)).state == SquareState.OBSTACLE)
                    numObsticles++;
            }
        }

        Assert.assertTrue(numObsticles > 0 && numObsticles <= 10);
    }
}
