package paths.project2.engine;

import java.awt.*;

public enum SquareState {
    CLOSED(255, 0, 0,
            "closed"),
    OPEN(0, 255, 0,
            "open"),
    BLANK(255, 255, 255,
            "blank"),
    OBSTACLE(0, 0, 0,
            "obstacle"),
    END(128, 0, 128,
            "end"),
    START(255, 165 ,0,
            "start"),
    PATH(64, 224, 208,
            "path"),
    MAZECRAWLER(153, 153, 255,
            "maze explorer");

    public final Color color;
    public final String description;

    SquareState(int r, int g, int b, String description){
        color = new Color(r,g,b);
        this.description = description;
    }
}
