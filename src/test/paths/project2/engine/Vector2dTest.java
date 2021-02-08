package paths.project2.engine;

import org.junit.Assert;
import org.junit.Test;

public class Vector2dTest {

    private final Vector2d vector1 = new Vector2d(-2, 2);
    private final Vector2d vector2 = new Vector2d(1, 3);
    private final Vector2d vector3 = new Vector2d(-1, 0);

    @Test
    public void testEquals() {
        Assert.assertEquals(vector1, new Vector2d(-2, 2));
        Assert.assertEquals(vector2, new Vector2d(1, 3));
        Assert.assertNotEquals(vector2, vector3);
    }

    @Test
    public void testToString() {
        Assert.assertEquals(vector1.toString(), "(-2,2)");
        Assert.assertEquals(vector2.toString(), "(1,3)");
        Assert.assertEquals(vector3.toString(), "(-1,0)");
    }

    @Test
    public void testPrecedes() {
        Assert.assertTrue(vector1.precedes(vector2));
        Assert.assertFalse(vector3.precedes(vector1));
        Assert.assertTrue((vector1.precedes(vector1)));
    }

    @Test
    public void testFollows() {
        Assert.assertTrue(vector2.follows(vector3));
        Assert.assertFalse(vector3.follows(vector1));
        Assert.assertTrue((vector2.follows(vector2)));
    }

    @Test
    public void testAdd() {
        Assert.assertEquals(vector1.add(vector2), new Vector2d(-1, 5));
        Assert.assertEquals(vector2.add(vector3), new Vector2d(0, 3));
        Assert.assertEquals(vector3.add(vector2), new Vector2d(0, 3));
    }
}