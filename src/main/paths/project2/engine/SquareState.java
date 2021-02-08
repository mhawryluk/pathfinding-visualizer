package paths.project2.engine;

import java.awt.*;

public enum SquareState {
    START(46, 196, 182,
            "start"),
    END(255, 159, 28,
            "end"),
    OBSTACLE(53, 80, 112,
            "obstacle"),
    BLANK(190, 227, 219,
            "unvisited"),
    OPEN(234, 172, 139,
            "visited"),
    CLOSED(229, 107, 111,
            "processed"),
    PATH(0, 175, 185,
            "path"),
    MAZECRAWLER(2, 195, 154,
            "maze explorer");

    public final Color color;
    public final String description;

    SquareState(int r, int g, int b, String description) {
        color = new Color(r, g, b);
        this.description = description;
    }
}
