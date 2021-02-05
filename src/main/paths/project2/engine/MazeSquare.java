package paths.project2.engine;

public class MazeSquare {

    public final int i;
    public final int j;
    public final int preI;
    public final int preJ;

    public MazeSquare(int preI, int preJ, int i, int j){
        this.i = i;
        this.j = j;
        this.preI = preI;
        this.preJ = preJ;
    }

    @Override
    public String toString() {
        return i + " " + j + " " + preI + " " + preJ;
    }
}
