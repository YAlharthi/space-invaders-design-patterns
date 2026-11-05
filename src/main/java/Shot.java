public class Shot extends Sprite {

    private final int H_SPACE = 6;
    private final int V_SPACE = 1;

    /* The empty shot Board holds before the first one is fired. It has no image. */
    public Shot() {
    }

    Shot(int x, int y) {
        setX(x + H_SPACE);
        setY(y - V_SPACE);
    }
}
