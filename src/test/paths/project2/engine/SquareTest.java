package paths.project2.engine;

import org.junit.Assert;
import org.junit.Test;

public class SquareTest {

    @Test
    public void testDistance() {
        Square square = new Square(1,2);

        Assert.assertEquals(1, square.dist(new Vector2d(1,1)));
        Assert.assertEquals(2, square.dist(new Vector2d(0,0)));
        Assert.assertEquals(0, square.dist(new Vector2d(1,2)));
        Assert.assertEquals(9, square.dist(new Vector2d(10,1)));
    }

    @Test
    public void testReset() {
        Square square = new Square(0,0);
        square.visited = true;
        square.g = 15;

        square.reset();
        Assert.assertEquals(square.g, Double.POSITIVE_INFINITY, 0.0);
        Assert.assertFalse(square.visited);
    }
}
