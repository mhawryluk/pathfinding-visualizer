package paths.project2.engine;

import java.awt.*;

public enum SquareState {
    CLOSED(255, 0, 0),
    OPEN(0, 255, 0),
    BLANK(255, 255, 255),
    OBSTACLE(0, 0, 0),
    END(128, 0, 128),
    START(255, 165 ,0),
    PATH(64, 224, 208),
    MAZECRAWLER(153, 153, 255);

    public final Color color;

    SquareState(int r, int g, int b){
        color = new Color(r,g,b);
    }

}
